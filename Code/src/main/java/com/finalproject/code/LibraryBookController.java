package com.finalproject.code;

import com.finalproject.code.classes.Book;
import com.finalproject.code.classes.LibraryBook;
import com.finalproject.code.classes.User;
import com.finalproject.code.utilities.DatabaseUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.Objects;

public class LibraryBookController {

    // Reference to the UI components
    @FXML
    private ImageView bookCover;
    @FXML
    private Label bookTitle;
    @FXML
    private Label bookAuthor;
    @FXML
    private Label bookGenre;
    @FXML
    private Label bookPages;
    @FXML
    private Button readButton;
    @FXML
    private Button deleteButton;

    // Variables
    private LibraryBook book;
    private UserLibraryController userLibraryController;

    // Dependency injection used to access the methods from User Library Controller
    public void setUserLibraryController(UserLibraryController userLibraryController) {
        this.userLibraryController = userLibraryController;
    }

    public void setLibraryBookData(LibraryBook book) {
        this.book = book;

        // Populate the book view with data from the books object
        bookTitle.setText(book.getTitle());
        bookAuthor.setText("Author: " + book.getAuthor());
        bookGenre.setText("Genre: " + book.getGenre());
        bookPages.setText("Pages: " + book.getPageCount());

        // If there is no cover image available, provide a 'no cover' image
        if (book.getCoverUrl() != null) {
            bookCover.setImage(new Image(book.getCoverUrl(), true));
        } else {
            bookCover.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("noCover.jpg"))));
        }

        updateReadButton();
    }

    // Set the read button's visibility and style
    private void updateReadButton() {
        // when is enabled
        if (book.getIsRead()) {
            readButton.getStyleClass().removeAll("read-button-unread");
            readButton.getStyleClass().add("read-button-read");
            readButton.setVisible(true);
            // when is disabled
        } else {
            readButton.getStyleClass().removeAll("read-button-read");
            readButton.getStyleClass().add("read-button-unread");
            readButton.setVisible(false);
        }
    }

    // Display the buttons on hover
    @FXML
    private void onMouseEnter() {
        // only when the button is enabled
        if (!book.getIsRead()) {
            readButton.setVisible(true);
        }

        deleteButton.setVisible(true);
    }

    // Hide the buttons when hovering stops
    @FXML
    private void onMouseExit() {
        // only when the button is disabled
        if (!book.getIsRead()) {
            readButton.setVisible(false);
        }

        deleteButton.setVisible(false);
    }

    @FXML
    private void onReadButtonClicked() {
        if (book != null) {
            try {
                // Change the isRead to the opposite value on every click
                assert User.getInstance() != null;
                DatabaseUtility.updateIsReadOfBook(User.getInstance().getUsername(), book.getTitle(), book.getAuthor(), !book.getIsRead());
                // Adjust the book object's isRead value accordingly too
                book.setIsRead(!book.getIsRead());
                updateReadButton();
            } catch (SQLException error) {
                if (userLibraryController != null) {
                    userLibraryController.showSnackbar("Something went wrong");
                }
            }
        } else {
            if (userLibraryController != null) {
                userLibraryController.showSnackbar("There was a problem updating the read status the book");
            }
        }
    }

    @FXML
    private void onDeleteButtonClicked() {
        if (book != null) {
            try {
                // only delete the book if it's in the database
                assert User.getInstance() != null;
                if (DatabaseUtility.getBookIdByUsername(User.getInstance().getUsername(), book.getTitle(), book.getAuthor()) != null) {
                    // Delete the book from the database
                    assert User.getInstance() != null;
                    DatabaseUtility.deleteBook(User.getInstance().getUsername(), book.getTitle(), book.getAuthor());
                    if (userLibraryController != null) {
                        userLibraryController.showSnackbar("Book removed from the library");
                        // fetch all the books again to make the deleted one disappear
                        userLibraryController.populateUserLibrary();
                    }
                } else {
                    if (userLibraryController != null) {
                        userLibraryController.showSnackbar("Book was already removed");
                    }
                }
            } catch (SQLException error) {
                if (userLibraryController != null) {
                    userLibraryController.showSnackbar("There was a problem deleting the book");
                }
            }
        } else {
            if (userLibraryController != null) {
                userLibraryController.showSnackbar("There was a problem deleting the book");
            }
        }
    }

}
