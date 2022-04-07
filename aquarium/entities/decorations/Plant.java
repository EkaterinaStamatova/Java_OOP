package aquarium.entities.decorations;

public class Plant extends BaseDecoration{
    public static final int PLANT_COMFORT = 5;
    public static final double PLANT_PRICE = 10;

    public Plant() {
        super(PLANT_COMFORT, PLANT_PRICE);
    }
}
