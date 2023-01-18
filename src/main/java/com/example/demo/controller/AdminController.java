/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

/**
 *
 * @author Cedrick
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Admin;
import com.example.demo.util.exception.JSONException;
import com.example.demo.util.security.Security;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author P14A_30_Ando
 */
@RestController
@CrossOrigin
public class AdminController {
    HashMap<String, Object> returnValue;
    
    public AdminController() {
        returnValue = new HashMap<>();
    }  
    
    @PostMapping("/admin")
    public HashMap<String, Object> admin(@RequestHeader(name="email") String email, @RequestHeader(name="mdp") String mdp) throws Exception{
        try {
            returnValue.clear();
            BDD bdd = new BDD("postgres", "cedric10", "enchere", "postgresql");
            try (Connection c = bdd.getConnection()) {
                Admin admin = new Admin();
                admin.setEmail(email);
                admin.setMdp(mdp);

                admin.find(c);
                if(admin.getIdAdmin()>0){
                    returnValue.put("data", admin.getMyToken());
                } else {
                    returnValue.put("error", new JSONException("403", "User not found"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            returnValue.put("error", new JSONException("500", ex.getMessage()));
            return returnValue;
        }
        returnValue.put("response", new JSONException("200", "Insertion OK"));
        return returnValue;
    }

    public HashMap<String, Object> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(HashMap<String, Object> returnValue) {
        this.returnValue = returnValue;
    }
}
