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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SideMenuController {

    // Reference to the UI components
    @FXML
    private ImageView profilePicture;

    @FXML
    private Label userName;

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

    // Directs the user to their profile page
    @FXML
    public void onUserProfileButtonClick(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user-profile-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("home.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // Directs the user to their library page
    @FXML
    public void onMyLibraryButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user-library-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("home.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // Directs the user to the search books page
    @FXML
    public void onSearchBooksButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-books-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("home.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // Directs the user to their reading goal page
    @FXML
    public void onReadingGoalButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reading-goal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("home.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // Directs the user to the recommendations page
    @FXML
    public void onRecommendationsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recommendations-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("home.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // Directs the user to their friends page
    @FXML
    public void onFriendsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("friends-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("home.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
