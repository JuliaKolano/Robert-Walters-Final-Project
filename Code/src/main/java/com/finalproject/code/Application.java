package com.finalproject.code;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("login.css")).toExternalForm());
        stage.setTitle("Book Library");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}