package com.DEPI.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    // Database connection details
    private static final String URL = "jdbc:postgresql://localhost:5432/project0";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";
    private static Connection connection=null;
    /**
     * Establishes and returns a connection to the database.
     *
     * @return active connection to the database, or null if the connection fails
     */
    public static Connection getConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Closes the database connection.
     *
     * @param connection the connection to close
     */
    /*
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
                e.printStackTrace(); // Print stack trace for debugging
            }
        }
    }

    public static void testConnection() {
        Connection connection = null;
        try {
            connection = getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database connection established successfully!");
            } else {
                System.out.println("Failed to establish database connection.");
            }
        } catch (SQLException e) {
            System.err.println("Error checking database connection: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            closeConnection(connection); // Ensure the connection is closed
        }
    }*/
}