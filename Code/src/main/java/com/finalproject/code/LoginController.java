package com.finalproject.code;

import com.finalproject.code.classes.User;
import com.finalproject.code.utilities.DatabaseUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    // Reference to the UI components
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorMessage;

    // Directs the user to the sign-up page
    @FXML
    public void onSignUpButtonClick(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sign-up-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("login.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // Login the user to the application and direct them to the home page
    public void onLoginButtonClick(ActionEvent event) throws IOException, SQLException {
        // Get user inputs from the text fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Make the user provide some input
        if (username.isEmpty()) {
            errorMessage.setText("Username is required.");
        } else if (password.isEmpty()) {
            errorMessage.setText("Password is required.");
        } else if (checkCredentials(username, password)) {

            // Create a user object with the provided username
            User.getInstance(username);

            // Take the user to the home page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user-library-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("home.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } else {
            // If the user credentials are not correct
            errorMessage.setText("Invalid username or password.");
        }
    }

    // Check if the user credentials match with the ones in the database
    private boolean checkCredentials(String username, String password) throws SQLException {
        // if the username doesn't exist in the database
        if (DatabaseUtility.getUsername(username) == null) {
            return false;
        } else {
            // return true if the password associated with the username in the database equals to the password provided
            return Objects.equals(DatabaseUtility.getPasswordByUsername(username), password);
        }
    }
}
