package com.example.demo.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;
import com.example.demo.dbmanager.connection.BDD;

public class RechargeValide extends BddObject {
    @PrimaryKey
    private int idRechargeValide;
    private RechargementCompte rechargementCompte;
    private int idRechargement;
    private Date dateValidation;

    public int getIdRechargeValide() {
        return idRechargeValide;
    }
    public void setIdRechargeValide(int idRechargeValide) {
        this.idRechargeValide = idRechargeValide;
    }
    public RechargementCompte getRechargementCompte() throws Exception {
        if(this.rechargementCompte == null) {
            this.rechargementCompte = new RechargementCompte();
            this.rechargementCompte.setIdRechargement(this.getIdRechargement());
            try {
                Connection c = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql").getConnection();
                this.rechargementCompte.find(c);
                c.close();
            } catch (Exception ex) {
                throw ex;
            } 
        }
        return rechargementCompte;
    }
    public void setRechargementCompte(RechargementCompte rechargementCompte) {
        this.rechargementCompte = rechargementCompte;
    }
    public int getIdRechargement() {
        return idRechargement;
    }
    public void setIdRechargement(int idRechargement) {
        this.idRechargement = idRechargement;
    }
    public Date getDateValidation() {
        return dateValidation;
    }
    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    @Override
    public void create(Connection c) throws Exception {
        // TODO Auto-generated method stub
        super.create(c);
        String sql="insert into RechargeValide(idRechargement) values (?)";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.idRechargement);
        executeQuery(c, sql, objects);
    }
}
