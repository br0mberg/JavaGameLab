package GameMap;

import Objects.GameObject;
import Objects.Player;

import java.util.ArrayList;
import java.util.Random;

public class Map<T extends GameObject> {

    public int width;

    public int height;

    ArrayList<Cell> cells;

    public ArrayList<T> gameObjects;
    private boolean PlayerOnMap;

    public Cell getCell(int x, int y){
        for(int i = 0; i < cells.size(); ++i) {
            if(cells.get(i).position.x == x && cells.get(i).position.y == y) {
                return cells.get(i);
            }
        }
        return null;
    }

    public void setObjectOnMap(T gameObject) {
        if (gameObject instanceof Player) this.PlayerOnMap = true;
        Random rand = new Random();
        gameObject.map = this;
        Cell newCell = getCells().get(rand.nextInt(width * height));
        newCell.putIntoCell(gameObject);
        gameObject.cellPosition = newCell.position;
    }
    public Cell getCell(Position position){
        return getCell(position.x, position.y);
    }

    public ArrayList<Cell> getCells(){
        return cells;
    }

    private void makeCells(){
        cells = new ArrayList<>();
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells.add(new Cell(new Position(x, y),this));
            }
        }
    }

    public void ClearCells(){
        makeCells();
    }

    public void setCells(ArrayList<Cell> cells){
        this.cells = cells;
    }

    public ArrayList<T> getObjectsSet(){
        return gameObjects;
    }

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        makeCells();
        this.gameObjects = new ArrayList<>();
    }

    public boolean isPlayerOnMap() {
        return PlayerOnMap;
    }

    public void setPlayerOnMap(boolean playerOnMap) {
        PlayerOnMap = playerOnMap;
    }
}
