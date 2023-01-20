package com.example.demo.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Client;
import com.example.demo.model.RechargementCompte;
import com.example.demo.util.exception.JSONException;

@CrossOrigin
@RestController
public class CompteController {
    HashMap<String, Object> returnValue;
    BDD bdd;
    
    public CompteController() {
        try {
            bdd = new com.example.demo.dbmanager.connection.BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        returnValue = new HashMap<>();
    }
    
    public HashMap<String, Object> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(HashMap<String, Object> returnValue) {
        this.returnValue = returnValue;
    }

    @GetMapping("/rechargements")
    public HashMap<String, Object> getRechargement() throws Exception{
        try {
            returnValue.clear();
            
            Connection c = bdd.getConnection();
            RechargementCompte rechargeValide = new RechargementCompte();
            rechargeValide.setEtat(1);
            returnValue.put("data", rechargeValide.findAll(c));
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CompteController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue;
        } 
        return returnValue;
    }

    @PostMapping("/rechargements")
    public HashMap<String, Object> addRechargement(@RequestHeader(name="idRechargement") int idRechargement) throws Exception{
        Connection c = null;
        try {
            returnValue.clear();
            c = bdd.getConnection();
            c.setAutoCommit(false);

            RechargementCompte rechargementCompte = new RechargementCompte();
            rechargementCompte.setIdRechargement(idRechargement);
            rechargementCompte.find(c);

            Client client = new Client();
            client.setIdClient(rechargementCompte.getIdClient());
            client.find(c);
            client.crediter(rechargementCompte.getMontant());

            rechargementCompte.updateEtat(c);
            client.updateSolde(c);
            c.commit();
        } catch (SQLException ex) {
            if(c != null) c.rollback();
            Logger.getLogger(CompteController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue;
        } finally {
            if(c != null) c.close();
        }
        returnValue.put("response", new JSONException("200", "Insertion OK"));
        return returnValue;
    }
}
