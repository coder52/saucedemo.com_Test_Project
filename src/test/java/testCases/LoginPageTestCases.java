package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import poms.LoginPagePOM;
import utils.BaseDriver;


public class LoginPageTestCases extends BaseDriver {

    /*
     * This test checks' login behavior for different usernames.
     */
    @Test(dataProvider = "credentialsProvider")
    public void loginTests(String username, String password, String expectedBehavior) {
        LoginPagePOM pom = new LoginPagePOM(driver);

        pom.usernameInput.clear();
        pom.usernameInput.sendKeys(username);
        pom.passwordInput.clear();
        pom.passwordInput.sendKeys(password);
        pom.loginButton.click();
        switch (expectedBehavior){
            case "success":
                String text = pom.titleProducts.getText();
                Assert.assertEquals(text, "PRODUCTS");
                driver.navigate().back();
                break;
            case "error":
                String errText = pom.errorMessage.getText();
                Assert.assertTrue(errText.contains("Sorry"));
                pom.errorCancel.click();
                break;
            case "problem":
                String problemText = pom.anyTextInProblemPage.getText();
                Assert.assertTrue(problemText.toLowerCase().contains("sleek"));
                driver.navigate().back();
                break;
            case " delay":
                wait.until(ExpectedConditions.presenceOfElementLocated(pom.titleProductsLocator));
                String delayText = pom.titleProducts.getText();
                Assert.assertEquals(delayText, "Products");
                driver.navigate().back();
                break;
        }
    }




}
