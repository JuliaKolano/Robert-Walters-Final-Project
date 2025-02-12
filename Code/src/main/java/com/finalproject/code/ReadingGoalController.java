package com.finalproject.code;

import com.finalproject.code.classes.ReadingGoal;
import com.finalproject.code.classes.User;
import com.finalproject.code.utilities.DatabaseUtility;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class ReadingGoalController {
    // References to UI elements
    // Set to public for tests to work
    @FXML
    public TextField readingGoalField;
    @FXML
    public ToggleButton readingGoalToggle;
    @FXML
    public HBox readingGoalReached;
    @FXML
    public Label readingGoalMessage;
    @FXML
    public Label errorMessage;
    @FXML
    public Label snackbarLabel;

    @FXML
    public void onReadingGoalToggled() {
        // Change the text depending on the answer toggled
        // Update the reading goal's isReached value in the database accordingly
        if (readingGoalToggle.isSelected()) {
            try {
                readingGoalToggle.setText("Yes");

                assert User.getInstance() != null;
                DatabaseUtility.updateIsReachedAndSetDateOfReadingGoal(User.getInstance().getUsername(), true);

                // Refresh the page
                setUpPage();

            } catch (SQLException e) {
                showSnackbar("Something went wrong when updating the reading goal");
            }
        } else {
            try {
                readingGoalToggle.setText("No");

                assert User.getInstance() != null;
                DatabaseUtility.updateIsReachedAndSetDateOfReadingGoal(User.getInstance().getUsername(), false);

                // Refresh the page
                setUpPage();

            } catch (SQLException e) {
                showSnackbar("Something went wrong when updating the reading goal");
            }
        }
    }

    @FXML
    public void initialize() {
        setUpPage();
    }

    @FXML
    public void onUpdateButtonClicked() {
        // Get the text input from the input box
        String readingGoal = readingGoalField.getText().trim();

        // Clear error messages
        errorMessage.setVisible(false);

        // only continue if there was input from the user
        if (!readingGoal.isEmpty()) {

            // Make sure user input is a number
            try {
                int readingGoalInt = Integer.parseInt(readingGoal);

                // Make sure that the reading goal is greater than zero and smaller than max range of smallint (to protect the database)
                if (readingGoalInt > 0 && readingGoalInt < 32_767) {
                    try {
                        // Update the page count, reached status, and date set of the reading goal in database
                        assert User.getInstance() != null;
                        DatabaseUtility.updatePageCountOfReadingGoal(User.getInstance().getUsername(), readingGoalInt);
                        DatabaseUtility.updateIsReachedAndSetDateOfReadingGoal(User.getInstance().getUsername(), false);
                        setUpPage();

                    } catch (SQLException error) {
                        showSnackbar("Something went wrong when updating the reading goal");
                    }
                } else if (readingGoalInt <= 0) {
                    errorMessage.setVisible(true);
                    errorMessage.setText("The reading goal must be greater than 0");
                } else {
                    errorMessage.setVisible(true);
                    errorMessage.setText("The reading goal cannot be that large");
                }
            } catch (NumberFormatException error) {
                errorMessage.setText("Please enter a sensible number");
                errorMessage.setVisible(true);
            }
        }
    }

    // Set up / Refresh the whole page
    private void setUpPage() {
        try {
            // Get the user's reading goal information
            assert User.getInstance() != null;
            ReadingGoal readingGoal = DatabaseUtility.getReadingGoal(User.getInstance().getUsername());

            // Check if the reading goal object exists
            if (readingGoal != null) {

                // Show a different page depending on if the reading goal was set up yet or not
                if (readingGoal.getPageCount() == 0) {
                    initializeWithoutReadingGoal();
                } else {
                    initializeWithReadingGoal(readingGoal);
                }

            } else {
                showSnackbar("Unable to obtain the reading goal information");
            }
        } catch (SQLException error) {
            showSnackbar("Something went wrong when loading the information");
        }
    }

    // Page set up without reading goal
    private void initializeWithoutReadingGoal() {
        readingGoalReached.setVisible(false);
        readingGoalMessage.setText("You have not set up your reading goal yet. You can do it using the text box above :)");
    }

    // Page set up with reading goal
    private void initializeWithReadingGoal(ReadingGoal readingGoal) {
        setUpReadingGoalReached(readingGoal);
        setUpReadingGoalMessage(readingGoal);
    }

    private void setUpReadingGoalReached(ReadingGoal readingGoal) {
        // If the reading goal was not updated on the same day, reset the goal status (since it's a daily goal)
        if (!Objects.equals(readingGoal.getDateSet(), LocalDate.now())) {
            resetReadingGoalReached();
        }

        // Change the toggle status based on if the reading goal is reached
        readingGoalToggle.setSelected(readingGoal.isReached());
        readingGoalToggle.setText(readingGoal.isReached() ? "Yes" : "No");
    }

    // Display an appropriate message depending on the reading goal status
    private void setUpReadingGoalMessage(ReadingGoal readingGoal) {
        if (!readingGoal.isReached()) {
            if (readingGoal.getPageCount() == 1) {
                readingGoalMessage.setText("You have not yet reached your goal of 1 read page today." +
                        " You can still do it!");
            } else {
                readingGoalMessage.setText("You have not yet reached your goal of " + readingGoal.getPageCount() + " read pages today." +
                        " You can still do it!");
            }
        } else {
            if (readingGoal.getPageCount() == 1) {
                readingGoalMessage.setText("You have reached your daily goal of 1 read page." +
                        " Well done!");
            } else {
                readingGoalMessage.setText("You have reached your daily goal of " + readingGoal.getPageCount() + " read pages." +
                        " Well done!");
            }
        }
    }

    // Reset the daily reading goal
    private void resetReadingGoalReached() {
        try {
            assert User.getInstance() != null;
            DatabaseUtility.updateIsReachedOfReadingGoal(User.getInstance().getUsername(), false);
            setUpPage();
        } catch (SQLException error) {
            showSnackbar("There was a problem updating the reading goal");
        }
    }

    public void showSnackbar(String message) {

        // Set the message on the snackbar
        snackbarLabel.setText(message);

        // Fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), snackbarLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Set up a new thread to show the snackbar
        fadeIn.setOnFinished(_ -> {
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Snackbar will show for two seconds
                } catch (InterruptedException ignore) {}

                // Fade-out animation
                FadeTransition fadeOut = new FadeTransition(Duration.millis(300), snackbarLabel);
                fadeOut.setFromValue(1);
                fadeOut.setToValue(0);
                fadeOut.play();
            }).start();
        });
        fadeIn.play();
    }
}
