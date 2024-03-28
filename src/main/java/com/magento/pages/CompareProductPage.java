package com.magento.pages;

import com.magento.base.BaseLogin;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CompareProductPage extends BaseLogin {

    @FindBy(xpath = "//ol[contains(@class,'product-items widget-product-grid')]//following-sibling::li[1]")
    private WebElement firstProduct;

    @FindBy(xpath = "//span[text()='Add to Compare']")
    @CacheLookup
    private WebElement addToCompareBtn;

    @FindBy(xpath = "//a[text()='Home']")
    @CacheLookup
    private WebElement homeButton;

    @FindBy(xpath = "//ol[contains(@class,'product-items widget-product-grid')]//following-sibling::li[2]")
    private WebElement secondProduct;

    @FindBy(xpath = "//ol[contains(@class,'product-items widget-product-grid')]//following-sibling::li[4]")
    private WebElement thirdProduct;

    @FindBy(xpath = "//a[text()='comparison list']")
    @CacheLookup
    private WebElement comparisonList;

    @FindBy(xpath = "//ul[contains(@class,'compare wrapper')]//ancestor::span[contains(@class,'counter qty')]")
    private WebElement quantity;

    public CompareProductPage() {
        PageFactory.initElements(driver, this);
        loginToSystem();
    }

    public void addFirstProduct() {
        firstProduct.click();
        addToCompareBtn.click();
        homeButton.click();
    }

    public void addSecondProduct() {
        secondProduct.click();
        addToCompareBtn.click();
        homeButton.click();
    }

    public void addThirdProduct() {
        thirdProduct.click();
        addToCompareBtn.click();
        homeButton.click();
    }

    public String compareListItem() {
        thirdProduct.click();
        addToCompareBtn.click();
        comparisonList.click();
        return quantity.getText();
    }
}
