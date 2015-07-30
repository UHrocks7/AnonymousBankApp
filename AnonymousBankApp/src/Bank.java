
import java.util.*;
import java.sql.*;

public class Bank {

	 	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   private static final String DB_URL = "jdbc:mysql://localhost:3306/teamanon";
	   //  Database credentials
	   private static final String USER = "root";
	   private static final String PASS = "cmpukahi";
	   
	   //main method
	   public static void main(String[] args) {
		   
		   int choice = 0;
		   
		   try{
		   
		   while(choice != 3){
		   //Prints a welcome message then let user choose to log in or register
		   System.out.println("Welcome To CCJ Online Bank!");
		   System.out.println("1.) Login");
		   System.out.println("2.) Register");
		   System.out.println("3.) Exit");
		   System.out.print("type the number of your choice: ");
		   
		   //gets input from user
		   Scanner in = new Scanner(System.in);
		   choice = in.nextInt();
		   //if choice = 1 login if choice = 2 register an account.
		   if(choice == 1){
			  login();
			   
		   }else if(choice == 2){
			   register();
			   main(args);
		   }
		   
		   }//end of while
		   }catch(InputMismatchException e){
			   System.out.println("Invalid Input. Exiting...");
		   }
	   }//end of main
	 
	   //a function that would register new users in the system  
	   private static void register(){
		   
		   String name;
		   String username;
		   String password;
		   String email;
		   String birthdate;
		   String status = "available";
		   
		   int userid = 0;
		   
		   
		   Scanner in2 = new Scanner(System.in);
		   System.out.print("Enter your name: ");
		   name = in2.nextLine();
		   System.out.print("Enter desired username: ");
		   username = in2.nextLine();
		   System.out.print("Enter desired password: ");
		   password = in2.nextLine();
		   
		   //check to see if password requirements is fulfilled
		   
		   if (isValid(password)) 
	        {  
	            //do nothing  
	        } 
	        else 
	        {  
	        	while(!isValid(password)){
	            System.out.println("\n\nInvalid Password.");
	            System.out.println("Password must contain a digit and a letter.");
	            System.out.println("Password must be atleast 8 characters.");
	            System.out.println("no special characters allowed.\n");
	            	          
	            System.out.print("Enter desired password: ");
	 		   	password = in2.nextLine();
	        	}
	        }  
		   
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
		      query = "SELECT iduser FROM user ORDER BY iduser desc LIMIT 1";
		      stmt = conn.createStatement();
	          ResultSet rs;
	 
	          rs = stmt.executeQuery(query);
	          //update the userid to current one/
	          while (rs.next()) {
	        	  userid = rs.getInt(1);
	          }
	          
	          userid++;
	          
		     
		      //make a query to input new user info on database
		      String sql;
		      sql = "INSERT INTO user (iduser, name, username, password, email, birthdate, status) " +
		    		  	"VALUES (?,?,?,?,?,?,?)";
		      // create prepared statement to organize inputs
		      PreparedStatement preparedStmt = conn.prepareStatement(sql);
		      preparedStmt.setInt (1, userid);
		      preparedStmt.setString (2, name);
		      preparedStmt.setString (3, username);
		      preparedStmt.setString (4, password);
		      preparedStmt.setString (5, email);
		      preparedStmt.setString (6, birthdate);
		      preparedStmt.setString(7, status);
		      //execute statement
		      preparedStmt.execute();
		      
		      //add userid to bank statement to link account to it		      
		      sql = "INSERT INTO bank_statement (userid) VALUES (?)";
		      PreparedStatement preparedStmt2 = conn.prepareStatement(sql);
		      preparedStmt2.setInt (1, userid);
		      
		      preparedStmt2.execute();
          
		      //close connection
		      conn.close();
		      System.out.println("Account Successfully registered\n");
		      
		   
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
	   
	   
	   private static void login(){
		   
		   
		   
		   String username;
		   String password;
		   int check = 0;
		   int countattempt = 0;
		   //ask user for username and password
		   Scanner in3 = new Scanner(System.in);
		   System.out.print("Enter your username: ");
		   username = in3.nextLine();
		   System.out.print("Enter your password");
		   password = in3.nextLine();
		   
		   Connection conn = null;
		   Statement stmt = null;
		   try{
		      //Register JDBC driver
			   Class.forName("com.mysql.jdbc.Driver");
		      //Open a connection
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //query check whether user and pass exist, if does returns 1 if not returns 0
		      String query ="SELECT username, password FROM user WHERE username = ? AND password = ?";
		      PreparedStatement st =conn.prepareStatement(query);
		      st.setString(1,username);
		      st.setString(2,password);
		      ResultSet resultSet = st.executeQuery();
		      String rs;
		      //check whether input is valid or not
		      
		    	  if (!resultSet.next() ) {
		    		  System.out.println("Invalid Username/Password. Please try again\n");
		    		  login();
		    		  countattempt++;
		    	  } else {
		    		  System.out.println("Logged in.\n");

		    		//method for bankstatement
		    	  }
		      //close connection
		      conn.close();
		       
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
	   }//end of log in
	   
	   
	   //return true if and only if password:
       //1. have at least eight characters.
       //2. consists of only letters and digits.
	   public static boolean isValid(String password) {  
	       //returns true or false depending if condition is met
	        if (password.length() < 8) {   
	            return false;  
	        } else {      
	            char c;  
	            int count = 1;   
	            for (int i = 0; i < password.length() - 1; i++) {  
	                c = password.charAt(i);  
	                if (!Character.isLetterOrDigit(c)) {          
	                    return false;  
	                }  
	            }  
	        }  
	        return true;  
	    }   
	   
}//end of class


/*sql = "SELECT iduser FROM user WHERE username = '" + username + "'";
rs = stmt.executeQuery(sql);

		while(rs.next()){
		//Retrieve by column name
		user_id = rs.getString("iduser");

		System.out.println("User ID: " + user_id);

	}*/ 