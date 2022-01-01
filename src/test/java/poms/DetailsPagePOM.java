package poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DetailsPagePOM extends ProductPagePOM{
    public DetailsPagePOM(WebDriver driver) {
        super(driver);
    }

        @FindBy(id = "back-to-products")
        public WebElement backToProductsButton;

}
