package com.example.demo.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;

public class Commission extends BddObject {
    @PrimaryKey
    int idCommission;
    double pourcentage;
    
    public int getIdCommission() {
        return idCommission;
    }
    public void setIdCommission(int idCommission) {
        this.idCommission = idCommission;
    }
    public double getPourcentage() {
        return pourcentage;
    }
    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    @Override
    public void create(Connection c) throws Exception {
        String sql = "INSERT INTO Commission(pourcentage) VALUES (?)";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.pourcentage);
        executeQuery(c, sql, objects);

    }
}
