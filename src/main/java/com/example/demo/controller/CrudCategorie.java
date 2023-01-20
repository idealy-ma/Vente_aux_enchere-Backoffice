package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Categorie;

@CrossOrigin
@RestController
public class CrudCategorie {
    BDD bdd = null;
    public CrudCategorie() {
        try {
            bdd = new BDD("vae","vae","vae","postgresql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/categories")
    public HashMap<String, Object> getCategorie() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            ArrayList<Object> arrayList = new Categorie().findAll(bdd.getConnection());
            hashMap.put("data", arrayList);
        } catch (Exception e) {
            hashMap.put("error", e.getMessage());
            e.printStackTrace();
        }
        return hashMap;
    }

    @PostMapping("/categories")
    public HashMap<String, Object> saveCategorie(@RequestBody Categorie categorie) throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            categorie.create(bdd.getConnection());
            hashMap.put("data", true);
        } catch (Exception e) {
            hashMap.put("error", e.getMessage());
            e.printStackTrace();
        }
        return hashMap;
    }
}
