package com.example.demo.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.demo.dbmanager.annotation.DBTable;
import com.example.demo.dbmanager.bdd.object.BddObject;
import com.example.demo.dbmanager.connection.BDD;

@DBTable(tableName = "v_chiffreAffaire")
public class CategorieAffaire extends BddObject {
    private int idCategorie;
    private double chiffreAffaire;
    private Categorie categorie;
    public int getIdCategorie() {
        return idCategorie;
    }
    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
    public double getChiffreAffaire() {
        return chiffreAffaire;
    }
    public void setChiffreAffaire(double chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }
    public Categorie getCategorie() {
        if(this.categorie == null) {
            this.categorie = new Categorie();
            this.categorie.setIdCategorie(this.getIdCategorie());
            try {
                Connection c = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql").getConnection();
                this.categorie.find(c);
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
