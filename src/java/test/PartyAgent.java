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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author harshal
 */
@WebServlet(name = "PartyAgent", urlPatterns = {"/PartyAgent/*"})
public class PartyAgent extends HttpServlet {
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
        String path=request.getPathInfo();
        if(path!=null){
            try {
                path=path.substring(1);
                Connection conn=StaticDatabaseConnectionHolder.getDatabaseConnection();
                String query="SELECT * FROM "
                        + "PARTY_AGENT, "
                        + "PARTY, "
                        + "AGENT "
                        + "WHERE "
                        + "PARTY_AGENT.PARTY_GSTIN = PARTY.GSTIN AND "
                        + "PARTY_AGENT.AGENT_PHONE = AGENT.AGENT_PHONE";
                ResultSet rs;
                Statement statement=conn.createStatement();
                if(path.equalsIgnoreCase("all")){
                    //return all party-agent info
                    rs=statement.executeQuery(query);
                }
                else{
                    Integer partyAgentId=Integer.parseInt(path);
                    rs=statement.executeQuery(query+" AND PARTY_AGENT_ID = "+partyAgentId);
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
        
        
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String json=request.getParameter("json");
        
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
