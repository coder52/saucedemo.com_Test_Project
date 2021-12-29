package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseDriver {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    public void setup(){
        driver = ThreadLocalBaseDriver.getDriver();
        driver.get("https://www.saucedemo.com/");
        wait = new WebDriverWait(driver, 10);
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver(){
        driver.quit();
    }
}
