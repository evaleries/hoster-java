package com.hoster.app.models;

public class Karyawan extends Person {

    public Karyawan(int id, String noKtp, String namaDepan, String namaBelakang, String jenisKelamin, String email, String telepon, String alamat, String kota, String tanggalRekrut) {
        super(id, noKtp, namaDepan, namaBelakang, jenisKelamin, email, telepon, alamat, kota);
        setTanggalRekrut(tanggalRekrut);
    }

}
