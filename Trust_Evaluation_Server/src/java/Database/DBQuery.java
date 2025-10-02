package Database;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBQuery {

    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

  
      public int user_register(String name,String password,String gender,String email,String mob,String profession) throws ClassNotFoundException, SQLException
    {
        String pattern = "dd-MM-yyyy";
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

  String tdate = simpleDateFormat.format(new Date());
  System.out.println(tdate);
    String qry = "insert into user_details values('"+name+"','"+password+"','"+gender+"','"+email+"','"+mob+"','"+profession+"','"+tdate+"')";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    int rows = stmt.executeUpdate(qry);
     String qry1 = "insert into user_rewards values('"+mob+"','0')";
    rows = stmt.executeUpdate(qry1);
    String qry2 = "insert into user_exp values('"+mob+"','0')";
    rows = stmt.executeUpdate(qry2);
    String qry3 = "insert into user_rep values('"+mob+"','0','0')";
    rows = stmt.executeUpdate(qry3);
    
    
    
    return rows;
    }
   public int update_user_loc(String mob,String lat,String lon) throws ClassNotFoundException, SQLException
    {
        String pattern = "dd-MM-yyyy";
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

  String tdate = simpleDateFormat.format(new Date());
  System.out.println(tdate);
  String pattern1 = "HH:mm:ss";
  SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);

  String tdate1 = simpleDateFormat1.format(new Date());
  System.out.println(tdate1);
  String date=tdate+" "+tdate1;
  
  
  String q="delete from user_location where mob='"+mob+"'";
    String qry = "insert into user_location values('"+mob+"','"+lat+"','"+lon+"','"+date+"')";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    stmt.executeUpdate(q);
    int rows = stmt.executeUpdate(qry);
    return rows;
    }
    public int add_user_interest(String mob,String interest,String status) throws ClassNotFoundException, SQLException
    {
    int st=2;
    int rows =0;
    String q="select * from user_interests where mob='"+mob+"' and interests='"+interest+"'";
        System.out.println(q);
    String q1 = "insert into user_interests values('"+mob+"','"+interest+"','"+status+"')";
    String q2 = "update user_interests set status ='"+status+"' where mob='"+mob+"' and interests='"+interest+"'";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs=stmt.executeQuery(q);
    if(rs.next())
    {
    
    st=rs.getInt("status");
    }
        System.out.println("111>>"+st);
    if(st==2)
    {
        System.out.println(q1);
        rows = stmt.executeUpdate(q1);
    
    }
    else 
    {
        System.out.println(q2);
     rows = stmt.executeUpdate(q2);
    }
    
    return rows;
    }
    public int get_user_reputation(String user) throws ClassNotFoundException, SQLException
    {
    int count=0;
 
    String q="select * from user_reputation where user='"+user+"'";
    System.out.println(q);
  
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs=stmt.executeQuery(q);
    if(rs.next())
    {
    
    count=rs.getInt("reputation");
    }
        
   
    
    return count;
    }
    public int get_user_exp(String user) throws ClassNotFoundException, SQLException
    {
    int count=0;
 
    String q="select * from user_exp where user='"+user+"'";
    System.out.println(q);
  
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs=stmt.executeQuery(q);
    if(rs.next())
    {
    
    count=rs.getInt("exp");
    }
        
   
    
    return count;
    }
     public int get_user_pos_rep(String user) throws ClassNotFoundException, SQLException
    {
    int count=0;
 
    String q="select * from user_rep where user='"+user+"'";
    System.out.println(q);
  
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs=stmt.executeQuery(q);
    if(rs.next())
    {
    
    count=rs.getInt("pos_rep");
    }
        
   
    
    return count;
    }
      public void add_user_rep(String user,int pc,int nc) throws ClassNotFoundException, SQLException
    {
    int st=2;
    int rows =0;
    
    String q1 = "insert into user_rep values('"+user+"','"+pc+"','"+nc+"')";
    String q2 = "delete from user_rep  where user='"+user+"'";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    stmt.executeUpdate(q2);
    stmt.executeUpdate(q1);
    
    
   
    }
     public int get_user_neg_rep(String user) throws ClassNotFoundException, SQLException
    {
    int count=0;
 
    String q="select * from user_rep where user='"+user+"'";
    System.out.println(q);
  
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs=stmt.executeQuery(q);
    if(rs.next())
    {
    
    count=rs.getInt("neg_rep");
    }
        
   
    
    return count;
    }
     public void add_user_exp(String user,int count) throws ClassNotFoundException, SQLException
    {
    int st=2;
    int rows =0;
    
    String q1 = "insert into user_exp values('"+user+"','"+count+"')";
        System.out.println(q1);
    String q2 = "delete from user_exp  where user='"+user+"'";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    stmt.executeUpdate(q2);
    stmt.executeUpdate(q1);
    
    
   
    }
     
     
      public int get_user_trust_count(String user) throws ClassNotFoundException, SQLException
    {
   
 int c=0;
    String q="select * from trust_count where user='"+user+"'";
    System.out.println(q);
  
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs=stmt.executeQuery(q);
    while(rs.next())
    {
    
    c=rs.getInt("tcount");
   
    }
        
   
    
    return c;
    }
     
     public String get_user_last_posted_date(String user) throws ClassNotFoundException, SQLException
    {
    String udate="";

    String q="select * from user_data where mob='"+user+"'";
    System.out.println(q);
  
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs=stmt.executeQuery(q);
    while(rs.next())
    {
    
    udate=rs.getString("udate");
    
    }
        
   
    
    return udate;
    }
    public void add_user_reputation(String user,int count) throws ClassNotFoundException, SQLException
    {
    int st=2;
    int rows =0;
    
    String q1 = "insert into user_reputation values('"+user+"','"+count+"')";
    String q2 = "delete from user_reputation  where user='"+user+"'";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    stmt.executeUpdate(q2);
    stmt.executeUpdate(q1);
    
    
   
    }
   
     public String get_user_interests(String mob) throws ClassNotFoundException, SQLException
    {
    String interests="";
    String q="select * from user_interests where mob='"+mob+"' and status='1'";
        System.out.println(q);
    
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    ResultSet rs=stmt.executeQuery(q);
    while(rs.next())
    {
    
    interests+=rs.getString("interests")+"#";
    }
       
    
    return interests;
    }
