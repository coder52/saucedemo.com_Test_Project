package testCases;

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

        clearTextBoxAndSendKeys(pom.usernameInput, username);
        clearTextBoxAndSendKeys(pom.passwordInput, password);
        waitAndClick(pom.loginButton);
        switch (expectedBehavior){
            case "success":
                String text = waitAndGetText(pom.titleProducts);
                Assert.assertEquals(text.toLowerCase(), "products");
                driver.navigate().back();
                break;
            case "error":
                String errText = waitAndGetText(pom.errorMessage);
                Assert.assertTrue(errText.contains("Sorry"));
                waitAndClick(pom.errorCancel);
                break;
            case "problem":
                String problemText = waitAndGetText(pom.anyTextInProblemPage);
                Assert.assertTrue(problemText.toLowerCase().contains("sleek"));
                driver.navigate().back();
                break;
            case " delay":
                wait.until(ExpectedConditions.presenceOfElementLocated(pom.titleProductsLocator));
                String delayText = waitAndGetText(pom.titleProducts);
                Assert.assertEquals(delayText.toLowerCase(), "products");
                driver.navigate().back();
                break;
        }
    }




}
