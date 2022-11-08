package Objects;

import GameMap.Position;
import Interfaces.Attacker;
import Interfaces.Damageable;
import Interfaces.Damager;
import Interfaces.Lootable;
import Inventory.Inventory;
import Inventory.Items.Equipment;
import Inventory.Items.EquipmentCell;
import Inventory.Items.Item;
import Inventory.Items.Weapon;
import Objects.Creature;

import java.util.ArrayList;

import static GameControl.GameLogic.calculateDPS;

public class Mob extends Creature implements Damageable, Attacker, Lootable {
    private ArrayList<EquipmentCell> equipmentListWeapons = new ArrayList<EquipmentCell>();
    private ArrayList<EquipmentCell> equipmentListArmor = new ArrayList<EquipmentCell>();
    int expPoints;
    int level;
    int attackPower;
    int levelDefense;
    public int APS = 1;
    int rewardMoney;

    Inventory inventor = new Inventory();
    public Mob(String name, int healthPoints, int levelDefense, int attackPower, int expPoints, int level, int rewardMoney) {
        super(name, healthPoints);
        this.expPoints = expPoints;
        this.level = level;
        this.attackPower = attackPower;
        this.levelDefense = levelDefense;
        this.rewardMoney = rewardMoney;
    }

    public int getRewardMoney() {
        return this.rewardMoney;
    }
    public void getAllInfo() {
        System.out.println("\n");
        System.out.printf("Name of mob: %s\n Id: %d \n Power of attack: %d; Health Points: %d;\n Level of Defense: %d;",
                this.name, this.getID(), this.attackPower, this.getHealthPoints(), this.levelDefense, this.level, this.expPoints);
        inventor.printInventory();
        printEquipmentCells();
        System.out.println("\n----That's all-----\n");
    }

    public void printEquipmentCells() {
        System.out.printf("Classes.Inventory.Items.Equipment Cells:\n");
        for(int i = 0; i < equipmentListWeapons.size(); ++i) {
            System.out.printf("%s ", equipmentListWeapons.get(i).getName());
            if (equipmentListWeapons.get(i).getCell() instanceof Weapon) {
                System.out.printf("%d", ((Weapon) equipmentListWeapons.get(i).getCell()).getDamage());
            }
        }
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
    public int getAttackPower() {
        return this.attackPower;
    }

    public Mob() {
        super(null, 0);
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
    public <T extends Item> void getEquipment(T equipment) {
        this.inventor.putOneItem(equipment);
    }
    @Override
    public ArrayList<Item> dropLoot() {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList = this.inventor.getItemList();
        this.inventor.removeItemList();
        return itemList;
    }

    public EquipmentCell getMobWeapon() {
        return new EquipmentCell(getOneWeaponToHands());
    }

    public ArrayList<EquipmentCell> getMobArmor() {
        return equipmentListArmor;
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
        System.out.printf(" наносит урон %d мобу: %s %d", damage, this.getName(), this.getID());
    }

};
