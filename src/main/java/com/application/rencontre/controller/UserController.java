package com.application.rencontre.controller;

//import com.example.dbSync.dbmanager.connection.BDD;
import com.application.rencontre.dbmanager.connection.BDD;
import com.application.rencontre.model.Utilisateur;
import java.sql.Connection;
import java.sql.SQLException;
//import java.util.ArrayList;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

//import com.example.demo.util.exception.JSONException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import java.util.logging.Level;
//import java.util.logging.Logger;

@CrossOrigin
@RestController
public class UserController{
    HashMap<String, Object> returnValue;

    public UserController() {
        returnValue = new HashMap<>();
    }
    
    @PostMapping("/addUser")
    public HashMap<String, Object> synchroniser(@RequestBody Utilisateur object){
        HashMap<String, Object> retVal = new HashMap<>();
        System.out.println(object.getSexe());
        try {
            try (Connection c = new BDD("i.m.a","login","rencontre","postgresql").getConnection()) {
                object.create(c);
            }
//            retVal.put("data", 1);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            retVal.put("data", 0);
            return retVal;
        }
        return this.returnValue;
    }
    
    @GetMapping("/trait")
    public HashMap<String, Integer> getTraits(@RequestBody Utilisateur object){
         HashMap<String, Integer> retVal = new HashMap<>();
        try {
            try (Connection c = new BDD("i.m.a","login","rencontre","postgresql").getConnection()) {
                object.find(c);
                String[] text = {"je", "j'", "amour", "mort", "triste"};
                retVal = object.getCaracter(text);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }
}