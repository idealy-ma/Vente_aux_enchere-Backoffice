package com.example.demo.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.example.demo.dbmanager.connection.BDD;

public class Statistique {
    private ArrayList<CategorieAffaire> arrayListCategoriesAffaires;
    private ArrayList<SoldeParMois> arrayListSoldeParMois;
    private BDD bdd;

    public Statistique(){
        try {
            bdd = new BDD("vae", "vae", "vae","Postgresql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CategorieAffaire> getArrayListCategoriesAffaires() {
        if( arrayListCategoriesAffaires == null ){
            arrayListCategoriesAffaires = new ArrayList<>();
            Connection connection;
            try {
                connection = bdd.getConnection();
                ArrayList<Object> arrayList = new CategorieAffaire().findAll(connection);
                for (Object categorieAffaire : arrayList) {
                    arrayListCategoriesAffaires.add((CategorieAffaire)categorieAffaire);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayListCategoriesAffaires;
    }
    public void setArrayListCategoriesAffaires(ArrayList<CategorieAffaire> arrayListCategoriesAffaires) {
        this.arrayListCategoriesAffaires = arrayListCategoriesAffaires;
    }
    public ArrayList<SoldeParMois> getArrayListSoldeParMois() {
        if( arrayListSoldeParMois == null ){
            arrayListSoldeParMois = new ArrayList<>();
            Connection connection;
            try {
                connection = bdd.getConnection();
                ArrayList<Object> arrayList = new SoldeParMois().findAll(connection);
                for (Object solde : arrayList) {
                    arrayListSoldeParMois.add((SoldeParMois)solde);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayListSoldeParMois;
    }
    public void setArrayListSoldeParMois(ArrayList<SoldeParMois> arrayListSoldeParMois) {
        this.arrayListSoldeParMois = arrayListSoldeParMois;
    }
}
