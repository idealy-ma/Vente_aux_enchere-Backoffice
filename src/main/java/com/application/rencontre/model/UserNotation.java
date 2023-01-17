/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application.rencontre.model;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author i.m.a
 */
public class UserNotation {
    private Utilisateur u;
    private double note;

    public Utilisateur getU() {
        return u;
    }

    public void setU(Utilisateur u) {
        this.u = u;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }
    
    public double NotateAge(double min, double max, String name){
        double age = 0.0;
        try {
            Object result = this.getU().getClass().getMethod("get"+name).invoke(this.getU());
            age = Double.parseDouble(result.toString());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(UserNotation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(age > min && age < max){
            note = 20.0;
        }
        
        return note;
    }
}
