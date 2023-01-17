/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application.rencontre.model;

import com.application.rencontre.dbmanager.annotation.PrimaryKey;
import com.application.rencontre.dbmanager.bdd.object.BddObject;
import com.application.rencontre.dbmanager.connection.BDD;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author i.m.a
 */
public class CoefficientCritere extends BddObject{
    @PrimaryKey
    private int id;
    private Critere critereInfo;
    private int coefficient;
    private double min;
    private double max;
    private int critereId;
    private int utilisateurId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Critere getCritereInfo() {
        return critereInfo;
    }

    public void setCritereInfo(Critere critereInfo) {
        this.critereInfo = critereInfo;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getCritereId() {
        return critereId;
    }

    public void setCritereId(int critereId) {
        this.critereId = critereId;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    
    public static void main(String[] args) {
//        Utilisateur u = new Utilisateur();
//        u.setDetail("je suis comme ca replit d'amour tristesse ca me rend triste triste mort de jalousie amoureux amour amour, tristesse  , triste , amour \n amour j'aime voir je suis j'ai je je je");
//        
//        String[] text = {"je", "j'", "amour", "mort", "triste"};
//        
//        System.out.println(u.getTraitDeCaractere(text));
        CoefficientCritere c = new CoefficientCritere();
        c.setCritereId(7);
        c.setCoefficient(10);
        c.setUtilisateurId(1);
        c.setMin(3);
        c.setMax(7);
        
        try {
            c.create(new BDD("i.m.a","login","rencontre","postgresql").getConnection());
        } catch (Exception ex) {
            Logger.getLogger(CoefficientCritere.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
