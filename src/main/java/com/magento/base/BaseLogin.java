package com.magento.base;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BaseLogin extends TestBase {
    @FindBy(xpath = "//li[contains(@class,'authorization-link')]//parent::li//child::a[contains(text(),'Sign In')]")
    @CacheLookup
    private WebElement signInHyperLink;

    @FindBy(xpath = "//input[contains(@title,'Email')]")
    @CacheLookup
    private WebElement email;
    @FindBy(xpath = "//input[contains(@title,'Password')]")
    @CacheLookup
    private WebElement password;

    @FindBy(xpath = "//div[contains(@class,'actions-toolbar')]//ancestor::span[text()='Sign In']")
    @CacheLookup
    private WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class,'nested')]//preceding-sibling::input[contains(@id,'search')]")
    @CacheLookup
    private WebElement searchBarField;

    @FindBy(xpath = "//button[contains(@type,'submit') and span[text()='Search']]")
    @CacheLookup
    private WebElement searchIcon;

    public BaseLogin() {
        PageFactory.initElements(driver, this);
    }

    public void loginToSystem() {
        signInHyperLink.click();
        email.sendKeys(prop.getProperty("email"));
        password.sendKeys(prop.getProperty("password"));
        loginButton.click();
    }

//    public void searchItem(){
//        searchBarField.sendKeys(prop.getProperty("search_key"));
//        searchIcon.click();
//    }
}
