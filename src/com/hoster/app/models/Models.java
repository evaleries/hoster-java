package com.hoster.app.models;

import com.hoster.app.helpers.DBConnect;
import com.hoster.app.helpers.DBHelper;

import java.sql.ResultSet;

public class Models {
    protected DBHelper db = null;
    protected ResultSet resultSet;

    protected Models() {
        db = new DBHelper(DBConnect.doConnect());
    }

}
