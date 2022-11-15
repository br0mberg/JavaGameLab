package Controllers;

import GameMap.Cell;
import GameMap.Map;
import GameMap.Position;
import Objects.GameObject;
import Objects.Mob;
import Objects.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class MapController<T extends GameObject> implements Runnable{
    private Map map;
    private int sleepTimer = 1000;

    public MapController(Map map) {
        this.map = map;
    }

    @Override
    public void run() {
        boolean runnable = true;
        while(runnable) {
            for(int i = 0; i < map.getCells().size(); ++i) {
                for(int x = 0; x < map.width; x++) {
                    for (int y = 0; y < map.height; y++) {
                        ArrayList<T> objects = map.getCell(new Position(x, y)).gameObjects;
                        ArrayList<Mob> mobs = new ArrayList<>();
                        if (objects.size() > 1) {
                            for( T temp : objects) {
                                if(temp instanceof Mob) {
                                    mobs.add((Mob) temp);
                                }
                            }
                        }
                        if (mobs.size() > 1) {
                            ArrayList<String> names = new ArrayList<>();
                            for (Mob temp : mobs) {
                                temp.baseATK += 1;
                                names.add(temp.getName());
                            }
                            System.out.printf("\nКлетка [%d;%d] встретились: %s", x, y, names.toString());
                        }
                    }
                }
            }
            try {
                Thread.currentThread().sleep(sleepTimer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
            if (map.gameObjects.size() < 2) runnable = false;
        }
        Thread.currentThread().interrupt();
    }
}
