package com.test.runner;

import com.magento.base.TestBase;
import com.magento.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends TestBase {

    public LoginPage loginPage;

    public LoginPageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        loginPage = new LoginPage();
    }

    @Test(priority = 1)
    public void loginPageTitleTest() throws InterruptedException {
        String s = loginPage.validatedLoginPageTitle();
        Assert.assertEquals(s, "Customer Login");
    }

    @Test(priority = 2)
    public void logInPageDisplayed() {
        boolean b = loginPage.validLoginPage();
        Assert.assertTrue(b);
    }

    @Test(priority = 3)
    public void loginTest() throws InterruptedException {
        loginPage.validatedLoginPageTitle();
        loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
