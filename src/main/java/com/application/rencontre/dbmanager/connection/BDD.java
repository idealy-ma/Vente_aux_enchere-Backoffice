/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application.rencontre.dbmanager.connection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author i.m.a
 */
public final class BDD {
    private String user;
    private String databaseName;
    private String mdp;
    private String databaseType;

    public BDD(String user, String mdp, String databaseName, String databaseType) throws Exception {
        this.setUser(user);
        this.setDatabaseName(databaseName);
        this.setMdp(mdp);
        this.setDatabaseType(databaseType);
    }
    
    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) throws Exception {
        if(!databaseType.equalsIgnoreCase("postgresql") && !databaseType.equalsIgnoreCase("mssql")) throw new Exception("Unknown database");
        this.databaseType = databaseType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Connection getConnection() throws SQLException{
        Connection c = null;
        if(this.getDatabaseType().equalsIgnoreCase("postgresql")){
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+this.getDatabaseName(),this.getUser(),this.getMdp());
        } else if(this.getDatabaseType().equalsIgnoreCase("mssql")) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerConnection");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            c = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;encrypt=false;DatabaseName="+this.getDatabaseName(),this.getUser(),this.getMdp());
        }
        return c;
    }
}
