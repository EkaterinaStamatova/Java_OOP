package aquarium.entities.fish;

import aquarium.common.ExceptionMessages;

public class BaseFish implements Fish{
    private String name;
    private String species;

    public void setSize(int size) {
        this.size = size;
    }

    private int size;
    private double price;

    private static final int INCREASED_SIZE_WHEN_EATING = 5;

    public BaseFish(String name, String species, double price) {
        this.setName(name);
        this.setSpecies(species);
        this.setPrice(price);
    }

    public void setSpecies(String species) {
        if(species == null || species.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.SPECIES_NAME_NULL_OR_EMPTY);
        }
        this.species = species;
    }

    public void setPrice(double price) {
        if(price<0){
            throw new IllegalArgumentException(ExceptionMessages.FISH_PRICE_BELOW_OR_EQUAL_ZERO);
        }
        this.price = price;
    }

    @Override
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.FISH_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public void eat() {
       this.size += INCREASED_SIZE_WHEN_EATING;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPrice() {
        return this.price;
    }
}
