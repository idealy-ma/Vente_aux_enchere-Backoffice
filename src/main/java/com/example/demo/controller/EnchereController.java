package com.example.demo.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Enchere;
import com.example.demo.model.EnchereValide;
import com.example.demo.util.exception.JSONException;

@CrossOrigin
@RestController
public class EnchereController {
    HashMap<String, Object> returnValue;
    
    public EnchereController() {
        returnValue = new HashMap<>();
    }
    
    public HashMap<String, Object> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(HashMap<String, Object> returnValue) {
        this.returnValue = returnValue;
    }

    @GetMapping("/encheres:validate")
    public HashMap<String, Object> findEnchereValider() throws Exception{
        returnValue.clear();
        ArrayList<Enchere> list = new ArrayList<>();
        try {
            BDD bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
            Connection c = bdd.getConnection();
            Enchere enchere = new Enchere();
            ArrayList<Object> listeObjectClient = enchere.findEnchereValide(c);
            
            
            for (Object ench : listeObjectClient) {
                list.add((Enchere)ench);
            }
            
            returnValue.put("data", list);
            c.close();
        } catch (SQLException ex) {
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        }
        return returnValue;
    }

    @GetMapping("/encheres:notvalidate")
    public HashMap<String, Object> findEnchereNotValider() throws Exception{
        returnValue.clear();
        ArrayList<Enchere> list = new ArrayList<>();
        try {
            BDD bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
            Connection c = bdd.getConnection();
            Enchere enchere = new Enchere();
            ArrayList<Object> listeObjectClient = enchere.findEnchereNotValide(c);
            
            
            for (Object ench : listeObjectClient) {
                list.add((Enchere)ench);
            }
            
            returnValue.put("data", list);
            c.close();
        } catch (SQLException ex) {
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue; 
        }
        return returnValue;
    }

    @PostMapping("/encheres:validation")
    public HashMap<String, Object> ValideAjoutEnchere(@RequestBody EnchereValide enchereValide) throws Exception{
        returnValue.clear();
        try {
            BDD bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
            Connection c = bdd.getConnection();
            
            enchereValide.create(c);
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
