/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application.rencontre.model;

import com.application.rencontre.dbmanager.connection.BDD;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author i.m.a
 */
public class UserCritere {
    private Utilisateur utilisateur;
    private ArrayList<CoefficientCritere> listeCritere;
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ArrayList<CoefficientCritere> getListeCritere(Connection c) {
        if(listeCritere==null){
            CoefficientCritere cc = new CoefficientCritere();
            cc.setUtilisateurId(this.getUtilisateur().getId());
            listeCritere = new ArrayList<>();
            try {
                ArrayList<Object> objectList = cc.findAll(c);
                for (Object object : objectList) {
                    listeCritere.add((CoefficientCritere) object);
                }
            } catch (Exception ex) {
                Logger.getLogger(UserCritere.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listeCritere;
    }

    public void setListeCritere(ArrayList<CoefficientCritere> listeCritere) {
        this.listeCritere = listeCritere;
    }
    
    public ArrayList<UserNotation> getNoteUser(ArrayList<Utilisateur> listUser){
        ArrayList<UserNotation> uN = new ArrayList<>();
        double noteAge = 0.0;
        for (Utilisateur utilisateur : listUser) {
            Connection c = null;
            try {
                c = new BDD("i.m.a","login","rencontre","postgresql").getConnection();
            } catch (Exception ex) {
                Logger.getLogger(UserCritere.class.getName()).log(Level.SEVERE, null, ex);
            }
            UserNotation uNTemp = new UserNotation();
            uNTemp.setU(utilisateur);
            String[] critere = {"Age","Salaire", "Taille", "Couleur", "Morphologie", "Foi", "Bacc"};
            
            for (int i = 0; i < critere.length; i++) {
                noteAge += uNTemp.NotateAge(this.getListeCritere(c).get(i).getMin(), this.getListeCritere(c).get(i).getMax(),critere[i])*this.getListeCritere(c).get(i).getCoefficient();
            }
            uNTemp.setNote(noteAge);
            uN.add(uNTemp);
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserCritere.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return uN;
    }
    
  
}
