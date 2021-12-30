package poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuBtnPOM extends LoginPagePOM{
    public MenuBtnPOM(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "react-burger-menu-btn")
    public WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    public WebElement logoutSidebarLink;

    @FindBy(id = "about_sidebar_link")
    public WebElement aboutSidebarLink;

    @FindBy(id = "shopping_cart_container")
    public WebElement shoppingCartContainer;

    @FindBy(id = "inventory_sidebar_link")
    public WebElement inventorySidebarLink;

}
