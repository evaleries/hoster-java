package com.hoster.app.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    Connection connection;
    private static final String DBMS = "mysql";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "hoster";
    private static final String USERNAME = "eval";
    private static final String PASSWORD = "google123";

    public static Connection doConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String host = String.format("jdbc:%s://%s:%s/%s", DBMS, HOST, PORT, DATABASE);
            Connection connection = DriverManager.getConnection(host, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException | ClassNotFoundException s) {
            System.out.println(s.getMessage());
            return null;
        }
    }

}
