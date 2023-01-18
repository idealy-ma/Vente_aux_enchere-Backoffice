package com.example.demo.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.application.vaeBackoffice.dbmanager.connection.BDD;
import com.example.demo.model.Client;
import com.example.demo.model.RechargeValide;
import com.example.demo.model.RechargementCompte;
import com.example.demo.util.exception.JSONException;

@CrossOrigin
@RestController
public class CompteController {
    HashMap<String, Object> returnValue;
    
    public CompteController() {
        returnValue = new HashMap<>();
    }
    
    public HashMap<String, Object> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(HashMap<String, Object> returnValue) {
        this.returnValue = returnValue;
    }
    @PostMapping("/rechargement?valide")
    public HashMap<String, Object> addRechargement(@RequestHeader(name="idRechargement") int idRechargement) throws Exception{
        try {
            returnValue.clear();
            BDD bdd = new BDD("vae", "vae", "vae", "postgresql");
            Connection c = bdd.getConnection();

            RechargeValide rechargeValide = new RechargeValide();
            rechargeValide.setIdRechargement(idRechargement);
            rechargeValide.create(c);
            
            RechargementCompte rechargementCompte = new RechargementCompte();
            rechargementCompte.setIdRechargement(idRechargement);
            rechargementCompte = (RechargementCompte)rechargementCompte.findAll(c).get(0);

            Client client = new Client();
            client.setIdClient(rechargementCompte.getIdClient());
            client = (Client)client.findAll(c).get(0);
            client.setSoldeClient(client.getSoldeClient()+rechargementCompte.getMontant());
            client.update(c);

            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CompteController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue;
        } 
        returnValue.put("response", new JSONException("200", "Insertion OK"));
        return returnValue;
    }
}
