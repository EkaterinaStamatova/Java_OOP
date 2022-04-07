package shopAndGoods;


import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

public class ShopTest {
    private static Shop shop;
    private static Goods goods;

    @Before
    public void setUp(){
        shop = new Shop();
        goods = new Goods("Bread", "123");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testShouldThrowExceptionForTryingToChangeUnmodifiableCollection() {
        shop.getShelves().clear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailsForAddingGoodsInNoneExistingShelf() throws OperationNotSupportedException{
        shop.addGoods("not existing", goods);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailsWhenAddingGoodsToTakenShelf() throws OperationNotSupportedException {
        shop.addGoods("Shelves1", goods);
        Goods nonExistingGood = new Goods("nonExistingGood", "nonExistingCode");
        shop.addGoods("Shelves1", nonExistingGood);
    }

    @Test
    public void testShouldAddGoodsCorrectly() throws OperationNotSupportedException {
        String added = shop.addGoods("Shelves5", goods);
        String actual = "Goods: 123 is placed successfully!";
        Assert.assertEquals(added, actual);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowExceptionForAlreadyExistingGoods() throws OperationNotSupportedException {
        shop.addGoods("Shelves1", goods);
        shop.addGoods("Shelves2", goods);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailsWhenRemovingGoodsFromNotExistingShelf(){
        shop.removeGoods("not existing", goods);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailsWhenRemovingNotExistingGoods() throws OperationNotSupportedException {
        Goods testGoods = new Goods("not existing", "none");
        shop.removeGoods("Shelves1", testGoods);
    }

    @Test
    public void testShouldRemoveGoodsFromShelvesCorrectly() throws OperationNotSupportedException {
        shop.addGoods("Shelves1", goods);

        String removed = shop.removeGoods("Shelves1", goods);
        String actual = "Goods: 123 is removed successfully!";
        Assert.assertEquals(removed, actual);
    }

    @Test
    public void testShouldGetGoodsCodeCorrectly(){
        String expectedCode = "123";
        String actual = goods.getGoodsCode();

        Assert.assertEquals(expectedCode, actual);
    }

    @Test
    public void testRemoveGoodsShouldSetTheShelveValueToNull() throws OperationNotSupportedException {
        Goods goods = new Goods("testGood", "testCode");
        shop.addGoods("Shelves1", goods);
        shop.removeGoods("Shelves1", goods);

        Goods emptySlot = shop.getShelves().get("Shelves1");
        Assert.assertNull(emptySlot);
    }
}