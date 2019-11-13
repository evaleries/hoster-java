package com.hoster.app.models;

import java.sql.ResultSet;

public interface DBModel {
    ResultSet getAll();
    ResultSet find(String keyword);
}
