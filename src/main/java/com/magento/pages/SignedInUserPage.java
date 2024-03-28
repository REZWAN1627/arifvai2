package com.magento.pages;

import com.magento.base.BaseLogin;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignedInUserPage extends BaseLogin {

    @FindBy(xpath = "//div[contains(@class,'nested')]//preceding-sibling::input[contains(@id,'search')]")
    @CacheLookup
    private WebElement searchBarField;

    @FindBy(xpath = "//button[contains(@type,'submit') and span[text()='Search']]")
    @CacheLookup
    private WebElement searchIcon;

    @FindBy(xpath = "//dt[text()='Related search terms']//following-sibling::dd//a")
    private WebElement fistElement;

    @FindBy(xpath = "//dt[text()='Related search terms']//following-sibling::dd[2]//a")
    private WebElement secondElement;

    @FindBy(xpath = "//dt[text()='Related search terms']//following-sibling::dd[3]//a")
    private WebElement thirdElement;
    @FindBy(xpath = "//dt[text()='Related search terms']//following-sibling::dd[4]//a")
    private WebElement fourthElement;

    @FindBy(xpath = "//div[contains(@class,'message notice')]//following-sibling::div")
    private WebElement emptyResult;


    public SignedInUserPage() {
        PageFactory.initElements(driver, this);
        loginToSystem();
    }


    public void search(String value) {
        searchBarField.sendKeys(value);
        searchIcon.click();
    }

    public boolean isFirstElementDisplayed(String value) {
        search(value);
        return fistElement.isDisplayed();
    }

    public boolean isSecondElementDisplayed(String value) {
        search(value);
        return secondElement.isDisplayed();
    }

    public boolean isThirdElementDisplayed(String value) {
        search(value);
        return thirdElement.isDisplayed();
    }

    public boolean isFourthElementDisplayed(String value) {
        search(value);
        return fourthElement.isDisplayed();
    }

    public String invalidSearch(String value) {
        search(value);
        return emptyResult.getText();
    }
}
