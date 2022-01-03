package testCases;

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

    @Test(priority = 2)
    public void logoutTestCase(){
        waitAndClick(pom.menuButton);
        waitAndClick(pom.logoutSidebarLink);
        String actualLoginValue = pom.loginButton.getAttribute("value").toLowerCase();
        String expectedLoginValue = "login";
        Assert.assertEquals(actualLoginValue, expectedLoginValue);
        login();
    }

    @Test(priority = 2)
    public void aboutTestCase(){
        waitAndClick(pom.menuButton);
        waitAndClick(pom.aboutSidebarLink);
        System.out.println(driver.getCurrentUrl());
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://saucelabs.com/";
        Assert.assertEquals(actualUrl, expectedUrl);
        driver.navigate().back();
    }

    @Test(priority = 2)
    public void inventoryTestCase(){ // All Items test case
        waitAndClick(pom.shoppingCartContainer);
        waitAndClick(pom.menuButton);
        waitAndClick(pom.inventorySidebarLink);
        wait.until(ExpectedConditions.presenceOfElementLocated(pom.titleProductsLocator));
        String delayText = waitAndGetText(pom.titleProducts);
        Assert.assertEquals(delayText.toLowerCase(), "products");
    }
}
