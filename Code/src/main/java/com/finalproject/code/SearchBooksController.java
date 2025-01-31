package com.finalproject.code;

import com.finalproject.code.classes.Book;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SearchBooksController {

    //Variables
    String googleBooksApiKey = "AIzaSyCix_ZeHki89qIH0zsaPs6dZGflpLqz7J8"; // TODO move to environment variables later

    // Reference to the UI components
    @FXML
    private TextField searchBar;
    @FXML
    private FlowPane bookFlowPane;
    @FXML
    private Label snackbarLabel;

    @FXML
    public void searchBooks() {

        // Get the text input from the search bar
        String query = searchBar.getText().trim();

        // if the text input is empty do nothing
        if (query.isEmpty()) {
            return;
        }

        // Set up the http client for accessing web APIs
        OkHttpClient client = new OkHttpClient();

        // Set up a request to the Google Books API passing the search bar input as a query
        Request request = new Request.Builder()
                .url("https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=40&key=" + googleBooksApiKey)
                .build();

        // Send the request to the API and get a response
        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();

            // Convert the response to a JSON object
            JSONObject jsonObject = new JSONObject(body);

            // Clear the list of books from the previous query
            bookFlowPane.getChildren().clear();

            // Check if there are any books available that match the query
            if (jsonObject.has("items")) {
                JSONArray booksArray = jsonObject.getJSONArray("items");

                // Go through all the book items returned by the API
                for (int i = 0; i < booksArray.length(); i++) {
                    JSONObject bookJson = booksArray.getJSONObject(i);
                    JSONObject volumeInfo = bookJson.getJSONObject("volumeInfo");

                    // Get the book data
                    String title = volumeInfo.optString("title", "None");
                    String author = volumeInfo.has("authors") ? volumeInfo.getJSONArray("authors").getString(0) : "Unknown";
                    String genre = volumeInfo.has("categories") ? volumeInfo.getJSONArray("categories").getString(0) : "Unknown";
                    int pageCount = volumeInfo.optInt("pageCount", 0);
                    String coverUrl = volumeInfo.has("imageLinks") ? volumeInfo.getJSONObject("imageLinks").optString("smallThumbnail", null) : null;

                    // Create a book object using extracted data
                    Book book = new Book(title, author, genre, pageCount, coverUrl);

                    // Load the book view for each book
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/finalproject/code/book-view.fxml"));
                    Parent bookView = loader.load();

                    // Set the book data through the book controller
                    BookController controller = loader.getController();
                    controller.setBookData(book);

                    // Pass the reference of itself to the Book Controller
                    controller.setSearchBooksController(this);

                    // Add the populated book view to the flow pane
                    bookFlowPane.getChildren().add(bookView);
                }
            } else {
                showSnackbar("No books found");
            }
        } catch (IOException | JSONException error) {
            showSnackbar("Something went wrong");
        }
    }

    public void showSnackbar(String message) {

        // Set the message on the snackbar
        snackbarLabel.setText(message);

        // Fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), snackbarLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Set up a new thread to show the snackbar
        fadeIn.setOnFinished(_ -> {
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Snackbar will show for two seconds
                } catch (InterruptedException ignore) {}

                // Fade-out animation
                FadeTransition fadeOut = new FadeTransition(Duration.millis(300), snackbarLabel);
                fadeOut.setFromValue(1);
                fadeOut.setToValue(0);
                fadeOut.play();
            }).start();
        });
        fadeIn.play();
    }
}
