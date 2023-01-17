/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application.rencontre.controller;

import com.application.rencontre.dbmanager.connection.BDD;
import com.application.rencontre.model.CoefficientCritere;
import com.application.rencontre.model.Critere;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author i.m.a
 */
@CrossOrigin
@RestController
public class CritereController{
    HashMap<String, Object> returnValue;

    public CritereController() {
        returnValue = new HashMap<>();
    }
    
    @GetMapping("/criteres")
    public ArrayList<Critere> synchroniser(){
        ArrayList<Critere> listeCritere = null;
        try {
            try (Connection c = new BDD("i.m.a","login","rencontre","postgresql").getConnection()) {
                ArrayList<Object> list = new Critere().findAll(c);
                listeCritere = new ArrayList<>();
                
                for (Object object : list) {
                    listeCritere.add((Critere) object);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listeCritere;
    }
    
    @PostMapping("/addCritere")
    public HashMap<String, Object> synchroniser(@RequestBody CoefficientCritere object){
        HashMap<String, Object> retVal = new HashMap<>();
        try {
            try (Connection c = new BDD("i.m.a","login","rencontre","postgresql").getConnection()) {
                object.create(c);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            retVal.put("data", 0);
            return retVal;
        }
        return this.returnValue;
    }
}
