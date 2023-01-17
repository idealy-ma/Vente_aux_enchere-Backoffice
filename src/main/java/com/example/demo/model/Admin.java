/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;



import com.example.demo.dbmanager.bdd.object.BddObject;
import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.util.security.Security;
import com.example.demo.util.security.TokenUserModel;
import com.example.demo.util.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cedrick
 */
public class Admin extends BddObject{
    private int idAdmin;
    private String email;
    private String mdp;
    private TokenUserModel myToken;

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
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
    public void generateToken() throws Exception{
        TokenUserModel tum = new TokenUserModel();
        tum.setUserId(this.getIdAdmin());
        tum.setHash(Security.getMd5(String.valueOf(this.getIdAdmin())));
        tum.setExpirationDate(Timestamp.valueOf(LocalDateTime.now()));
        try {
            BDD bdd = new BDD("postgres", "cedric10", "enchere", "postgresql");
            Connection c = bdd.getConnection();
            tum.find(c);
            c.close();
            this.setMyToken(tum);
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public TokenUserModel getMyToken() {
        if(this.myToken == null){
            try {
                this.myToken = new TokenUserModel();
                myToken.setUserId(this.getIdAdmin());
                BDD bdd = new BDD("postgres", "cedric10", "enchere", "postgresql");
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
