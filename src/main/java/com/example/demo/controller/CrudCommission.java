package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dbmanager.connection.BDD;
import com.example.demo.model.Commission;

@CrossOrigin
@RestController
public class CrudCommission {
    BDD bdd = null;
    public CrudCommission() {
        try {
            bdd = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/commissions")
    public HashMap<String, Object> getCommission() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            ArrayList<Object> arrayList = new Commission().findAll(bdd.getConnection());
            hashMap.put("data", arrayList);
        } catch (Exception e) {
            hashMap.put("error", e.getMessage());
            e.printStackTrace();
        }
        return hashMap;
    }

    @PostMapping("/commissions")
    public HashMap<String, Object> saveCommission(@RequestBody Commission commission) throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            commission.create(bdd.getConnection());
            hashMap.put("data", true);
        } catch (Exception e) {
            hashMap.put("error", e.getMessage());
            e.printStackTrace();
        }
        return hashMap;
    }
}
