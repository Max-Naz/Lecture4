package com.qatestlab.tests;

import com.qatestlab.DriverManager;
import com.qatestlab.GeneralActions;
import com.qatestlab.model.ProductData;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;


public class ProductAdditionTest {
    public WebDriver driver;
    public GeneralActions generalActions;
    public ProductData newProduct;


    @Parameters({"driverParam"})
    @BeforeTest
    public void setup(String browser) {
        driver = DriverManager.getConfiguredDriver(browser);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        generalActions = new GeneralActions(driver);
        newProduct = ProductData.getProductData();
        Reporter.setEscapeHtml(false);
    }


    @Test(dataProvider = "LoginData")
    public void productCreation(String userEmail, String userPass){
        log("Part A. PRODUCT CREATION.");
        log("Step-1. LOGIN TO ADMIN PANEL.");
        generalActions.loginToAdminPanel(userEmail, userPass);
        log("Step-2. OPEN PRODUCTS PAGE.");
        generalActions.openAdminProductsCatalogPage();
        log("Step-3. OPEN NEW PRODUCT PAGE.");
        generalActions.openNewProductPage();
        log("Step-4. FILL PRODUCT DATA.");
        generalActions.fillNewProductData(newProduct);
        log("Step-5. ACTIVATE PRODUCT.");
        generalActions.activateNewProduct();
        log("Step-6. SAVE PRODUCT.");
        generalActions.saveNewProduct();
    }

    @Test(dependsOnMethods = {"productCreation"})
    public void productDisplay() throws InterruptedException {
        log("Part B. PRODUCT DISPLAY CHECK.");
        log("Step-1. OPEN MAIN PAGE.");
        generalActions.openMainPage();
        log("Step-2. OPEN ALL PRODUCTS PAGE AND CHECK DISPLAYING OF CREATED PRODUCT.");
        generalActions.openAllProductsPage();
        generalActions.checkNewProductDisplay();
        log("Step-3. OPEN PRODUCT PAGE AND CHECK PRODUCT DATA.");
        generalActions.checkNewProductData();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() {
        Object[][] data = new Object[1][2];
        data[0][0] = "webinar.test@gmail.com";
        data[0][1] = "Xcg7299bnSmMuRLp9ITw";
        return data;
    }

    private void log(String message) {
        Reporter.log(message + "<br>");
    }
}
