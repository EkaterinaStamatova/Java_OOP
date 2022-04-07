package catHouse.core;

import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.BaseToy;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static catHouse.common.ExceptionMessages.*;
import static catHouse.common.ConstantMessages.*;

public class ControllerImpl implements Controller{
    private ToyRepository toys;
    private Map<String, House> houses;

    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new LinkedHashMap<>();
    }

    @Override
    public String addHouse(String type, String name) {
        House house;
        switch (type){
            case "ShortHouse":
                house = new ShortHouse(name);
                break;
            case "LongHouse":
                house = new LongHouse(name);
                break;
            default:
                throw new IllegalArgumentException(INVALID_HOUSE_TYPE);
        }
        this.houses.put(name, house);
        return String.format(SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {
       Toy toy;
       switch (type){
           case "Ball":
               toy = new Ball();
               break;
           case "Mouse":
               toy = new Mouse();
               break;
           default:
               throw new IllegalArgumentException(INVALID_TOY_TYPE);
       }
       this.toys.buyToy(toy);
        return String.format(SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        House house = this.houses.get(houseName);
        Toy toy = this.toys.findFirst(toyType);

        if(toy == null){
            throw new IllegalArgumentException(String.format(NO_TOY_FOUND,toyType));
        }

        house.buyToy(toy);
        this.toys.removeToy(toy);

        return String.format(SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType,houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
       Cat cat;
       switch (catType){
           case "ShorthairCat":
               cat = new ShorthairCat(catName,catBreed, price);
               break;
           case "LonghairCat":
               cat = new LonghairCat(catName,catBreed,price);
               break;
           default:
               throw new IllegalArgumentException(INVALID_CAT_TYPE);
       }
       boolean canLive = checkIfSuitable(houseName,catType);
       if(canLive){
           this.houses.get(houseName).addCat(cat);
           return String.format(SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType,houseName);
       }else{
           return String.format(UNSUITABLE_HOUSE);
       }

    }

    @Override
    public String feedingCat(String houseName) {
        this.houses.get(houseName).feeding();
        return String.format(FEEDING_CAT, this.houses.get(houseName).getCats().size());
    }

    @Override
    public String sumOfAll(String houseName) {
        double totalSum = getCatsTotalPrice(houseName) + getToysTotalPrice(houseName);
        return String.format(VALUE_HOUSE, houseName, totalSum);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();

        this.houses.values()
                .forEach(house -> sb.append(house.getStatistics()).append(System.lineSeparator()));
        return sb.toString().trim();
    }

    private boolean checkIfSuitable(String houseName, String catBreed) {
        return (this.houses.get(houseName).getClass().getSimpleName().equals("LongHouse"))
                && (catBreed.equals("LonghairCat"))
                || (this.houses.get(houseName).getClass().getSimpleName().equals("ShortHouse"))
                && (catBreed.equals("ShorthairCat"));
    }

    private double getCatsTotalPrice(String houseName){
        double sum = this.houses.get(houseName).getCats().stream()
                .mapToDouble(Cat::getPrice).sum();
        return sum;
    }

    private double getToysTotalPrice(String houseName){
        double sum = this.houses.get(houseName).getToys().stream()
                .mapToDouble(Toy::getPrice).sum();
        return sum;
    }
}
