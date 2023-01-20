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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author P14A_30_Ando
 */
@RestController
@CrossOrigin
public class AdminController {
    BDD bdd = null;
    HashMap<String, Object> returnValue;
    
    public AdminController() {
        returnValue = new HashMap<>();
        try {
            bdd = new BDD("vae", "vae", "vae", "postgresql");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }  
    
    @PostMapping("/login")
    public HashMap<String, Object> admin(@RequestBody Admin admin) throws Exception{
        try {
            returnValue.clear();
            try (Connection c = bdd.getConnection()) {
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
        return returnValue;
    }

    public HashMap<String, Object> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(HashMap<String, Object> returnValue) {
        this.returnValue = returnValue;
    }
}
