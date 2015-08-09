import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.Action;
import javax.swing.JPasswordField;


import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.Button;
import javax.swing.JTable;
import java.awt.Label;
import java.awt.Panel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;





public class BankGUI2 {

	

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/teamanon";
	//  Database credentials
	private static final String USER = "root";
	private static final String PASS = "teamano";
	
	
	private JFrame frame;
	static JFrame popupMessage;
	private JTextField usernameLoginField;
	private JPasswordField passwordLoginField;
	private JTextField nameRegField;
	private JTextField usernameRegField;
	private JPasswordField passwordRegField;
	private JTextField emailRegField;
	private JTextField birthdateRegField;
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel = new JPanel();
	 JTextArea checkingsField = new JTextArea();
	 JTextArea savingsField = new JTextArea();
	int countattempt = 0;
	String status = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankGUI2 window = new BankGUI2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BankGUI2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 553, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		mainPanel.setBounds(0, 0, 535, 433);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(cardLayout);
		
		//creates the login panel
		JPanel loginPanel = new JPanel();
		mainPanel.add(loginPanel, "loginPanel");
		loginPanel.setLayout(null);
		
		JLabel lblWelcomeToCcj = new JLabel("Welcome to CCJ BANK App");
		lblWelcomeToCcj.setBounds(130, 35, 275, 27);
		loginPanel.add(lblWelcomeToCcj);
		lblWelcomeToCcj.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblWelcomeToCcj.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel UsernameLabel = new JLabel("Username :");
		UsernameLabel.setBounds(106, 172, 67, 16);
		loginPanel.add(UsernameLabel);
		
		usernameLoginField = new JTextField();
		usernameLoginField.setBounds(190, 169, 205, 22);
		loginPanel.add(usernameLoginField);
		usernameLoginField.setColumns(10);
		
		JLabel PasswordLabel = new JLabel("Password :");
		PasswordLabel.setBounds(106, 201, 64, 16);
		loginPanel.add(PasswordLabel);
		
		passwordLoginField = new JPasswordField();
		passwordLoginField.setBounds(190, 198, 205, 22);
		loginPanel.add(passwordLoginField);
		
		JButton submitLoginBtn = new JButton("Log-in");
		submitLoginBtn.setBounds(187, 258, 67, 25);
		loginPanel.add(submitLoginBtn);
		
		JButton registerLogin = new JButton("Register");
		registerLogin.setBounds(266, 258, 79, 25);
		loginPanel.add(registerLogin);
		
		
		//creates the register panel
		JPanel registerPanel = new JPanel();
		mainPanel.add(registerPanel, "registerPanel");
		GridBagLayout gbl_registerPanel = new GridBagLayout();
		gbl_registerPanel.columnWidths = new int[]{71, 78, 222, 0, 0, 0};
		gbl_registerPanel.rowHeights = new int[]{73, 22, 22, 25, 22, 22, 0, 0, 0, 0, 0};
		gbl_registerPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_registerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		registerPanel.setLayout(gbl_registerPanel);
		
		JLabel lblRegistration = new JLabel("REGISTRATION");
		lblRegistration.setFont(new Font("Times New Roman", Font.BOLD, 20));
		GridBagConstraints gbc_lblRegistration = new GridBagConstraints();
		gbc_lblRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistration.gridx = 2;
		gbc_lblRegistration.gridy = 0;
		registerPanel.add(lblRegistration, gbc_lblRegistration);
		
		JLabel nameRegLabel = new JLabel("Name :");
		GridBagConstraints gbc_nameRegLabel = new GridBagConstraints();
		gbc_nameRegLabel.anchor = GridBagConstraints.WEST;
		gbc_nameRegLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameRegLabel.gridx = 1;
		gbc_nameRegLabel.gridy = 1;
		registerPanel.add(nameRegLabel, gbc_nameRegLabel);
		
		nameRegField = new JTextField();
		GridBagConstraints gbc_nameRegField = new GridBagConstraints();
		gbc_nameRegField.anchor = GridBagConstraints.NORTH;
		gbc_nameRegField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameRegField.insets = new Insets(0, 0, 5, 5);
		gbc_nameRegField.gridx = 2;
		gbc_nameRegField.gridy = 1;
		registerPanel.add(nameRegField, gbc_nameRegField);
		nameRegField.setColumns(10);
		
