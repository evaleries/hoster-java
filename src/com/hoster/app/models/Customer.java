package com.hoster.app.models;

import java.util.HashMap;
import java.util.Map;

public class Customer extends Person {
    public Customer(int id, String noKtp, String namaDepan, String namaBelakang, String jenisKelamin, String email, String telepon, String alamat, String kota, String negara) {
        super(id, noKtp, namaDepan, namaBelakang, jenisKelamin, email, telepon, alamat, kota);
        setNegara(negara);
    }

    public Map<String, String> toHashMap() {
        Map<String, String> custData = new HashMap<>();
        custData.put("no_ktp", noKtp);
        custData.put("nama_depan", namaDepan);
        custData.put("nama_belakang", namaBelakang);
        custData.put("jenis_kelamin", "L");
        custData.put("email", email);
        custData.put("telepon", telepon);
        custData.put("alamat", alamat);
        custData.put("kota", kota);
        custData.put("negara", negara);
        return custData;
    }
}
