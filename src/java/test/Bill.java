/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author harshal
 */
public class Bill extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        if(request.getPathInfo()==null){
            response.getWriter().println(Json.createObjectBuilder().build());
            return;
        }
        Connection conn=StaticDatabaseConnectionHolder.getDatabaseConnection();
        try {
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM BILL_BOOK WHERE BILL_NO = "+request.getPathInfo().substring(1));
            response.getWriter().println(ResultSetToJson.getJson(rs));
        } catch (SQLException ex) {
            Logger.getLogger(Bill.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
        PrintWriter out=response.getWriter();
        try{
            Connection conn=StaticDatabaseConnectionHolder.getDatabaseConnection();
            ResultSet rs;
            if(request.getPathInfo().equalsIgnoreCase("/all"))
                rs=conn.createStatement().executeQuery("SELECT * FROM BILL_BOOK");
            else
                rs=conn.createStatement().executeQuery("SELECT * FROM BILL_BOOK WHERE BILL_NO = "+Integer.parseInt(request.getPathInfo().substring(1)));
            out.println(ResultSetToJson.getJson(rs));
        } catch (SQLException ex) {
            out.println("There was a problem opening database!");
            ex.printStackTrace(out);
        }
        catch(NumberFormatException nfe){
            out.println("please provide bill number as argument!");
            nfe.printStackTrace(out);
        }
        catch(NullPointerException npe){
            out.println("invalid i/o");
            npe.printStackTrace(out);
        }
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getPathInfo());
    }
    @Override
    protected void doPut(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        
    }
    
    public String getServletInfo() {
        return "Short description";
    }

}
