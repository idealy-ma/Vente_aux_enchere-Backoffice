package com.example.demo.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import com.example.demo.dbmanager.annotation.PrimaryKey;
import com.example.demo.dbmanager.bdd.object.BddObject;
import com.example.demo.dbmanager.connection.BDD;


public class RechargementCompte extends BddObject {
    @PrimaryKey
    private int idRechargement;
    private Client client;
    private int idClient;
    private double montant;
    private Date dateRechargement;
    private int etat;
    
    public int getIdRechargement() {
        return idRechargement;
    }
    public void setIdRechargement(int idRechargement) {
        this.idRechargement = idRechargement;
    }
    public Client getClient() throws Exception {
        if(this.client == null) {
            this.client = new Client();
            this.client.setIdClient(this.getIdClient());
            try {
                Connection c = new BDD("postgres", "HY6NINF73nbTN5zYpzsk", "railway", "postgresql").getConnection();
                this.client.find(c);
                c.close();
            } catch (Exception ex) {
                throw ex;
            } 
        }
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public int getIdClient() {
        return idClient;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public double getMontant() {
        return montant;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }
    public Date getDateRechargement() {
        return dateRechargement;
    }
    public void setDateRechargement(Date dateRechargement) {
        this.dateRechargement = dateRechargement;
    }
    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public void create(Connection c) throws Exception {
        super.create(c);
        String sql="insert into RechargementCompte(idClient,montant) values (?,?)";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.idClient);
        objects.add(this.montant);
        executeQuery(c, sql, objects);
    }

    public void updateEtat(Connection c) throws Exception {
        String sql = "UPDATE RechargementCompte set etat = 5 where idRechargement=?";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.getIdRechargement());
        executeQuery(c, sql, objects);
    }
}
