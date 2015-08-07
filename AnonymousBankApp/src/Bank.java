
import java.util.*;
import java.sql.*;

public class Bank {

	 	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   private static final String DB_URL = "jdbc:mysql://localhost:3306/teamanon";
	   //  Database credentials
	   private static final String USER = "root";
	   private static final String PASS = "teamano";
	   static int countattempt = 0;
	   
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
		      System.out.println("Invalid Input. Try Again");
		   }catch(Exception e){
		      //Handle errors for Class.forName
		     // e.printStackTrace();
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
			    	  System.out.println("Invalid Input. Try Again");
			      }//end finally try
			   }//end try
		   
	   }//end of register function
	   
	   
	   private static void login(){
		   
		   
		   
		   String username;
		   String password;
		   String status = "";
		  //int check = 0;
		   
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
		      
		      String q1 ="SELECT username FROM user WHERE username = ?";
		      PreparedStatement sttt =conn.prepareStatement(q1);
		      sttt.setString(1,username);
		      ResultSet resultSett = sttt.executeQuery();
		      
		      //checks if username exist or not, then continue to check username and password
		   if (!resultSett.next() ) {
			   System.out.print("Username does not exist!\n\n");
		   }else{ 		 
		      
		      //query check whether user and password match, if does returns 1 if not returns 0
		      String query ="SELECT username, password FROM user WHERE username = ? AND password = ?";
		      PreparedStatement st =conn.prepareStatement(query);
		      st.setString(1,username);
		      st.setString(2,password);
		      ResultSet resultSet = st.executeQuery();
		      String rs;
		      //check whether input is valid or not
		      
		    	  if (!resultSet.next() ) {
		    		 
		    		  //disable account if count attempt is equal to 3
		    		  if(countattempt < 2){
		    			  System.out.println("Invalid Username/Password. Please try again\n");
		    			  countattempt++;
		    			  System.out.println("attempt: " + countattempt);
		    			  //redirects to login method again if attempt doesnt exceeds 3
		    			  login();
		    			  		    		      	    			  
		    		  }else{
		    			  String statchange = "disable";
		    			  // update the status to disable
		    			  String q ="UPDATE user SET status = ?  WHERE username = ?";
		    		      PreparedStatement st2 =conn.prepareStatement(q);
		    		      st2.setString(1,statchange);
		    		      st2.setString(2,username);
		    		      st2.execute();
		    		      	 
		    		      System.out.println("\nAccount is disabled\n");
		    		      
		    			  //redirects to login method again if attempt doesnt exceeds 3
		    			  
		    		  }
		    	  } else {
		    		  String qq = "SELECT status FROM user WHERE username = '" + username + "'";
				      Statement stmt2 = conn.createStatement();
			          ResultSet rss = stmt2.executeQuery(qq);
			          //get the current status of account/
			          while (rss.next()) {
			        	  status = rss.toString();
			          }
		    		  //checks whether user account is available or disabled
		    		  //checks the attempts of user
		    		  if(status == "available"){
		    			  System.out.println("Logged in.\n");
		    			  //method for bankstatement
		    			  bankstatement(username);
		    	  	  }else{
		    	  		  System.out.println("Error : Account is disabled.\n Please call Customer Service\n\n.");
		    	  	  }
		    	 
		    	  }	  
		   }
		      //close connection
		      conn.close();
		       
		   }catch(SQLException se){
		      //Handle errors for JDBC
		   
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      //e.printStackTrace();
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
			    	  System.out.println("Invalid Input. Try Again");
			      }//end finally try
			   }//end try
	   }//end of log in

		public static void bankstatement(String username){

			Statement stmt = null;
			Connection conn = null;
			Scanner in = new Scanner(System.in);
			String query;
			String user_id = "";
			int selection;
			String account = "";
			String balance = "";

			try{

				//Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				//Open a connection
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				stmt = conn.createStatement();

				System.out.println("Please choose an account to check balance");
				System.out.println("1. Checking");
				System.out.println("2. Savings");


				//stores user selction
				selection = in.nextInt();

				//chooses account type based on user selection
				if(selection == 1){

					account = "checking";

				}

				else if(selection == 2){

					account = "savings";

				}

				//query to retrieve user ID based on who logged in
				query = "SELECT iduser FROM user WHERE username = '" + username + "'";
				ResultSet rs = stmt.executeQuery(query);

				while(rs.next()) {
					//Retrieve by column name
					user_id = rs.getString("iduser");


			}

				//query to retrieve user balance based on who logged in
				query = "SELECT " + account + " FROM bank_statement WHERE userid = " + user_id + "";

				rs = stmt.executeQuery(query);

				while(rs.next()) {
					//Retrieve by column name
					balance = rs.getString(account);

					System.out.println("Account Balance: " + balance + "\n");

				}

	}catch(SQLException se){
		//Handle errors for JDBC
		//se.printStackTrace();
	}catch(Exception e){
		//Handle errors for Class.forName
		//e.printStackTrace();
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
			 System.out.println("Invalid Input. Try Again");
			 }//end finally try
	}//end try
		}
	   
	   
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


