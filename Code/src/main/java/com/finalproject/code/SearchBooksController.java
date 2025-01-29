package com.finalproject.code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SearchBooksController {

    //Variables
    String googleBooksApiKey = "AIzaSyCix_ZeHki89qIH0zsaPs6dZGflpLqz7J8"; // TODO move to environment variables later

    // Reference to the UI components
    @FXML
    private TextField searchBar;

    @FXML
    public void searchBooks() {

        // Get the text input from the search bar
        String query = searchBar.getText();

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

            if (jsonObject.has("items")) {
                JSONArray books = jsonObject.getJSONArray("items");

                for (int i = 0; i < books.length(); i++) {
                    JSONObject book = books.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                    // Get the cover url
                    try {
                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                        String coverURL = imageLinks.getString("smallThumbnail");
                        System.out.println(coverURL);
                    } catch (JSONException error) {
                        System.out.println("No cover found");
                    }

                    // Get title
                    try {
                        String title = volumeInfo.getString("title");
                        System.out.println(title);
                    } catch (JSONException error) {
                        System.out.println("No title");
                    }

                    // Get the author
                    try {
                        JSONArray authors = volumeInfo.getJSONArray("authors");
                        String author = authors.getString(0);
                        System.out.println(author);
                    } catch (JSONException error) {
                        System.out.println("No author");
                    }

                    // Get the genre
                    try {
                        JSONArray genres = volumeInfo.getJSONArray("categories");
                        String genre = genres.getString(0);
                        System.out.println(genre);
                    } catch (JSONException error) {
                        System.out.println("No genre");
                    }

                    // Get the number of pages
                    try {
                        String pages = volumeInfo.getString("pageCount");
                        System.out.println(pages);
                    } catch (JSONException error) {
                        System.out.println("No page count");
                    }
                }
            } else {
                System.out.println("No books found");
            }

        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
