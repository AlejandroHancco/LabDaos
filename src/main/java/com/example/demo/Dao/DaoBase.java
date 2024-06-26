package com.example.demo.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoBase {
    public Connection getConnection() throws  SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch( ClassNotFoundException ex){ex.printStackTrace();}
       String url = "jdbc:mysql://localhost:3306/lab7";
       String user = "root";
       String password = "root";
       return DriverManager.getConnection(url, user, password);
    }
}
