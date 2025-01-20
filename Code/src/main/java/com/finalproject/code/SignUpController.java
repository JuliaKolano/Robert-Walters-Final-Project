package com.finalproject.code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignUpController {

    @FXML
    public void onLoginButtonClick(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // gets the current stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("login.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
