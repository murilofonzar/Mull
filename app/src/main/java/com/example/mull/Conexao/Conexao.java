package com.example.mull.Conexao;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    private static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
    private static final String userName="mulle";
    private static final String password ="";
    private static final String url = "jdbc:mysql://192.168.50.205:3306/mull";
    public Connection conn  = null ;
    public Conexao(){
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(url,userName,password);
        }
        catch (Exception ex){
            System.err.println("-----------------------------");
            System.err.println("Não pode se conectar");
            System.err.println("-----------------------------");
            ex.printStackTrace();
        }
    }


}