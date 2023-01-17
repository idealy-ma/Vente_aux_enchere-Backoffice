package com.application.rencontre.model;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.application.rencontre.dbmanager.annotation.PrimaryKey;
import com.application.rencontre.dbmanager.bdd.object.BddObject;
import com.application.rencontre.dbmanager.connection.BDD;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author i.m.a
 */
public class Utilisateur extends BddObject{
    @PrimaryKey
    private int id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private double salaire;
    private int foi;
    private double taille;
    private int couleur;
    private double morphologie;
    private int bacc;
    private String detail;
    private int sexe;
    private String traitDeCaractere;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public int getFoi() {
        return foi;
    }

    public void setFoi(int foi) {
        this.foi = foi;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public double getMorphologie() {
        return morphologie;
    }

    public void setMorphologie(double morphologie) {
        this.morphologie = morphologie;
    }

    public int getBacc() {
        return bacc;
    }

    public void setBacc(int bacc) {
        this.bacc = bacc;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getSexe() {
        return sexe;
    }

    public void setSexe(int sexe) {
        this.sexe = sexe;
    }
    
    public int getAge(){
        return (LocalDate.now().getYear()-this.getDateNaissance().toLocalDate().getYear());
    }

    public String getTraitDeCaractere(String[] text) {
        if(this.traitDeCaractere == null){
            this.setTraitDeCaractere(this.getCaracter(text));
        }
        return traitDeCaractere;
    }

    public void setTraitDeCaractere(HashMap<String, Integer> caracter) {
        String trait = "Cette utilisateur est de nature "; 
        if(caracter.get("excentrique") > 5){
            trait += "excentrique, ";
        }
        
        if(caracter.get("depressif") > 5){
            trait += "depressif, ";
        }
        
        if(caracter.get("amoureux") > 5){
            trait += "tres amoureux.";
        }
        
        this.traitDeCaractere = trait;
    }
    
    public HashMap<String, Integer> evaluate(String[] text){
        String[] value = this.detail.split(" ");
        HashMap<String, Integer> note = new HashMap<>();
        for (String string : text) {
            Integer d = (note.get(string)!=null) ? note.get(string) : 0;
            for (String mot : value) {
                if(mot.contains(string) || mot.equalsIgnoreCase(string)){
                    if(note.get(string)==null){
                        note.put(string, 0);
                        d = 0; 
                    }
                    d++;
                }
            }
            note.put(string, d);
            
        }
        
        return note;
    }
    
    public HashMap<String, Integer> getCaracter(String[] text){
        HashMap<String, Integer> caracter = new HashMap<>();
        
        caracter.put("excentrique",0);
        caracter.put("depressif",0);
        caracter.put("amoureux",0);
        
        
        HashMap<String, Integer> note = this.evaluate(text);
        int valeurExc = note.get("je")+note.get("j'");
        int valeurDepre = note.get("triste")+note.get("mort");
        int valeurAmoureux = note.get("amour");
        
        for (String string : text) {
            System.out.println(string + " : " + note.get(string));
        }


        caracter.put("excentrique",valeurExc);
        caracter.put("depressif",valeurDepre);
        caracter.put("amoureux",valeurAmoureux);
        
        this.setTraitDeCaractere(caracter);
        
        return caracter;
    }
    
    
    
    public static void main(String[] args) {
        Utilisateur u = new Utilisateur();
//        u.setDetail("je suis comme ca replit d'amour tristesse ca me rend triste triste mort de jalousie amoureux amour amour, tristesse  , triste , amour \n amour j'aime voir je suis j'ai je je je");
        try {
            ArrayList<Object> listUser = u.findAll(new BDD("i.m.a","login","rencontre","postgresql").getConnection());
            UserCritere uc = new UserCritere();
            uc.setUtilisateur((Utilisateur)listUser.get(0));
            ArrayList<Utilisateur> alU = new ArrayList<>();
            
            for (Object object : listUser) {
                alU.add((Utilisateur) object);
            }
            ArrayList<UserNotation> liUc = uc.getNoteUser(alU);
            
            for (UserNotation userNotation : liUc) {
                System.out.println(userNotation.getU().getNom()+" : "+userNotation.getNote());
            }
        } catch (Exception ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
//        String[] text = {"je", "j'", "amour", "mort", "triste"};
//        
//        System.out.println(u.getTraitDeCaractere(text));
        
    }
}
