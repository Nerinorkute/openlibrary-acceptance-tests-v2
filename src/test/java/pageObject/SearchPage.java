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
     * XPath template matching a search result by exact title and publication year.
     * First %s = title, second %s = year.
     */
    private static final String RESULT_ITEM_XPATH =
            "//li[contains(@class,'searchResultItem')]" +
                    "[.//a[normalize-space(.)='%s']]" +
                    "[.//*[contains(.,'%s')]]";

    // --- Constructor ---

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    // --- Actions ---

    /**
     * Finds the search result matching title and year,
     * extracts the book code from the href attribute.
     * href format: /works/OL.../Title?edition=key%3A/books/OL39029829M
     */
    public String getBookCodeFromResult(String title, String year) {
        WebElement bookLink = getBookItem(title, year)
                .findElement(By.cssSelector("div.resultTitle a"));
        String href = bookLink.getAttribute("href");
        return href.replaceAll(".*books/([^?&\"]+).*", "$1");
    }

    /**
     * Finds the search result matching title and year,
     * returns the author name displayed in the result.
     */
    public String getAuthorNameFromResult(String title, String year) {
        WebElement authorLink = getBookItem(title, year)
                .findElement(By.cssSelector("span.bookauthor a"));
        return authorLink.getText();
    }

    /**
     * Waits for and returns the search result item matching both title and year.
     */
    private WebElement getBookItem(String title, String year) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format(RESULT_ITEM_XPATH, title, year))
        ));
    }
}
