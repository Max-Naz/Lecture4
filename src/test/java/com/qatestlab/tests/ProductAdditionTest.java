package com.qatestlab.tests;

import com.qatestlab.DriverManager;
import com.qatestlab.GeneralActions;
import com.qatestlab.model.ProductData;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;


public class ProductAdditionTest {
    public WebDriver driver;
    public GeneralActions generalActions;



    @Parameters({"driverParam"})
    @BeforeTest
    public void setUp(String browser) {
        driver = DriverManager.getConfiguredDriver(browser);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        generalActions = new GeneralActions(driver);
    }


    @Test(dataProvider = "LoginData")
    public void productCreation(String userEmail, String userPass){
        generalActions.loginToAdminPanel(userEmail, userPass);
        generalActions.openProductCreatePage();
        generalActions.createNewProduct();
    }

    @Test(dependsOnMethods = {"productCreation"})
    public void productDisplay() {
        generalActions.productDisplayCheck();

    }


    /*
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    */


    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() {
        Object[][] data = new Object[1][2];
        data[0][0] = "webinar.test@gmail.com";
        data[0][1] = "Xcg7299bnSmMuRLp9ITw";
        return data;
    }
}
