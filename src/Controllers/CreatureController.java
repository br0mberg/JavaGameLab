package Controllers;

import GameMap.Position;
import Interfaces.Attacker;
import Inventory.Items.Weapon;
import Objects.Creature;
import Objects.Mob;
import Objects.Player;

import static GameControl.GameLogic.fight;

public class CreatureController<T extends Creature> implements Runnable{
    private T controlCreature;
    private int sleepTimer = 1000;

    public CreatureController(T controlCreature) {
        this.controlCreature = controlCreature;
    }

    public synchronized void MobDeathOperationMap(Mob mob) {
        if (mob.getHealthPoints() <= 0) {
            System.out.printf("\n%s был повержен в схватке\n", mob.getName());
            mob.map.gameObjects.remove(mob);
            mob.map.getCell(mob.cellPosition).gameObjects.remove(mob);
        }
    }

    public synchronized void PlayerDeath(Player player) {
        if (player.getHealthPoints() <= 0) {
            System.out.printf("\n%s был повержен в схватке\n", player.getName());
            player.map.gameObjects.remove(player);
            player.map.getCell(player.cellPosition).gameObjects.remove(player);
            player.map.setPlayerOnMap(false);
        }
    }

    public void FightToTheDeath (Player player, Mob mob) {
        if (player == null || mob == null ) return ;
        while (player.getHealthPoints() > 0 && mob.getHealthPoints() > 0) {
            synchronized (mob.map.gameObjects) {
                fight((Attacker) player, mob);

                if (mob.getHealthPoints() <= 0) {
                    MobDeathOperationMap(mob);
                    player.inventor.putItemList(mob.dropLoot());
                    Weapon mobWeapon = mob.getOneWeaponToHands();
                    int mobDamage = 0;
                    if (mobWeapon != null) mobDamage = mobWeapon.getDamage();
                    if (mobDamage > player.getOneWeaponToHands().getDamage()) {
                        player.getItem(mobWeapon);
                        mob.removeEquipmentList();
                        player.equipmentToCell();
                    }
                    player.rewardMoney(mob.getRewardMoney());
                } else {
                    fight((Attacker) mob, player);
                    if (player.getHealthPoints() <= 0) {
                        PlayerDeath(player);
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while(controlCreature.getHealthPoints() > 0 && !Thread.currentThread().isInterrupted() && controlCreature.map.isPlayerOnMap()) {
            if(controlCreature instanceof Player) {
                FightToTheDeath((Player) controlCreature,
                        controlCreature.map.getCell(controlCreature.cellPosition).getEnemyForFight());
            }

            Position newPos = controlCreature.map.getCell(controlCreature.cellPosition).getCellMoveNextRandom().position;
            if (newPos != null) controlCreature.moveTo(newPos);

            try {
                Thread.currentThread().sleep(sleepTimer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //System.out.printf("\n*****%d****\n", controlCreature.map.gameObjects.size());
            if (controlCreature.map.gameObjects.size() < 2) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
