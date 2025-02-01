package com.finalproject.code;

import com.finalproject.code.classes.LibraryBook;
import com.finalproject.code.classes.User;
import com.finalproject.code.utilities.DatabaseUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

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

        // Populate the user's book library
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

                    // TODO pass the parent controller for the snackbar later

                    // Add the populated book view to the flow pane
                    bookFlowPane.getChildren().add(bookView);
                }
            } catch (IOException error) {
                // TODO problem with loading page
                System.out.println(error.getMessage());
            }
        } else {
            // TODO no books found
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
            error.printStackTrace(); // TODO change (problem fetching books)
        }

        return books;
    }
}