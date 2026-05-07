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
import pageObject.MainPage;
import pageObject.SearchPage;

public class FindBookSteps {
    private WebDriver driver;
    private MainPage mainPage;
    private SearchPage searchPage;
    private String currentTitle;
    private String currentYear;
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
        currentTitle = title;
        searchPage = mainPage.userSearchesForBookByTitle(title);
        System.out.println("Book title: " + title);
    }

    @And("user retrieves author from API for book published in {string}")
    public void userRetrievesAuthorFromApiForBookPublishedIn(String year) {
        currentYear = year;
        String bookCode = searchPage.getBookCodeFromResult(currentTitle, currentYear);
        apiAuthorName = new OpenLibraryApiClient().getAuthorNameByBookCode(bookCode);
        System.out.println("API author: " + apiAuthorName);
    }

    @Then("author from API matches author on book page")
    public void authorFromApiMatchesUi() {
        String webAuthorName = searchPage.getAuthorNameFromResult(currentTitle, currentYear);
        System.out.println("WEB author: " + webAuthorName);
        Assertions.assertEquals(
                apiAuthorName,
                webAuthorName,
                "Author mismatch — API: '" + apiAuthorName + "' | WEB: '" + webAuthorName + "'"  
        );
    }


}
