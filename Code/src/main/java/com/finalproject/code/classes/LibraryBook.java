package com.finalproject.code.classes;

public class LibraryBook extends Book {

    private final boolean isRead;

    // Constructor
    public LibraryBook(String title, String author, String genre, int pageCount, String coverUrl, boolean isRead) {
        super(title, author, genre, pageCount, coverUrl);
        this.isRead = isRead;
    }

    // Getter
    public boolean isRead() {
        return isRead;
    }
}
