package com.finalproject.code;

import com.finalproject.code.classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SideMenuController {

    // Reference to the UI components
    // Set to public for tests to work
    @FXML
    public ImageView profilePicture;
    @FXML
    public Label userName;
    @FXML
    public Label errorMessage;

    @FXML
    public void initialize() {
        // Initialise the logged-in user's information from the user object

        // Get the singleton instance of the user object
        User user = User.getInstance();

        // If the user exists, initialize their details
        if (user != null) {
            // Set the profile picture displayed
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(user.getProfilePicturePath())));
            profilePicture.setImage(image);
            // Set the username displayed
            userName.setText(user.getUsername());
        } else {
            // Use default values if the user doesn't exist
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("user.png")));
            profilePicture.setImage(image);
            userName.setText("Unknown User");
        }
    }

    // Directs the user to their library page
    @FXML
    public void onMyLibraryButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user-library-view.fxml"));
            loadScene(event, fxmlLoader);
        } catch (IOException error) {
            errorMessage.setText("There was a problem loading the page");
            errorMessage.setVisible(true);
        }
    }

    // Directs the user to the search books page
    @FXML
    public void onSearchBooksButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-books-view.fxml"));
            loadScene(event, fxmlLoader);
        } catch (IOException error) {
            errorMessage.setText("There was a problem loading the page");
            errorMessage.setVisible(true);
        }
    }

    // Directs the user to their reading goal page
    @FXML
    public void onReadingGoalButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reading-goal-view.fxml"));
            loadScene(event, fxmlLoader);
        } catch (IOException error) {
            errorMessage.setText("There was a problem loading the page");
            errorMessage.setVisible(true);
        }
    }

    private void loadScene(ActionEvent event, FXMLLoader fxmlLoader) throws IOException {
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("home.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
