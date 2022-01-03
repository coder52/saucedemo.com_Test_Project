package testCases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import poms.DetailsPagePOM;
import utils.BaseDriver;

import java.util.List;
import java.util.Random;

public class DetailsPageTestCases extends BaseDriver {
    DetailsPagePOM pom;
    @BeforeClass
    public void login(){
        pom = new DetailsPagePOM(driver);
        String username = "standard_user";
        String password = "secret_sauce";
        pom.usernameInput.clear();
        pom.usernameInput.sendKeys(username);
        pom.passwordInput.clear();
        pom.passwordInput.sendKeys(password);
        pom.loginButton.click();
    }

    @BeforeClass(dependsOnMethods = {"login"})
    public void clickRandomInventoryItemName(){
        Random random = new Random();
        List<WebElement> elements = pom.inventoryItemNames;
        int idxOfItem = random.nextInt(elements.size());
        elements.get(idxOfItem).click();
    }

    @Test(priority = 4)
    public void backToProductTestCase(){
        pom.backToProductsButton.click();
        String actualText = pom.titleProducts.getText();
        Assert.assertEquals(actualText, "PRODUCTS");
    }

    @Test(priority = 4)
    public void addToCartTestCase(){
        // Add part
        String itemCountText = pom.shoppingCartContainer.getText();
        int beforeClick = 0; // number of items in shopping cart before click on add to cart button
        if(!(itemCountText.equals(""))) {
            beforeClick = Integer.parseInt(itemCountText);
        }
        waitAndClick(pom.addToCartButtons.get(0));
        itemCountText = pom.shoppingCartContainer.getText();
        int afterClick = 0;
        if(!(itemCountText.equals(""))) {
            afterClick = Integer.parseInt(itemCountText);// number of items in shopping cart after click on remove button
        }
        Assert.assertEquals(afterClick-beforeClick,1);
    }

    @Test (priority = 4, dependsOnMethods = {"addToCartTestCase"})
    public void removeButtonInDetailsPageTestCase(){
        String itemCountText = waitAndGetText(pom.shoppingCartContainer);
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
}
