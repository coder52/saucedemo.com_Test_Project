package poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductPagePOM extends MenuBtnPOM{
    public ProductPagePOM(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "product_sort_container")
    public WebElement productSortContainer;

    @FindBy(className = "inventory_item_name")
    public List<WebElement> inventoryItemNames;

    @FindBy(className = "inventory_item_price")
    public List<WebElement> inventoryItemPrices;

    @FindBy(xpath = "//button[text()='Add to cart']")
    public List<WebElement> addToCartButtons;

    @FindBy(id = "shopping_cart_container")
    public WebElement shoppingCartContainer;

    @FindBy(xpath = "//button[text()='Remove']")
    public List<WebElement> removeButtons;

    @FindBy(className = "inventory_details_name")
    public WebElement inventoryItemNameInDetailsPage;

}
