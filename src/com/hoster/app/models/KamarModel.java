package com.hoster.app.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KamarModel extends Models implements DBModel {

    private static final String TABLE = "kamar";
    private static final String JENIS = "jenis_kamar";
    private static final String TRANSAKSI = "transaksi";

    @Override
    public ResultSet getAll() {
        resultSet = db.custom(String.format("SELECT * FROM %s k JOIN %s jk on k.id_jenis = jk.id", TABLE, JENIS));
        return resultSet;
    }

    @Override
    public ResultSet find(String keyword) {
        keyword = keyword.replace("'", "").replace("\"", "");
        resultSet = db.selectAll(TABLE,
                String.format("nama LIKE '%%%s%%' OR kamar.id LIKE '%%%s%%' OR id_jenis LIKE '%%%s%%' OR harga LIKE '%%%s%%' OR keterangan LIKE '%%%s%%'", keyword, keyword, keyword, keyword, keyword),
                JENIS,
                "id_jenis"
        );
        return resultSet;
    }
    @Deprecated
    public ResultSet findEmptyRoom(String date) {
        resultSet = db.custom(String.format("SELECT * FROM %s WHERE id NOT IN (SELECT id_kamar FROM %s WHERE %s NOT BETWEEN book_date AND check_out)", TABLE, TRANSAKSI, date));
        return resultSet;
    }
}
