package GameControl;

import Interfaces.Attacker;
import Interfaces.Damageable;

public class GameLogic {
    public static void fight(Attacker attacker, Damageable target) {
        attacker.attack(target);
    }
    public static int calculateDPS(int attackPower, int weaponDamage, int APS) {
        return attackPower * weaponDamage * APS;
    }
}
