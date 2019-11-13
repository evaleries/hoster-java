package com.hoster.app.models;

public class Kamar {
    private int id, harga;
    private String nama, tipe, keterangan;

    public Kamar(int id, int harga, String nama, String tipe, String keterangan) {
        this.id = id;
        this.harga = harga;
        this.nama = nama;
        this.tipe = tipe;
        this.keterangan = keterangan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
