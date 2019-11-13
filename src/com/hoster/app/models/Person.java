package com.hoster.app.models;

public class Person extends Models {
    protected int id;
    protected String noKtp;
    protected String namaDepan;
    protected String namaBelakang;
    protected String jenisKelamin;
    protected String email;
    protected String telepon;
    protected String alamat;
    protected String kota;
    protected String tanggalRekrut;
    protected String negara;

    public Person(int id, String noKtp, String namaDepan, String namaBelakang, String jenisKelamin, String email, String telepon, String alamat, String kota) {
        this.id = id;
        this.noKtp = noKtp;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.jenisKelamin = jenisKelamin;
        this.email = email;
        this.telepon = telepon;
        this.alamat = alamat;
        this.kota = kota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        if (noKtp.length() > 16) {
            this.noKtp = noKtp.substring(0, 15);
        } else {
            this.noKtp = noKtp;
        }
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat.trim();
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getTanggalRekrut() {
        return tanggalRekrut;
    }

    public void setTanggalRekrut(String tanggalRekrut) {
        this.tanggalRekrut = tanggalRekrut;
    }

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }
}
