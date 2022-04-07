package restaurant.core;

import restaurant.common.ExceptionMessages;
import restaurant.common.OutputMessages;
import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.Fresh;
import restaurant.entities.drinks.Smoothie;
import restaurant.entities.healthyFoods.Salad;
import restaurant.entities.healthyFoods.VeganBiscuits;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.tables.InGarden;
import restaurant.entities.tables.Indoors;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.*;

import static restaurant.common.ExceptionMessages.*;
import static restaurant.common.OutputMessages.*;

public class ControllerImpl implements Controller {

    private BeverageRepository<Beverages> beverageRepository;
    private HealthFoodRepository<HealthyFood> healthFoodRepository;
    private TableRepository<Table> tableRepository;

    private double totalMoney;

    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository, BeverageRepository<Beverages> beverageRepository, TableRepository<Table> tableRepository) {
        this.healthFoodRepository = healthFoodRepository;
        this.beverageRepository = beverageRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        HealthyFood healthyFood;
        switch (type){
            case "Salad":
                healthyFood = new Salad(name, price);
                break;
            case "VeganBiscuits":
                healthyFood = new VeganBiscuits(name, price);
                break;
            default:
                healthyFood = null;
                break;
        }

        HealthyFood previousHealthyFood = this.healthFoodRepository.foodByName(name);
        if(previousHealthyFood!= null){
            throw new IllegalArgumentException(String.format(FOOD_EXIST, name));
        }

        this.healthFoodRepository.add(healthyFood);
        return String.format(OutputMessages.FOOD_ADDED, name);

    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name){
        Beverages beverage;
        switch (type){
            case "Smoothie":
                beverage = new Smoothie(name, counter, brand);
                break;
            case "Fresh":
                beverage = new Fresh(name, counter, brand);
                break;
            default:
                beverage = null;
                break;
        }

        Beverages previousBeverage = this.beverageRepository.beverageByName(name,brand);
        if(previousBeverage!= null){
            throw new IllegalArgumentException(String.format(BEVERAGE_EXIST, name));
        }

        this.beverageRepository.add(beverage);
        return String.format(OutputMessages.BEVERAGE_ADDED, type,brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table table;

        switch (type){
            case "Indoors":
                table = new Indoors(tableNumber,capacity);
                break;
            case "InGarden":
                table = new InGarden(tableNumber,capacity);
                break;
            default:
                table = null;
                break;
        }

        Table previousTable = this.tableRepository.byNumber(capacity);
        if(previousTable!=null){
            throw new IllegalArgumentException(String.format(TABLE_EXIST, tableNumber));
        }
        this.tableRepository.add(table);
        return String.format(OutputMessages.TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserve(int numberOfPeople) {
        Table suitableTable = this.tableRepository
                .getAllEntities()
                .stream()
                .filter(table -> !table.isReservedTable() &&
                        table.getSize()>= numberOfPeople)
                .findFirst()
                .orElse(null);

        if (suitableTable == null) {
            return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }

        suitableTable.reserve(numberOfPeople);
        return String.format(TABLE_RESERVED, suitableTable.getTableNumber(), numberOfPeople);
    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {
        Table existingTable = this.tableRepository.byNumber(tableNumber);
        if(existingTable==null){
            return String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
        }
        HealthyFood healthyFood = this.healthFoodRepository.foodByName(healthyFoodName);
        if(healthyFood == null){
            return String.format(OutputMessages.NONE_EXISTENT_FOOD, healthyFoodName);
        }

        existingTable.orderHealthy(healthyFood);
        return String.format(BEVERAGE_ORDER_SUCCESSFUL, healthyFoodName,tableNumber);
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {
        Table table = this.tableRepository.byNumber(tableNumber);

        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }

        Beverages beverages = this.beverageRepository.beverageByName(name, brand);

        if (beverages == null) {
            return String.format(NON_EXISTENT_DRINK, name, brand);
        }

        table.orderBeverages(beverages);
        return String.format(BEVERAGE_ORDER_SUCCESSFUL, name, tableNumber);
    }

    @Override
    public String closedBill(int tableNumber) {
        Table tableToCheck = this.tableRepository.byNumber(tableNumber);
        double tableBill = tableToCheck.bill();
        tableToCheck.clear();
        totalMoney+=tableBill;
        return String.format(BILL, tableNumber, tableBill);
    }


    @Override
    public String totalMoney() {
        return String.format(TOTAL_MONEY, totalMoney);
    }
}
