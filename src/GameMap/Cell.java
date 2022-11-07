package GameMap;

import Objects.GameObject;
import Objects.Mob;

import java.util.ArrayList;
import java.util.Random;

import static GameControl.GameLogic.calculateDPS;

public class Cell {

    private Map linkedMap;

    public Position position;

    public ArrayList<GameObject> gameObjects;

    public boolean isEmpty(){
        return this.gameObjects.isEmpty();
    }

    public ArrayList<GameObject> getFromCell(){
        try {
            if (!isEmpty()) {
                return gameObjects;
            }
            else {
                return null;
            }
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public void removeFromCell(GameObject gameObject){
        this.gameObjects.remove(gameObject);
        this.linkedMap.gameObjects.remove(gameObject);
    }

    public void clear(){
        gameObjects.clear();
    }

    public void putIntoCell(GameObject gameObject){
        gameObjects.add(gameObject);
        linkedMap.gameObjects.add(gameObject);
    }

    public Cell(Position position){
        this.position = position;
        gameObjects = new ArrayList<>();
    }

    public Cell(Position position, Map map){
        this(position);
        this.linkedMap = map;
        gameObjects = new ArrayList<>();
    }

    public Cell(Map map){
        this(new Position(), map);
    }

    public ArrayList<Cell> getCellsAround(){
        ArrayList<Cell> cells = new ArrayList<>();
        for(Position direction : Position.AroundPositions){
            Position newPos = this.position.getRelative(direction);
            if (newPos.x >= 0 && newPos.y >= 0 && newPos.x < linkedMap.width && newPos.y < linkedMap.height ) {
                cells.add(linkedMap.getCell(newPos));
            }
        }
        return cells;
    }

    public synchronized Mob getEnemyForFight() {
        int maxAttackPower = 0;
        int iter = -1;
        for(int i = 0; i< gameObjects.size(); ++i) {
            GameObject tempObject = gameObjects.get(i);
            if(tempObject instanceof Mob) {
                int tempPower = calculateDPS(((Mob) tempObject).getAttackPower(),
                        ((Mob) tempObject).getOneWeaponToHands().getDamage(), ((Mob) tempObject).APS);
                if(maxAttackPower < tempPower) {
                    maxAttackPower = tempPower;
                    iter = i;
                }
            }
        }
        if (iter >= 0) return (Mob) gameObjects.get(iter);
        return null;
    }
    public Cell getCellMoveNextRandom() {
        Random rand = new Random();
        ArrayList<Cell> cells = getCellsAround();
        return cells.get(rand.nextInt(cells.size()));
    }
}