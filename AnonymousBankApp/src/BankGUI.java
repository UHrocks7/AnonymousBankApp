import javax.swing.*;
import javax.swing.JDialog;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class BankGUI extends JFrame implements ItemListener {

	 	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   private static final String DB_URL = "jdbc:mysql://localhost:3306/teamanon";
	   //  Database credentials
	   private static final String USER = "root";
	   private static final String PASS = "cmpukahi";
	   
	   JTextField nameRegField, usernameRegField, emailRegField, birthdateRegField;
	   JTextField usernameLoginField;
	   JPasswordField passwordRegField, passwordLoginField;
	   JLabel nameRegLabel, usernameRegLabel, passwordRegLabel, emailLabel, birthdateLabel;
	   JLabel usernameLoginLabel, passwordLoginLabel;
	   JLabel welcomeMessage = new JLabel("Welcome To CCJ Online Bank!");
	   JLabel loginMessage, registerMessage;
	   JPanel welcomePanel;
	   JFrame popupMessage;
	   JPanel loginPanel, registerPanel;
	   JPanel bankApp;
	   JButton exitLoginBtn, exitRegBtn, submitLoginBtn, submitRegBtn;
	   
	   public void addBankAppComponent(Container appPane) {
		   //JFrame bankApp;
		   //bankApp = new JFrame("CCJ Online Bank");
		   welcomePanel = new JPanel(new FlowLayout());
		   welcomeMessage = new JLabel("Welcome To CCJ Online Bank!");
		   welcomePanel.add("Top", welcomeMessage);
		   exitLoginBtn = new JButton("Exit");
		   exitRegBtn = new JButton("Exit");
		   JPanel menuPane = new JPanel();
		   String loginStr = new String("Login Page");
		   String registerStr = new String("Registration Page");
		   //String exitStr = new String("Exit");
		   String menuPaneItems[] = { loginStr, registerStr };
		   JComboBox cbMenu = new JComboBox(menuPaneItems);
		   cbMenu.setEditable(false);
		   cbMenu.addItemListener(this);
		   menuPane.add(cbMenu);
		   
		   loginPanel = new JPanel();
		   loginMessage = new JLabel("Login Page");
		   usernameLoginLabel = new JLabel("Username: ");
		   passwordLoginLabel = new JLabel("Password: ");
		   usernameLoginField = new JTextField("", 20);
		   passwordLoginField = new JPasswordField("", 20);
		   submitLoginBtn = new JButton("Login to Account");
		   loginPanel.add("Top", welcomeMessage);
		   loginPanel.add(usernameLoginLabel);
		   loginPanel.add(usernameLoginField);
		   loginPanel.add(passwordLoginLabel);
		   loginPanel.add(passwordLoginField);
		   loginPanel.add("Bottom", submitLoginBtn);
		   loginPanel.add(exitLoginBtn);
		   
		   registerPanel = new JPanel();
		   registerMessage = new JLabel("Registration Page");
		   nameRegLabel = new JLabel("Name: ");
		   usernameRegLabel = new JLabel("Username: ");
		   passwordRegLabel = new JLabel("Password: ");
		   emailLabel = new JLabel("Email: ");
		   birthdateLabel = new JLabel("Birthdate: ");
		   
		   nameRegField = new JTextField("", 30);
		   usernameRegField = new JTextField("", 20);
		   passwordRegField = new JPasswordField("", 20);
		   emailRegField = new JTextField("", 20);
		   birthdateRegField = new JTextField("", 10);
		   submitRegBtn = new JButton("Submit Registration");
		   
		   registerPanel.add("Top", welcomeMessage);
		   registerPanel.add("Top", nameRegLabel);
		   registerPanel.add("Bottom", nameRegField);
		   
		   registerPanel.add("Left", usernameRegLabel);
		   registerPanel.add("Right", usernameRegField);
		   
		   registerPanel.add("Left", passwordRegLabel);
		   registerPanel.add("Right", passwordRegField);
		   
		   registerPanel.add("Left", emailLabel);
		   registerPanel.add("Right", emailRegField);
		   
		   registerPanel.add("Left", birthdateLabel);
		   registerPanel.add("Right", birthdateRegField);
		   registerPanel.add("Bottom", submitRegBtn);
		   registerPanel.add(exitRegBtn);
		   
		   bankApp = new JPanel(new CardLayout());
		   bankApp.add(loginPanel, loginStr);
		   bankApp.add(registerPanel, registerStr);
		   
		   appPane.add(welcomePanel, BorderLayout.PAGE_START);
		   appPane.add(menuPane, BorderLayout.PAGE_START);
		   appPane.add(bankApp, BorderLayout.CENTER);

		   submitLoginBtn.addActionListener(new login());
		   submitRegBtn.addActionListener(new register());
		   exitLoginBtn.addActionListener(new exitProgram());
		   exitRegBtn.addActionListener(new exitProgram());
		   //welcomeMessage.setFont(welcomeMessage.getFont().deriveFont(Font.BOLD, 14f));
		   //loginMessage.setFont(loginMessage.getFont().deriveFont(Font.BOLD, 14f));
		   //registerMessage.setFont(registerMessage.getFont().deriveFont(Font.BOLD, 14f));

		   /*welcomePanel.add(welcomeMessage);
		   welcomePanel.add(loginBtn);
		   welcomePanel.add(registerBtn);
		   welcomePanel.add(exitBtn);*/
		   
		   /*addWindowListener(new WindowAdapter(){
			  @Override
			  public void windowClosing(WindowEvent e) { System.exit(0); }
		   });
		   //loginBtn.addActionListener(new);
		   //loginBtn.addActionListener(new login());
		   //registerBtn.addActionListener(new register());*/
		   
	   }
	   
	   public void itemStateChanged(ItemEvent appEvent) {
		   CardLayout clApp = (CardLayout)(bankApp.getLayout());
		   clApp.show(bankApp, (String)appEvent.getItem());
	   }
	   
	   private static void prepareBankApp() {
		   JFrame window = new JFrame("CCJ Online Bank");
		   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   BankGUI openBank = new BankGUI();
		   openBank.addBankAppComponent(window.getContentPane());
		   window.pack();
		   window.setVisible(true);
	   }
	   
	   class exitProgram implements ActionListener {
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   System.exit(0);
		   }
	   }
	   
	   class register implements ActionListener {
		@Override
		   public void actionPerformed(ActionEvent e) {
			   String name;
			   String username;
			   char[] password;
			   String passwordStr;
			   String email;
			   String birthdate;
			   String status = "available";
			   
			   int userid = 0;
			   
			   name = nameRegField.getText();
			   username = usernameRegField.getText();
			   password = passwordRegField.getPassword();
			   passwordStr = new String(password);
			   email = emailRegField.getText();
			   birthdate = birthdateRegField.getText();
			   
			   if (isValid(passwordStr)) {
				   
			   }
			   else {
				   while(!isValid(passwordStr)){
					   JOptionPane.showMessageDialog(popupMessage, "Invalid Password.\n"
					   		+ "Password must contain a digit and a letter.\n"
					   		+ "Password must be at least 8 characters.\n"
					   		+ "No special characters allowed.\n");
			            	          
			            //System.out.print("Enter desired password: ");
			 		   	//passwordStr = in2.nextLine();
					   passwordStr = "";
					   
			        	}
			   }
			   
			   
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
			      preparedStmt.setString (4, passwordStr);
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
			      JOptionPane.showMessageDialog(popupMessage, "Account Successfully registered\n");
			      //System.out.println("Account Successfully registered\n");
			      
			   
			   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e1){
			      //Handle errors for Class.forName
			      e1.printStackTrace();
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
		   }
	   }
	   
	   class login implements ActionListener {
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   String username;
			   char[] password;
			   String passwordStr;
			   int check = 0;
			   int countattempt = 0;

			   username = usernameLoginField.getText();
			   password = passwordLoginField.getPassword();
			   passwordStr = new String(password);
			   
			   login loginAgain = new login();
			   
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
			      st.setString(2,passwordStr);
			      ResultSet resultSet = st.executeQuery();
			      String rs;
			      //check whether input is valid or not
			      
			    	  if (!resultSet.next() ) {
			    		  //System.out.println("Invalid Username/Password. Please try again\n");
			    		  JOptionPane.showMessageDialog(popupMessage, "Invalid Username/Password. Please try again\n");
			    		 // loginAgain.actionPerformed(e); 
			    		  countattempt++;
			    	  } else {
			    		  //System.out.println("Logged in.\n");
			    		  JOptionPane.showMessageDialog(popupMessage, "Logged in.\n");

			    		//method for bankstatement
			    	  }
			      //close connection
			      conn.close();
			       
			   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e1){
			      //Handle errors for Class.forName
			      e1.printStackTrace();
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
		   }
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
	 
	   //a function that would register new users in the system  
	   /*private static void register(){
		   
		  
		   
		   
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
	            System.out.println("Password must be at least 8 characters.");
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

*/
	   
	   public static void main(String[] args) {
		   javax.swing.SwingUtilities.invokeLater(new Runnable(){
			   public void run() {
				   prepareBankApp();
			   }
		   });
	   }
	   
}//end of class


/*sql = "SELECT iduser FROM user WHERE username = '" + username + "'";
rs = stmt.executeQuery(sql);

		while(rs.next()){
		//Retrieve by column name
		user_id = rs.getString("iduser");

		System.out.println("User ID: " + user_id);

	}*/ 