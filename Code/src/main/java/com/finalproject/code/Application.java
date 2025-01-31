package com.finalproject.code;

import com.finalproject.code.utilities.DatabaseUtility;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static javafx.application.Platform.exit;

public class Application extends javafx.application.Application {

    // Set up the database connection when application starts
    @Override
    public void init() {
        DatabaseUtility.getConnection();
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
            exit();
        }
    }

//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("search-books-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("home.css")).toExternalForm());
//        stage.setTitle("Book Library");
//        stage.setScene(scene);
//        stage.show();
//    }

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
            System.out.println(error.getMessage());
        }
    }

    // Launch the application
    public static void main(String[] args) {
        launch();
    }
}