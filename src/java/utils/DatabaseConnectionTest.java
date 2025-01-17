/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jbcrypt.BCrypt;

/**
 *
 * @author hazik
 */
public class DatabaseConnectionTest {

    // Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
    private static final String DB_USER = "CareGiver";
    private static final String DB_PASSWORD = "system";

    public static void main(String[] args) {
        testDatabaseConnection();
        createPassword();

    }

    public static void testDatabaseConnection() {
        System.out.println("Attempting to connect to the database...");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
    
    public static void createPassword(){
        String plainPassword = "admin";
        String newHash = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        System.out.println("Generated Hash: " + newHash);

        // Verify the new hash with the same password
        if (BCrypt.checkpw(plainPassword, newHash)) {
            System.out.println("Generated hash matches the password!");
        } else {
            System.out.println("Generated hash does not match!");
        }
    }
    
    public static void testPassword(){
        String plainPassword = "admin";
        String storedHash = "$2a$10$EjYoQmIvykRJGyv7nvw1J.L7HQEM4YX9oSGaUJoAxjBMo8MYO0Chy";

        if (BCrypt.checkpw(plainPassword, storedHash)) {
            System.out.println("Password matches!");
        } else {
            System.out.println("Password does not match!");
        }
    }
}
