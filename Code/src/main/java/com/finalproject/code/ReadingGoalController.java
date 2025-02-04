package com.finalproject.code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ReadingGoalController {
    @FXML
    private ToggleButton readingGoalToggle;

    @FXML
    private void onReadingGoalToggled() {
        if (readingGoalToggle.isSelected()) {
            readingGoalToggle.setText("Yes");
        } else {
            readingGoalToggle.setText("No");
        }
    }
}
