/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBQuery;
//import Logic.BarChart;
//import Logic.py_path;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sumit
 */
public class update_data extends HttpServlet {

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
           String data=request.getParameter("data");
           String service=request.getParameter("service");
           String lat=request.getParameter("lat");
           String lon=request.getParameter("lon");
            System.out.println("user="+user);
            System.out.println("service="+service);
            System.out.println("data="+data);
            System.out.println("lat="+lat);
            System.out.println("lon="+lon);
            DBQuery db=new DBQuery();
            String save=user+"#"+service+"#"+data+"#"+lat+"#"+lon;
//            File f=new File("C:/Users/sumit/Downloads/trust_blk/save.txt");
//		        try {
//					FileWriter fw=new FileWriter(f);
//					fw.write(save);
//					fw.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
                        
                        
//                        File f=new File(py_path.path+"Trust_Testing/input.txt");
//		        try {
//					FileWriter fw=new FileWriter(f);
//					fw.write(data);//service+"##"+
//					fw.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//                        
                        
                        Thread.sleep(4000);
                        
//                         File file = new File(py_path.path+"status.txt"); 
//  
//                    BufferedReader br = new BufferedReader(new FileReader(file)); 
//  
//                   String st="",data1=""; 
//                   while ((st = br.readLine()) != null) 
//                   {
//                   System.out.println(st);
//                   data1=st;
//                   }
                   // System.out.println("data1="+data1);
//		        try {
//					FileWriter fw=new FileWriter(file);
//					fw.write("");
//					fw.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//                    File file1 = new File(py_path.path+"Test_Accuracy.txt"); 
//  
//                    BufferedReader br1 = new BufferedReader(new FileReader(file1)); 
//  
                  String st1="",acc="90"; 
//                   while ((st1 = br1.readLine()) != null) 
//                   {
//                   System.out.println(st1);
//                   acc=st1;
//                   }
//                    System.out.println("accuracy="+acc);
                    
                   // acc=acc.substring(0,acc.length()-1);
                    if(Double.parseDouble(acc)>=90)
                    {
                     int pos_rep=db.get_user_pos_rep(user);
                     pos_rep++;
                     int neg_rep=db.get_user_neg_rep(user);
                     db.add_user_rep(user, pos_rep,neg_rep);
                        System.out.println("pos rep "+pos_rep);
                        System.out.println("neg rep "+neg_rep);
                    }
                    else if(Double.parseDouble(acc)<90)
                    {
                    int neg_rep=db.get_user_neg_rep(user);
                     neg_rep++;
                     int pos_rep=db.get_user_pos_rep(user);
                     db.add_user_rep(user, pos_rep,neg_rep);
                     System.out.println("pos rep "+pos_rep);
                        System.out.println("neg rep "+neg_rep);
                    }
                    
                    
                    
                       int count_exp=db.get_user_exp(user);
                       count_exp++;
                       db.add_user_exp(user, count_exp);
                       double tc=db.get_user_trust_count(user);
                       System.out.println("trust count "+tc);
                      // int count=db.get_user_reputation(user);
                      
                      
                      String ldate=db.get_user_last_posted_date(user);
                      
                      System.out.println("last posted date "+ldate);
                      
                      
                      String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String tdate = simpleDateFormat.format(new Date());
            System.out.println(tdate);
  
  
  
  
            String pattern1 = "HH:mm:ss";
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);

            String tdate1 = simpleDateFormat1.format(new Date());
            System.out.println(tdate1);
            String date=tdate+" "+tdate1;
            
            
            
            String diff= db.time_diff(date, ldate);
                      if(diff==null || diff.equalsIgnoreCase("null")){diff="0";}
                      System.out.println(">>>>>>>>>diff===="+diff);
                      if(Integer.parseInt(diff)>7)
                      {
                      
                       int count_exp1=db.get_user_exp(user);
                          System.out.println("count_exp1="+count_exp1);
                       count_exp1--;
                       count_exp1--;
                       db.add_user_exp(user, count_exp1);
                      }
                       
           // BarChart bc=new BarChart();
       
            try {
                int count_exp1=db.get_user_exp(user);
                int pos_rep1=db.get_user_pos_rep(user);
                int neg_rep1=db.get_user_neg_rep(user);
                double tc1=db.get_user_trust_count(user);
              //  bc.draw(user, count_exp1, pos_rep1, neg_rep1, tc1);
               
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } 
//                       if(data1.equalsIgnoreCase("matched"))
//                       {
//                       //   count=count+1;
//                       //   db.add_user_reputation(user, count);
//                       int i=db.add_user_data(user, service, data,lat,lon,acc);
//                        out.print("ok");
//                       }
//                       else{
//                          // count=count-1;
//                         // db.add_user_reputation(user, count);
//                           System.out.println("Not matching");
//                       out.print("notok");
//                       
//                       }
                        
                        
                        
                        
            
        }catch(Exception e)
        {
        e.printStackTrace();
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
