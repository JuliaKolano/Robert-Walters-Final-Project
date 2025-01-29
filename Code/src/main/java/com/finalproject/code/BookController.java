package com.finalproject.code;

import com.finalproject.code.classes.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        if (book != null) {
            System.out.println(book.getTitle());
            System.out.println(book.getAuthor());
            System.out.println(book.getGenre());
            System.out.println(book.getPageCount());
            System.out.println(book.getCoverUrl());
        }
    }
}
