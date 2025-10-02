/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBQuery_1 {
    
    Connection con;
    Statement st;
    ResultSet rs;
    
    public String get_data() throws ClassNotFoundException, SQLException{
    int i=0;
    String w1="",w2="",w3="",m1="",m2="",m3="",d="",ph="",data="";
    con=DBConnection.getConnection();
    st=con.createStatement();
    
    String q="select * from sensor_details ";
    
    rs=st.executeQuery(q);
    while(rs.next())
    {
    
    w1=rs.getString("w1");
    w2=rs.getString("w2");
    w3=rs.getString("w3");
    m1=rs.getString("m1");
    m2=rs.getString("m2");
    m3=rs.getString("m3");
      ph=rs.getString("ph");
    d=rs.getString("atime");
    
    
    data="Level1="+w1+"#Level2="+w2+"#Level3="+w3+"#M1="+m1+"#M2="+m2+"#M3="+m3+"#PH="+ph+"#Date="+d;
    
    
    
    
    
    }
    
    return data;
    }
     public int add_data(String h,String t,String d,String mq) throws ClassNotFoundException, SQLException{
    int i=0;
    
    con=DBConnection.getConnection();
    st=con.createStatement();
    Date dd=new Date();
    String date=dd.toLocaleString();
    String q="insert into  sensor_details values('"+h+"','"+t+"','"+d+"','"+mq+"','"+date+"')";
   
    i=st.executeUpdate(q);
     System.out.println(i);
  
    
    
    return i;
    }
      public String loginCheck(String user, String pass) throws ClassNotFoundException, SQLException {
        String utype = "";
        con = DBConnection.getConnection();
        String q = "select utype from login where userid='" + user + "' and password='" + pass + "'";
        System.out.println("q = " + q);
        st = con.createStatement();
        rs = st.executeQuery(q);
        if (rs.next()) {
            utype = rs.getString("utype");
        }
        return utype;
    }
     
     
      
}
