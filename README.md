# OpenLibrary Acceptance Tests

Acceptance test suite for [OpenLibrary](https://openlibrary.org) validating that author data retrieved from the API matches the author displayed on the book page.

## Tech Stack

- Java
- Selenium WebDriver
- REST Assured
- Cucumber (BDD)
- JUnit 5
- Gradle
- WebDriverManager

## Test Scenario

The test:
- searches for a book by title,
- selects a specific edition by publication year,
- retrieves the author via API,
- validates that the API author matches the author displayed on the UI.

## How to Run

bash
./gradlew test

## Project Structure

```text
src/test/java/
├── pageObject/
│   ├── BasePage
│   ├── BookPage
│   ├── MainPage
│   ├── OpenLibraryApiClient
│   └── SearchPage
│
├── runner/
│   └── TestRunner
│
└── stepDescriptions/
    └── FindBookSteps

src/test/resources/
└── features/
    └── search_book.feature
    
