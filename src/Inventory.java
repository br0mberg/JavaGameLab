import java.util.ArrayList;

public class Inventory<T extends Item> {
    private ArrayList<T> itemList = new ArrayList<>();

    public void setItemList(ArrayList<T> itemList) {
        this.itemList = itemList;
    }

    public int sizeItemList() {
        return this.itemList.size();
    }

    public ArrayList<T> getItemList() {
        return itemList;
    }

    public void putOneItem(T item) {
        itemList.add(item);
    }


    public void printInventory() {
        System.out.println("\nInventory:");
        for (int i = 0; i < this.itemList.size(); ++i) {
            int count = i + 1;
            System.out.printf("%d): %s\n", count, this.itemList.get(i).getName());
        }
    }

    public void putItemList(ArrayList<T> dropLoot) {
        int i = 0;
        while (dropLoot.size() != 0) {
            this.putOneItem(dropLoot.get(i));
            ++i;
        }
    }

    public void putEquipmentList(ArrayList<EquipmentCell> dropLoot) {
        for (int i = 0; i < dropLoot.size(); ++i) {
            this.putOneItem((T) dropLoot.get(i).getCell());
        }
    }

    public void removeItemList() {
        this.itemList.clear();
    }
}
