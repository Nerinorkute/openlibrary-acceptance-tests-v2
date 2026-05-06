package pageObject;

import static io.restassured.RestAssured.given;

public class OpenLibraryApiClient {

    private static final String BASE_URI = "https://openlibrary.org";

    /**
     * Resolves the author name for a given book code by making two sequential API calls:
     * 1. GET /books/{bookCode}.json  — extracts the author key (e.g. /authors/OL5039268A)
     * 2. GET /authors/{authorCode}.json — extracts the author name (e.g. "Margery Williams Bianco")
     *
     * @param bookCode the OpenLibrary book code (e.g. OL18522220M)
     * @return the full author name as stored in the OpenLibrary API
     */
    public String getAuthorNameByBookCode(String bookCode) {
        String authorKey = given()
                .baseUri(BASE_URI)
                .when()
                .get("/books/" + bookCode + ".json")
                .then()
                .statusCode(200)
                .extract()
                .path("authors[0].key");

        String[] parts = authorKey.split("/");
        if (parts.length < 3) {
            throw new RuntimeException("Unexpected author key format: " + authorKey);
        }
        String authorCode = parts[2];

        return given()
                .baseUri(BASE_URI)
                .when()
                .get("/authors/" + authorCode + ".json")
                .then()
                .statusCode(200)
                .extract()
                .path("name");
    }
}
