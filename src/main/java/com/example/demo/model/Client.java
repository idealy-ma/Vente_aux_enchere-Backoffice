/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;
import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.util.security.Security;
import com.example.demo.util.security.TokenUserModel;

/**
 *
 * @author P14A_30_Ando
 */
public class Client extends BddObject{
    @PrimaryKey
    private int idClient;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private double soldeClient;
    private TokenUserModel myToken;

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    public double getSoldeClient() {
        return soldeClient;
    }
    public void setSoldeClient(double soldeClient) {
        this.soldeClient = soldeClient;
    }
    
    public void generateToken() throws Exception{
        TokenUserModel tum = new TokenUserModel();
        tum.setUserId(this.getIdClient());
        tum.setHash(Security.getMd5(String.valueOf(this.getIdClient())));
        tum.setExpirationDate(Timestamp.valueOf(LocalDateTime.now()));
        try {
            BDD bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
            Connection c = bdd.getConnection();
            tum.find(c);
            c.close();
            this.setMyToken(tum);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public TokenUserModel getMyToken() {
        if(this.myToken == null){
            try {
                this.myToken = new TokenUserModel();
                myToken.setUserId(this.getIdClient());
                BDD bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
                Connection c = bdd.getConnection();
                myToken.find(c);
                c.close();
                // TODO : verification si le token est expirer
                if(myToken.getHash()==null) this.generateToken();
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return myToken;
    }

    public void setMyToken(TokenUserModel myToken) {
        this.myToken = myToken;
    }

    public void updateSolde(Connection c) throws Exception {
        String sql = "UPDATE client set soldeClient = ? where idClient = ?";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.getSoldeClient());
        objects.add(this.getIdClient());
        executeQuery(c, sql, objects);
    }

    public boolean verifySolde(double solde) {
        if (this.getSoldeClient()>solde) {
            return true;
        }
        return false;
    }

    public void debiter(double valeur){
        this.setSoldeClient(this.getSoldeClient()-valeur);
    }

    public void crediter(double valeur){
        this.setSoldeClient(this.getSoldeClient()+valeur);
    }
}
