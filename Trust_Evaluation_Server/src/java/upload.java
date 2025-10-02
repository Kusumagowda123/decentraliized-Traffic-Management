

import Database.DBQuery;
import Logic.info;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author win 10
 */
public class upload extends HttpServlet {

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

            // Get the Base64 image string
            String base64String = request.getParameter("image");

            if (base64String == null || base64String.isEmpty()) {
                System.out.println("Error: Image data is empty");
                response.getWriter().write("Error: No image data provided.");
                return;
            }

            System.out.println("Base64 Image String: " + base64String);

            try {
                // Define the path to save the uploaded image
                // Using a relative path that points to the "uploads" directory inside your server's web app folder
                String outputPath = info.pb+"a.jpg";//getServletContext().getRealPath("/uploads/") + "image.jpg";

                // Create the directory if it doesn't exist
                File uploadDir = new File(getServletContext().getRealPath("/uploads/"));
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Process the image (decode and save)
                processImage(base64String, outputPath);
                Thread.sleep(2000);
                File fd=new File(info.p+id+".jpg");
                File fs=new File(outputPath);
              //  fd.deleteOnExit();
                copyFileUsingStream(fs,fd);
                
                // Respond with a success message
                response.getWriter().write("Image uploaded successfully to: " + outputPath);

            } catch (IOException e) {
                // Handle errors
                e.printStackTrace();
                response.getWriter().write("Error: Failed to save image.");
            }
        } catch (Exception e) {
            // General exception handling
            e.printStackTrace();
            response.getWriter().write("Error: An unexpected error occurred.");
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
private void processImage(String base64String, String outputPath) throws IOException {
    // Print the received Base64 string for debugging
    System.out.println("Received Base64 String: " + base64String);

    // Remove any unwanted characters (spaces, newlines, etc.)
    base64String = base64String.replaceAll("[^A-Za-z0-9+/=]", "");

    // URL-safe Base64 encoding uses '-' and '_', replace these with standard '+' and '/'
    base64String = base64String.replace('-', '+').replace('_', '/');

    // Add padding if necessary
    int paddingCount = base64String.length() % 4;
    if (paddingCount != 0) {
        base64String += "=".repeat(4 - paddingCount); // Ensure proper padding
    }

    // Debug: Print the processed Base64 string
    System.out.println("Processed Base64 String: " + base64String);

    try {
        // Decode the Base64 string
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // Save the image to the server
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(decodedBytes);
        }
    } catch (IllegalArgumentException e) {
        // Handle invalid Base64 data error
        System.err.println("Error: Invalid Base64 string.");
        throw new IOException("Invalid Base64 string.", e);
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

}
