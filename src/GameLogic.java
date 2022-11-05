import Interfaces.Attacker;
import Interfaces.Damageable;

public class GameLogic {
    public static void fight(Attacker attacker, Damageable target) {
        attacker.attack(target);
    }
}
