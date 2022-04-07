package aquarium.entities.decorations;

public abstract class BaseDecoration implements Decoration{

    public BaseDecoration(int comfort, double price) {
        this.comfort = comfort;
        this.price = price;
    }

    private int comfort;
    private double price;

    @Override
    public int getComfort() {
        return this.comfort;
    }

    @Override
    public double getPrice() {
        return this.price;
    }
}
