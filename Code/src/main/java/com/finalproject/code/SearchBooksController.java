package com.finalproject.code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public class SearchBooksController {

    //Variables
    String googleBooksApiKey = "AIzaSyCix_ZeHki89qIH0zsaPs6dZGflpLqz7J8"; // TODO move to environment variables later

    @FXML
    public void searchBooks() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://www.googleapis.com/books/v1/volumes?q=quilting&maxResults=40&key=" + googleBooksApiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            System.out.println(body);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
