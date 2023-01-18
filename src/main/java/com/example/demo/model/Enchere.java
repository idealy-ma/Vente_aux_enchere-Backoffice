/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.application.vaeBackoffice.dbmanager.annotation.PrimaryKey;
import com.application.vaeBackoffice.dbmanager.bdd.object.BddObject;
import com.application.vaeBackoffice.dbmanager.connection.BDD;

/**
 *
 * @author P14A_30_Ando
 */
public class Enchere extends BddObject{
    @PrimaryKey
    private int idEnchere;
    private String nomProduit;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private double prixMin;
    private String description;
    private Categorie categorie;
    private int idCategorie;  
    private Client client;
    private int idClient;



    public int getIdEnchere() {
        return idEnchere;
    }

    public Client getClient() {
        if(this.client == null) {
            this.client = new Client();
            this.client.setIdClient(this.getIdClient());
            try {
                Connection c = new BDD("vae","vae","vae","postgresql").getConnection();
                this.client.find(c);
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public double getPrixMin() {
        return prixMin;
    }

    public void setPrixMin(double prixMin) {
        this.prixMin = prixMin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categorie getCategorie() {
        if(this.categorie == null) {
            this.categorie = new Categorie();
            this.categorie.setIdCategorie(this.getIdCategorie());
            try {
                Connection c = new BDD("vae","vae","vae","postgresql").getConnection();
                this.categorie.find(c);
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    
    @Override
    public void create(Connection c) throws Exception {
        super.create(c);
        String sql="insert into Enchere(nomproduit,prixMin,description,idCategorie,idClient) values (?,?,?,?,?)";
        ArrayList<Object> objects=new ArrayList<>();
        objects.add(this.nomProduit);
        objects.add(this.prixMin);
        objects.add(this.description);
        objects.add(this.idCategorie);
        objects.add(this.idClient);
        executeQuery(c, sql, objects);
    }

    public ArrayList<Object> findEnchereValide(Connection connection) throws Exception {
        String sql = "select * from enchere e left join enchereValide ev on ev.idEnchere = e.idEnchere where ev.idEnchereValide is not null";
        return executeResultedQuery(connection, sql, null);             
    }
    
    public ArrayList<Object> findEnchereNotValide(Connection connection) throws Exception {
        String sql = "select * from enchere e left join enchereValide ev on ev.idEnchere = e.idEnchere where ev.idEnchereValide is null";
        return executeResultedQuery(connection, sql, null);             
    }
}
