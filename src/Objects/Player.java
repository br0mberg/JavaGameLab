package Objects;

import Interfaces.Attacker;
import Interfaces.Damageable;
import Interfaces.Damager;
import Inventory.Inventory;
import Inventory.Items.Equipment;
import Inventory.Items.EquipmentCell;
import Inventory.Items.Item;
import Inventory.Items.Weapon;
import Objects.Creature;

import java.util.ArrayList;

import static GameControl.GameLogic.calculateDPS;

public class Player extends Creature implements Damageable, Attacker {
    private ArrayList<EquipmentCell> equipmentListWeapons = new ArrayList<EquipmentCell>();
    private ArrayList<EquipmentCell> equipmentListArmor = new ArrayList<EquipmentCell>();
    int attackPower;
    int level;
    int levelDefense;
    int money;
    int APS = 1;
    public Inventory inventor = new Inventory();

    public Player() {
        super(null, 0);
    }

    public Player(String name, int healthPoints, int levelDefense, int attackPower, int level, int money) {
        super(name, healthPoints);
        this.attackPower = attackPower;
        this.level = level;
        this.levelDefense = levelDefense;
        this.money = money;
    }

    public void getAllInfo() {
        System.out.println("\n");
        System.out.printf("Name of opponent: %s\n ID: %d \n Power of attack: %d; Health Points: %d;\n Level of Defense: %d; Money: %d;",
                this.name, this.getID(), this.attackPower, this.getHealthPoints(), this.levelDefense, this.money);
        inventor.printInventory();
        this.printEquipmentCells();
        System.out.println("\n----That's all-----\n");
    }
    public int getAttackPower() {
        return this.attackPower;
    }

    public void setEquipmentListWeapons(ArrayList<EquipmentCell> equipmentListWeapons) {
        this.equipmentListWeapons = equipmentListWeapons;
    }
    public int sizeEquipList() {
        return this.equipmentListWeapons.size();
    }
    public void putOneEquipment(Equipment cell) {
        EquipmentCell newCell = new EquipmentCell(cell);
        if (cell instanceof Weapon) {
            equipmentListWeapons.add(newCell);
        }
        else {
            equipmentListArmor.add(newCell);
        }
    }
    public ArrayList<EquipmentCell> getEquipmentListWeapons() {
        return equipmentListWeapons;
    }

    public <T extends Item> void equipmentToCell() {
        ArrayList<T> ItemList = this.inventor.getItemList();
        for (int i = 0; i < ItemList.size(); ++i) {
            if(ItemList.get(i) instanceof Equipment) {
                putOneEquipment((Equipment) ItemList.get(i));
                this.inventor.getItemList().remove(i);
            }
        }
    }

    public Weapon getOneWeaponToHands() {
        this.equipmentToCell();
        int iterWithMaxDamage = 0;
        int maxDamage = 0;
        if (equipmentListWeapons.size() == 0) return null;
        else {
            for(int i = 1; i < equipmentListWeapons.size(); ++i) {
                if (equipmentListWeapons.get(i).getCell() instanceof Weapon) {
                    int currDamage = ((Weapon) equipmentListWeapons.get(i).getCell()).getDamage();
                    if(currDamage >= maxDamage) {
                        iterWithMaxDamage = i;
                        maxDamage = currDamage;
                    }
                }
            }
            return (Weapon) equipmentListWeapons.get(iterWithMaxDamage).getCell();
        }
    }
    public void removeEquipmentList() {
        this.equipmentListWeapons.clear();
    }

    public void checkLevelItem(int levelForKeep){
        if (this.level >= levelForKeep){
            System.out.printf("Person %s can keep the damager", this.name);
        }
        else {
            System.out.printf("Person %s can't keep the damager", this.name);
        }
    }

    public void rewardMoney(int rewardMoney) {
        this.money += rewardMoney;
    }
    public void getItem(Equipment equipment) {
        this.inventor.putOneItem(equipment);
    }
    public void printEquipmentCells() {
        System.out.printf("Classes.Inventory.Items.Equipment Cells:\n");
        for(int i = 0; i < equipmentListWeapons.size(); ++i) {
            System.out.printf("%s", equipmentListWeapons.get(i).getName());
        }
    }
    @Override
    public synchronized void attack(Damageable target) {
        System.out.printf("\n%s %d %s", this.getClass(), this.getID(), this.getName());
        int damage = calculateDPS(this.getAttackPower(), getOneWeaponToHands().getDamage(), this.APS);
        Weapon newDamager = new Weapon("", 0, 0, damage);
        target.getHit((Damager) newDamager);
    }

    @Override
    public synchronized void getHit(Damager damager) {
        int damage = damager.getDamage();
        this.setHealthPoints(this.getHealthPoints() - damage);
        System.out.printf(" наносит урон %d игроку %s %d", damage, this.getName(), this.getID());
    }
};
