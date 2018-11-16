package com.qatestlab;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



public class ProductAdditionTest {
    public WebDriver driver;


    @Parameters({"driverParam"})
    @BeforeTest
    public void setUp(String browser) {
        driver = DriverManager.getDriver(browser);
        driver.manage().window().maximize();
    }


    @Test
    public void login(){
        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/index.php?controller=AdminLogin&token=d251f363cceb5a849cb7330938c64dea&redirect=AdminDashboard");
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
