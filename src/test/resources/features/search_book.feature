Feature: Validate that author retrieved from API matches the author displayed on the book page

  Scenario Outline: Author from API matches author on book page
    Given user opens OpenLibrary page
    And user sets website language to English
    When user searches for a book by title "<title>"
    And user selects book with title "<title>" published in "<year>"
    And user retrieves author from API
    Then author from API matches author on book page

    Examples:
      | title                | year |
      | Hello Stranger       | 2023 |
      | Hello Stranger       | 2018 |
      | The Velveteen Rabbit | 1900 |
      | The Velveteen Rabbit | 1989 |