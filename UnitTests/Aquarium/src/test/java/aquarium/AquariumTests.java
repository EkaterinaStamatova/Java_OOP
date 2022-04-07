package aquarium;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AquariumTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS Aquarium

    private Aquarium aquarium;

    private Fish fish;
    private static final Fish NULL_FISH = null;

    private static final String AQUARIUM_NAME = "BikiniBottom";
    private static final String FISH_NAME = "SpongeBob";
    private static final int CAPACITY = 1;

    private static final int FISH_COUNT = 1;

    private static final String NONE_EXISTING_NAME = "MGB";
    private static final String REPORT_MESSAGE = "Fish available at %s: %s";

    @Before
    public void setUp(){
        aquarium = new Aquarium(AQUARIUM_NAME, CAPACITY);
        fish = new Fish(FISH_NAME);
        aquarium.add(fish);
    }

    @Test(expected = NullPointerException.class)
    public void testShouldFailIfNameIsNull(){
       aquarium = new Aquarium(null, CAPACITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShoildFailIfCapacityIsANegativeNumber(){
        aquarium = new Aquarium(AQUARIUM_NAME, -10);
    }
    @Test
    public void testShouldGetComputersCountFromTheComputerManager() {
        int fishCountTested = aquarium.getCount();
        Assert.assertEquals(FISH_COUNT, fishCountTested);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowExceptionForTryingToOverflowAquarium() {
       aquarium.add(fish);
    }

    @Test
    public void testShouldRemoveFishByGivenName() {
       aquarium.remove(FISH_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldTrowExceptionForNoneExistingFish() {
        aquarium.remove(NONE_EXISTING_NAME);
    }

    @Test
    public void testShouldSellFishWithGivenName(){
        Fish soldFish = aquarium.sellFish(FISH_NAME);
        Assert.assertEquals(fish,soldFish);
        Assert.assertFalse(soldFish.isAvailable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldTrowExceptionForSellingNoneExistingFish() {
        aquarium.sellFish(NONE_EXISTING_NAME);
    }

    @Test
    public void testShouldGetCorrectFishWithGivenName() {
        Fish expected = this.fish;
        Fish actual = this.aquarium.sellFish(FISH_NAME);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testShouldReturnTheReportForTheFishNamesInTheAquarium() {
        String expectedReport = String.format(REPORT_MESSAGE, aquarium.getName(), FISH_NAME);
        String aquariumReport = aquarium.report();
        Assert.assertEquals(expectedReport, aquariumReport);
    }

    @Test
    public void testShouldGetCorrectCapacity(){
        int actualCapacity = this.aquarium.getCapacity();
        Assert.assertEquals(CAPACITY,actualCapacity);
    }
}

