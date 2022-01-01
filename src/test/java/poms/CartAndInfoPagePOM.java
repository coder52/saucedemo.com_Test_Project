package poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartAndInfoPagePOM extends DetailsPagePOM{
    public CartAndInfoPagePOM(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "continue-shopping")
    public WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    public WebElement checkoutbutton;

    @FindBy(id = "header_container")
    public WebElement headerContainer;

    @FindBy(id = "cancel")
    public WebElement cancelButton;

    @FindBy(className = "cart_desc_label")
    public WebElement cartDescriptionLabel;

    @FindBy(id = "first-name")
    public WebElement firstNameBox;

    @FindBy(id = "last-name")
    public WebElement lastNameBox;

    @FindBy(id = "postal-code")
    public WebElement postalCodeBox;

    @FindBy(id = "continue")
    public WebElement continueButton;


}
