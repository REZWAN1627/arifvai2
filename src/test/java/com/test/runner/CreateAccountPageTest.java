package com.test.runner;

import com.magento.base.TestBase;
import com.magento.pages.CreateAccountPage;
import com.magento.util.ExcelParseUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//TEST_002
public class CreateAccountPageTest extends TestBase {
    private CreateAccountPage createAccountPage;

    public CreateAccountPageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        createAccountPage = new CreateAccountPage();
    }

    @Test(priority = 1)
    public void clickOnCreateAccount() {
        createAccountPage.clickOnCreateAccount();
    }

    @Test(priority = 2)
    public void checkRequiredFieldsDisplay() {
        createAccountPage.clickOnCreateAccount();
        String s = createAccountPage.showRequiredFieldsOnClick();
        Assert.assertEquals(s, "This is a required field.");
    }

    @Test(priority = 3, dataProvider = "getTestData")
    public void checkPasswordErrorMsg(String firstname, String lastName, String email, String pass, String confirmPass) throws InterruptedException {
        createAccountPage.clickOnCreateAccount();
        String s = createAccountPage.checkPasswordStreanth(firstname, lastName, email, pass, confirmPass);
        Assert.assertEquals(s, "Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.");
    }

    @Test(priority = 4, dataProvider = "getTestData")
    public void checkExistingEmail(String firstname, String lastName, String email, String pass, String confirmPass) {
        createAccountPage.clickOnCreateAccount();
        String s = createAccountPage.emailAlreadyExist(firstname, lastName, email, prop.getProperty("password"), prop.getProperty("password"));
        Assert.assertEquals(s, "There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.");
    }

    @Test(priority = 5, dataProvider = "getTestDataV2")
    public void putValidEmail(String firstname, String lastName, String email, String pass, String confirmPass) {
        createAccountPage.clickOnCreateAccount();
        String s = createAccountPage.createWithValidEmail(firstname, lastName, email, pass, confirmPass);
        Assert.assertEquals(s, "Thank you for registering with Main Website Store.");
    }

    @DataProvider
    public Object[][] getTestData() {
        return ExcelParseUtil.geClientData(2);
    }

    @DataProvider
    public Object[][] getTestDataV2() {
        return ExcelParseUtil.geClientData(3);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
