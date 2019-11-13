package com.hoster.app.models;

import java.sql.ResultSet;
import java.util.Map;

public class CustomerModel extends Models implements DBModel {
    public static final String TABLE = "customers";

    @Override
    public ResultSet getAll() {
        resultSet = db.selectAll(TABLE);
        return resultSet;
    }

    @Override
    public ResultSet find(String keyword) {
        resultSet = db.selectAll(TABLE,
                String.format("nama_depan LIKE '%%%s%%' OR nama_belakang LIKE '%%%s%%' OR email LIKE '%%%s%%' OR alamat LIKE '%%%s%%' OR kota LIKE '%%%s%%' OR telepon LIKE '%%%s%%' OR negara LIKE '%%%s%%'", keyword, keyword, keyword, keyword, keyword, keyword, keyword)
        );
        return resultSet;
    }

    public boolean store(Map<String, String> param) {
        return db.insert(TABLE, param);
    }
}
