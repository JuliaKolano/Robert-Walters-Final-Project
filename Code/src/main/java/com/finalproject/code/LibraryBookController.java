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

    // Variables
    private LibraryBook book;

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

    // Display the mark as read button on hover (only when not enabled)
    @FXML
    private void onMouseEnter() {
        if (!book.getIsRead()) {
            readButton.setVisible(true);
        }
    }

    // Hide the mark as read button when hovering stops (only when not enabled)
    @FXML
    private void onMouseExit() {
        if (!book.getIsRead()) {
            readButton.setVisible(false);
        }
    }

    @FXML
    private void onReadButtonClicked() {
        try {
            // Change the isRead to the opposite value on every click
            assert User.getInstance() != null;
            DatabaseUtility.updateIsReadOfBook(User.getInstance().getUsername(), book.getTitle(), book.getAuthor(), !book.getIsRead());
            // Adjust the book object's isRead value accordingly too
            book.setIsRead(!book.getIsRead());
            updateReadButton();
        } catch (SQLException error) {
            error.printStackTrace(); // TODO change to something went wrong
        }
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
}
