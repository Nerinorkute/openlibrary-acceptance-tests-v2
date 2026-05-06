package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookPage extends BasePage {

    // --- Locators ---

    /** Matches the author link on a book edition page using the schema.org itemprop attribute. */
    private static final By AUTHOR_LINK = By.cssSelector("a[itemprop='author']");

    // --- Constructor ---

    public BookPage(WebDriver driver) {
        super(driver);
    }

    // --- Actions ---

    /**
     * Extracts the book code (e.g. OL18522220M) from the current URL.
     * Expected URL format: https://openlibrary.org/books/{bookCode}/{title}
     */
    public String getBookCodeFromUrl() {
        String url = driver.getCurrentUrl();
        return url.replaceAll(".*/books/([^/]+).*", "$1");
    }

    /**
     * Returns the author name displayed on the book edition page.
     */
    public String getAuthorNameFromPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        WebElement author = wait.until(ExpectedConditions.visibilityOfElementLocated(AUTHOR_LINK));
        return author.getText();
    }
}