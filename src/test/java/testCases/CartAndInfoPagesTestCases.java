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

    @Test(priority = 5)
    public void continueShoppingButtonTestCase(){
        pom.shoppingCartContainer.click();
        pom.continueShoppingButton.click();
        String actualText = pom.titleProducts.getText();
        Assert.assertEquals(actualText, "PRODUCTS");
        driver.navigate().back();
    }

    @Test(priority = 5)
    public void removeButtonInCartPageTestCase(){
        pom.shoppingCartContainer.click();
        String itemCountText = pom.shoppingCartContainer.getText();
        int beforeClick = 0;
        if(!itemCountText.equals("")){
            beforeClick = Integer.parseInt(itemCountText);// number of items in shopping cart before click on remove button
        }
        waitAndClick(pom.removeButtons.get(0));
        itemCountText = pom.shoppingCartContainer.getText();
        int afterClick = 0;
        if(!(itemCountText.equals(""))) {
            afterClick = Integer.parseInt(itemCountText);// number of items in shopping cart after click on remove button
        }
        Assert.assertEquals(afterClick-beforeClick,-1);
    }

    @Test(priority = 5)
    public void checkoutButtonTestCase(){
        pom.shoppingCartContainer.click();
        waitAndClick(pom.checkoutbutton);
        String header = pom.headerContainer.getText();
        Assert.assertTrue(header.contains("YOUR INFORMATION"));
    }
    // here beginning "Your Information" Page Tests
    @Test(priority = 5)
    public void cancelButtonTestCase(){
        pom.shoppingCartContainer.click();
        waitAndClick(pom.checkoutbutton);
        pom.cancelButton.click();
        String cartDescLabel = pom.cartDescriptionLabel.getText();
        Assert.assertEquals(cartDescLabel,"DESCRIPTION");
        driver.navigate().back();
    }

    @Test(priority = 5)
    public void formAndContinueButtonTestCase(){
        pom.shoppingCartContainer.click();
        waitAndClick(pom.checkoutbutton);
        pom.firstNameBox.sendKeys("Muster");
        pom.lastNameBox.sendKeys("Musterman");
        pom.postalCodeBox.sendKeys("67000");
        pom.continueButton.click();
        String cartDescLabel = pom.cartDescriptionLabel.getText();
        Assert.assertEquals(cartDescLabel,"DESCRIPTION");
    }
}
