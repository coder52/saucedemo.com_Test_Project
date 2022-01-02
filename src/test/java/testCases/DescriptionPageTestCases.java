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

    @Test(priority = 1)
    public void ItemPriseVerificationTestCase(){
        List<WebElement> selectedItems = pom.cartItemLabels;
        for(int i=0;i<selectedItems.size();i++){
            String itemName = pom.inventoryItemNames.get(i).getText();
            String itemPriceInCart = pom.inventoryItemPrices.get(i).getText();
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

    @Test(priority = 2)
    public void ItemTotalVerificationTestCase() {
        List<WebElement> selectedItems = pom.cartItemLabels;
        double sumOfPrices = 0.0d;
        for(int i=0;i<selectedItems.size();i++){
            Double itemPrice = getDoubleFromText(pom.inventoryItemPrices.get(i).getText());
            sumOfPrices+=itemPrice;
        }
        double summaryTotal = getDoubleFromText(pom.summarySubTotal.getText());
        Assert.assertEquals(summaryTotal, sumOfPrices,0.01);
    }

    @Test(priority = 3)
    public void taxCalculationVerificationTestCase(){
        double summaryTotal = getDoubleFromText(pom.summarySubTotal.getText());
        double summaryTax = getDoubleFromText(pom.summaryTaxLabel.getText());
        Assert.assertEquals(summaryTax, summaryTotal*0.08, 0.01);
    }

    @Test(priority = 4)
    public void TotalVerificationTestCase(){
        double summaryTotal = getDoubleFromText(pom.summarySubTotal.getText());
        double summaryTax = getDoubleFromText(pom.summaryTaxLabel.getText());
        double total = getDoubleFromText(pom.total.getText());
        Assert.assertEquals(total, summaryTotal+summaryTax, 0.01);
    }

    @Test(priority = 5)
    public void finishButtonTestCase(){
        pom.finishButton.click();
        String text = pom.thankYouMessage.getText();
        Assert.assertTrue(text.toLowerCase().contains("thank you"));
    }

    @Test(priority = 6, dependsOnMethods = {"finishButtonTestCase"})
    public void backHomeButtonTestCase(){
        pom.backHomeButton.click();
        String text = waitAndGetText(pom.titleProducts);
        Assert.assertEquals(text.toLowerCase(), "products");
    }
}
