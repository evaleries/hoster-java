package com.hoster.app.models;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthModel extends Models {
    public static final String TABLE = "login";

    public boolean authenticate(String username, String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(password));
            password = String.format("%032x", new BigInteger(1, md5.digest()));
            resultSet = db.selectAll(TABLE, String.format("username = '%s' AND password = '%s'", username, password));
            return resultSet.next();
        } catch (SQLException | NoSuchAlgorithmException s) {
            System.out.println(s.getMessage());
        }
        return false;
    }

    public void register(String username, String password) {
        // panggil class karyawan->add()
        // insert credentials ke login
    }
}
