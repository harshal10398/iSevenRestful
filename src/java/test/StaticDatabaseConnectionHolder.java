/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author harshal
 */
public class StaticDatabaseConnectionHolder {
    public static Connection conn;
    public static void initStaticDatabaseConnectionHolder(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/loom", "application" , "1StackOn!application");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Connection getDatabaseConnection(){
        
        if(conn==null)
            initStaticDatabaseConnectionHolder();
        
        return conn;
    }
}
