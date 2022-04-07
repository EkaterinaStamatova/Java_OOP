package garage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GarageTests {
    private Garage garage;
    private Car car;

    private static final String BRAND = "Mercedes";
    private static final int MAX_SPEED = 10;
    private static final double PRICE = 100;


    @Before
    public void setUp(){
        this.garage = new Garage();
        this.car = new Car(BRAND,MAX_SPEED,PRICE);
        this.garage.addCar(car);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testShouldThrowExceptionForTryingToModifyUnmodifiableCollection() {
        garage.getCars().clear();
    }

    @Test
    public void testShouldFindCarWithMaxSpeed(){
        Car testCarOne = new Car("NewBrand", MAX_SPEED, 5);
        this.garage.addCar(testCarOne);

        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(car);
        expectedCars.add(testCarOne);

        List<Car> actualCars = new ArrayList<>();
        actualCars =  this.garage.findAllCarsWithMaxSpeedAbove(5);

        Assert.assertEquals(expectedCars,actualCars);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFailForAddingNullCar(){
        garage.addCar(null);
    }

    @Test
    public void testGetsTheMostExpensiveCar(){
        Car expectedCar = this.car;
        Car actualCar = this.garage.getTheMostExpensiveCar();

        Assert.assertEquals(expectedCar,actualCar);
    }

    @Test
    public void testShouldGetCarByBrandCorrectly() {
        List<Car> actualCars = this.garage.findAllCarsByBrand(BRAND);
        Car outputCar = actualCars.get(0);
        Assert.assertEquals(this.car, outputCar);
    }




}