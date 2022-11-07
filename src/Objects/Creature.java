package Objects;

import GameMap.Cell;
import GameMap.Map;
import GameMap.Position;
import Interfaces.Movable;

public class Creature extends GameObject implements Movable {
    private int healthPoints;

    public int baseATK = 0;

    private int baseDEF = 0;

    public Creature(String name, int healthPoints) {
        super(name);
        this.healthPoints = healthPoints;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    public int getHealthPoints() {
        return this.healthPoints;
    }

    public void setHealthPoints(int hp) {
        this.healthPoints = hp;
    }

    public void moveTo(Position position){
        map.getCell(this.cellPosition).removeFromCell(this);
        this.map.getCell(position).putIntoCell(this);
        this.cellPosition = position;
        System.out.printf("\n%s переместился в клетку: %d %d", this.getName(), position.x, position.y);
    }
};
