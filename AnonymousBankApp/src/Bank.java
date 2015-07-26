
import java.util.*;
import java.sql.*;

public class Bank {

	 	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/teamanon";
	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "teamano";
	   
	   //main method
	   public static void main(String[] args) {
		   
		   int choice = 0;
		   
		   
		   //Prints a welcome message then let user choose to log in or register
		   System.out.println("Welcome To CCJ Online Bank!");
		   System.out.println("1.) Login");
		   System.out.println("2.) Register");
		   System.out.print("type the number of your choice: ");
		   
		   //gets input from user
		   Scanner in = new Scanner(System.in);
		   choice = in.nextInt();
		   //if choice = 1 login if choice = 2 register an account.
		   if(choice == 1){
			  login();
			   
		   }else if(choice == 2){
			   register();
		   }
	   }//end of main
	 
	   //a function that would register new users in the system  
	   public static void register(){
		   
		   String name;
		   String username;
		   String password;
		   String email;
		   String birthdate;
		   
		   int userid = 0;
		   
		   
		   Scanner in2 = new Scanner(System.in);
		   System.out.print("Enter your name: ");
		   name = in2.nextLine();
		   System.out.print("Enter desired username: ");
		   username = in2.nextLine();
		   System.out.print("Enter desired password: ");
		   password = in2.nextLine();
		   System.out.print("Enter a valid email address: ");
		   email = in2.nextLine();
		   System.out.print("Enter your birthdate(YYYY-MM-DD): ");
		   birthdate = in2.nextLine();
		   
		   Connection conn = null;
		   Statement stmt = null;
		   try{
		      //Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
		      //Open a connection
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      String query;
		      //query to get last userid to make a new userid for new account
		     
		      //make a query to input new user info on database
		      String sql;
		      sql = "INSERT INTO user (iduser, name, username, password, email, birthdate) " +
		    		  	"VALUES (?,?,?,?,?,?)";
		      // create prepared statement to organize inputs
		      PreparedStatement preparedStmt = conn.prepareStatement(sql);
		      preparedStmt.setInt (1, userid);
		      preparedStmt.setString (2, name);
		      preparedStmt.setString (3, username);
		      preparedStmt.setString (4, password);
		      preparedStmt.setString (5, email);
		      preparedStmt.setString (6, birthdate);
		      //execute statement
		      preparedStmt.execute();
          
		      //close connection
		      conn.close();
		      System.out.println("Account Successfully registered");
		      userid++;
		   
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
			    //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try
		   
	   }//end of register function
	   
	   
	   public static void login(){
		   
		   String username;
		   String password;
		   //ask user for username and password
		   Scanner in3 = new Scanner(System.in);
		   System.out.print("Enter your username: ");
		   username = in3.nextLine();
		   System.out.print("Enter your password");
		   password = in3.nextLine();
		   
		   
		   
	   }
	   
	   
	   
}//end of class

/*sql = "SELECT iduser FROM user WHERE username = '" + username + "'";
rs = stmt.executeQuery(sql);

		while(rs.next()){
		//Retrieve by column name
		user_id = rs.getString("iduser");

		System.out.println("User ID: " + user_id);

	}*/