package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {

    // --- Locators ---

    private static final By SEARCH_FIELD     = By.name("q");
    private static final By LANGUAGE_SUMMARY = By.cssSelector("div.language-component details summary");
    private static final By ENGLISH_OPTION   = By.cssSelector("a[data-lang-id='en']");

    // --- Constructor ---

    public MainPage(WebDriver driver) {
        super(driver);
        driver.get("https://openlibrary.org/");
    }

    // --- Actions ---

    /**
     * Switches the site language to English by opening the language dropdown
     * and selecting the English option.
     * If the site is already in English, the selector may not be present — handled silently.
     */
    public void setLanguageToEnglish() {
        try {
            WebElement dropdown = driver.findElement(LANGUAGE_SUMMARY);
            if (dropdown.isDisplayed()) {
                dropdown.click();
                driver.findElement(ENGLISH_OPTION).click();
            }
        } catch (Exception ignored) {
            // Already in English or language selector not present
        }
    }

    /**
     * Types the given title into the search field, submits the search,
     * and returns the resulting SearchPage.
     */
    public SearchPage userSearchesForBookByTitle(String title) {
        driver.findElement(SEARCH_FIELD).sendKeys(title);
        driver.findElement(SEARCH_FIELD).sendKeys(Keys.ENTER);
        return new SearchPage(driver);
    }
}