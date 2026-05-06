package stepDescriptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.OpenLibraryApiClient;
import pageObject.BookPage;
import pageObject.MainPage;
import pageObject.SearchPage;

public class FindBookSteps {

    private WebDriver driver;
    private MainPage mainPage;
    private SearchPage searchPage;
    private BookPage bookPage;
    private String apiAuthorName;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("user opens OpenLibrary page")
    public void userOpensOpenLibraryPage() {
        mainPage = new MainPage(driver);
    }

    @And("user sets website language to English")
    public void userSetsWebsiteLanguageToEnglish() {
        mainPage.setLanguageToEnglish();
    }

    @When("user searches for a book by title {string}")
    public void userSearchesForBookByTitle(String title) {
        searchPage = mainPage.userSearchesForBookByTitle(title);
        System.out.println("Book title: " + title);
    }

    @And("user selects book with title {string} published in {string}")
    public void userSelectsBookWithTitleAndYear(String title, String year) {
        bookPage = searchPage.clickBookByTitleAndYear(title, year);
    }

    @And("user retrieves author from API")
    public void getAuthorFromApi() {
        String bookCode = bookPage.getBookCodeFromUrl();
        apiAuthorName = new OpenLibraryApiClient().getAuthorNameByBookCode(bookCode);
    }

    @Then("author from API matches author on book page")
    public void authorFromApiMatchesUi() {
        String webAuthorName = bookPage.getAuthorNameFromPage();
        System.out.println("API author: " + apiAuthorName);
        System.out.println("WEB author: " + webAuthorName);
        Assertions.assertEquals(
                apiAuthorName.trim().toLowerCase(),
                webAuthorName.trim().toLowerCase(),
                "Author mismatch — API: '" + apiAuthorName + "' | WEB: '" + webAuthorName + "'"
        );
    }
}
