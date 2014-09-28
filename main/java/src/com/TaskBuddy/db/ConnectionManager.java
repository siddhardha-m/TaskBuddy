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
	 * Throws exception if open connection fails.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void openConnection() throws SQLException
	{
		System.out.println("Opening connection...");
		if (conn == null) {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("Connection opened.");
			return;
		}
		System.out.println("Connection is already opened.");
	}
	
	/**
	 * 
	 * Returns connection to MySQL database.
	 * If connection is null, then a new connection is opened and returned.
	 * Throws exception if open connection fails.
	 * 
	 * @return Connection object
	 * @throws SQLException
	 * 
	 */
	public Connection getConnection() throws SQLException
	{
		if (conn == null) {
			openConnection();
		}
		return conn;
	}
	
	/**
	 * 
	 * Closes connection to MySQL database.
	 * Throws exception if close connection fails.
	 * 
	 * @throws SQLException
	 * 
	 */
	public void closeConnection() throws SQLException
	{
		System.out.println("Closing connection...");
		if (conn != null) {
			conn.close();
			conn = null;
			System.out.println("Connection closed.");
			return;
		}
		System.out.println("Connection is already closed.");
	}
}
