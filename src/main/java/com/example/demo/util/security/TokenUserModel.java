package com.example.demo.util.security;

import com.example.demo.dbmanager.bdd.object.BddObject;
import java.sql.Timestamp;

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
 