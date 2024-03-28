package com.test.runner;

import com.magento.base.TestBase;
import com.magento.pages.CreateAccountPage;
import com.magento.pages.LoginPage;
import com.magento.util.ExcelParseUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//TEST_001
public class LoginPageTest extends TestBase {

    private LoginPage loginPage;

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
    public void loginTestWithNonRegisterEmail() throws InterruptedException {
        loginPage.validatedLoginPageTitle();
        String errorMsg = loginPage.loginInvalid(prop.getProperty("non_register_email"), prop.getProperty("password"));
        Assert.assertEquals(errorMsg, "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
    }

    @Test(priority = 4, dataProvider = "getTestData")
    public void createAccount(String firstname, String lastName, String email, String pass, String confirmPass) throws InterruptedException {
        loginPage.validatedLoginPageTitle();
        CreateAccountPage newAccount = loginPage.createNewAccount();
        newAccount.createAccount(firstname, lastName, email, pass, confirmPass);
    }

    @DataProvider
    public Object[][] getTestData() {
        return ExcelParseUtil.geClientData(1);
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