//    public static void main(String[] args) {
//        DBQuery db=new DBQuery();
//        try {
//            int i=db.add_user_data("11", "temp", "22","12.442","77.32525");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
public int add_user_data(String mob,String service,String data,String lat,String lon,String area) throws ClassNotFoundException, SQLException
    {
    int st=2;
    int rows =0;
     String pattern = "yyyy-MM-dd";
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

  String tdate = simpleDateFormat.format(new Date());
  System.out.println(tdate);
  String pattern1 = "HH:mm:ss";
  SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);

  String tdate1 = simpleDateFormat1.format(new Date());
  System.out.println(tdate1);
  String date=tdate+" "+tdate1;
    String q = "insert into user_data set mob='"+mob+"',service='"+service+"' ,data='"+data+"',lat='"+lat+"' ,lon='"+lon+"',udate='"+date+"',status='Pending',vote_count='0',agree_count='0',deny_count='0',area='"+area+"'";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    int id=0;
        rows = stmt.executeUpdate(q);
        String q1="select MAX(id) from user_data";
        ResultSet rs=stmt.executeQuery(q1);
        if(rs.next()){
        id=rs.getInt(1);
        }
    
    
    
    return id;
    }

public void update_user_data(String id,String user,String opinion,String lat,String lon) throws ClassNotFoundException, SQLException
    {
    int st=2;
    int rows =0;
   
     String pattern = "yyyy-MM-dd";
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

  String tdate = simpleDateFormat.format(new Date());
  System.out.println(tdate);
  String pattern1 = "HH:mm:ss";
  SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);

  String tdate1 = simpleDateFormat1.format(new Date());
  System.out.println(tdate1);
  String date=tdate+" "+tdate1;
  
    String q = "insert into user_opinion set user='"+user+"',id='"+id+"' ,opinion='"+opinion+"',lat='"+lat+"' ,lon='"+lon+"',vdate='"+date+"'";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
     String m="";
     String qa="select mob from user_data where id='"+id+"'";
        rs=stmt.executeQuery(qa);
            if (rs.next()) {
                m=rs.getString("mob");
            }
        rows = stmt.executeUpdate(q);
        String q1="update user_data set  vote_count = vote_count+1 where id='"+id+"'";
        stmt.executeUpdate(q1);
        if(opinion.equals("Agree"))
        {
        String q2="update user_data set  agree_count = agree_count+1 where id='"+id+"'";
        stmt.executeUpdate(q2);
        
        
        
        String q22="update trust_count set  tcount = tcount+1 where user='"+m+"'";
        stmt.executeUpdate(q22);
        }
        else if(opinion.equals("Deny"))
        {
        String q2="update user_data set  deny_count = deny_count+1 where id='"+id+"'";
        stmt.executeUpdate(q2);
        String q22="update trust_count set  tcount = tcount-1 where user='"+m+"'";
        stmt.executeUpdate(q22);
        }
        
    String q4="select * from user_data where id='"+id+"'";
    int vote_count=0,agree_count=0,deny_count=0;
    ResultSet rs=stmt.executeQuery(q4);
        if (rs.next()) {
            vote_count=rs.getInt("vote_count");
            agree_count=rs.getInt("agree_count");
            deny_count=rs.getInt("deny_count");
        }
        if (vote_count==5) {
            if (agree_count>deny_count) {
                System.out.println(id+" approved");
                String q5="update user_data set  status = 'Approved' where id='"+id+"'";
                stmt.executeUpdate(q5);
                String q6="select user from user_opinion where opinion='Agree' and id='"+id+"'";
                rs=stmt.executeQuery(q6);
                ArrayList al=new ArrayList();
                while (rs.next()) {
                    al.add(rs.getString("user"));
                  
                    
                }
                String qq8="select * from accounts where user='"+m+"'";
                    System.out.println(qq8);
                    rs=stmt.executeQuery(qq8);
                    while (rs.next()) {
                       String acnum=rs.getString("acnum");
                       String ackey=rs.getString("ackey");
                       File f=new File(Logic.info.py+"user.txt");
                        System.out.println(Logic.info.py+"user.txt");
		        try {
					FileWriter fw=new FileWriter(f);
					fw.write(acnum+"#@"+ackey+"#@1");
                                        System.out.println(acnum+"#@"+ackey+"#@2");
					fw.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                     
                      }
                
                 File f28=new File(Logic.info.py+"task.txt");
		        try {
					FileWriter fw2=new FileWriter(f28);
					fw2.write("transfer1");
					fw2.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                String users="";
                for (int i = 0; i < al.size(); i++) {
                    String q2="update user_rewards set  rewards = rewards+1 where user='"+al.get(i).toString()+"'";
                    stmt.executeUpdate(q2);
                    String q22="update user_rep set  pos_rep = pos_rep+1 where user='"+al.get(i).toString()+"'";
                    stmt.executeUpdate(q22);
                    
                    String qq="select * from accounts where user='"+al.get(i).toString()+"'";
                    System.out.println(qq);
                    rs=stmt.executeQuery(qq);
                    while (rs.next()) {
                       String acnum=rs.getString("acnum");
                       String ackey=rs.getString("ackey");
                       File f=new File(Logic.info.py+al.get(i).toString()+"_accounts.txt");
                        System.out.println(Logic.info.py+al.get(i).toString()+"_accounts.txt");
		        try {
					FileWriter fw=new FileWriter(f);
					fw.write(acnum+"#@"+ackey+"#@1");
                                        System.out.println(acnum+"#@"+ackey+"#@1");
					fw.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                     users=users+"#"+al.get(i).toString();
                    }
                    
                    
                }
                File f=new File(Logic.info.py+"users.txt");
		        try {
					FileWriter fw=new FileWriter(f);
					fw.write(users);
					fw.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                File f2=new File(Logic.info.py+"task.txt");
		        try {
					FileWriter fw2=new FileWriter(f2);
					fw2.write("transfer");
					fw2.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                String q7="select user from user_opinion where opinion='Deny' and id='"+id+"'";
                rs=stmt.executeQuery(q7);
                ArrayList al1=new ArrayList();
                while (rs.next()) {
                    al1.add(rs.getString("user"));
                  
                    
                }
                for (int i = 0; i < al1.size(); i++) {
                    String q2="update user_rewards set  rewards = rewards-1 where user='"+al1.get(i).toString()+"'";
                    stmt.executeUpdate(q2);
                    String q22="update user_rep set  neg_rep = neg_rep+1 where user='"+al.get(i).toString()+"'";
                    stmt.executeUpdate(q22);
                }
            }
            else{
                 System.out.println(id+" not  approved");
                 String q5="update user_data set  status = 'Rejected' where id='"+id+"'";
                 stmt.executeUpdate(q5);
                 
                 String q6="select user from user_opinion where opinion='Agree' and id='"+id+"'";
                rs=stmt.executeQuery(q6);
                ArrayList al=new ArrayList();
                while (rs.next()) {
                    al.add(rs.getString("user"));
                  
                    
                }
                for (int i = 0; i < al.size(); i++) {
                    String q2="update user_rewards set  rewards = rewards-1 where user='"+al.get(i).toString()+"'";
                    stmt.executeUpdate(q2);
                }
                String q7="select user from user_opinion where opinion='Deny' and id='"+id+"'";
                rs=stmt.executeQuery(q7);
                ArrayList al1=new ArrayList();
                while (rs.next()) {
                    al1.add(rs.getString("user"));
                  
                    
                }
                for (int i = 0; i < al1.size(); i++) {
                    String q2="update user_rewards set  rewards = rewards+1 where user='"+al1.get(i).toString()+"'";
                    stmt.executeUpdate(q2);
                }
                 
                 
            }
            
            
        }
    
   
    }



public String time_diff(String fdate,String tdate) throws ClassNotFoundException, SQLException {
   
                   
         String diff="";
        Connection con = DBConnection.getConnection();
        Statement stmt = con.createStatement();
        String Data ="SELECT DATEDIFF('"+fdate+"','"+tdate+"')";
        System.out.println(Data);
        System.out.println("DATEDIFF('"+fdate+"','"+tdate+"')");
        rs = stmt.executeQuery(Data);
        if(rs.next())
        {
         diff=rs.getString("DATEDIFF('"+fdate+"','"+tdate+"')");
        }
        System.out.println(diff);
        return diff;
        
    }
     public int user_login_check(String mob,String password) throws ClassNotFoundException, SQLException
    {
    int u=0;
    
    String qry = "select  * from  user_details where mob='"+mob+"' and password='"+password+"'";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    if(rs.next())
    {
   
    u=1;
    }
    return u;
}
     
     public int user_opinion_check(String user,String id) throws ClassNotFoundException, SQLException
    {
    int u=0;
    
    String qry = "select  * from  user_opinion where user='"+user+"' and id='"+id+"'";
        System.out.println(qry);
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    if(rs.next())
    {
   
    u=1;
    }
    return u;
}
      
         public String  get_data(String user,String service,String lat,String lon,String area) throws ClassNotFoundException, SQLException
    {
    int u=0;
    String details="";
    
    String qry = "select  * from  user_data where service='"+service+"' and status ='Approved' and area='"+area+"'";
        System.out.println(qry);
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    while(rs.next())
    {
    int id=rs.getInt("id");
    String data=rs.getString("data");
    String mob=rs.getString("mob");
    String udate=rs.getString("udate");
//    u=get_user_reputation(mob);
//    if(u>=2)
//    {
    details+=id+"#"+data+"#"+udate+"@@";
   // }
    }
    
    String qq8="select * from accounts where user='"+user+"'";
                    System.out.println(qq8);
                    rs=stmt.executeQuery(qq8);
                    if (rs.next()) {
                       String acnum=rs.getString("acnum");
                       String ackey=rs.getString("ackey");
                       File f=new File(Logic.info.py+"fromuser.txt");
                        System.out.println(Logic.info.py+"fromuser.txt");
		        try {
					FileWriter fw=new FileWriter(f);
					fw.write(acnum+"#@"+ackey+"#@1");
                                        System.out.println(acnum+"#@"+ackey+"#@1");
					fw.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                     
                      }
                
                 File f28=new File(Logic.info.py+"task.txt");
		        try {
					FileWriter fw2=new FileWriter(f28);
					fw2.write("transfer2");
					fw2.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
    
    
    
    return details;
}
       public String login_check(String user,String password) throws ClassNotFoundException, SQLException
{
 String utype="";
    String qry = "select  * from  login_details where userid='"+user+"' and password='"+password+"'";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    if(rs.next())
    {
    
    utype=rs.getString("utype");
    }
    return utype;
}
       
       
       public String find_nearest_rsu(String lat,String lon) throws ClassNotFoundException, SQLException
{
    String rsu="";
  //  findDistance fd=new findDistance();
    double min_dis=0.0;
    int c=0;
    String qry = "select  * from  rsu_details";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    while(rs.next())
    {
    String lt=rs.getString("lat");
    String ln=rs.getString("lon");
    String loc=rs.getString("location");
    
    double dis= 0;//fd.distance(Double.parseDouble(lat),Double.parseDouble( lon), Double.parseDouble(lt),Double.parseDouble(ln), 'K');
    System.out.println(loc+"===="+dis); 
    if(c==0)
    {
    min_dis=dis;
    rsu=loc;
    }
    else if(min_dis>dis)
    {
    min_dis=dis;
    rsu=loc;
    
    }
    c++;
    
    
    }
    return rsu;
}
public ArrayList get_user_data(String user) throws ClassNotFoundException, SQLException
{
    ArrayList al=new ArrayList();
    String qry = "select  * from  user_details where mob='"+user+"' ";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    if(rs.next())
    {
    
    al.add(rs.getString("name"));
    al.add(rs.getString("mob"));
    al.add(rs.getString("email"));
    
 
    }
    return al;
}

public int add_new_msg(String user,String msg,String crsu,String lat,String lon) throws ClassNotFoundException, SQLException
{
  
   Date d=new Date();
   String dt=d.toLocaleString();
    String qry1 = "insert into   msg_posted set lat='"+lat+"', lon='"+lon+"', user='"+user+"',msg='"+msg+"', post_time='"+dt+"',status='initiated',witness='"+user+"'";
    int i=0;
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    i=stmt.executeUpdate(qry1);
   String q1="select MAX(mid) from msg_posted";
   rs=stmt.executeQuery(q1);
   if(rs.next())
   {
   i=rs.getInt(1);
   
   }
    
    return i;
}

public ArrayList get_posted_data(String user,String crsu) throws ClassNotFoundException, SQLException
{
    ArrayList al=new ArrayList();
    String qry = "select  * from  msg_posted where  user <> '"+user+"' ";//and status <> 'expired' 
    System.out.println(".."+qry);
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    while(rs.next())
    {
    
    String mid=rs.getString("mid");
   String puser=rs.getString("user");
    String msg=rs.getString("msg");
    String wit=rs.getString("witness");
    String pn=rs.getString("post_time");
    String status=rs.getString("status");
 String data=mid+"##"+puser+"##"+msg+"##"+wit+"##"+pn+"##"+status;
 al.add(data);
    }
    return al;
}
public int add_witness(String user,String mid) throws ClassNotFoundException, SQLException
{
   String wit="";
   int i=0;
    String qry = "select  * from  msg_posted where mid='"+mid+"'";
    System.out.println(".."+qry);
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    if(rs.next())
    {
    
    
     wit=rs.getString("witness");
  
    }
    wit+="##"+user;
    String q="update msg_posted set witness='"+wit+"', status='on process' where mid='"+mid+"'";
    System.out.println(q);
    i=stmt.executeUpdate(q);
    
     String a[]=wit.split("##");
    System.out.println(">>>>>wit="+wit+">>>.len="+a.length);
    if(a.length>2)
    {
     String q1="update msg_posted set status='active' where mid='"+mid+"'";
    System.out.println(q1);
    i=stmt.executeUpdate(q1);
    
    
    
    }
    
    return i;
}
public int update_witness(String user,String mid) throws ClassNotFoundException, SQLException
{
   String wit="";
   int i=0;
    String qry = "select  * from  msg_posted where mid='"+mid+"'";
    System.out.println(".................."+qry);
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    if(rs.next())
    {
    
    
     wit=rs.getString("witness");
  
    }
    int count=0;
    
    String a[]=wit.split("##");
    System.out.println(">>>>>wit="+wit+">>>.len="+a.length);
    if(a.length>2)
    {
     String q="update msg_posted set status='active' where mid='"+mid+"'";
    System.out.println(q);
    i=stmt.executeUpdate(q);
    
    
    
    }
   
    
    
    return i;
}
public int get_count(String loc) throws ClassNotFoundException, SQLException
{
    int c=0;
    String qry = "select  count(*) from  online_user where loc='"+loc+"' ";
    System.out.println(qry);
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    if(rs.next())
    {
    
    c=rs.getInt(1);
 
    }
    return c;
}
public int get_loc_count() throws ClassNotFoundException, SQLException
{
    int c=0;
    String qry = "select  count(*) from  loc_details";
    System.out.println(qry);
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    if(rs.next())
    {
    
    c=rs.getInt(1);
 
    }
    return c;
}


public String[][] get_nearby_user(String loc,int count) throws ClassNotFoundException, SQLException
{
    String a[][]=new String[count][5];
    int c=0;
    String qry = "select  * from  online_user where loc='"+loc+"' ";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    while(rs.next())
    {
    
    
 
    a[c][0]=rs.getString("clat");
    a[c][1]=rs.getString("clon");
    a[c][2]=rs.getString("loc");
    
   c++;
    }
    return a;
}
public String[][] get_loc_details(int count) throws ClassNotFoundException, SQLException
{
    String a[][]=new String[count][3];
    int c=0;
    String qry = "select  * from  loc_details";
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    while(rs.next())
    {
    
    a[c][0]=rs.getString("location");
    a[c][1]=rs.getString("lat");
    a[c][2]=rs.getString("lon");
    
    
   c++;
    }
    return a;
}

public String get_active_msg() throws ClassNotFoundException, SQLException
{
    String d="";
   // ArrayList al=new ArrayList();
    String qry = "select  * from  msg_posted where status='active'";//and status <> 'expired' 
    System.out.println(".."+qry);
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    while(rs.next())
    {
    
    String mid=rs.getString("mid");
   String rsu=rs.getString("loc");
    
 String data=mid+"##"+rsu;
 d+=data+"@@";
// al.add(data);
    }
    return d;
}

public String get_posted_msg(String user,String loc) throws ClassNotFoundException, SQLException
{
    String d="";
    String qry = "select  * from  msg_posted where loc='"+loc+"' and user <> '"+user+"' ";//and status <> 'expired' 
    System.out.println(".."+qry);
    Connection con = DBConnection.getConnection();
    Statement stmt = con.createStatement();
    rs=stmt.executeQuery(qry);
    while(rs.next())
    {
    
    String mid=rs.getString("mid");
   String puser=rs.getString("user");
    String msg=rs.getString("msg");
    String wit=rs.getString("witness");
    String pn=rs.getString("post_time");
    String status=rs.getString("status");
 String data=mid+"##"+msg;
 d+=data+"@@";
    }
    return d;
}
} 

