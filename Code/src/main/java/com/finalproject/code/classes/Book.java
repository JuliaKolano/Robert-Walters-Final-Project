package com.finalproject.code.classes;

public class Book {
    private final String title;
    private final String author;
    private final String genre;
    private final int pageCount;
    private final String coverUrl;

    // Constructor
    public Book(String title, String author, String genre, int pageCount, String coverUrl) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pageCount = pageCount;
        this.coverUrl = coverUrl;
    }

    // Getters

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getCoverUrl() {
        return coverUrl;
    }
}
