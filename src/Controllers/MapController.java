package Controllers;

import GameMap.Map;
import Objects.GameObject;
import Objects.Mob;
import Objects.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class MapController implements Runnable{
    private Map map;
    private int sleepTimer = 1000;

    public MapController(Map map) {
        this.map = map;
    }

    @Override
    public synchronized void run() {
        try {
            Thread.currentThread().sleep(sleepTimer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while(map.gameObjects.size() > 1) {


            System.out.printf("\n****** %d *********\n", map.gameObjects.size());
            ArrayList<GameObject> mapObjects = map.gameObjects;
            Iterator<GameObject> iterator = mapObjects.iterator();
            while(iterator.hasNext()) {
                if (iterator instanceof Mob && !(iterator instanceof Player)) {
                    boolean isSameType = false;
                    ArrayList<GameObject> cellObjects = map.getCell(((Mob) iterator).cellPosition).gameObjects;


                    ArrayList<String> names = new ArrayList<>();

                    if (cellObjects.size() > 1) {
                        for (GameObject temp2 : cellObjects) {
                            if (temp2 instanceof Mob && !iterator.equals(temp2)) {
                                ((Mob) temp2).baseATK += 1;
                                isSameType = true;
                                mapObjects.remove(temp2);
                                names.add(temp2.getName());
                            }
                        }
                    }

                    if (isSameType == true) {
                        ((Mob) iterator).baseATK += 1;
                        System.out.printf("\n Клетка [ %d; %d] встретились мобы: %s ", map.getCell(((Mob) iterator).cellPosition).position.x, map.getCell(((Mob) iterator).cellPosition).position.x, ((Mob) iterator).getName());
                        System.out.println(names);
                    }
                }
            }
            try {
                Thread.currentThread().sleep(sleepTimer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Thread.currentThread().interrupt();
    }
}
