/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application.rencontre.model;

import com.application.rencontre.dbmanager.annotation.PrimaryKey;
import com.application.rencontre.dbmanager.bdd.object.BddObject;

/**
 *
 * @author i.m.a
 */
public class Critere extends BddObject{
    @PrimaryKey
    private int id;
    private String description;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
