package com.example.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    private static Connection cnx;
    private PreparedStatement pstm;
    private ResultSet rs;
    private int ok;

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/revisJX";
        String user = "root";
        String password = "";
        try {
            //Chaargement du pilote
            Class.forName("com.mysql.jdbc.Driver");
            //Ouverture de le connexion
            cnx= DriverManager.getConnection(url,user,password);
            System.out.println("Connexion Reussie !");

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return cnx;
    }

    public void initPrepar(String sql){
        try {
            getConnection();
            pstm = cnx.prepareStatement(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet ExecuteSelect() {
        rs = null;
        try{
            rs = pstm.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public int executeMaj() {
        try{
            ok = pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }
    public void closeConnection(){
        try{
            if(cnx !=null)
                cnx.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public PreparedStatement getPstm() {
        return pstm;
    }
}
