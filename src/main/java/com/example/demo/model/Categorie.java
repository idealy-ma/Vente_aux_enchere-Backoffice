/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.sql.Connection;
import java.util.ArrayList;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;

/**
 *
 * @author P14A_30_Ando
 */
public class Categorie extends BddObject{
    @PrimaryKey
    private int idCategorie;
    private String nomCategorie;

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
    
    @Override
    public void create(Connection c) throws Exception {
        String sql = "INSERT INTO Categorie(nomcategorie) VALUES (?)";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.nomCategorie);
        executeQuery(c, sql, objects);
    }
}
