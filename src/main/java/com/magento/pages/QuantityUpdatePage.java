package com.magento.pages;

import com.magento.base.BaseLogin;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class QuantityUpdatePage extends BaseLogin {
    private ItemCartPage itemCartPage;

    @FindBy(xpath = "(//div[@class='primary']//a)[1]")
    @CacheLookup
    private WebElement firstItem;

    @FindBy(xpath = "(//div[@class='primary']//a)[2]")
    @CacheLookup
    private WebElement secondItem;

    @FindBy(xpath = "//button[@title='Update Cart']")
    @CacheLookup
    private WebElement updateQuantity;

    public QuantityUpdatePage() {
        PageFactory.initElements(driver, this);
        itemCartPage = new ItemCartPage(driver);
        loginToSystem();
    }

    public void addFirstProducts() {
        itemCartPage.getSecondProductClick().click();
        itemCartPage.getProductDisplay_S().click();
        itemCartPage.getSizeColorSelected_YELLOW().click();


        itemCartPage.getAddToCart().click();
    }

    public void addSecondProduct(){
        itemCartPage.getThirdProduct().click();
        itemCartPage.getGetProductDisplay_L().click();
        itemCartPage.getGetSizeColorSelected_GREY().click();


        itemCartPage.getAddToCart().click();
    }

    public void updateFirstProd(String value) throws InterruptedException {
        itemCartPage.getCardOpenBtn().click();
        Thread.sleep(5000);
        itemCartPage.getCardOpenBtn().click();
        firstItem.click();
        WebElement quantityElement = itemCartPage.getUpdateQuantity();
        quantityElement.clear();
        quantityElement.sendKeys(value);
        Thread.sleep(3000);
        updateQuantity.click();
        Thread.sleep(3000);

    }

    public void updateSecondProd(String value) throws InterruptedException {
        itemCartPage.getCardOpenBtn().click();
        Thread.sleep(5000);
        itemCartPage.getCardOpenBtn().click();
        secondItem.click();
        WebElement quantityElement = itemCartPage.getUpdateQuantity();
        quantityElement.clear();
        quantityElement.sendKeys(value);
        Thread.sleep(3000);
        updateQuantity.click();
    }

    public String getTotalItemsInCart() throws InterruptedException {
        return itemCartPage.getTotalProductInCard();
    }
}
