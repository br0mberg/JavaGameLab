package Objects;

public class Creature extends GameObject {
    private int healthPoints;
    public Creature(String name, int healthPoints) {
        super(name);
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public int setHealthPoints(int hp) {
        this.healthPoints = hp;
    }
};
