package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import poms.MenuBtnPOM;
import utils.BaseDriver;

public class MenuBtnTestCases extends BaseDriver {
    MenuBtnPOM pom;
    @BeforeClass
    public void login(){
        pom = new MenuBtnPOM(driver);
        String username = "standard_user";
        String password = "secret_sauce";
        pom.usernameInput.clear();
        pom.usernameInput.sendKeys(username);
        pom.passwordInput.clear();
        pom.passwordInput.sendKeys(password);
        pom.loginButton.click();
    }

    @Test
    public void logoutTestCase(){
        waitAndClick(driver.findElement(By.id("react-burger-menu-btn")));
        waitAndClick(driver.findElement(By.id("logout_sidebar_link")));
        String actualLoginValue = pom.loginButton.getAttribute("value").toLowerCase();
        String expectedLoginValue = "login";
        Assert.assertEquals(actualLoginValue, expectedLoginValue);
        login();
    }

    @Test
    public void aboutTestCase(){
        waitAndClick(driver.findElement(By.id("react-burger-menu-btn")));
        waitAndClick(driver.findElement(By.id("about_sidebar_link")));
        System.out.println(driver.getCurrentUrl());
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://saucelabs.com/";
        Assert.assertEquals(actualUrl, expectedUrl);
        driver.navigate().back();
    }

    @Test
    public void inventoryTestCase(){ // All Items test case
        waitAndClick(driver.findElement(By.id("shopping_cart_container")));
        waitAndClick(driver.findElement(By.id("react-burger-menu-btn")));
        waitAndClick(driver.findElement(By.id("inventory_sidebar_link")));
        wait.until(ExpectedConditions.presenceOfElementLocated(pom.titleProductsLocator));
        String delayText = pom.titleProducts.getText();
        Assert.assertEquals(delayText.toLowerCase(), "products");
    }
}
