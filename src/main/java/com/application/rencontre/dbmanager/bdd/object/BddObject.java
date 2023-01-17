/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.application.rencontre.dbmanager.bdd.object;

import com.application.rencontre.dbmanager.annotation.DBTable;
import com.application.rencontre.dbmanager.annotation.PrimaryKey;
import com.application.rencontre.dbmanager.connection.BDD;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author i.m.a
 */
public class BddObject {
    public void find(Connection c) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        boolean openHere = false;
        try {
            if(c == null){
                BDD base = new BDD("idealy", "root", "gestion_dentiste", "postgresql");
                c = base.getConnection();
                openHere = true;
            }
            Method[] methods = this.getClass().getDeclaredMethods();
            
            String tableName = this.getTableName(this.getClass());
            String[] colList = this.getColumnNames(c);
            
            String sql = "SELECT * FROM "+ tableName+" WHERE 1=1";
            
            for (String colList1 : colList) {
                Method m = this.getMethodInto("get" + colList1, methods);
                Object o = m.invoke(this);
                o = (o!=null && (o.toString().compareToIgnoreCase("0")==0 || o.toString().compareToIgnoreCase("0.0")==0)) ? null : o ;
                if(o != null){
                    sql += " AND "+colList1+"='"+o+"'";
                }
            }
            preparedStatement = c.prepareStatement(sql);
            System.out.println(preparedStatement);
            
            resultSet = preparedStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            
            int column = resultSetMetaData.getColumnCount();
            
            while(resultSet.next()){
                for (int i = 1; i <= column; i++) {
                    Method m = this.getMethodInto("set"+resultSetMetaData.getColumnName(i), methods);
                    
                    if(m==null) throw new NoSuchMethodException("No method in findall, function");
                    
                    m.invoke(this, resultSet.getObject(i));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Erreur Dans la classe : " + this.getClass().getName()+ "fonction findAll(), probleme de SQL");
        } finally {
            if(resultSet!=null) resultSet.close();
            if(preparedStatement!=null) preparedStatement.close();
            if(openHere) c.close();
        }
    }
    
    public ArrayList<Object> findAll(Connection c) throws Exception {
        try {
            if(c == null){
                BDD base = new BDD("idealy", "root", "gestion_dentiste", "postgresql");
                c = base.getConnection();
            }
            
            ArrayList<Object> listeObject = new ArrayList<>();
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            ResultSetMetaData resultSetMetaData;
            
            Method[] methods = this.getClass().getDeclaredMethods();
            
            String tableName = this.getTableName(this.getClass());
            String[] colList = this.getColumnNames(c);
            
            String sql = "SELECT * FROM "+ tableName+" WHERE 1=1";
            
            for (String colList1 : colList) {
                Method m = this.getMethodInto("get" + colList1, methods);
                Object o = null;
                if(m != null){
                    o = m.invoke(this);
                    System.out.println(m);
                } else {
                    throw new Exception("NoSuchMethodException : does your fields has the same names as the columns in the database, get" + colList1 + "is The Method");
                }
                o = (o!=null && (o.toString().compareToIgnoreCase("0")==0 || o.toString().compareToIgnoreCase("0.0")==0)) ? null : o ;
                if(o != null){
                    sql += " AND "+colList1+"='"+o+"'";
                }
            }
            preparedStatement = c.prepareStatement(sql);
            System.out.println(preparedStatement);
            
            resultSet = preparedStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            
            int column = resultSetMetaData.getColumnCount();
            
            while(resultSet.next()){
                Object o = this.getClass().newInstance();
                for (int i = 1; i <= column; i++) {
                    Method m = this.getMethodInto("set"+resultSetMetaData.getColumnName(i), methods);
                    
                    if(m==null) throw new NoSuchMethodException("No method in findall, function");
                    
                    m.invoke(o, resultSet.getObject(i));
                }
                listeObject.add(o);
            }
            return listeObject;
        } catch (SQLException ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Erreur Dans la classe : " + this.getClass().getName()+ "fonction findAll(), probleme de SQL");
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Erreur Dans la classe " + this.getClass().getName()+ "fonction findAll(), probleme d'Instanciation");
        }
    }
    
    public void create(Connection c) throws Exception{
        PreparedStatement preparedStatement = null;
        try {
            if(c == null){
                BDD base = new BDD("idealy", "root", "gestion_dentiste", "postgresql");
                c = base.getConnection();
            }
            Method[] methods = this.getClass().getDeclaredMethods();
            String tableName = this.getTableName(this.getClass());
            String[] colList = this.getColumnNames(c);
            
            String sql = "INSERT INTO "+ tableName+"(";
            
            for (int i = 0; i < colList.length; i++) {
                if(!isPrimaryKey(colList[i])){
                    sql+=colList[i];
                    if(i+1!=colList.length){
                        sql+=",";
                    }
                }
            }
            
            sql += ") VALUES (";
            
            for (int i = 0; i < colList.length; i++) {
                if(!isPrimaryKey(colList[i])){
                    sql+="?";
                    if(i+1!=colList.length){
                        sql+=",";
                    }
                }
            }
            
            sql+=")";
            
            System.out.println(sql);
            
            preparedStatement = c.prepareStatement(sql);
            
            for (int i = 0; i < colList.length; i++) {
                if(!isPrimaryKey(colList[i])){
                    System.out.println(colList[i]);
                    Method m = this.getMethodInto("get"+colList[i], methods);
                    preparedStatement.setObject(i, m.invoke(this));
                    System.out.println(preparedStatement);
                }
            }
            
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Erreur Dans la classe : " + this.getClass().getName()+ "fonction findAll(), probleme de SQL");
        } finally {
            if(preparedStatement!=null) preparedStatement.close();
        }
    }
    
    public void executeQuery(Connection c, String sql, ArrayList<Object> objects) throws SQLException, Exception{
        PreparedStatement preparedStatement = null;
        boolean isOpen = false;
        try {
            if(c == null){
                c = new BDD("idealy", "root", "gestion_dentiste", "postgresql").getConnection();
                isOpen = true;
            }
            
            preparedStatement = c.prepareStatement(sql);
            if(objects!=null && !objects.isEmpty()){
                int i = 1;
                for (Object object : objects) {
                    if(!object.toString().startsWith("seq")){
                        preparedStatement.setObject(i, object);
                        i++;
                    }
                }
            }
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Erreur Dans la classe : " + this.getClass().getName()+ "fonction findAll(), probleme de SQL");
        } catch (Exception ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if(preparedStatement!=null) preparedStatement.close();
            if(isOpen) c.close(); 
        }
    }
    
    public ArrayList<Object> executeResultedQuery(Connection c, String sql, ArrayList<Object> objects) throws SQLException, Exception{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        boolean isOpen = false;
        try {
            if(c == null){
                c = new BDD("idealy", "root", "gestion_dentiste", "postgresql").getConnection();
                isOpen = true;
            }
            
            preparedStatement = c.prepareStatement(sql);
            if(objects!=null && !objects.isEmpty()){
                int i = 1;
                for (Object object : objects) {
                    preparedStatement.setObject(i, object);
                    i++;
                }
            }
            
            Method[] methods = this.getClass().getDeclaredMethods();
            
            resultSet = preparedStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            
            ArrayList<Object> listeObject = new ArrayList<>();
            
            int column = resultSetMetaData.getColumnCount();
            
            while(resultSet.next()){
                Object o = this.getClass().newInstance();
                for (int i = 1; i <= column; i++) {
                    Method m = this.getMethodInto("set"+resultSetMetaData.getColumnName(i), methods);
                    
                    if(m==null) throw new NoSuchMethodException("No method in findall, function");
                    
                    m.invoke(o, resultSet.getObject(i));
                }
                listeObject.add(o);
            }
            return listeObject;
            
        } catch (SQLException ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Erreur Dans la classe : " + this.getClass().getName()+ "fonction findAll(), probleme de SQL");
        } catch (Exception ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if(resultSet != null) resultSet.close();
            if(preparedStatement!=null) preparedStatement.close();
            if(isOpen) c.close(); 
        }
    }
    
    private boolean isPrimaryKey(String columnName){
        try {
            Field f = this.getClass().getDeclaredField(columnName);
            return f.getAnnotation(PrimaryKey.class)!=null;
        } catch (NoSuchFieldException | SecurityException ex) {
//            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public String getStartedSequence(Connection c,String sequenceName, Integer nextvalue) throws SQLException{
        String seq = "?";
        PreparedStatement ps = null;
        try {
            if(nextvalue == null){
                seq = "concat('cli1-','"+getNextValSequence(c, sequenceName)+"')";
            } else {
                seq = "concat('cli1-','"+nextvalue+"')";
            }
        } catch (SQLException ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
        return seq;
    }
    
    public int getNextValSequence(Connection c,String sequenceName) throws SQLException{
        int seq = -1;
        PreparedStatement ps = null;
        try {
            if(c.getMetaData().getDatabaseProductName().equalsIgnoreCase("Microsoft SQL Server")) {
                ps = c.prepareStatement("SELECT next value for "+sequenceName+" nextval");
                ResultSet rs = ps.executeQuery();
                while(rs.next())seq = rs.getInt("nextval");
            }
            else {
                ps = c.prepareStatement("SELECT nextval('"+sequenceName+"')");
                ResultSet rs = ps.executeQuery();
                while(rs.next())seq = rs.getInt("nextval");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if(ps != null) ps.close();
        }
        return seq;
    }
    
    public void update(Connection c) throws Exception{
//        PreparedStatement preparedStatement = null;
//        try {
//            if(c == null){
//                c = BDD.getConnection();
//            }
//            Method[] methods = this.getClass().getDeclaredMethods();
//            String[] className = this.getClass().getCanonicalName().split("\\.");
//            String[] colList = this.getColumnNames(c);
//            
//            String sql = "INSERT INTO "+ className[className.length-1]+" VALUES (";
//            for (int i = 0; i < colList.length; i++) {
//                sql+="?";
//                if(i+1!=colList.length){
//                    sql+=",";
//                }
//            }
//            sql+=")";
//            
//            preparedStatement = c.prepareStatement(sql);
//            
//            for (int i = 0; i < colList.length; i++) {
//                Method m = this.getMethodInto("get"+colList[i], methods);
//                preparedStatement.setObject(i+1, m.invoke(this));
//                System.out.println(preparedStatement);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
//            throw new SQLException("Erreur Dans la classe : " + this.getClass().getName()+ "fonction findAll(), probleme de SQL");
//        } finally {
//            if(preparedStatement!=null) preparedStatement.close();
//        }
    }
    
    public void delete(Connection c) throws Exception{
//        PreparedStatement preparedStatement = null;
//        try {
//            if(c == null){
//                c = BDD.getConnection();
//            }
//            Method[] methods = this.getClass().getDeclaredMethods();
//            String[] className = this.getClass().getCanonicalName().split("\\.");
//            String[] colList = this.getColumnNames(c);
//            
//            String sql = "INSERT INTO "+ className[className.length-1]+" VALUES (";
//            for (int i = 0; i < colList.length; i++) {
//                sql+="?";
//                if(i+1!=colList.length){
//                    sql+=",";
//                }
//            }
//            sql+=")";
//            
//            preparedStatement = c.prepareStatement(sql);
//            
//            for (int i = 0; i < colList.length; i++) {
//                Method m = this.getMethodInto("get"+colList[i], methods);
//                preparedStatement.setObject(i+1, m.invoke(this));
//                System.out.println(preparedStatement);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
//            throw new SQLException("Erreur Dans la classe : " + this.getClass().getName()+ "fonction findAll(), probleme de SQL");
//        } finally {
//            if(preparedStatement!=null) preparedStatement.close();
//        }
    }
    
    private String getTableName(Class c){
         DBTable annotation = (DBTable)c.getAnnotation(DBTable.class);
         if (annotation != null) {
            return annotation.tableName();
        }
        return getClassName(c);
    }
    
    public String getClassName(Class c){
        String[] name = c.getCanonicalName().split("\\.");
        return name[name.length-1];
    }
    
    public String[] getColumnNames(Connection c) throws SQLException, Exception {
        String[] col = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        
        if(c == null){
            try {
                BDD base = new BDD("i.m.a", "vehicule","login","postgres");
                c = base.getConnection();
            } catch (Exception ex) {
                Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            }
        }
        
        try {
            statement = c.createStatement();
            String tableName = this.getTableName(this.getClass());
            String sql = "SELECT * FROM "+tableName;
            resultSet = statement.executeQuery(sql);
            resultSetMetaData = resultSet.getMetaData();
            
            int colCount = resultSetMetaData.getColumnCount();
            
            col = new String[colCount];
            
            for (int i = 0; i < colCount; i++) {
                col[i] = resultSetMetaData.getColumnName(i+1);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(BddObject.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
        }
        
        return col;
    }
    
    public Method getMethodInto(String methodName, Method[] methods){
        Method m = null;
        for (Method method : methods) {
            if(methodName.compareToIgnoreCase(method.getName()) == 0){
                return method;
            }
        }
        
        return m;
    }
    
    
}
