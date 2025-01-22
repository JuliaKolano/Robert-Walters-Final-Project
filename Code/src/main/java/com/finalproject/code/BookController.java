package com.finalproject.code;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class BookController {

    // Reference to the UI components
    @FXML
    private ImageView bookCover;

    public void initialize() {
        // Set the book cover
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("bookCover.jpg")));
        bookCover.setImage(image);
    }

}
