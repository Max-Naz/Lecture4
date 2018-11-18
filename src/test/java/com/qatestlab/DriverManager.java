package com.qatestlab;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class DriverManager {


    public  static WebDriver getDriver(String browser) {
        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver",
                        new File(DriverManager.class.getResource("/geckodriver_0_23_0.exe").getFile()).getPath());
                return new FirefoxDriver();
            case "ie":
                System.setProperty("webdriver.ie.driver",
                        new File(DriverManager.class.getResource("/IEDriverServer_3_14_0.exe").getFile()).getPath());
                return new InternetExplorerDriver();
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver",
                        new File(DriverManager.class.getResource("/chromedriver_2_43.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }

    public static EventFiringWebDriver getConfiguredDriver(String browser) {
        EventFiringWebDriver driver = new EventFiringWebDriver(getDriver(browser));
        driver.register(new EventHandler());
        return driver;
    }
}
