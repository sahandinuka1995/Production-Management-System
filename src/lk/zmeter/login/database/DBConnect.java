/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.zmeter.login.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sahan
 */
public class DBConnect {

    private static DBConnect dbConnect;
    private Connection conn;

    private DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String host = "jdbc:mysql://localhost:3306/zmeter?" + "autoReconnect=true&useSSL=false";
        String user = "root";
        String pass = "root";

        conn = DriverManager.getConnection(host, user, pass);
    }

    public static DBConnect getInstance() throws ClassNotFoundException, SQLException {
        return (dbConnect == null) ? (dbConnect = new DBConnect()) : (dbConnect);
    }

    public Connection getConnection() {
        return conn;
    }
}
