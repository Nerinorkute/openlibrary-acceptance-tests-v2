package pageObject;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    protected WebDriver driver;

    /** Default wait timeout in seconds used across all page classes. */
    protected static final int DEFAULT_TIMEOUT = 20;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
}