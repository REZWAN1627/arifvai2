package com.magento.pages;

import com.magento.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends TestBase {
    @FindBy(xpath = "//li[contains(@class,'authorization-link')]//parent::li//child::a[contains(text(),'Sign In')]")
    @CacheLookup
    private WebElement signInPageDisplayed;
    @FindBy(xpath = "//li[contains(@class,'authorization-link')]//parent::li//child::a[contains(text(),'Sign In')]")
    @CacheLookup
    private WebElement signInBtn;

    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    public boolean isSignInPageDisplayed() {
        return signInPageDisplayed.isDisplayed();
    }


    public LoginPage signIn() {
        signInBtn.click();
        return new LoginPage();


    }
}
