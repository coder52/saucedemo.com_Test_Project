package poms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPagePOM {
    public LoginPagePOM(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='user-name']")
    public WebElement usernameInput;

    @FindBy(xpath = "//input[@data-test='password']")
    public WebElement passwordInput;

    @FindBy(xpath = "//input[contains(@class,'btn_action')]")
    public WebElement loginButton;

    @FindBy(css = "span.title")
    public WebElement titleProducts;

    public By titleProductsLocator = new By.ByCssSelector("span.title");

    @FindBy(xpath = "//h3[@data-test=\"error\"]")
    public WebElement errorMessage;

    @FindBy(className = "error-button")
    public WebElement errorCancel;

    @FindBy(css = "#item_4_title_link~.inventory_item_desc")
    public WebElement anyTextInProblemPage;



}
