package com.finalproject.code;

import com.finalproject.code.utilities.DatabaseUtility;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static javafx.application.Platform.exit;

public class Application extends javafx.application.Application {

    // Set up the database connection when application starts
    @Override
    public void init() {
        try {
            DatabaseUtility.getConnection();
        } catch (Exception e) {
            Application.showAlert("Database error", "There was a problem connecting to the database");
        }
    }

    // Set up the stage and load the first scene when after application started
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("login.css")).toExternalForm());
            stage.setTitle("Book Library");
            stage.setScene(scene);
            stage.show();
        } catch (IOException error) {
            // If the first scene cannot be loaded, close the application
            showAlert("Application couldn't start", "An error occurred while trying to start the application.");
        }
    }

    // Close the database connection when application stops
    @Override
    public void stop() {
        try {
            Connection connection = DatabaseUtility.getConnection();
            // Check if the connection has already been closed, if not close it
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException error) {
            showAlert("Database error", "An error occurred while trying to close the database.");
        }
    }

    // A pop-up alert if a fatal error occurs
    public static void showAlert(String header, String content) {
        Platform.runLater(() -> {
            // Set up the alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(content);

            // Close the alert and the application
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        });
    }

    // Launch the application
    public static void main(String[] args) {
        launch();
    }
}