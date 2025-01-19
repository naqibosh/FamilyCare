/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Update with your DB details
    private static final String USER = "CareGiver"; // Replace with your username
    private static final String PASSWORD = "system"; // Replace with your password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Oracle JDBC Driver not found.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Connection initializeDatabase() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

