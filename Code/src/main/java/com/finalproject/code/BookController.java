package com.finalproject.code;

import com.finalproject.code.classes.Book;
import com.finalproject.code.classes.User;
import com.finalproject.code.utilities.DatabaseUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.Objects;

public class BookController {

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
    private Button addButton;

    // Variables
    private Book book;

    public void setBookData(Book book) {
        this.book = book;
        // Populate the book view with data from the books object
        bookTitle.setText(book.getTitle());
        bookAuthor.setText("Author: " + book.getAuthor());
        bookGenre.setText("Genre: " + book.getGenre());
        bookPages.setText("Pages: " + book.getPageCount());

        // If there is no cover image available, provide a no cover image
        if (book.getCoverUrl() != null) {
            bookCover.setImage(new Image(book.getCoverUrl(), true));
        } else {
            bookCover.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("noCover.jpg"))));
        }
    }

    // Display the add button on hover
    @FXML
    private void onMouseEnter() {
        addButton.setVisible(true);
    }

    // Hide the add button when hovering stops
    @FXML
    private void onMouseExit() {
        addButton.setVisible(false);
    }

    @FXML
    private void onAddButtonClicked() {
        // If the book clicked on exists add it to the user's library
        if (book != null) {

            // Prepare the data to be stored in the database according to database restrictions
            String username = Objects.requireNonNull(User.getInstance()).getUsername();
            String title = book.getTitle().length() > 80 ? book.getTitle().substring(0, 80) : book.getTitle();
            String author = book.getAuthor().length() > 50 ? book.getAuthor().substring(0, 50) : book.getAuthor();
            String genre = book.getGenre().length() > 50 ? book.getGenre().substring(0, 50) : book.getGenre();
            int pageCount = Math.min(book.getPageCount(), 32_767); // Take the smaller value
            // Can be null, but if it's not and is bigger than smallint then truncate it
            String coverUrl = (book.getCoverUrl() != null && book.getCoverUrl().length() > 2083)
                            ? book.getCoverUrl().substring(0, 2083) : book.getCoverUrl();

            // Add user and book data to database
            try {
                DatabaseUtility.createLibraryBook(username, title, author, genre, pageCount, coverUrl);
            } catch (SQLException error) {
                System.out.println(error.getMessage());
            }

        }
    }
}
