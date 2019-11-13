package com.hoster.app.helpers;

import com.hoster.app.helpers.Config;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DBHelper {

    protected static Connection connection;

    public DBHelper(Connection connect) {
        connection = connect;
    }

    public static ResultSet custom(String query) {
        return getResultSet(query);
    }

    public static ResultSet selectAll(String table){
        String sql = String.format("SELECT * FROM %s", table);
        return getResultSet(sql);
    }

    public static ResultSet selectAll(String table, String requirements){
        String sql = String.format("SELECT * FROM %s WHERE %s", table, requirements);
        return getResultSet(sql);
    }

    public static ResultSet selectAll(String table, String joinTable, String foreignKey){
        String sql = String.format("SELECT * FROM %s JOIN %s ON %s.%s = %s.id", table, joinTable, table, foreignKey, joinTable);
        return getResultSet(sql);
    }

    public static ResultSet selectAll(String table, String requirment, String joinTable, String foreignKey){
        String sql = String.format("SELECT * FROM %s JOIN %s ON %s.%s = %s.id WHERE %s", table, joinTable, table, foreignKey, joinTable, requirment);
        return getResultSet(sql);
    }

    public static ResultSet selectColumn(String table, String[] columns){
        String col = "";
        for(String s : columns){
            col += s + ", ";
        }
        col = col.substring(0, col.length() - 2);
        String sql = String.format("SELECT %s FROM %s", col, table);
        return getResultSet(sql);
    }

    public static ResultSet selectColumn(String table, String[] columns, String requirment){
        String col = "";
        for(String s : columns){
            col += s + ", ";
        }
        col = col.substring(0, col.length() - 2);
        String sql = String.format("SELECT %s FROM %s WHERE %s", col, table, requirment);
        return getResultSet(sql);
    }

    public static boolean insert(String table, Map<String, String> params){
        String columns = table + "(";
        String values = "VALUES(";
        for(String key : params.keySet()){
            columns += String.format("%s,", key);
        }
        columns = columns.substring(0, columns.length() - 1);
        columns += ") ";

        for(String val : params.values()){
            values += String.format("'%s',", val);
        }
        values = values.substring(0, values.length() - 1);
        values += ") ";

        String sql = "INSERT INTO " + columns + values;
        System.out.println(sql);
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(String table, Map<String, String> params, String clause){
        String sql = "UPDATE " + table + " SET ";
        for(int i=0; i<params.size(); i++){
            String col = (String) params.keySet().toArray()[i];
            String val = (String) params.values().toArray()[i];
            sql += col + " = " + val;
            if(i != params.size() - 1){
                sql += ", ";
            }
        }
        sql += " WHERE " + clause;
        System.out.println(sql);
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static ResultSet getResultSet(String sql) {
        System.out.println(sql);
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}