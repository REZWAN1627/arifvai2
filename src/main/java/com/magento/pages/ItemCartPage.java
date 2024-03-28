package com.magento.pages;

import com.magento.base.BaseLogin;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ItemCartPage extends BaseLogin {

    @FindBy(xpath = "//ol[contains(@class,'product-items widget-product-grid')]//following-sibling::li[1]")
    @CacheLookup
    private WebElement firstProductClick;

    @FindBy(xpath = "//div[contains(@role,'listbox')]//ancestor::div[text()='XS']")
    @CacheLookup
    private WebElement selectSize_XS;

    @FindBy(xpath = "//div[contains(@option-label,'Blue')]")
    @CacheLookup
    private WebElement selectColor_BLUE;

    @FindBy(xpath = "//span[text()='Add to Cart']")
    @CacheLookup
    private WebElement addToCart;

    @FindBy(xpath = "//a[text()='Home']")
    @CacheLookup
    private WebElement homePageBtn;

    @FindBy(xpath = "//ol[contains(@class,'product-items widget-product-grid')]//following-sibling::li[2]")
    @CacheLookup
    private WebElement secondProductClick;

    @FindBy(xpath = "//div[contains(@role,'listbox')]//ancestor::div[text()='S']")
    @CacheLookup
    private WebElement productDisplay_S;

    @FindBy(xpath = "//div[contains(@option-label,'Yellow')]")
    @CacheLookup
    private WebElement sizeColorSelected_YELLOW;

    @FindBy(xpath = "//input[contains(@type,'number')]//ancestor::div[contains(@class,'control')]//following-sibling::input")
    @CacheLookup
    private WebElement updateQuantity;

    @FindBy(xpath = "//ol[contains(@class,'product-items widget-product-grid')]//following-sibling::li[3]")
    @CacheLookup
    private WebElement thirdProduct;

    @FindBy(xpath = "//div[contains(@role,'listbox')]//ancestor::div[text()='L']")
    @CacheLookup
    private WebElement getProductDisplay_L;

    @FindBy(xpath = "//div[@option-label='Gray']")
    @CacheLookup
    private WebElement getSizeColorSelected_GREY;

    @FindBy(xpath = "//span[text()='My Cart']//ancestor::a[contains(@class,'action showcart')]")
    @CacheLookup
    private WebElement cardOpenBtn;

    @FindBy(xpath = "//div[contains(@class,'items-total')]//following-sibling::span[contains(@class,'count')]")
    @CacheLookup
    private WebElement verifyUnits;


    public ItemCartPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }
    public ItemCartPage() {
        PageFactory.initElements(driver, this);
        loginToSystem();
    }

    public void addFirstProductToCart() {
        firstProductClick.click();
        selectSize_XS.click();
        selectColor_BLUE.click();
        addToCart.click();
        homePageBtn.click();
    }

    public void addSecondProductToCart(String unit) {
        secondProductClick.click();
        productDisplay_S.click();
        sizeColorSelected_YELLOW.click();
        updateQuantity.clear();
        updateQuantity.sendKeys(unit);
        addToCart.click();
        homePageBtn.click();
    }

    public void addThirdProductToCart(String unit) {
        thirdProduct.click();
        getProductDisplay_L.click();
        getSizeColorSelected_GREY.click();
        updateQuantity.clear();
        updateQuantity.sendKeys(unit);
        addToCart.click();
        homePageBtn.click();
    }

    public String getTotalProductInCard() throws InterruptedException {
        cardOpenBtn.click();
        Thread.sleep(5000);
        cardOpenBtn.click();
        return verifyUnits.getText();
    }

    public WebElement getSecondProductClick() {
        return secondProductClick;
    }

    public WebElement getProductDisplay_S() {
        return productDisplay_S;
    }

    public WebElement getSizeColorSelected_YELLOW() {
        return sizeColorSelected_YELLOW;
    }

    public WebElement getThirdProduct() {
        return thirdProduct;
    }

    public WebElement getGetProductDisplay_L() {
        return getProductDisplay_L;
    }

    public WebElement getGetSizeColorSelected_GREY() {
        return getSizeColorSelected_GREY;
    }

    public WebElement getAddToCart() {
        return addToCart;
    }

    public WebElement getUpdateQuantity() {
        return updateQuantity;
    }

    public WebElement getHomePageBtn() {
        return homePageBtn;
    }

    public WebElement getCardOpenBtn() {
        return cardOpenBtn;
    }
}
