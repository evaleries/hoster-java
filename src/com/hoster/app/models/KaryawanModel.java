package com.hoster.app.models;

import java.sql.ResultSet;

public class KaryawanModel extends Models implements DBModel {
    public static final String TABLE = "karyawan";

    @Override
    public ResultSet getAll() {
        resultSet = db.selectAll(TABLE);
        return resultSet;
    }

    @Override
    public ResultSet find(String keyword){
        resultSet = db.selectAll(TABLE,
                String.format("nama_depan LIKE '%%%s%%' OR nama_belakang LIKE '%%%s%%' OR email LIKE '%%%s%%' OR alamat LIKE '%%%s%%' OR kota LIKE '%%%s%%' OR telepon LIKE '%%%s%%'", keyword, keyword, keyword, keyword, keyword, keyword)
        );
        return resultSet;
    }
}
