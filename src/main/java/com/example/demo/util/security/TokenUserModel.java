package com.example.demo.util.security;

import java.sql.Timestamp;

import com.application.vaeBackoffice.dbmanager.bdd.object.BddObject;

public class TokenUserModel extends BddObject{
    private int userId;
    private String hash;
    private Timestamp expirationDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }
}
 