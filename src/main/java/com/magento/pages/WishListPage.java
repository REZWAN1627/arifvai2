package com.magento.pages;

import com.magento.base.BaseLogin;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WishListPage extends BaseLogin {

    private ItemCartPage itemCartPage;
    @FindBy(xpath = "//div[contains(@class,'product-social-links')]//ancestor::span[text()='Add to Wish List']")
    @CacheLookup
    private WebElement addToWishList;

    @FindBy(xpath = "//div[contains(@class,'page messages')]//ancestor::a[text()='here']")
    @CacheLookup
    private WebElement shopMore;

    @FindBy(xpath = "//div[contains(@role,'alert')]//div//div")
    @CacheLookup
    private WebElement verifyMsg;

    @FindBy(xpath = "//span[text()='You have no items in your wish list.']")
    @CacheLookup
    private WebElement wishListEmpty;

    @FindBy(xpath = "(//button[@class='action switch'])[1]")
    @CacheLookup
    private WebElement leftMostCornerDropDown;

    @FindBy(xpath = "(//li[@class='link wishlist']//a)[1]")
    @CacheLookup
    private WebElement withListFromDropDown;

    public WishListPage() {
        PageFactory.initElements(driver, this);
        itemCartPage = new ItemCartPage(driver);
        loginToSystem();
    }

    public String wishListEmpty() {
        leftMostCornerDropDown.click();
        withListFromDropDown.click();
        return wishListEmpty.getText();
    }

    public void addFirstProduct() {
        itemCartPage.getSecondProductClick().click();
        itemCartPage.getProductDisplay_S().click();
        itemCartPage.getSizeColorSelected_YELLOW().click();
        addToWishList.click();
        shopMore.click();
    }

    public String addSecondProduct() {
        itemCartPage.getThirdProduct().click();
        itemCartPage.getGetProductDisplay_L().click();
        itemCartPage.getGetSizeColorSelected_GREY().click();
        addToWishList.click();
        return verifyMsg.getText();
    }
}
