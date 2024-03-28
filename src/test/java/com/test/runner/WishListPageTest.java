package com.test.runner;

import com.magento.base.BaseLogin;
import com.magento.pages.WishListPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
//TEST_007
public class WishListPageTest extends BaseLogin {

    private WishListPage wishListPage;

    @BeforeMethod
    public void setUp() {
        initialization();
        wishListPage = new WishListPage();
    }


    @Test(priority = 1)
    public void wishListEmpty() {
        String s = wishListPage.wishListEmpty();
        Assert.assertEquals(s,"You have no items in your wish list.");
    }

    @Test(priority = 2)
    public void addFirstProduct() {
        wishListPage.addFirstProduct();
    }

    @Test(priority = 3)
    public void addSecondProduct(){
        String s = wishListPage.addSecondProduct();
        Assert.assertEquals(s,"");
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
