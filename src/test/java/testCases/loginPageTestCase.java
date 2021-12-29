package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.BaseDriver;


public class loginPageTestCase extends BaseDriver {

    @Test(dataProvider = "credentialsProvider")
    public void loginTests(String username, String password, String expectedBehavior) {
        WebElement usernameInput = driver.findElement(By.xpath("//input[@id='user-name']"));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.xpath("//input[@data-test='password']"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        driver.findElement(By.xpath("//input[contains(@class,'btn_action')]")).click();
        switch (expectedBehavior){
            case "success":
                String text = driver.findElement(By.cssSelector("span.title")).getText();
                Assert.assertEquals(text, "PRODUCTS");
                driver.navigate().back();
                break;
            case "error":
                String errText = driver.findElement(By.xpath("//h3[@data-test=\"error\"]")).getText();
                Assert.assertTrue(errText.contains("Sorry"));
                driver.findElement(By.className("error-button")).click();
                break;
            case "problem":
                String problemText = driver.findElement(By.cssSelector("#item_4_title_link~.inventory_item_desc")).getText();
                Assert.assertTrue(problemText.toLowerCase().contains("sleek"));
                driver.navigate().back();
                break;
            case " delay":
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.title")));
                String delayText = driver.findElement(By.cssSelector("span.title")).getText();
                Assert.assertEquals(delayText, "Products");
                driver.navigate().back();
                break;
        }
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

}