		JLabel usernameRegLabel = new JLabel("Username :");
		GridBagConstraints gbc_usernameRegLabel = new GridBagConstraints();
		gbc_usernameRegLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameRegLabel.insets = new Insets(0, 0, 5, 5);
		gbc_usernameRegLabel.gridx = 1;
		gbc_usernameRegLabel.gridy = 2;
		registerPanel.add(usernameRegLabel, gbc_usernameRegLabel);
		
		usernameRegField = new JTextField();
		GridBagConstraints gbc_usernameRegField = new GridBagConstraints();
		gbc_usernameRegField.anchor = GridBagConstraints.NORTH;
		gbc_usernameRegField.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameRegField.insets = new Insets(0, 0, 5, 5);
		gbc_usernameRegField.gridx = 2;
		gbc_usernameRegField.gridy = 2;
		registerPanel.add(usernameRegField, gbc_usernameRegField);
		usernameRegField.setColumns(10);
		
		JLabel passwordRegLabel = new JLabel("Password :");
		GridBagConstraints gbc_passwordRegLabel = new GridBagConstraints();
		gbc_passwordRegLabel.anchor = GridBagConstraints.NORTH;
		gbc_passwordRegLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordRegLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passwordRegLabel.gridx = 1;
		gbc_passwordRegLabel.gridy = 3;
		registerPanel.add(passwordRegLabel, gbc_passwordRegLabel);
		
		passwordRegField = new JPasswordField();
		GridBagConstraints gbc_passwordRegField = new GridBagConstraints();
		gbc_passwordRegField.anchor = GridBagConstraints.SOUTH;
		gbc_passwordRegField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordRegField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordRegField.gridx = 2;
		gbc_passwordRegField.gridy = 3;
		registerPanel.add(passwordRegField, gbc_passwordRegField);
		
		JLabel emailLabel = new JLabel("Email :");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 4;
		registerPanel.add(emailLabel, gbc_emailLabel);
		
		emailRegField = new JTextField();
		GridBagConstraints gbc_emailRegField = new GridBagConstraints();
		gbc_emailRegField.anchor = GridBagConstraints.NORTH;
		gbc_emailRegField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailRegField.insets = new Insets(0, 0, 5, 5);
		gbc_emailRegField.gridx = 2;
		gbc_emailRegField.gridy = 4;
		registerPanel.add(emailRegField, gbc_emailRegField);
		emailRegField.setColumns(10);
		
		JLabel birthdateLabel = new JLabel("Birthdate :");
		GridBagConstraints gbc_birthdateLabel = new GridBagConstraints();
		gbc_birthdateLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_birthdateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_birthdateLabel.gridx = 1;
		gbc_birthdateLabel.gridy = 5;
		registerPanel.add(birthdateLabel, gbc_birthdateLabel);
		
		birthdateRegField = new JTextField();
		GridBagConstraints gbc_birthdateRegField = new GridBagConstraints();
		gbc_birthdateRegField.insets = new Insets(0, 0, 5, 5);
		gbc_birthdateRegField.anchor = GridBagConstraints.NORTH;
		gbc_birthdateRegField.fill = GridBagConstraints.HORIZONTAL;
		gbc_birthdateRegField.gridx = 2;
		gbc_birthdateRegField.gridy = 5;
		registerPanel.add(birthdateRegField, gbc_birthdateRegField);
		birthdateRegField.setColumns(10);
		
		JLabel lblyyyymmdd = new JLabel("(YYYY/MM/DD)");
		GridBagConstraints gbc_lblyyyymmdd = new GridBagConstraints();
		gbc_lblyyyymmdd.insets = new Insets(0, 0, 5, 5);
		gbc_lblyyyymmdd.gridx = 1;
		gbc_lblyyyymmdd.gridy = 6;
		registerPanel.add(lblyyyymmdd, gbc_lblyyyymmdd);
		
		JButton submitRegBtn = new JButton("Register");
		GridBagConstraints gbc_submitRegBtn = new GridBagConstraints();
		gbc_submitRegBtn.insets = new Insets(0, 0, 5, 5);
		gbc_submitRegBtn.gridx = 3;
		gbc_submitRegBtn.gridy = 8;
		registerPanel.add(submitRegBtn, gbc_submitRegBtn);
		
		JButton backLoginPage = new JButton("Back");
		GridBagConstraints gbc_backLoginPage = new GridBagConstraints();
		gbc_backLoginPage.insets = new Insets(0, 0, 0, 5);
		gbc_backLoginPage.gridx = 3;
		gbc_backLoginPage.gridy = 9;
		registerPanel.add(backLoginPage, gbc_backLoginPage);
		

