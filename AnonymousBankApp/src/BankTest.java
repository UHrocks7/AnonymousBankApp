import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.sql.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.experimental.results.PrintableResult;

public class BankTest {

 	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost:3306/teamanon";
	//  Database credentials
	private static final String USER = "root";
	private static final String PASS = "cmpukahi";
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void testTableData() {
		Connection conn = null;
		Statement stmt = null;
	    try {
	    	//Register JDBC driver
		   Class.forName("com.mysql.jdbc.Driver");
	      //Open a connection
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      Statement stmt2 = conn.createStatement();
	      ResultSet rsTable1 = stmt2.executeQuery("SELECT * FROM user");
	      while (rsTable1.next()) { //prints out data from "user" table
	    	  int userid = rsTable1.getInt("iduser");
	    	  String name = rsTable1.getString("name");
	    	  String username = rsTable1.getString("username");
	    	  String password = rsTable1.getString("password");
	    	  String email = rsTable1.getString("email");
	    	  String birthdate = rsTable1.getString("birthdate");
	    	  System.out.println(userid + "    " + name + "    " + username + "    " + password + "    " + email + "    " + birthdate);
	      }
	      System.out.println();
	      ResultSet rsTable2 = stmt2.executeQuery("SELECT * FROM bank_statement");
	      while (rsTable2.next()) { //prints out data from "bank_statement" table
	    	  int userid = rsTable2.getInt("userid");
	    	  String checking = rsTable2.getString("checking");
	    	  String savings = rsTable2.getString("savings");
	    	  System.out.println(userid + "    " + checking + "    " + savings);
	      }
	      
	    }
	    catch(SQLException se){
		      //Handle errors for JDBC
	    	fail("Error: Look Below for Errors");  
	    	se.printStackTrace();
		   }
	    catch(Exception e){
		      //Handle errors for Class.forName
	    	fail("Error: Look Below for Errors");  
	    	e.printStackTrace();
	    }
	    finally {
		    //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		    	  fail("An error has happened.");
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		    	  fail("An error has happened.");
		         se.printStackTrace();
		      }//end finally try
	    }
		
		//fail("Not yet implemented");
	}

	//test bankstatement method
	public void testBankStatement(){

		String username = "chasem";

		Bank statement = new Bank();

		//simulates user input
		String inputData = "1";
		System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));

		//broken for now, retrns SQL syntax error
		statement.bankstatement(username);
	}

}
