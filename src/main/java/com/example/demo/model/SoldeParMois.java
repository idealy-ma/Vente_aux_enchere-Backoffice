package com.example.demo.model;

import com.example.demo.dbmanager.annotation.DBTable;
import com.example.demo.dbmanager.bdd.object.BddObject;

@DBTable(tableName = "v_soldeParMois")
public class SoldeParMois extends BddObject{
    private double solde;
    private int mois;
    public double getSolde() {
        return solde;
    }
    public void setSolde(double solde) {
        this.solde = solde;
    }
    public int getMois() {
        return mois;
    }
    public void setMois(int mois) {
        this.mois = mois;
    }
}
