package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends BasePage {

    // --- Locators ---

    /**
     * XPath template that matches a search result item by exact title and publication year.
     * %s placeholders are replaced at runtime: first with title, second with year.
     */
    private static final String BOOK_LINK_XPATH =
            "//li[contains(@class,'searchResultItem')]" +
                    "[.//a[normalize-space(.)='%s']]" +
                    "[.//*[contains(.,'%s')]]//div[@class='resultTitle']//a";

    // --- Constructor ---

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    // --- Actions ---

    /**
     * Waits for a search result matching both the given title and publication year,
     * clicks it, and returns the BookPage that opens.
     */
    public BookPage clickBookByTitleAndYear(String title, String year) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        WebElement bookLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format(BOOK_LINK_XPATH, title, year))
        ));
        bookLink.click();
        return new BookPage(driver);
    }
}