package com.magento.pages;

import com.magento.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateAccountPage extends TestBase {
    @FindBy(xpath = "//div[contains(@class,'field field-name-firstname required')]//parent::div//child::input[contains(@name,'firstname')]")
    @CacheLookup
    private WebElement firstName;

    @FindBy(xpath = "//div[contains(@class,'field field-name-lastname required')]//parent::div//child::input[contains(@name,'lastname')]")
    @CacheLookup
    private WebElement lastName;

    @FindBy(xpath = "//input[contains(@title,'Email')]")
    @CacheLookup
    private WebElement email;

    @FindBy(xpath = "//div[contains(@class,'field password required')]//parent::div//child::input[contains(@title,'Password')]")
    @CacheLookup
    private WebElement password;

    @FindBy(xpath = "//div[contains(@class,'field confirmation required')]//parent::div//child::input[contains(@title,'Password')]")
    @CacheLookup
    private WebElement confirmPassword;

    @FindBy(xpath = "//button[contains(@class,'action submit primary')]//ancestor::span[text()='Create an Account']")
    @CacheLookup
    private WebElement createAccountBtn;

    @FindBy(xpath = "//li[contains(@class,'authorization-link')]//parent::li//child::a[contains(text(),'Sign In')]")
    @CacheLookup
    private WebElement signInHyperLink;

    @FindBy(xpath = "//div[contains(@class,'actions-toolbar')]//ancestor::span[text()='Create an Account']")
    @CacheLookup
    private WebElement createNewAccount;

    @FindBy(xpath = "(//input[contains(@class,'input-text required-entry')]/following-sibling::div)[1]")
    @CacheLookup
    private WebElement requireFields;

    @FindBy(xpath = "//input[contains(@aria-describedby,'password-error')]//following-sibling::div[contains(text(),'Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.')]")
    @CacheLookup
    private WebElement passwordNotStrongEnough;

    @FindBy(xpath = "//div[contains(@class,'message-error error message')]//parent::div//child::div[contains(text(),'There is already an account with this email address. If you are sure that it is your email address, ')]")
    @CacheLookup
    private WebElement emailAlreadyExist;

    @FindBy(xpath = "//div[contains(@class,'message-success success message')]")
    @CacheLookup
    private WebElement createSuccessfully;

    @FindBy(xpath = "//header[contains(@class,'page-header')]//parent::span//child::button[contains(@type,'button')]")
    @CacheLookup
    private WebElement menuBarDisplay;

    @FindBy(xpath = "//li[contains(@class,'customer-welcome active')]//parent::li//child::li[contains(@class,'authorization-link')]//parent::li//child::a")
    @CacheLookup
    private WebElement signOutBtn;


    public CreateAccountPage() {
        PageFactory.initElements(driver, this);
    }

    public void setFirstName(String name) {
        firstName.sendKeys(name);
    }

    public void setLastName(String name) {
        lastName.sendKeys(name);
    }

    public void setEmail(String clientEmail) {
        email.sendKeys(clientEmail);
    }

    public void setPassword(String pass) {
        password.sendKeys(pass);
    }

    public void setConfirmPassword(String conPass) {
        confirmPassword.sendKeys(conPass);
    }

    public void createAccount(String... args) throws InterruptedException {
        setFirstName(args[0]);
        setLastName(args[1]);
        setEmail(args[2]);
        setPassword(args[3]);
        setConfirmPassword(args[4]);
        Thread.sleep(3000);
        createAccountBtn.click();
    }

    public void clickOnCreateAccount() {
        signInHyperLink.click();
        createNewAccount.click();
    }

    public String showRequiredFieldsOnClick() {
        createAccountBtn.click();
        return requireFields.getText();
    }

    public String checkPasswordStreanth(String... args) throws InterruptedException {
        setFirstName(args[0]);
        setLastName(args[1]);
        setEmail(args[2]);
        setPassword(args[3]);
        setConfirmPassword(args[4]);
        Thread.sleep(3000);
        createAccountBtn.click();
        return passwordNotStrongEnough.getText();
    }

    public String emailAlreadyExist(String... args) {
        setFirstName(args[0]);
        setLastName(args[1]);
        setEmail(args[2]);
        setPassword(args[3]);
        setConfirmPassword(args[4]);
        createAccountBtn.click();
        return emailAlreadyExist.getText();
    }

    public String createWithValidEmail(String... args) {
        setFirstName(args[0]);
        setLastName(args[1]);
        setEmail(args[2]);
        setPassword(args[3]);
        setConfirmPassword(args[4]);
        createAccountBtn.click();
        return createSuccessfully.getText();
    }

    public boolean isMenuBarDisplayed() {
        return menuBarDisplay.isDisplayed();
    }

    public void clickMenuBar() {
        menuBarDisplay.click();
    }

    public HomePage signOutBtnClick() {
        signOutBtn.click();
        return new HomePage();
    }
}
