package computers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class ComputerManagerTests {
    // TODO: Test ComputerManager
    private ComputerManager computerManager;
    private Computer computer;
    private List<Computer> computerList;

    @Before
    public void setUp(){
        computerList = new ArrayList<>();
        this.computer = new Computer("TU-Sofiq", "Merc", 10.0);
        this.computerList.add(this.computer);
        this.computerManager = new ComputerManager();
        this.computerManager.addComputer(computer);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testShouldThrowExceptionForTryingToChangeUnmodifiableCollection() {
        this.computerManager.getComputers().clear();
    }

    @Test
    public void testShouldAddComputer(){
        Computer testComputer = new Computer("SAP", "Benz", 102.2);
        this.computerManager.addComputer(testComputer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFailIfWeAddAnExsistingComputer(){
       this.computerManager.addComputer(computer);
    }

    @Test
    public void testShouldAddHeroToRepositoryAndGetCorrectCount() {
        int expectedCount = 1;
        int actualCount = this.computerManager.getCount();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testShouldRemoveCorrectComputer() {
        Computer testComp = this.computerManager.getComputer("TU-Sofiq", "Merc");
        Computer actual = this.computerManager.removeComputer("TU-Sofiq", "Merc");
        Assert.assertEquals(testComp,actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFailIfManufacturerNameIsNull(){
        this.computerManager.getComputersByManufacturer(null);
    }

    @Test
    public void testShouldReturnCollectionOfComputersByGivenManufacturer(){
            List<Computer> test  = this.computerList.stream().filter(c -> c.getManufacturer().equals("TU-Sofiq"))
                    .collect(Collectors.toList());
            Assert.assertEquals(test, computerManager.getComputersByManufacturer("TU-Sofiq"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFailIfComputerIsNull(){
        this.computerManager.getComputer("lalal", "jfsjfs");
    }


    @Test
    public void testShouldGetComputerByManufacturerAndModel() {
        Computer computerByManufacturerAndModel = computerManager.getComputer("TU-Sofiq", "Merc");
        assertEquals(computer, computerByManufacturerAndModel);
    }

    @Test
    public void testShouldGetComputerByManufacturerCorrectly() {
        List<Computer> computersByManufacturer = computerManager.getComputersByManufacturer("TU-Sofiq");
        assertEquals(computer, computersByManufacturer.get(0));
    }
}