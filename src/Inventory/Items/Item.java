package Inventory.Items;

import Objects.GameObject;

public class Item extends GameObject {
    int cost;
    int levelForKeep;
    public Item(String name, int cost, int levelForKeep) {
        super(name);
        this.cost = cost;
        this.levelForKeep = levelForKeep;
    }

    public String getName() {
        return this.getName();
    }
    public int getLevelForKeep() {
        return this.levelForKeep;
    }

    public void getAllInfo() {
        System.out.println("\n");
        System.out.printf("Name of Inventory.Items.Item: %s\n Id: %d\n Cost: %d;\n Level for keep: %d;",
                this.getName(), this.getID(),this.cost, this.levelForKeep);
    }

};
