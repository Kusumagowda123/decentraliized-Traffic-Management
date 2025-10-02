

import Database.DBQuery;
import Logic.info;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import com.oreilly.servlet.MultipartRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class UploadData1 extends HttpServlet {
    
    String location = "",uid="",obj="",lat="",lon="",date="",time="",desc="",area="";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
    String fileDir = "C:/temp/";
   
        System.out.println(">>"+fileDir);
    String paramname=null,filePath="";
  
            String user=request.getParameter("user");
           String data=request.getParameter("data");
           String service=request.getParameter("service");
           String lat=request.getParameter("lat");
           String lon=request.getParameter("lon");
           String area=request.getParameter("area");
            System.out.println("user="+user);
            System.out.println("service="+service);
            System.out.println("data="+data);
            System.out.println("lat="+lat);
            System.out.println("lon="+lon);
            System.out.println("area="+area);
            DBQuery db=new DBQuery();
            String save=user+"#"+service+"#"+data+"#"+lat+"#"+lon+"#"+area;
            int id=db.add_user_data(user, service, data, lat, lon,area);
            System.out.println("id "+id);
            save=id+"#"+save;
  
    {
        MultipartRequest multi = new MultipartRequest(request, fileDir,	10 * 1024 * 1024); // 10MB
                       
        
        Enumeration files = multi.getFileNames();	
	while (files.hasMoreElements()) 
	{
            paramname = (String) files.nextElement();
            String fPath="";
            if(paramname != null && paramname.equals("uploaded_file"))
            {
		filePath = multi.getFilesystemName(paramname);
                fPath = fileDir+filePath;
                System.out.println("::::::::::::="+filePath);
                System.out.println("::::::::::::="+fPath);
                
                String[] name = filePath.split(".");
                String val = location;
                session.setAttribute("filepath",filePath);
                
//                int count = dbq.countIDss();
                 // String ids = dbq.getIDs(email);
             //   int val1 = dbq.uploadData(val);
              File fdb=new File(info.pb+"a.jpg");
                fdb.deleteOnExit();
                 File fs=new File(fPath);
               
                File fd=new File(info.p+id+".jpg");
               
              //  fd.deleteOnExit();
                copyFileUsingStream(fs,fd);
                
                //File fd1=new File(path.py+filePath+".jpg");
               
              //  fd.deleteOnExit();
                //copyFileUsingStream(fs,fd1);
                File fh=new File(info.py+"data.txt");
                FileWriter fwh=new FileWriter(fh);
                fwh.write(save);
                fwh.close();
                File fh1=new File(info.py+"task.txt");
                FileWriter fwh1=new FileWriter(fh1);
                fwh1.write("upload");
                fwh1.close();
                File fh2=new File(info.py+"alert.txt");
                FileWriter fwh2=new FileWriter(fh2);
                fwh2.write(save);
                fwh2.close();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UploadData1.class.getName()).log(Level.SEVERE, null, ex);
                }
                   
                        
                out.print("ok");
                
                   }
                   
                   
                   
            
               
              //  out.print("ok");

      
    }
    }
    }

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UploadData1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UploadData1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

private static void copyFileUsingStream(File source, File dest) throws IOException {
    InputStream is = null;
    OutputStream os = null;
    try {
        is = new FileInputStream(source);
        os = new FileOutputStream(dest);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
    } finally {
        is.close();
        os.close();
    }
}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UploadData1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UploadData1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
