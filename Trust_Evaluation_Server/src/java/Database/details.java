/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 *
 * @author sumit
 */
public class details {
    public static int count=0;
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
           try{
               String POLYGON="",GEOMETRYCOLLECTION="",LINESTRING="";
        File directory = new File("F:/chunks/");
            File ff[]=directory.listFiles();
               System.out.println(""+ff.length);
               POLYGON="hhjkh kjwhdkjqhdkjh";
            FileOutputStream foutP=new FileOutputStream(new File("E://t.txt"));
            
             String line_content1="";
            System.out.println("E://t.txt");
              FileReader fr11 = new FileReader("E://t.txt");
              LineNumberReader lnr11 = new LineNumberReader(fr11);
              File file1 =new File("E://t.txt");
              FileInputStream fis1=new FileInputStream(file1);
              BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
                 System.out.println("*******************");
              while (lnr11.readLine() != null){
                line_content1=br1.readLine(); 
              }
               System.out.println(line_content1);
            
            
              foutP.write(POLYGON.getBytes());
              foutP.close();
               
           for(int i=0;i<ff.length;i++)
             {
              System.out.println(1+""+ff[i].getAbsolutePath());
              FileReader fr1 = new FileReader(ff[i].getAbsoluteFile().toString());
              LineNumberReader lnr1 = new LineNumberReader(fr1);
              File file =new File(ff[i].getAbsoluteFile().toString());
              FileInputStream fis=new FileInputStream(file);
              BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                 System.out.println("*******************");
              while (lnr1.readLine() != null){
               String line_content=br.readLine();  
               if(line_content.contains("POLYGON")&&!line_content.contains("GEOMETRYCOLLECTION"))
               {
               
                //   System.out.println("POLYGON>>"+line_content);
              POLYGON+=line_content+"\n";
               }
               else if(line_content.contains("GEOMETRYCOLLECTION"))
               {
               
               // System.out.println("GEOMETRYCOLLECTION>>"+line_content);
               GEOMETRYCOLLECTION+=line_content+"\n";
               }
               else if(line_content.contains("LINESTRING"))
               {
               
               // System.out.println("LINESTRING>>"+line_content);
                LINESTRING+=line_content+"\n";
               }
              }
             
             System.out.println("POLYGON************************************\n"+POLYGON);
             System.out.println("***********************************");
             System.out.println("GEOMETRYCOLLECTION************************************\n"+GEOMETRYCOLLECTION);
             System.out.println("***********************************");
             System.out.println("LINESTRING************************************\n"+LINESTRING);
//              FileOutputStream foutP=new FileOutputStream(new File("F://chunks/"+i+"/POLYGON/p.txt"));
//              foutP.write(POLYGON.getBytes());
//              foutP.close();
//                        
//              FileOutputStream foutG=new FileOutputStream(new File("F://chunks/"+i+"/GEOMETRYCOLLECTION/g.txt"));
//              foutG.write(GEOMETRYCOLLECTION.getBytes());
//              foutG.close();
//                
//              FileOutputStream foutL=new FileOutputStream(new File("F://chunks/"+i+"/LINESTRING/l.txt"));
//              foutL.write(LINESTRING.getBytes());
//              foutL.close();
          }
           }catch(Exception e)
           {
           
           
           }
          
    }
}
