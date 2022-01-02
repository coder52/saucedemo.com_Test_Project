package testCases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import poms.CartAndInfoPagePOM;
import utils.BaseDriver;

import java.util.List;
import java.util.Random;

public class CartAndInfoPagesTestCases extends BaseDriver {
    CartAndInfoPagePOM pom;
    @BeforeClass
    public void login(){
        pom = new CartAndInfoPagePOM(driver);
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
    }

    @Test(priority = 1)
    public void continueShoppingButtonTestCase(){
        pom.continueShoppingButton.click();
        String actualText = pom.titleProducts.getText();
        Assert.assertEquals(actualText, "PRODUCTS");
        driver.navigate().back();
    }

    @Test(priority = 2)
    public void removeButtonInCartPageTestCase(){
        String itemCountText = pom.shoppingCartContainer.getText();
        int beforeClick = Integer.parseInt(itemCountText);// number of items in shopping cart before click on remove button
        pom.removeButtons.get(0).click();
        itemCountText = pom.shoppingCartContainer.getText();
        int afterClick;
        if(!(itemCountText.equals(""))) {
            afterClick = Integer.parseInt(itemCountText);// number of items in shopping cart after click on remove button
        } else {
            afterClick = 0;
        }
        Assert.assertEquals(afterClick-beforeClick,-1);
    }

    @Test(priority = 3)
    public void checkoutButtonTestCase(){
        pom.checkoutbutton.click();
        String header = pom.headerContainer.getText();
        Assert.assertTrue(header.contains("YOUR INFORMATION"));
    }
    // here beginning "Your Information" Page Tests
    @Test(priority = 4, dependsOnMethods = {"checkoutButtonTestCase"})
    public void cancelButtonTestCase(){
        pom.cancelButton.click();
        String cartDescLabel = pom.cartDescriptionLabel.getText();
        Assert.assertEquals(cartDescLabel,"DESCRIPTION");
        driver.navigate().back();
    }

    @Test(priority = 5, dependsOnMethods = {"checkoutButtonTestCase"})
    public void formAndContinueButtonTestCase(){
        pom.firstNameBox.sendKeys("Muster");
        pom.lastNameBox.sendKeys("Musterman");
        pom.postalCodeBox.sendKeys("67000");
        pom.continueButton.click();
        String cartDescLabel = pom.cartDescriptionLabel.getText();
        Assert.assertEquals(cartDescLabel,"DESCRIPTION");
    }
}
