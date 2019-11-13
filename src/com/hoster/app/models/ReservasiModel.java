package com.hoster.app.models;

import java.sql.ResultSet;
import java.util.Map;

public class ReservasiModel extends Models implements DBModel {
    private static final String TABLE = "transaksi";
    private static final String KAMAR = "kamar";
    private static final String JENIS = "jenis_kamar";
    private static final String CUSTOMER = "customers";


    @Override
    public ResultSet getAll(){
        return db.custom(
            String.format(
                "SELECT t.id, concat(c.nama_depan, ' ', c.nama_belakang) as nama_customer, k.nama as nama_kamar, jk.nama as nama_jenis_kamar, t.book_date, t.check_in, t.check_out, t.created_at FROM %s t " +
                "JOIN %s c ON t.id_customer = c.id " +
                "JOIN %s k ON t.id_kamar = k.id " +
                "JOIN %s jk ON k.id_jenis = jk.id"
            ,TABLE, CUSTOMER, KAMAR, JENIS)
        );
    }

    @Override
    public ResultSet find(String keyword) {
        return db.custom(
            String.format(
                "SELECT t.id, concat(c.nama_depan, ' ', c.nama_belakang) as nama_customer, k.nama as nama_kamar, jk.nama as nama_jenis_kamar, t.book_date, t.check_in, t.check_out, t.created_at FROM %s t " +
                "JOIN %s c ON t.id_customer = c.id " +
                "JOIN %s k ON t.id_kamar = k.id " +
                "JOIN %s jk ON k.id_jenis = jk.id " +
                "WHERE k.nama LIKE '%%%s%%' OR c.nama_depan LIKE '%%%s%%' OR c.nama_belakang LIKE '%%%s%%' OR jk.nama LIKE '%%%s%%' OR t.book_date LIKE '%%%s%%' " +
                "OR t.check_in LIKE '%%%s%%' OR t.check_out LIKE '%%%s%%'"
            ,TABLE, CUSTOMER, KAMAR, JENIS, keyword, keyword, keyword, keyword, keyword, keyword, keyword)
        );
    }

    public boolean store(Map<String, String> param) {
        return db.insert(TABLE, param);
    }

    public ResultSet getListJenisKamar() {
        return db.selectAll(JENIS, "aktif = 1");
    }

    public ResultSet getKamarKosongByJenis(String jenis, String startDate, int lamaInap) {
        return db.custom(String.format("SELECT * FROM kamar k JOIN jenis_kamar jk ON k.id_jenis = jk.id WHERE jk.nama LIKE '%%%s%%' AND k.id NOT IN (SELECT id_kamar FROM %s WHERE check_out <= '%s' OR book_date >= DATE_ADD('%s', INTERVAL %s DAY))", jenis, TABLE, startDate, startDate, lamaInap));
    }

    public ResultSet getHargaByKamar(String nama) {
        return db.custom(String.format("SELECT * FROM kamar WHERE nama LIKE '%%%s%%' LIMIT 1", nama));
    }

    public ResultSet getIdCustomerByNameKTP(String nama_depan, String ktp) {
        return db.custom(String.format("SELECT id FROM %s WHERE nama_depan LIKE '%%%s%%' AND no_ktp = '%s' ORDER BY created_at DESC LIMIT 0,1", CUSTOMER, nama_depan, ktp));
    }
}
