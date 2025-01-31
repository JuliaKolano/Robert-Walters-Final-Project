package com.finalproject.code;

import com.finalproject.code.utilities.DatabaseUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class SignUpController {

    // Reference to the UI components
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField repeatPasswordField;
    @FXML
    private Label errorMessage;

    // Directs the user to the login page
    @FXML
    public void onLoginButtonClick(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("login.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException error) {
            errorMessage.setText("There was a problem loading the page");
        }
    }

    // Create the user account and direct them to the login page
    @FXML
    public void onSignUpButtonClick(ActionEvent event) {
        // Get the user input from the text fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();

        // Make the user provide some input
        if (username.isEmpty()) {
            errorMessage.setText("Username is required.");
        } else if (password.isEmpty()) {
            errorMessage.setText("Password is required.");
        } else if (repeatPassword.isEmpty() || !repeatPassword.equals(password)) {
            // Make sure the two inputted passwords match
            errorMessage.setText("Passwords do not match.");
        } else if (username.length() > 15) {
            // Database doesn't allow for more than 15 characters
            errorMessage.setText("Username cannot be longer than 15 characters.");
        } else if (!validUsername(username)) {
            errorMessage.setText("Username already exists.");
        } else if (password.length() > 20) {
            // Database doesn't allow for more than 20 characters
            errorMessage.setText("Password cannot be longer than 20 characters.");
        } else if (!validPassword(password)) {
            errorMessage.setText("Password must be at least 8 characters long and contain at least one number and capital letter.");
        } else if (validUsername(username) && validPassword(password)) {
            // If all requirements pass then create user's account

            // Clear the error message
            errorMessage.setText("");

            try {
                // Add user's credentials to the database
                DatabaseUtility.createUser(username, password);
            } catch (SQLException error) {
                errorMessage.setText("There was a problem creating your account");
            }

            try {
                // Direct the user to the login page
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("login.css")).toExternalForm());
                stage.setScene(scene);
                stage.show();
            } catch (IOException error) {
                errorMessage.setText("There was a problem loading the page");
            }

        } else {
            errorMessage.setText("Invalid username or password.");
        }
    }

    // Check if the username is valid
    private boolean validUsername(String username) {
        try {
            // if the database returns a username, username is invalid, otherwise it is (hasn't been used before)
            return DatabaseUtility.getUsername(username) == null;
        } catch (SQLException error) {
            errorMessage.setText("Couldn't connect to the server");
            return false;
        }
    }

    // Check if the password is valid
    private boolean validPassword(String password) {
        return password.length() >= 8 && // must be at least 8 characters long
                password.matches(".*\\d.*") && // must have at least one number
                password.matches(".*[A-Z].*"); // must have at least one capital letter
    }
}
