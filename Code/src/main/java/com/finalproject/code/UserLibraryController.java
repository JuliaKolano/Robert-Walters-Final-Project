package com.finalproject.code;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class UserLibraryController {
    @FXML
    private ImageView profilePicture;

    @FXML Label userName;

    @FXML
    public void initialize() {
        // Set the user's profile picture
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("user.png")));
        profilePicture.setImage(image);

        // Set the user's name
        userName.setText("Julia Kolano");
    }
}