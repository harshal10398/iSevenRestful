/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author harshal
 */
public class Party extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        String path=request.getPathInfo();
        if(path!=null){
            try {
                path=path.substring(1);
                Connection conn=StaticDatabaseConnectionHolder.getDatabaseConnection();
                String query="SELECT * FROM PARTY";
                ResultSet rs;
                Statement statement=conn.createStatement();
                if(path.equalsIgnoreCase("all")){
                    //return all party-agent info
                    rs=statement.executeQuery(query);
                }
                else{
                    rs=statement.executeQuery(query+" WHERE GSTIN = '"+path+"'");
                }
                response.getWriter().println(ResultSetToJson.getJson(rs));
            } catch (SQLException ex) {
                Logger.getLogger(PartyAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name,address,phone,gstin;
        name=request.getParameter("partyName");
        address=request.getParameter("partyAddress");
        phone=request.getParameter("partyPhone");
        gstin=request.getParameter("partyGstin");
        ServletOutputStream out=response.getOutputStream();
        if(gstin==null || !gstin.equalsIgnoreCase("") || gstin.length()!=15){
            out.println("gstin invalid or not provided! failed!");
            return;
        }
        StringBuilder updateQuery=new StringBuilder();
        updateQuery.append("(");
        if(name!=null || !name.equalsIgnoreCase("")){
            updateQuery.append("'"+name+"'");
        }
        if(phone!=null || !phone.equalsIgnoreCase("")){
            updateQuery.append(",'"+phone+"'");
        }
        if(address!=null || !address.equalsIgnoreCase("")){
            updateQuery.append(",'"+address+"'");
        }
        updateQuery.append(")");
        System.out.println(updateQuery.toString());
        /*
        if((name!=null || !name.equalsIgnoreCase("")) && 
                (address!=null || !address.equalsIgnoreCase("")) && 
                (phone!=null || !phone.equalsIgnoreCase("")) && 
                (gstin!=null || !gstin.equalsIgnoreCase("")) )
        {
            try{
                ServletOutputStream out=response.getOutputStream();
                Connection conn=StaticDatabaseConnectionHolder.getDatabaseConnection();
                conn.createStatement().executeUpdate("INSERT INTO PARTY VALUES ('"+gstin+"','"+name+"','"+phone+"','"+address+"')");
                out.println("success");
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
            catch(SQLException sqle){
//                sqle.printStackTrace();
                response.getOutputStream().write(sqle.getMessage().getBytes());
            }
        }
        */
    }
    
    @Override
    public void doPut(HttpServletRequest request,HttpServletResponse response){
        String name,address,phone,gstin;
        name=request.getHeader("partyName");
        address=request.getHeader("partyAddress");
        phone=request.getHeader("partyPhone");
        gstin=request.getHeader("partyGstin");
        if((name!=null || !name.equalsIgnoreCase("")) && 
                (address!=null || !address.equalsIgnoreCase("")) && 
                (phone!=null || !phone.equalsIgnoreCase("")) && 
                (gstin!=null || !gstin.equalsIgnoreCase("")) )
        {
            try{
                ServletOutputStream out=response.getOutputStream();
                Connection conn=StaticDatabaseConnectionHolder.getDatabaseConnection();
                conn.createStatement().executeUpdate("INSERT INTO PARTY VALUES ('"+gstin+"','"+name+"','"+phone+"','"+address+"')");
                out.println("success");
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
            catch(SQLException sqle){
                try {
                    //                sqle.printStackTrace();
                    response.getOutputStream().write(sqle.getMessage().getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(Party.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
