package to_do_interface;

import java.sql.* ;


public class CnxDB {
	 static Connection cnx ; 
	 
	 public static Connection Connect(){
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 cnx= DriverManager.getConnection("jdbc:mysql://localhost/to_do_list?useSSL=false", "root", "");
			 System.out.println("CNX OK !!!");
		 }catch(Exception e){
			 System.out.println("probl�me de cnnection !!!");
			 System.out.println(e.getMessage());
		 }
		 return cnx ;
	 }

	
	 
}
