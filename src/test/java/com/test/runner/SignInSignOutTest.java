package com.test.runner;

import com.magento.base.TestBase;
import com.magento.pages.CreateAccountPage;
import com.magento.pages.HomePage;
import com.magento.pages.LoginPage;
import com.magento.util.ExcelParseUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//TEST_003
public class SignInSignOutTest extends TestBase {

    private CreateAccountPage createAccountPage;

    public SignInSignOutTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        createAccountPage = new CreateAccountPage();
    }

    @Test(priority = 1, dataProvider = "getTestData")
    public void putValidEmail(String firstname, String lastName, String email, String pass, String confirmPass) {
        createAccountPage.clickOnCreateAccount();
        String s = createAccountPage.createWithValidEmail(firstname, lastName, email, pass, confirmPass);
        Assert.assertEquals(s, "Thank you for registering with Main Website Store.");

        createAccountPage.clickMenuBar();

        boolean menuBarDisplayed = createAccountPage.isMenuBarDisplayed();
        Assert.assertTrue(menuBarDisplayed);
//
        HomePage homePage = createAccountPage.signOutBtnClick();

        boolean signInPageDisplayed = homePage.isSignInPageDisplayed();
        Assert.assertTrue(signInPageDisplayed);

        LoginPage loginPage = homePage.signIn();

        loginPage.loginValid(email, pass);
    }


    @DataProvider
    public Object[][] getTestData() {
        return ExcelParseUtil.geClientData(4);
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
