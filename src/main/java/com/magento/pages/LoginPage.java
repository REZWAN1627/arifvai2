package com.magento.pages;

import com.magento.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends TestBase {

    //Page-Factory

    @FindBy(xpath = "//input[contains(@title,'Email')]")
    @CacheLookup
    private WebElement email;
    @FindBy(xpath = "//input[contains(@title,'Password')]")
    @CacheLookup
    private WebElement password;

    @FindBy(xpath = "//div[contains(@class,'actions-toolbar')]//ancestor::span[text()='Sign In']")
    @CacheLookup
    private WebElement loginButton;

    @FindBy(xpath = "//h1[@class='page-title']//span[1]")
    private WebElement customLogInTitle;

    @FindBy(xpath = "//li[contains(@class,'authorization-link')]//parent::li//child::a[contains(text(),'Sign In')]")
    @CacheLookup
    private WebElement signInHyperLink;

    @FindBy(xpath = "//div[@data-ui-id='message-error']//div[1]")
    @CacheLookup
    private WebElement invalidLoginMsg;

    @FindBy(xpath = "//div[contains(@class,'actions-toolbar')]//ancestor::span[text()='Create an Account']")
    @CacheLookup
    private WebElement createNewAccount;

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }


    //Action
    public String validatedLoginPageTitle() throws InterruptedException {
        signInHyperLink.click();
        Thread.sleep(1000);
        return driver.getTitle();
    }

    public boolean validLoginPage() {
        return customLogInTitle.isDisplayed();
    }

    public String loginInvalid(String userEmail, String userPassword) {
        email.sendKeys(userEmail);
        password.sendKeys(userPassword);
        loginButton.click();

        return invalidLoginMsg.getText();
    }

    public CreateAccountPage createNewAccount(){
        createNewAccount.click();
        return new CreateAccountPage();
    }

    public void loginValid(String userEmail, String userPassword) {
        email.sendKeys(userEmail);
        password.sendKeys(userPassword);
        loginButton.click();
    }
}
