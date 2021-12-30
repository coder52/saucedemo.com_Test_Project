package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class BaseDriver {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(String browser){
        if(browser.equals("firefox")){
            ThreadLocalBaseDriver.setBrowser("firefox");}
        driver = ThreadLocalBaseDriver.getDriver();
        driver.get("https://www.saucedemo.com/");
        wait = new WebDriverWait(driver, 10);
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver(){
        ThreadLocalBaseDriver.quitDriver();
    }

    @DataProvider(name = "credentialsProvider")
    public Object[][] data() {
        return new Object[][]{
                {"standard_user", "secret_sauce", "success"},
                {"locked_out_user", "secret_sauce", "error"},
                {"problem_user", "secret_sauce", "problem"},
                {"performance_glitch_user", "secret_sauce", "delay"}

        };
    }

    public void clearTextBoxAndSendKeys(WebElement element, String keys){
        element.clear();
        element.sendKeys(keys);
    }

    public void waitAndClick(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }
}
