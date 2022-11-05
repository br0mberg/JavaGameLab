class Item extends GameObject{
    int cost;
    int levelForKeep;
    public Item(String name, int cost, int levelForKeep) {
        super(name);
        this.cost = cost;
        this.levelForKeep = levelForKeep;
    }

    public String getName() {
        return this.name;
    }
    public int getLevelForKeep() {
        return this.levelForKeep;
    }

    public void getAllInfo() {
        System.out.println("\n");
        System.out.printf("Name of Item: %s\n Id: %d\n Cost: %d;\n Level for keep: %d;",
                this.name, this.getID(),this.cost, this.levelForKeep);
    }

};
