package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import poms.ProductPagePOM;
import utils.BaseDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ProductsPageTestCases extends BaseDriver {
    ProductPagePOM pom;
    @BeforeClass
    public void login(){
        pom = new ProductPagePOM(driver);

        String username = "standard_user";
        String password = "secret_sauce";
        pom.usernameInput.clear();
        pom.usernameInput.sendKeys(username);
        pom.passwordInput.clear();
        pom.passwordInput.sendKeys(password);
        pom.loginButton.click();
    }

    @Test
    public void sortContainerTestCase() {
        for (int i=0;i<4;i++) {
            Select options = new Select(driver.findElement(By.className("product_sort_container")));
            driver.findElement(By.className("product_sort_container")).click();
            String optionText = options.getOptions().get(i).getText();
            options.selectByIndex(i);
            // collect item names in a list
            List<WebElement> items = driver.findElements(By.className("inventory_item_name"));
            List<String> itemNames = new ArrayList<>();
            for(WebElement item:items){
                String itemName = item.getText();
                itemNames.add(itemName);
            }
            //collect item prices in a list
            items = driver.findElements(By.className("inventory_item_price"));
            List<Double> itemPrices = new ArrayList<>();
            for(WebElement item:items){
                Double itemPrice = getDoubleFromText(item.getText());
                itemPrices.add(itemPrice);
            }
            // test each option
            switch (optionText){
                case "Name (A to Z)":
                    Assert.assertTrue(isSortedString(itemNames));
                    break;
                case "Name (Z to A)":
                    Collections.reverse(itemNames); // reverse itemNames list then it must in order A to Z
                    Assert.assertTrue(isSortedString(itemNames));
                    break;
                case "Price (low to high)":
                    Assert.assertTrue(isSortedDouble(itemPrices));
                    break;
                case "Price (high to low)":
                    Collections.reverse(itemPrices);
                    Assert.assertTrue(isSortedDouble(itemPrices));
                    break;
                default:
                    System.out.println("Invalid option name");
                    break;
            }
        }
    }

    @Test
    public void addToCartTestCase(){
        Random random = new Random();
        List<WebElement> items;
        int expected = 3;
        for (int i=0;i<3;i++) {
            items = driver.findElements(By.xpath("//button[text()='Add to cart']"));
            int idxOfItem = random.nextInt(items.size());
            items.get(idxOfItem).click();
        }
        String itemCountText = driver.findElement(By.id("shopping_cart_container")).getText();
        int actual = Integer.parseInt(itemCountText);
        Assert.assertEquals(actual, expected);
    }

    @Test(dependsOnMethods = {"addToCartTestCase"})
    public void removeFromCartTestCase(){
        List<WebElement> removeElements = driver.findElements(By.xpath("//button[text()='Remove']"));
        int size = removeElements.size();
        for(WebElement element:removeElements){
            element.click();
        }
        int sizeAfterRemove = driver.findElements(By.xpath("//button[text()='Remove']")).size();
        Assert.assertEquals(0, sizeAfterRemove);
    }

    @Test
    public void inventoryItemNameLinkTestCase(){
        Random random = new Random();
        List<WebElement> elements;
        for (int i=0;i<3;i++) {
            elements = driver.findElements(By.className("inventory_item_name"));
            int idxOfItem = random.nextInt(elements.size());
            WebElement element = elements.get(idxOfItem);
            String elementName = element.getText();
            element.click();
            String elementNameInLink = driver.findElement(By.className("inventory_details_name")).getText();
            Assert.assertEquals(elementName,elementNameInLink);
            driver.navigate().back();
        }
    }

}
