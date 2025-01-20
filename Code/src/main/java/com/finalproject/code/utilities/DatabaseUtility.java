package com.finalproject.code.utilities;

import java.sql.*;

public class DatabaseUtility {

    // Server and database credentials
    private static String url = "jdbc:sqlserver://localhost;database=BookLibraryDB";
    private static String user = "SA";
    private static String password = "Passw0rd";
    private static Connection connection;

    // Set up the connection with the database
    public static Connection getConnection() {
        // Check if the connection exists first
        if (connection == null) {
            // If it doesn't then try and connect
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException error) {
                System.out.println(error.getMessage());
            }
        }
        return connection;
    }

    // Fetch the username of a user with the specified username
    public static String getUserByUsername(String username) throws SQLException {
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
        resultSet.next();
        return resultSet.getString("id");
    }

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
        String passwordSql = "insert into passwords (user_id, password) values (?, ?)";
        PreparedStatement passwordInsertStatement = connection.prepareStatement(passwordSql);
        passwordInsertStatement.setString(1, userId);
        passwordInsertStatement.setString(2, password);
        passwordInsertStatement.executeUpdate();
        passwordInsertStatement.close();
    }
}
