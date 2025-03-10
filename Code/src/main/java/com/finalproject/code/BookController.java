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
    // Set to public for tests to work
    @FXML
    public ImageView bookCover;
    @FXML
    public Label bookTitle;
    @FXML
    public Label bookAuthor;
    @FXML
    public Label bookGenre;
    @FXML
    public Label bookPages;
    @FXML
    public Button addButton;

    // Variables
    private Book book;
    private SearchBooksController searchBooksController;

    // Dependency injection used to access the methods from Search Books Controller
    public void setSearchBooksController(SearchBooksController searchBooksController) {
        this.searchBooksController = searchBooksController;
    }

    public void setBookData(Book book) {
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
    }

    // Display the add button on hover
    @FXML
    public void onMouseEnter() {
        addButton.setVisible(true);
    }

    // Hide the add button when hovering stops
    @FXML
    public void onMouseExit() {
        addButton.setVisible(false);
    }

    @FXML
    public void onAddButtonClicked() {
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
                // only add it if it's not already in the library
                if (DatabaseUtility.getBookIdByUsername(username, title, author) == null) {
                    DatabaseUtility.createLibraryBook(username, title, author, genre, pageCount, coverUrl, false);
                    if (searchBooksController != null) {
                        searchBooksController.showSnackbar("Book successfully added to library");
                    }
                } else {
                    if (searchBooksController != null) {
                        searchBooksController.showSnackbar("Book is already in the library");
                    }
                }
            } catch (SQLException error) {
                if (searchBooksController != null) {
                    searchBooksController.showSnackbar("There was an error adding the book to the library");
                }
            }
        // If the book object is null
        } else {
            if (searchBooksController != null) {
                searchBooksController.showSnackbar("There was an error adding the book to the library");
            }
        }
    }
}
