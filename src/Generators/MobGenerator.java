package Generators;

import Inventory.Items.Equipment;
import Inventory.Items.Weapon;
import Objects.Mob;

import java.util.Random;

public class MobGenerator {
    private static final String[] mobModels = new String[] {"Chervyak", "Jopik", "Krip", "Rubaka",
            "Sonic", "John Snoy", "Vincent", "Vonychka", "Kychmen", "Monkey", "IronMan", "Bayden",
            "Trump", "EgorKrid", "Bill", "Ryzvelt"};

    public static Mob getRandomMob() {
        Random rand = new Random();
        Mob newMob = new Mob(mobModels[rand.nextInt(15)], rand.nextInt(50), rand.nextInt(50),
                rand.nextInt(50), rand.nextInt(50), rand.nextInt(50), rand.nextInt(50));
        return newMob;
    }
}
