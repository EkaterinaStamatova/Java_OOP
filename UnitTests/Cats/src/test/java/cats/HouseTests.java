package cats;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HouseTests {
    private House house;
    private Cat cat;

    private static final String NAME = "Kati";
    private static final String NONE_EXISTING_NAME = "Lili";

    private static final int CAPACITY = 1;

    @Before
    public void setUp(){
        this.house = new House(NAME,CAPACITY);
        this.cat = new Cat(NAME);
        this.house.addCat(cat);
    }

    @Test(expected = NullPointerException.class)
    public void testShouldFailForNullName(){
        House testHouse = new House(null, CAPACITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFailForZeroCapacity(){
        House testHouse = new House(NAME, -1);
    }

    @Test
    public void testShouldGetCorrectCatCollectionSize(){
        int actualSize = this.house.getCount();
        int expectedSize = 1;
        Assert.assertEquals(expectedSize,actualSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFailWhenHouseIsFull(){
        this.house.addCat(cat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFailForNoneExistingCat(){
        this.house.removeCat(NONE_EXISTING_NAME);
    }

    @Test
    public void testShouldRemoveCatCorrectly(){
        Cat testCat = new Cat(NAME);
        House testHouse = new House(NAME,CAPACITY);

        testHouse.addCat(testCat);
        testHouse.removeCat(NAME);

        int actual = testHouse.getCount();
        int expected = 0;
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFailForSellingNoneExistingCat(){
        this.house.catForSale(NONE_EXISTING_NAME);
    }

    @Test
    public void testShouldSetCatHungerToFalse(){
        this.house.catForSale(NAME);
        Assert.assertFalse(this.cat.isHungry());
    }

    @Test
    public void testShouldGetCorrectCatWithGivenName(){
        Cat expectedCat = this.cat;
        Cat actualCat = this.house.catForSale(NAME);

        Assert.assertEquals(expectedCat,actualCat);
    }

    @Test
    public void testShouldPrintCorrectStatistics(){
        String expectedOutput =  "The cat Kati is in the house Kati!";
        String actualOutput = this.house.statistics();

        Assert.assertEquals(expectedOutput,actualOutput);
    }
}
