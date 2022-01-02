package poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class DescriptionPagePOM extends CartAndInfoPagePOM{
    public DescriptionPagePOM(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "cart_item_label")
    public List<WebElement> cartItemLabels;

    @FindBy(className = "summary_subtotal_label")
    public WebElement summarySubTotal;

    @FindBy(className = "summary_tax_label")
    public WebElement summaryTaxLabel;

    @FindBy(className = "summary_total_label")
    public WebElement total;

    @FindBy(id = "finish")
    public WebElement finishButton;

    @FindBy(xpath = "//h2[text()= 'THANK YOU FOR YOUR ORDER']")
    public WebElement thankYouMessage;

    @FindBy(id = "back-to-products")
    public WebElement backHomeButton;


}
