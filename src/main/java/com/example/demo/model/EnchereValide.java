package com.example.demo.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;
import com.example.demo.dbmanager.connection.BDD;

public class EnchereValide extends BddObject {
    @PrimaryKey
    private int idEnchereValide;
    private Date dateValidation;
    private int idEnchere;
    private Enchere enchere;

    public int getIdEnchereValide() {
        return idEnchereValide;
    }
    public void setIdEnchereValide(int idEnchereValide) {
        this.idEnchereValide = idEnchereValide;
    }
    public Date getDateValidation() {
        return dateValidation;
    }
    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }
    public int getIdEnchere() {
        return idEnchere;
    }
    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }
    public Enchere getEnchere() throws Exception {
        if(this.enchere == null) {
            this.enchere = new Enchere();
            this.enchere.setIdEnchere(this.getIdEnchere());
            try {
                Connection c = new BDD("vae","vae","vae","postgresql").getConnection();
                this.enchere.find(c);
                c.close();
            } catch (Exception ex) {
                throw ex;
            } 
        }
        return enchere;
    }
    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }

    @Override
    public void create(Connection c) throws Exception {
        super.create(c);
        String sql = "insert into EnchereValide(idEnchere) values(?)";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.idEnchere);
        executeQuery(c, sql, objects);
    }
}
