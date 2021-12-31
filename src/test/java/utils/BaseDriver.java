package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.Iterator;
import java.util.List;

public class BaseDriver {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browser){
        if(browser.equals("firefox")){
            ThreadLocalBaseDriver.setBrowser("firefox");}
        driver = ThreadLocalBaseDriver.getDriver();
        driver.get("https://www.saucedemo.com/");
        wait = new WebDriverWait(driver, 10);
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver(){

//        ThreadLocalBaseDriver.quitDriver();

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
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public String waitAndGetText(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public boolean isSortedString(List<String> listOfStrings) {
        if (listOfStrings.isEmpty() || listOfStrings.size() == 1) {
            return true;
        }

        Iterator<String> iter = listOfStrings.iterator();
        String current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (previous.compareTo(current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }
    public boolean isSortedDouble(List<Double> listOfStrings) {
        if (listOfStrings.isEmpty() || listOfStrings.size() == 1) {
            return true;
        }

        Iterator<Double> iter = listOfStrings.iterator();
        double current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (previous > current) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    public Double getDoubleFromText(String cartPriceText) {
        return Double.valueOf(cartPriceText.replaceAll( "[^\\d.]","" ));
    }
}
