/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application.rencontre.view;

import com.application.rencontre.model.Critere;
import com.application.rencontre.model.Utilisateur;
import java.util.ArrayList;

/**
 *
 * @author i.m.a
 */
public class CritereView {
    private Utilisateur utilisateur;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ArrayList<Critere> getListeCritere() {
        return listeCritere;
    }

    public void setListeCritere(ArrayList<Critere> listeCritere) {
        this.listeCritere = listeCritere;
    }
    private ArrayList<Critere> listeCritere;
    
}