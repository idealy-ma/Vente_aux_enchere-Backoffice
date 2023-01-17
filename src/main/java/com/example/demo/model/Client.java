/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.application.vaeBackoffice.dbmanager.annotation.PrimaryKey;
import com.application.vaeBackoffice.dbmanager.bdd.object.BddObject;
import com.application.vaeBackoffice.dbmanager.connection.BDD;
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
    private float solde;
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
    public float getSolde() {
        return solde;
    }
    public void setSolde(float solde) {
        this.solde = solde;
    }
    
    public void generateToken() throws Exception{
        TokenUserModel tum = new TokenUserModel();
        tum.setUserId(this.getIdClient());
        tum.setHash(Security.getMd5(String.valueOf(this.getIdClient())));
        tum.setExpirationDate(Timestamp.valueOf(LocalDateTime.now()));
        try {
            BDD bdd = new BDD("vae", "vae", "vae", "postgresql");
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
                BDD bdd = new BDD("vae", "vae", "vae", "postgresql");
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
    
}
