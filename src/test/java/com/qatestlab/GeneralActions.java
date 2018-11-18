package com.qatestlab;

import com.qatestlab.model.ProductData;
import com.qatestlab.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    private ProductData newProduct;
    private Integer productQtyInt;
    private String productQtyStr;
    private String productName;
    private String productPrice;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);

        newProduct = ProductData.getProductData();

        productQtyInt = newProduct.getQty();
        productQtyStr = String.valueOf(productQtyInt);
        productName = newProduct.getName();
        productPrice = newProduct.getPrice();
    }

    public void loginToAdminPanel(String email, String password) {
        driver.get(Properties.getDefaultBaseAdminUrl());
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.name("submitLogin")).click();
    }

    public void openProductCreatePage() {
        By subtabAdminCatalogLocator = By.cssSelector("#subtab-AdminCatalog");
        wait.until(ExpectedConditions.visibilityOfElementLocated(subtabAdminCatalogLocator));

        WebElement catalogMenuItem = driver.findElement(By.cssSelector("#subtab-AdminCatalog"));
        WebElement productsMenuItem = driver.findElement(By.cssSelector("#subtab-AdminProducts"));

        Actions builder = new Actions(driver);
        builder.moveToElement(catalogMenuItem).pause(Duration.ofSeconds(2)).click(productsMenuItem).build().perform();

        By newProductBtnLocator = By.cssSelector("#page-header-desc-configuration-add");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newProductBtnLocator));
        driver.findElement(newProductBtnLocator).click();

        String productCreatePageTitle = "товары • prestashop-automation";
        String title = driver.getTitle();
        Assert.assertTrue(title.equals(productCreatePageTitle));
    }

    public void createNewProduct() {
        WebElement productNameField = driver.findElement(By.id("form_step1_name_1"));
        WebElement productQtyField = driver.findElement(By.id("form_step1_qty_0_shortcut"));
        WebElement productPriceField = driver.findElement(By.id("form_step1_price_shortcut"));

        productNameField.sendKeys(productName);
        productQtyField.sendKeys(Keys.chord(Keys.CONTROL, "a"), productQtyStr);
        productPriceField.sendKeys(Keys.chord(Keys.CONTROL, "a"), productPrice);

        System.out.println("Name - " + productName);
        System.out.println("Qty - " + productQtyStr);
        System.out.println("Price - " + productPrice);

        WebElement activateSwitchBtn = driver.findElement(By.className("switch-input "));
        activateSwitchBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Настройки обновлены.')]")));
        driver.findElement(By.cssSelector("div.growl-close")).click();

        WebElement saveBtn = driver.findElement(By.xpath("//button[@class = 'btn btn-primary js-btn-save']"));
        saveBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Настройки обновлены.')]")));
        driver.findElement(By.xpath("//div[@class = 'growl-close']")).click();
    }

    public void productDisplayCheck() {
        driver.get(Properties.getDefaultBaseUrl());
        driver.findElement(By.xpath("//a[@class='all-product-link pull-xs-left pull-md-right h4']")).click();
        By newProductLocator = By.xpath("//a[contains(text(), '"+ productName +"' ]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newProductLocator));
    }

}
