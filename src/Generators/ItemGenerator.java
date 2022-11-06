package Generators;

import Inventory.Items.Equipment;
import Inventory.Items.Item;
import Inventory.Items.Weapon;

import java.util.Random;

public class ItemGenerator {

    private static final String[] weaponModels = {"Ak47", "M4A4", "M4A1-S", "Kar98k", "SCAR-L", "Beryl-79"};


    private static final String[] armorModels = {"Chest", "Helmet", "leggings"};

    public static Weapon getRandomWeaponEquipment() {
        Random rand = new Random();
        Weapon newWeapon = new Weapon(weaponModels[rand.nextInt(5)], rand.nextInt(50), rand.nextInt(50), rand.nextInt(50));
        return newWeapon;
    }
}