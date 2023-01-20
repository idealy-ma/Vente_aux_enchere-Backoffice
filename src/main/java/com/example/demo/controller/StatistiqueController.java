package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Statistique;

@CrossOrigin
@RestController
public class StatistiqueController {
    /*private BDD bdd;

    public StatistiqueController(){
        try {
            bdd = new BDD("vae", "vae", "vae","Postgresql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @GetMapping("/statistiques")
    public HashMap<String, Object> getStatistique() {
        HashMap<String, Object> returnValue = new HashMap<>();
        returnValue.put("data", new Statistique());
        return returnValue;
    }

}
