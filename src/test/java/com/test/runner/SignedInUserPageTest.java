package com.test.runner;

import com.magento.base.BaseLogin;
import com.magento.pages.SignedInUserPage;
import com.magento.util.ExcelParseUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//TEST_004
public class SignedInUserPageTest extends BaseLogin {

    private SignedInUserPage signedInUserPage;

    @BeforeMethod
    public void setUp() {
        initialization();
        signedInUserPage = new SignedInUserPage();
    }


    @Test(priority = 1, dataProvider = "getTestData")
    public void searchInput(String value) {
        signedInUserPage.search(value);
    }

    @Test(priority = 2, dataProvider = "getTestData")
    public void isFirstElementDisplayed(String value) {
        boolean firstElementDisplayed = signedInUserPage.isFirstElementDisplayed(value);
        Assert.assertTrue(firstElementDisplayed);
    }

    @Test(priority = 3, dataProvider = "getTestData")
    public void isSecondElementDisplayed(String value) {
        boolean isSecondElementDisplayed = signedInUserPage.isSecondElementDisplayed(value);
        Assert.assertTrue(isSecondElementDisplayed);
    }

    @Test(priority = 4, dataProvider = "getTestData")
    public void isThirdElementDisplayed(String value) {
        boolean isThirdElementDisplayed = signedInUserPage.isThirdElementDisplayed(value);
        Assert.assertTrue(isThirdElementDisplayed);
    }

    @Test(priority = 5, dataProvider = "getTestData")
    public void isFourthElementDisplayed(String value) {
        boolean isFourthElementDisplayed = signedInUserPage.isFourthElementDisplayed(value);
        Assert.assertTrue(isFourthElementDisplayed);
    }

    @Test(priority = 6)
    public void inValidSearch(){
        String s = signedInUserPage.invalidSearch(prop.getProperty("invalid_search_key"));
        Assert.assertEquals(s,"Your search returned no results.");
    }

    @DataProvider
    public Object[][] getTestData() {
        return ExcelParseUtil.getSearchData(1);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
