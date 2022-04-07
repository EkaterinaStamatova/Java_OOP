package blueOrigin;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class SpaceshipTests {
    private static final String NAME_OF_SPACESHIP = "Astra";
    private static final int CAPACITY_OF_SPACESHIP = 1;

    private static final String NAME_OF_ASTRONAUT = "Ivan";
    private static final double OXYGEN_PERCENTAGE = 40;

    private Spaceship spaceship;
    private Astronaut astronaut;

    @Before
    public void setUp(){
        this.spaceship = new Spaceship(NAME_OF_SPACESHIP, CAPACITY_OF_SPACESHIP);
        this.astronaut = new Astronaut(NAME_OF_ASTRONAUT, OXYGEN_PERCENTAGE);
        this.spaceship.add(astronaut);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailsIfWeDoNotHaveEnoughCapacity(){
        Astronaut newAstronaut = new Astronaut("Iliq", OXYGEN_PERCENTAGE);
        spaceship.add(newAstronaut);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailsForAddingExistingAstronaut(){
        Spaceship ship = new Spaceship(NAME_OF_SPACESHIP, 2);
        ship.add(astronaut);
        ship.add(astronaut);
    }

    //TODO: Add astronaut correctly ?

    @Test
    public void testShouldRemoveAstronaut(){
        Assert.assertTrue(this.spaceship.remove(astronaut.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailsForNegativeCapacity(){
        Spaceship ship = new Spaceship(NAME_OF_SPACESHIP, -5);
    }
    @Test(expected = NullPointerException.class)
    public void testFailsForNullName(){
        Spaceship ship = new Spaceship(null, 5);
    }
}
