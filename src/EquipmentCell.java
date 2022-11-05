public class EquipmentCell<T extends Equipment>{
    private T cell;
    public EquipmentCell(T cell) {
        this.cell = cell;
    }

    public T getCell() {
        return cell;
    }
    public void setCell(T cell) {
        this.cell = cell;
    }
    public String getName() {
        return this.cell.name;
    }

}
