package com.finalproject.code.utilities;

import java.sql.*;

public class DatabaseUtility {

    // Variables
    private static Connection connection;
    private static String passwordEncryptionKey = "j)x4)Ab28cw8Gf89"; // TODO move to environment variables later

    // Server and database credentials
    private static String url = "jdbc:sqlserver://localhost;database=BookLibraryDB";
    private static String user = "SA";
    private static String password = "Passw0rd";

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


    // Update methods



    // Delete methods


}
