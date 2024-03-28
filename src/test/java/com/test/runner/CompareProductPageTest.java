package com.test.runner;

import com.magento.base.BaseLogin;
import com.magento.pages.CompareProductPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
//TEST_006
public class CompareProductPageTest extends BaseLogin {

    private CompareProductPage compareProductPage;

    @BeforeMethod
    public void setUp() {
        initialization();
        compareProductPage = new CompareProductPage();
    }


    @Test(priority = 1)
    public void addFirstProduct() {
        compareProductPage.addFirstProduct();
    }

    @Test(priority = 2)
    public void addSecondProduct() {
        compareProductPage.addSecondProduct();
    }

    @Test(priority = 3)
    public void addThirdProduct() {
        compareProductPage.addThirdProduct();
    }

    @Test(priority = 4)
    public void compareListItem() {
        String s = compareProductPage.compareListItem();
        Assert.assertEquals(s, prop.getProperty("compare_quantity"));
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}


