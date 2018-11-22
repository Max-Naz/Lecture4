package com.qatestlab;

import com.qatestlab.model.ProductData;
import com.qatestlab.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.time.Duration;


public class GeneralActions {
    private static WebDriver driver;
    private static WebDriverWait wait;

    private String productQty;
    private String productName;
    private String productPrice;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }

    public void loginToAdminPanel(String email, String password) {
        log("Open Login page.");
        driver.get(Properties.getDefaultBaseAdminUrl());
        log("Fill login data.");
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("passwd")).sendKeys(password);
        log("Click submit button.");
        driver.findElement(By.name("submitLogin")).click();
    }

    public void openAdminProductsCatalogPage() {
        log("Wait for main menu load.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("subtab-AdminCatalog")));

        WebElement catalogMenuItem = driver.findElement(By.id("subtab-AdminCatalog"));
        WebElement productsMenuItem = driver.findElement(By.id("subtab-AdminProducts"));

        log("Open products page from main menu.");
        Actions builder = new Actions(driver);
        builder.moveToElement(catalogMenuItem).pause(Duration.ofSeconds(1)).click(productsMenuItem).build().perform();
    }

    public  void openNewProductPage() {
        log("Wait for 'new product' button.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("page-header-desc-configuration-add")));
        driver.findElement(By.id("page-header-desc-configuration-add")).click();
        log("Click 'new product' button.");
    }

    public void fillNewProductData(ProductData newProduct) {
        productName = newProduct.getName();
        productQty = String.valueOf(newProduct.getQty());
        productPrice = newProduct.getPrice();
        log("Fill product name field.");
        WebElement nameField = driver.findElement(By.id("form_step1_name_1"));
        nameField.sendKeys(productName);
        log("Product Name is: '" + productName + "'.");

        log("Fill product quantity field.");
        WebElement qtyField= driver.findElement(By.id("form_step1_qty_0_shortcut"));
        qtyField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        qtyField.sendKeys(productQty);
        log("Product Quantity is: '" + productQty + "'.");

        log("Fill product price field.");
        WebElement priceField = driver.findElement(By.id("form_step1_price_shortcut"));
        priceField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        priceField.sendKeys(productPrice);
        log("Product Quantity is: '" + productPrice + "'.");
    }

    public void activateNewProduct() {
        WebElement activateSwitchBtn = driver.findElement(By.className("switch-input"));
        activateSwitchBtn.click();
        log("Click activate button.");
        GeneralActions.successMessageCheck();
    }

    public void saveNewProduct() {
        WebElement saveBtn = driver.findElement(By.xpath("//button[@class = 'btn btn-primary js-btn-save']"));
        saveBtn.click();
        log("Click save button.");
        GeneralActions.successMessageCheck();
    }

    public void openMainPage() {
        log("Open main page.");
        driver.get(Properties.getDefaultBaseUrl());
    }

    public void openAllProductsPage() {
        log("Open all products page.");
        driver.findElement(By.xpath("//a[@class='all-product-link pull-xs-left pull-md-right h4']")).click();
    }

    public void checkNewProductDisplay() throws InterruptedException {
        WebElement newProduct;
        boolean isNewProductOnPage = false;
        do {
            try {
                log("Search for created product.");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js-product-list")));
                newProduct = driver.findElement(By.xpath("//a[.='"+ productName +"']"));
                Assert.assertTrue(newProduct.isDisplayed(), "New product not displayed");
                log("Product is displaying.");
                isNewProductOnPage = true;
            }catch (NoSuchElementException e) {
               driver.findElement(By.xpath("//a[contains(text(),'Вперед')]")).click();
               log("Click next page.");
               Thread.sleep(3000);
            }
        }while (isNewProductOnPage == false);
    }

    public void checkNewProductData() {
        log("Open product page.");
        WebElement newProductLink = driver.findElement(By.xpath("//a[text() = '"+ productName +"']"));
        newProductLink.click();
        log("Wait for  product load.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'col-md-6']")));

        log("Name verification.");
        WebElement currentProductName = driver.findElement(By.xpath("//H1"));
        Assert.assertEquals(currentProductName.getText(), productName.toUpperCase(), "Unexpected product Name.");
        log("Current product name is: " + currentProductName.getText());

        log("Price verification.");
        WebElement currentProductPrice = driver.findElement(By.xpath("//span[@itemprop = 'price']"));
        Assert.assertEquals(currentProductPrice.getText(), productPrice + " ₴", "Unexpected product price.");
        log("Current product price is: " + currentProductPrice.getText());

        log("Quantities verification.");
        WebElement productQtyContainer = driver.findElement(By.xpath("//div[@class = 'product-quantities']"));
        WebElement currentProductQty = productQtyContainer.findElement(By.tagName("span"));
        Assert.assertEquals(currentProductQty.getText(), productQty + " Товары", "Unexpected product quantity.");
        log("Current product quantity is: " + currentProductQty.getText());
    }

    private static void successMessageCheck() {
        log("Wait for growl message appear.");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.growl-message")));
        WebElement growlMessage = driver.findElement(By.cssSelector("div.growl-message"));
        Assert.assertEquals("Настройки обновлены.", growlMessage.getText(), "Unexpected growl message");
        log("Message text is: " + "'" + growlMessage.getText() + "'.");
        driver.findElement(By.cssSelector("div.growl-close")).click();
        log("Close growl message.");
    }

    private static void log(String message) {
        Reporter.log(message + "<br>");
    }
}
