package halfLife;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlayerTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS Player
    private Player player;
    private Gun gun;
    @Before
    public void setUp(){
        this.player = new Player("Kati", 50);
        this.gun = new Gun("Rifle", 4);
    }

    @Test(expected = NullPointerException.class)
    public void testShouldFallForNullPlayer(){
        Player testPlayer = new Player(null, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFallForNegativeHealth(){
        Player testPlayer = new Player("Lili", -58);
    }

    @Test(expected = IllegalStateException.class)
    public void testChecksIfPlayerIsDead(){
        Player testPlayer = new Player("Kati", 0);
        testPlayer.takeDamage(40);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testShouldThrowExceptionForTryingToChangeUnmodifiableCollection() {
        this.player.getGuns().clear();
    }

    @Test
    public void testShoudCheckIfPlayerTakesDamage(){
        Player testPlayer = new Player("Yoni", 20);
        testPlayer.takeDamage(10);
        int expectedReturn = 10;
        Assert.assertEquals(expectedReturn, testPlayer.getHealth());
    }

    @Test
    public void testShoudSetHealthToZeroIfDamageIsBigger() {
        Player testPlayer = new Player("Kari", 20);
        int expectedReturn = 0;
        testPlayer.takeDamage(30);
        Assert.assertEquals(expectedReturn,testPlayer.getHealth());
    }

    @Test(expected = NullPointerException.class)
    public void testShouldBreakIfGunIsNull(){
        this.player.addGun(null);
    }

    @Test
    public void testShouldAddGun() {
        this.player.addGun(gun);
    }

    @Test
    public void testShouldRemoveCorrectGun() {
        this.player.addGun(gun);
        boolean isRemoved = this.player.removeGun(gun);
        assertTrue(isRemoved);
    }

    @Test
    public void testShouldGetCorrectGunWithGivenName() {
        Gun expected = this.gun;
        this.player.addGun(gun);
        Gun actual = this.player.getGun("Rifle");
        Assert.assertEquals(expected, actual);
    }
}
