package Objects;

import GameMap.Cell;
import GameMap.Map;
import GameMap.Position;

abstract public class GameObject {
    public Map map;
    public Position cellPosition;
    String name;
    private static int nextId = 0;
    private final int identifCode;
    public GameObject(String name) {
        this.name = name;
        this.identifCode = ++nextId;
    }

    public void setCellPosition(Cell cellPos) {
        this.cellPosition = cellPos.position;
        cellPos.gameObjects.add(this);
    }
    public int getID() {
        return identifCode;
    }

    public String getName() {
        return this.name;
    }
};
