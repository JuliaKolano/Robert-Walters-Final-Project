import com.finalproject.code.SearchBooksController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static javafx.application.Platform.runLater;
import static javafx.application.Platform.startup;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchBooksTest {

    // Set up the mock and the controller
    private static MockWebServer mockWebServer;
    private SearchBooksController searchBooksController;

    // Set up the mock server
    @BeforeAll
    static void startServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    // Set up JavaFX before running the tests
    @BeforeEach
    void setUp() {
        startup(() -> {

            System.setProperty("GOOGLE_BOOKS_API_KEY", "");

            searchBooksController = new SearchBooksController();
            searchBooksController.searchBar = new TextField();
            searchBooksController.bookFlowPane = new FlowPane();
            searchBooksController.snackbarLabel = new Label();
        });
    }

    // Run the tests one by one (Because JavaFX cannot be initialised multiple times in a single run)

    @Test
    void SearchBooksMethodShouldAppendBookObjectsAsChildrenOfTheFlowPaneWhenProvidedWithAValidSearchQuery() {
        runLater(() -> {
            String jsonResponse = "{ \"items\": [ { \"volumeInfo\": { \"title\": \"Effective Java\", \"authors\": [\"Joshua Bloch\"] } } ] }";
            mockWebServer.enqueue(new MockResponse().setBody(jsonResponse).setResponseCode(200));
            searchBooksController.searchBar.setText("Java Programming");
            searchBooksController.searchBooks();
            assertFalse(searchBooksController.bookFlowPane.getChildren().isEmpty());
        });
    }

    @Test
    void SearchBooksMethodShouldReturnNothingWhenTheSearchQueryIsEmpty() {
        runLater(() -> {
            searchBooksController.searchBar.setText("");
            searchBooksController.searchBooks();
            assertTrue(searchBooksController.bookFlowPane.getChildren().isEmpty());
        });
    }

    @Test
    void SearchBooksMethodShouldDisplayANoBooksFoundMessageWhenProvidedWithEmptyResponseBody() {
        runLater(() -> {
            String jsonResponse = "{}";
            mockWebServer.enqueue(new MockResponse().setBody(jsonResponse).setResponseCode(200));
            searchBooksController.searchBar.setText("Unknown Book");
            searchBooksController.searchBooks();
            assertEquals("No books found", searchBooksController.snackbarLabel.getText());
        });
    }

    @Test
    void SearchBooksMethodShouldReturnASnackbarErrorWhenAPIErrorOccurs() {
        runLater(() -> {
            mockWebServer.enqueue(new MockResponse().setResponseCode(500));
            searchBooksController.searchBar.setText("Java Programming");
            searchBooksController.searchBooks();
            assertEquals("Something went wrong", searchBooksController.snackbarLabel.getText());
        });
    }

    @AfterAll
    static void stopServer() throws IOException {
        mockWebServer.shutdown();
    }
}
