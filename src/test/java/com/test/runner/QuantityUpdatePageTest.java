package com.test.runner;

import com.magento.base.BaseLogin;
import com.magento.pages.QuantityUpdatePage;
import com.magento.util.ExcelParseUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class QuantityUpdatePageTest extends BaseLogin {

    private QuantityUpdatePage quantityUpdatePage;

    @BeforeMethod
    public void setUp() {
        initialization();
        quantityUpdatePage = new QuantityUpdatePage();
    }

    @Test(priority = 1)
    public void addProducts() {
        quantityUpdatePage.addFirstProducts();
    }

    @Test(priority = 2)
    public void addSecondProduct() {
        quantityUpdatePage.addSecondProduct();
    }


    @Test(priority = 3, dataProvider = "getTestData")
    public void updateFirstProd(String value1, String value2) throws InterruptedException {
        quantityUpdatePage.updateFirstProd(value1);
    }

    @Test(priority = 4, dataProvider = "getTestData")
    public void updateSecondProd(String value1, String value2) throws InterruptedException {
        quantityUpdatePage.updateSecondProd(value2);
    }

    @Test(priority = 5)
    public void getTotalItemsInCart() throws InterruptedException {
        String totalItemsInCart = quantityUpdatePage.getTotalItemsInCart();
        Assert.assertEquals(totalItemsInCart, "5");
    }

    @DataProvider
    public Object[][] getTestData() {
        return ExcelParseUtil.getUpdateQuantity(1);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
