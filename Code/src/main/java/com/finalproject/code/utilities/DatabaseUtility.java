package com.finalproject.code.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
