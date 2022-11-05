public class Weapon extends Equipment implements Damager{
    int attackPower;

    public Weapon(String name, int cost, int levelForKeep, int attackPower) {
        super(name, cost, levelForKeep);
        this.attackPower = attackPower;
    }

    public int getDamage() {
        return attackPower;
    }
}
