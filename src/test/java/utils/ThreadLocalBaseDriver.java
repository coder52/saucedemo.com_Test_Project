package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Optional;

public class ThreadLocalBaseDriver {
    private static ThreadLocal<WebDriver> webDriverHolder = new ThreadLocal<>();
    private static String browserName = "chrome";

    // webDriverHolder.get() ==> returns WebDriver inside
    // webDriverHolder.set(WebDriver) ==> will set WebDriver inside
    public static WebDriver getDriver() {
        if (webDriverHolder.get() == null) {
            switch (browserName) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    webDriverHolder.set(new FirefoxDriver());
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    webDriverHolder.set(new EdgeDriver());
                    break;
                default:
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    webDriverHolder.set(new ChromeDriver());
                    break;
            }
        }
        return webDriverHolder.get();
    }

    public static void quitDriver() {
        if (webDriverHolder.get() != null) {
            webDriverHolder.get().quit();
            webDriverHolder.set(null);
        }
    }
    public static void setBrowser(String browser) {

        browserName = browser;

    }

}
