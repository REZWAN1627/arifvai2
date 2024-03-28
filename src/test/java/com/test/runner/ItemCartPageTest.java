package com.test.runner;

import com.magento.base.BaseLogin;
import com.magento.pages.ItemCartPage;
import com.magento.util.ExcelParseUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//TEST_005
public class ItemCartPageTest extends BaseLogin {

    private ItemCartPage itemCartPage;

    @BeforeMethod
    public void setUp() {
        initialization();
        itemCartPage = new ItemCartPage();
    }


    @Test(priority = 1)
    public void addFirstProductToCart() {
        itemCartPage.addFirstProductToCart();
    }

    @Test(priority = 2,dataProvider = "getTestData")
    public void addSecondProductToCart(String quantity) {
        itemCartPage.addSecondProductToCart(quantity);
    }

    @Test(priority = 3,dataProvider = "getTestDataV2")
    public void addThirdProductToCart(String quantity) {
        itemCartPage.addThirdProductToCart(quantity);
    }

    @Test(priority = 4)
    public void getTotalQuantityFromCart() throws InterruptedException {
        String totalProductInCard = itemCartPage.getTotalProductInCard();
        Assert.assertEquals(totalProductInCard,prop.getProperty("card_quantity"));
    }


    @DataProvider
    public Object[][] getTestData() {
        return ExcelParseUtil.getQuantity(1);
    }

    @DataProvider
    public Object[][] getTestDataV2() {
        return ExcelParseUtil.getQuantity(2);
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
