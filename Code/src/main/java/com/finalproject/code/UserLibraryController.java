package com.finalproject.code;

import com.finalproject.code.classes.LibraryBook;
import com.finalproject.code.classes.User;
import com.finalproject.code.utilities.DatabaseUtility;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserLibraryController {

    // Reference to the UI components
    @FXML
    private FlowPane bookFlowPane;
    @FXML
    private Label snackbarLabel;

    @FXML
    public void initialize() {
        populateUserLibrary();
    }

    // Populate the user's book library
    public void populateUserLibrary() {
        // Clean out all the books before loading them again
        bookFlowPane.getChildren().clear();

        List<LibraryBook> books = getLibraryBooks();
        // Only try to display the books if there are any in the database
        if (!books.isEmpty()) {
            try {
                for (LibraryBook book : books) {

                    // Load the book view for ach book
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/finalproject/code/library-book-view.fxml"));
                    Parent bookView = loader.load();

                    // Set the book data through the book controller
                    LibraryBookController controller = loader.getController();
                    controller.setLibraryBookData(book);

                    // Pass the reference to itself to the Library Book Controller
                    controller.setUserLibraryController(this);

                    // Add the populated book view to the flow pane
                    bookFlowPane.getChildren().add(bookView);
                }
            } catch (IOException error) {
                showSnackbar("There was a problem loading the page");
            }
        } else {
            showSnackbar("There are no books in the library yet");
        }
    }

    private List<LibraryBook> getLibraryBooks() {
        List<LibraryBook> books = new ArrayList<>();

        try {
            // Get the current user's ID
            assert User.getInstance() != null;
            String userId = DatabaseUtility.getUserIdByUsername(User.getInstance().getUsername());

            // Get all the books from the user's library
            books = DatabaseUtility.getAllBooksByUserId(userId);

        } catch (SQLException error) {
            showSnackbar("There was a problem loading the books");
        }

        return books;
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