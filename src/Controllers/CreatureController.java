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

    public synchronized void FightToTheDeath (Player player, Mob mob) {
        if (player == null || mob == null ) return ;
        while (player.getHealthPoints() > 0 && mob.getHealthPoints() > 0) {
            fight((Attacker) player, mob);

            if(mob.getHealthPoints() <= 0) {
                System.out.printf("\n%s был повержен в схватке\n", mob.getName());
                player.inventor.putItemList(mob.dropLoot());
                Weapon mobWeapon = mob.getOneWeaponToHands();
                if (mobWeapon.getDamage() > player.getOneWeaponToHands().getDamage()) {
                    player.getItem(mobWeapon);
                    player.equipmentToCell();
                }
                player.rewardMoney(mob.getRewardMoney());
                mob.map.gameObjects.remove(mob);
                mob.map.getCell(mob.cellPosition).gameObjects.remove(mob);
            } else {
                fight((Attacker) mob, player);
            }
        }
    }

    @Override
    public void run() {
        while(controlCreature.getHealthPoints() > 0) {
            System.out.printf("\n****** %d *********\n", controlCreature.map.gameObjects.size());
            //if (!controlCreature.map.getCell(controlCreature.cellPosition).gameObjects.isEmpty()) {}
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
        }
        Thread.currentThread().interrupt();
    }
}
