package com.TaskBuddy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Siddhardha
 * 
 * ConnectionManager class for creating persistent database connection object 'conn'.
 * Singleton design pattern is used to create only one instance of ConnectionManager class.
 * 
 */
public class ConnectionManager {
	
	private static ConnectionManager instance = null;
	
	private static final String SERVER_NAME = "localhost";
	private static final String DB_NAME = "taskbuddy";
	private static final String USERNAME = "tb_db_user";
	private static final String PASSWORD = "tbdbuserpwd";
	private static final String CONN_STRING = "jdbc:mysql://" + SERVER_NAME + "/" + DB_NAME;
	
	private Connection conn = null;
	
	//Constructor is made private to prevent instantiation from outside the class
	private ConnectionManager() {
	}
	
	/**
	 * 
	 * ConnectionManager object is created when this method is called for the first time. 
	 * It is assigned to the private static field 'instance'.
	 * From the next time when the function is called, the already created instance of 
	 * ConnectionManager class is returned.
	 * 
	 * @return ConnectionManager instance
	 * 
	 */
	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}
	
	/**
	 * 
	 * Opens connection to MySQL database.
	 * Error message is printed if open connection fails.
	 * 
	 */
	public void openConnection()
	{
		System.out.println("Opening connection...");
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				System.out.println("Connection opened.");
			} catch (SQLException | ClassNotFoundException e) {
				System.err.println("Error message: " + e.getMessage());
				System.out.println("Connection open failed.");
			}
			return;
		}
		System.out.println("Connection is already opened.");
	}
	
	/**
	 * 
	 * Returns connection to MySQL database.
	 * If connection is null, then a new connection is opened and returned.
	 * Error message is printed if open connection fails.
	 * 
	 * @return Connection object
	 * 
	 */
	public Connection getConnection()
	{
		if (conn == null) {
			openConnection();
		}
		return conn;
	}
	
	/**
	 * 
	 * Closes connection to MySQL database.
	 * Error message is printed if close connection fails.
	 * 
	 */
	public void closeConnection()
	{
		System.out.println("Closing connection...");
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				System.out.println("Connection closed.");
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.out.println("Connection close failed.");
			}
			return;
		}
		System.out.println("Connection is already closed.");
	}
}
