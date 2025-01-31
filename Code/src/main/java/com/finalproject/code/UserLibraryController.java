package com.finalproject.code;

import com.finalproject.code.classes.LibraryBook;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.io.IOException;


public class UserLibraryController {

    // Reference to the UI components
    @FXML
    private FlowPane bookFlowPane;
    @FXML
    private Label snackbarLabel;

    @FXML
    public void initialize() {

        try {
            for (int i = 0; i < 10; i++) {
                // Create a book object using extracted data
                LibraryBook book = new LibraryBook("title", "author", "genre", 0, null, false);

                // Load the book view for each book
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/finalproject/code/library-book-view.fxml"));
                Parent bookView = loader.load();

                // Set the book data through the book controller
                LibraryBookController controller = loader.getController();
                controller.setLibraryBookData(book);

                // Add the populated book view to the flow pane
                bookFlowPane.getChildren().add(bookView);
            }
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }
    }
}