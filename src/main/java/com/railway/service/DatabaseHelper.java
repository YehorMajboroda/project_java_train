package com.railway.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseHelper {
    private static final String PROPERTIES_FILE = "/db.properties";
    private static Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseHelper.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + PROPERTIES_FILE);
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        // Забезпечуємо безпеку: URL та credentials зчитуються з файлу
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        
        // Повертаємо з'єднання
        return DriverManager.getConnection(url, user, password);
    }
}
