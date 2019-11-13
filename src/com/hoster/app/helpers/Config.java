package com.hoster.app.helpers;

import java.sql.Connection;
import java.sql.Statement;

public class Config {

    public static Connection connection;
    public static Statement statement;

    public static void setConnection(Connection connection) {
        Config.connection = connection;
    }
}
