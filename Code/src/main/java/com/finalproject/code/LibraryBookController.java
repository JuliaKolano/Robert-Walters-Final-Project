package com.finalproject.code;

import com.finalproject.code.classes.Book;
import com.finalproject.code.classes.LibraryBook;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    }
}