		//shows the first panel that we want to show which is loginpanel
		cardLayout.show(mainPanel, "loginPanel");
		
		
		JPanel statementPanel = new JPanel();
		mainPanel.add(statementPanel, "statementPanel");
		statementPanel.setLayout(null);
		
		Label title = new Label("Bank Statement");
		title.setFont(new Font("Dialog", Font.BOLD, 18));
		title.setBounds(36, 65, 142, 38);
		statementPanel.add(title);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setBounds(396, 338, 97, 25);
		statementPanel.add(btnLogOut);
		
		JLabel lblSavings = new JLabel("Savings :");
		lblSavings.setBounds(36, 128, 78, 16);
		statementPanel.add(lblSavings);
		
		JLabel lblCheckings = new JLabel("Checkings :");
		lblCheckings.setBounds(36, 163, 78, 16);
		statementPanel.add(lblCheckings);
		
		
		checkingsField.setBounds(112, 160, 106, 22);
		statementPanel.add(checkingsField);
		
		
		savingsField.setBounds(112, 125, 106, 22);
		statementPanel.add(savingsField);
		
				
		//Adds actions to buttons
		submitLoginBtn.addActionListener(new login());
		submitRegBtn.addActionListener(new register());
		registerLogin.addActionListener(new regPage());
		backLoginPage.addActionListener(new logPage());
		btnLogOut.addActionListener(new logPage());
	}
	
	//redirects to login page
	class logPage implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(mainPanel, "loginPanel");
			nameRegField.setText("");
			usernameRegField.setText("");
			passwordRegField.setText("");
			emailRegField.setText("");
			birthdateRegField.setText("");
		
		}
	}
	
	
	
	//redirects user to reg page
	class regPage implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(mainPanel, "registerPanel");
			usernameLoginField.setText("");
			passwordLoginField.setText("");
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
			if(username.equals("")){
				
				JOptionPane.showMessageDialog(popupMessage, "Username Field Empty. Please fill it up\n ");
				
			}
			else{
				
			if(UserExist(username)){
				//shows a warning that username exist already
				JOptionPane.showMessageDialog(popupMessage, "Username Exist.\n "
						+ "Please choose another username.\n");
			}

			if (isValid(passwordStr)) {
				//do nothing

			}
			else {
				//shows a warning that password need to change
				JOptionPane.showMessageDialog(popupMessage, "Invalid Password.\n"
							+ "Password must contain a digit and a letter.\n"
							+ "Password must be at least 8 characters.\n"
							+ "No special characters allowed.\n");

			}
			
			// will continue to register if password is valid and username doesnt exist
			//if not user should change password or username again and submit changes.
			if(isValid(passwordStr) && !UserExist(username)) {


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
				cardLayout.show(mainPanel, "loginPanel");
				//clears text values after registration
				nameRegField.setText("");
				usernameRegField.setText("");
				passwordRegField.setText("");
				emailRegField.setText("");
				birthdateRegField.setText("");
			
			
			}catch(SQLException se){
				//Handle errors for JDBC
				JOptionPane.showMessageDialog(popupMessage, "Incorrect Date Format.\n");
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
		}//closes if statement
		}// close another if statement
			}
	}
	
	class login implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String username;
			char[] password;
			String passwordStr;
				

			username = usernameLoginField.getText();
			password = passwordLoginField.getPassword();
			passwordStr = new String(password);

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
				   JOptionPane.showMessageDialog(popupMessage, "Username does not exist\n");
				  }else{ 		 
			      
			      //query check whether user and password match, if does returns 1 if not returns 0
			      String query ="SELECT username, password FROM user WHERE username = ? AND password = ?";
			      PreparedStatement st =conn.prepareStatement(query);
			      st.setString(1,username);
			      st.setString(2,passwordStr);
			      ResultSet resultSet = st.executeQuery();
			      
			      //check whether input is valid or not
			       	  if (!resultSet.next() ) {
			    		 
			    		  //disable account if count attempt is equal to 3
			    		  if(countattempt < 2){
			    			  JOptionPane.showMessageDialog(popupMessage, "Invalid Username/Password. Please try again\n");
			    			  countattempt++;
			    			  //redirects to login method again if attempt doesnt exceeds 3
			    			 			    			  		    		      	    			  
			    		  }else{
			    			  String statchange = "disable";
			    			  // update the status to disable
			    			  String q ="UPDATE user SET status = ?  WHERE username = ?";
			    		      PreparedStatement st2 =conn.prepareStatement(q);
			    		      st2.setString(1,statchange);
			    		      st2.setString(2,username);
			    		      st2.execute();
			    		      	 
			    		      JOptionPane.showMessageDialog(popupMessage, "Account is disabled.\n Please call Customer Service.\n");
			    		    			    		      
			    			  //redirects to login method again if attempt doesnt exceeds 3
			    			  
			    		  }
			    	  } else {
			    		  String qq = "SELECT status FROM user WHERE username = '" + username + "'";
					      Statement stmt2 = conn.createStatement();
				          ResultSet rss = stmt2.executeQuery(qq);
				          //get the current status of account/
				          while (rss.next()) {
				        	  status = rss.getString("status");
				        	  
				          }
				         
			    		  //checks whether user account is available or disabled
			    		  //checks the attempts of user
			    		  if(status.equals("available")){
			    			  
			    			 //logged in message pops
			    			  JOptionPane.showMessageDialog(popupMessage, "Logged in.\n");
			    			 // closes connection from login 
			    			  //go to statement panel
			    			  //do bankstatement method
			    			  conn.close();
			    			  cardLayout.show(mainPanel, "statementPanel");
			    			  usernameLoginField.setText("");
			    			  passwordLoginField.setText("");
			    			  bankstatement(username);
			    			  
			    			  
			    	  	  }else{
			    	  		JOptionPane.showMessageDialog(popupMessage, "Account is disabled.\n"
			    	  				+ "Please call Customer Service.\n");
			    	  		 conn.close();
			    	  	  }
			    	 
			    	  }	  
			   }
				

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
	
	//method to check statements of the current user
	public  void bankstatement(String username){
		
		
		Statement stmt = null;
		Connection conn = null;
		
		String query;
		String user_id = "";
		Integer savings = 0;
		Integer checkings = 0;
		String savingss = "";
		String checkingss = "";


		try{
			
			//Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//Open a connection
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();


			
			//query to retrieve user ID based on who logged in
			query = "SELECT iduser FROM user WHERE username = '" + username + "'";
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()) {
				//Retrieve by column name
				user_id = rs.getString("iduser");


		}

			//query to retrieve user balance based on who logged in
			query = "SELECT checking  FROM bank_statement WHERE userid = " + user_id + "";
			rs = stmt.executeQuery(query);

			while(rs.next()) {
				//Retrieve by column name
				checkings = rs.getInt("checking");
				//savings = rs.getInt("savings");
			}
			checkingss = checkings.toString();
			//savingss = savings.toString();
			checkingsField.setText(checkingss);
			//savingsField.setText(savingss);
			
			//query to retrieve user balance based on who logged in
			query = "SELECT savings  FROM bank_statement WHERE userid = " + user_id + "";
			rs = stmt.executeQuery(query);

			while(rs.next()) {
				//Retrieve by column name
				savings = rs.getInt("savings");
			}
			
			savingss = savings.toString();
			
			savingsField.setText(savingss);
			
		
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
		
		 }//end finally try
}//end try
	}

	/**
	 * Checks password for registration if password meets certain criteria, as listed below
	 * @param password
	 * @return true if and only if password has at least eight characters and consists of only letters and digits
	 */
	public static boolean isValid(String password) {
		//returns true or false depending if condition is met
		if (password.length() < 8) {
			return false;
		} else {
			char c;
		
			for (int i = 0; i < password.length() - 1; i++) {
				c = password.charAt(i);
				if (!Character.isLetterOrDigit(c)) {
					return false;
				}
			}
		}
		return true;
	}
	// checks whether username exist or not for registration
	public static boolean UserExist(String username) {
		
		Connection conn = null;
		Statement stmt = null;
		try{
			
		Class.forName("com.mysql.jdbc.Driver");
			//Open a connection
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		String q1 ="SELECT username FROM user WHERE username = ?";
	      PreparedStatement sttt =conn.prepareStatement(q1);
	      sttt.setString(1,username);
	      ResultSet resultSett = sttt.executeQuery();
	      
	      if (!resultSett.next() ) {
			   return false;
		   }else{
			   return true;
		   }
	      
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
		return false;
	}
}