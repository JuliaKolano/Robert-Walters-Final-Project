package com.finalproject.code.utilities;

import com.finalproject.code.classes.LibraryBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Platform.exit;

public class DatabaseUtility {

    // Variables
    private static Connection connection;
    private static final String passwordEncryptionKey = System.getenv("PASSWORD_ENCRYPTION_KEY");

    // Server and database credentials
    private static final String url = System.getenv("DATABASE_URL");
    private static final String user = System.getenv("DATABASE_USER");
    private static final String password = System.getenv("DATABASE_PASSWORD");

    // Set up the connection with the database
    public static Connection getConnection() {
        // Check if the connection exists first
        if (connection == null) {
            // If it doesn't then try and connect
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException error) {
                // If connection to the database can't be made, close the application
                exit();
            }
        }
        return connection;
    }


    // Create methods


    // Add a user to the database
    public static void createUser(String username, String password) throws SQLException {
        // Add the username to the Users table
        String userSql = "insert into users (username) values (?)";
        PreparedStatement userInsertStatement = connection.prepareStatement(userSql);
        userInsertStatement.setString(1, username);
        userInsertStatement.executeUpdate();
        userInsertStatement.close();

        // Get the user id from the Users table
        String userId = getUserIdByUsername(username);

        // Add the user id and password to the Passwords table
        String passwordSql = "insert into passwords (user_id, password) values (?, ENCRYPTBYPASSPHRASE(?, ?))";
        PreparedStatement passwordInsertStatement = connection.prepareStatement(passwordSql);
        passwordInsertStatement.setString(1, userId);
        passwordInsertStatement.setString(2, passwordEncryptionKey);
        passwordInsertStatement.setString(3, password);
        passwordInsertStatement.executeUpdate();
        passwordInsertStatement.close();
    }

    // Creates a book associated with (added to) the user's library
    public static void createLibraryBook(String username, String title, String author, String genre, int pageCount, String coverUrl, boolean isRead) throws SQLException {
        // Add the book to the Books table
        String bookSql = "insert into books (title, author, genre, pageCount, coverUrl, isRead) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement bookInsertStatement = connection.prepareStatement(bookSql);
        bookInsertStatement.setString(1, title);
        bookInsertStatement.setString(2, author);
        bookInsertStatement.setString(3, genre);
        bookInsertStatement.setInt(4, pageCount);
        bookInsertStatement.setString(5, coverUrl);
        bookInsertStatement.setBoolean(6, isRead);
        bookInsertStatement.executeUpdate();
        bookInsertStatement.close();

        // Get the user and book IDs
        String userId = getUserIdByUsername(username);
        String bookId = getBookIdByTitleAndAuthor(title, author);

        // Add the user and book IDs to the UserBook table
        String userBookSql = "insert into userbook (user_id, book_id) values (?, ?)";
        PreparedStatement userBookInsertStatement = connection.prepareStatement(userBookSql);
        userBookInsertStatement.setString(1, userId);
        userBookInsertStatement.setString(2, bookId);
        userBookInsertStatement.executeUpdate();
        userBookInsertStatement.close();
    }


    // Read methods


    // Fetch the username of a user with the specified username
    public static String getUsername(String username) throws SQLException {
        String sql = "select username from users where username = ?";
        PreparedStatement selectStatement = connection.prepareStatement(sql);
        selectStatement.setString(1, username);
        ResultSet resultSet = selectStatement.executeQuery();
        // if the username exists in the database return it, if not return null
        if (resultSet.next()) {
            return resultSet.getString("username");
        } else {
            return null;
        }
    }

    // Fetch the user id that corresponds to a username
    public static String getUserIdByUsername(String username) throws SQLException {
        String sql = "select id from users where username = ?";
        PreparedStatement selectStatement = connection.prepareStatement(sql);
        selectStatement.setString(1, username);
        ResultSet resultSet = selectStatement.executeQuery();
        // if the username exists in the database return the user id, if not return null
        if (resultSet.next()) {
            return resultSet.getString("id");
        } else {
            return null;
        }
    }

    // Fetch the username of a user with the specified username
    public static String getPasswordByUsername(String username) throws SQLException {
        // Get the user id first to use it as a foreign key for the passwords table
        String userId = getUserIdByUsername(username);
        // Get the password that corresponds to the user id
        String sql = "select convert(varchar(max), DECRYPTBYPASSPHRASE(?, password)) as password from passwords where user_id = ?";
        PreparedStatement selectStatement = connection.prepareStatement(sql);
        selectStatement.setString(1, passwordEncryptionKey);
        selectStatement.setString(2, userId);
        ResultSet resultSet = selectStatement.executeQuery();
        // if the user has a password in the database return it, if not return null
        if (resultSet.next()) {
            // The decryption algorithm inputs unknown symbols every other character, so remove them
            return resultSet.getString("password").replaceAll("(.).", "$1");
        } else {
            return null;
        }
    }

    // Fetch the book id that corresponds to its title and author
    public static String getBookIdByTitleAndAuthor(String title, String author) throws SQLException {
        String sql = "select id from books where title = ? and author = ?";
        PreparedStatement selectStatement = connection.prepareStatement(sql);
        selectStatement.setString(1, title);
        selectStatement.setString(2, author);
        ResultSet resultSet = selectStatement.executeQuery();
        // if the book exists in the database return the book id, if not return null
        if (resultSet.next()) {
            return resultSet.getString("id");
        } else {
            return null;
        }
    }

    // Fetch all the book data that corresponds to its ID
    public static List<LibraryBook> getAllBooksByUserId(String userId) throws SQLException {
        List<LibraryBook> books = new ArrayList<>();
        // join the UserBook and Book tables
        String sql = "select title, author, genre, pageCount, coverUrl, isRead from books " +
                "join userbook on books.id = userbook.book_id where userbook.user_id = ?";

        PreparedStatement selectStatement = connection.prepareStatement(sql);
        selectStatement.setString(1, userId);
        ResultSet resultSet = selectStatement.executeQuery();

        // Create library book objects for each result and add them to a list
        while (resultSet.next()) {
            books.add(new LibraryBook(resultSet.getString("title"),
                                    resultSet.getString("author"),
                                    resultSet.getString("genre"),
                                    resultSet.getInt("pageCount"),
                                    resultSet.getString("coverUrl"),
                                    resultSet.getBoolean("isRead")));
        }

        return books;
    }


    // Update methods



    // Delete methods


}
