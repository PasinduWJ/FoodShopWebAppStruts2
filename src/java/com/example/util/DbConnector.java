
package com.example.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    
     public static Connection getConnection() throws Exception{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/loginweb?autoReconnect=true&useSSL=false","root","admin");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new Exception("Database Connection Error");
        }
    }   
}
