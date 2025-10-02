/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBQuery;
import Logic.findDistance;
import Logic.info;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sumit
 */
public class update_location extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String user=request.getParameter("user");
           String lat=request.getParameter("lat");
           String lon=request.getParameter("lon");
            System.out.println("user>>>>"+user);
            System.out.println("lat>>>>"+lat);
            System.out.println("lon>>>>"+lon);
            DBQuery db=new DBQuery();
            try {
                db.update_user_loc(user, lat, lon);
                
                File file2 = new File(info.py+"alert.txt"); 
                    
                BufferedReader br2 = new BufferedReader(new FileReader(file2)); 
                String st2="",fdata2="";

               while ((st2 = br2.readLine()) != null) 
               {
               System.out.println(st2);
               fdata2=st2;
               }
                if (!fdata2.equals("")) {
                    
                
                String a[]=fdata2.split("#");
                if (!a[1].equals(user)) {
                    int i=db.user_opinion_check(user, a[0]);
                        System.out.println("i="+i);
                    if (i==0) {
                        
                    
                    findDistance fd=new findDistance();
                    double dis=fd.distance(Double.parseDouble(lat), Double.parseDouble(lon), Double.parseDouble(a[4]), Double.parseDouble(a[5]), 'K');
                    System.out.println("distance "+dis);
                    if (dis<.1) {
                        out.print(fdata2);
                    }
                    else{
                    out.print("no alert");
                    }
                    }
                    else{
                        System.out.println(user+" already voted for id "+a[0]);
                    out.print("no alert");
                    }
                }
                else{
                out.print("ok");
                }
                }
                
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(update_location.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(update_location.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
