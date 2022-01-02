package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import poms.DescriptionPagePOM;
import utils.BaseDriver;

import java.util.List;
import java.util.Random;

public class DescriptionPageTestCases extends BaseDriver {
    DescriptionPagePOM pom;
    @BeforeClass
    public void login(){
        pom = new DescriptionPagePOM(driver);
        String username = "standard_user";
        String password = "secret_sauce";
        pom.usernameInput.clear();
        pom.usernameInput.sendKeys(username);
        pom.passwordInput.clear();
        pom.passwordInput.sendKeys(password);
        pom.loginButton.click();
    }

    @BeforeClass(dependsOnMethods = {"login"})
    public void selectSomeInventory(){
        Random random = new Random();
        List<WebElement> items;
        int numberOfItems=3;
        for (int i=0;i<numberOfItems;i++) {
            items = pom.addToCartButtons;
            int idxOfItem = random.nextInt(items.size());
            items.get(idxOfItem).click();
        }
    }

    @BeforeClass(dependsOnMethods = {"login", "selectSomeInventory"})
    public void clickShoppingCartContainer(){
        pom.shoppingCartContainer.click();
        pom.checkoutbutton.click();
        pom.firstNameBox.sendKeys("Muster");
        pom.lastNameBox.sendKeys("Musterman");
        pom.postalCodeBox.sendKeys("67000");
        pom.continueButton.click();

    }

    @Test
    public void ItemPriseVerificationTestCase(){
        List<WebElement> selectedItems = driver.findElements(By.className("cart_item_label"));
        for(int i=0;i<selectedItems.size();i++){
            String itemName = driver.findElements(By.className("inventory_item_name")).get(i).getText();
            String itemPriceInCart = driver.findElements(By.className("inventory_item_price")).get(i).getText();
            waitAndClick(pom.menuButton);
            waitAndClick(pom.inventorySidebarLink);
            // xpath in this locator starts from item name then finds item prise
            //  "//div[text()='Test.allTheThings() T-Shirt (Red)']//following::div//following::div/div"
            String itemPriseInInventory = driver.findElement(By.xpath("//div[text()='" + itemName + "']" +
                                                                    "//following::div//following::div/div")).getText();
            Assert.assertEquals(itemPriseInInventory,itemPriceInCart);
            driver.navigate().back();
        }
    }

    @Test
    public void ItemTotalVerificationTestCase() {
        List<WebElement> selectedItems = driver.findElements(By.className("cart_item_label"));
        double sumOfPrices = 0.0d;
        for(int i=0;i<selectedItems.size();i++){
            String strItemPrice = driver.findElements(By.className("inventory_item_price")).get(i).getText();
            Double doubleItemPrice = getDoubleFromText(strItemPrice);
            sumOfPrices+=doubleItemPrice;
        }
        String strItemTotal = driver.findElement(By.className("summary_subtotal_label")).getText();
        double itemTotal = getDoubleFromText(strItemTotal);
        Assert.assertEquals(itemTotal, sumOfPrices,0.01);
    }

    @Test
    public void taxCalculationVerificationTestCase(){
        double itemTotal = getDoubleFromText(driver.findElement(By.className("summary_subtotal_label")).getText());
        double summaryTax = getDoubleFromText(driver.findElement(By.className("summary_tax_label")).getText());
        Assert.assertEquals(summaryTax, itemTotal*0.08, 0.01);
    }

    @Test
    public void TotalVerificationTestCase(){
        double itemTotal = getDoubleFromText(driver.findElement(By.className("summary_subtotal_label")).getText());
        double summaryTax = getDoubleFromText(driver.findElement(By.className("summary_tax_label")).getText());
        double total = getDoubleFromText(driver.findElement(By.className("summary_total_label")).getText());
        Assert.assertEquals(total, itemTotal+summaryTax, 0.01);
    }


}
