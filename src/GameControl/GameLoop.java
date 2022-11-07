package GameControl;

import Controllers.CreatureController;
import Controllers.MapController;
import GameMap.Map;
import Generators.ItemGenerator;
import Generators.MobGenerator;
import Interfaces.Attacker;
import Inventory.Items.Equipment;
import Inventory.Items.Item;
import Inventory.Items.Weapon;
import Objects.Mob;
import Objects.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameLoop {
    private ItemGenerator itemGenerator = new ItemGenerator();
    private MobGenerator mobGenerator = new MobGenerator();

    private Map currMap;
    private GameLogic baseLogic = new GameLogic();

    public void getFightResult(Player player) {
        if(player.getHealthPoints() <= 0) {
            System.out.printf("Main player %s is die by group of mobs", player.getName());
        } else {
            System.out.printf("Main player %s is win and killed group of mobs", player.getName());
        }
    }


    public void start() {
        Map map = new Map(2, 2);
        this.currMap = map;
        Random rand = new Random();

        Player Elena = new Player("Elena",rand.nextInt(1000) + 500, rand.nextInt(50), rand.nextInt(50),rand.nextInt(50), rand.nextInt(50));
        Elena.putOneEquipment(itemGenerator.getRandomWeaponEquipment());
        this.currMap.setObjectOnMap(Elena);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new CreatureController(Elena));


        for(int i = 0; i < rand.nextInt(2) + 3; ++i) {
            Mob newMob = mobGenerator.getRandomMob();
            newMob.getEquipment(itemGenerator.getRandomWeaponEquipment());
            this.currMap.setObjectOnMap(newMob);
            executorService.execute(new CreatureController(newMob));
        }
        executorService.execute(new MapController(currMap));
        executorService.shutdown();

        try {
            if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                if (Elena.getHealthPoints() > 0)
                    System.out.printf("\n%s win on map all enemy", Elena.getName());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
};
