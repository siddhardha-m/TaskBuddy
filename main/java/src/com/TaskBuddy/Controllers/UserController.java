package com.TaskBuddy.Controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.TaskBuddy.Models.User;
import com.TaskBuddy.db.ConnectionManager;


/**
 * @author Siddhardha
 *
 * Controller class for Users table
 *
 */
public class UserController {

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	private UserController() {
	}
	
	/**
	 * 
	 * Method to return all Users
	 * 
	 * @return ArrayList of all Users
	 * @throws SQLException
	 * 
	 */
	public static ArrayList<User> getAllUsers() throws SQLException {
		
		String sql = "SELECT " +
				"user_id, user_first_name, user_last_name, user_image, " +
				"user_created_date, is_user_deleted, total_score, current_score" +
				" FROM Users";
		
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			){
			
			ArrayList<User> usersList = new ArrayList<User>();
			
			while (rs.next()) {
				User userRow = new User();
				
				userRow.setUserId(rs.getInt("user_id"));
				userRow.setUserFirstName(rs.getString("user_first_name"));
				userRow.setUserLastName(rs.getString("user_last_name"));
				userRow.setUserImage(rs.getString("user_image"));
				userRow.setUserCreatedDate(rs.getDate("user_created_date"));
				userRow.setUserDeleted(rs.getBoolean("is_user_deleted"));
				userRow.setTotalScore(rs.getInt("total_score"));
				userRow.setCurrentScore(rs.getInt("current_score"));
				
				usersList.add(userRow);
			}
			
			return usersList;
		}
	}
	
	/**
	 * 
	 * Method to return the User for the given userId
	 * 
	 * @param userId
	 * @return User instance
	 * @throws SQLException
	 * 
	 */
	public static User getUserById(int userId) throws SQLException {
		
		String sql = "SELECT " +
				"user_id, user_first_name, user_last_name, user_image, " +
				"user_created_date, is_user_deleted, total_score, current_score" +
				" FROM Users" +
				" WHERE user_id = ? ";
		ResultSet rs = null;
		
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
			
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				User userRow = new User();
				
				userRow.setUserId(userId);
				userRow.setUserFirstName(rs.getString("user_first_name"));
				userRow.setUserLastName(rs.getString("user_last_name"));
				userRow.setUserImage(rs.getString("user_image"));
				userRow.setUserCreatedDate(rs.getDate("user_created_date"));
				userRow.setUserDeleted(rs.getBoolean("is_user_deleted"));
				userRow.setTotalScore(rs.getInt("total_score"));
				userRow.setCurrentScore(rs.getInt("current_score"));
				
				return userRow;
				
			} else {
				return null;
			}
			
		} finally {
			if (rs != null) rs.close(); 
		}
	}
	
	/**
	 * 
	 * Method to insert new User row
	 * 
	 * @param userRow
	 * @return boolean of whether the row is inserted successfully
	 * @throws SQLException
	 * 
	 */
	public static boolean insertUser(User userRow) throws SQLException {
		
		String sql = "INSERT INTO Users (user_first_name, user_last_name, user_image, " +
				"user_created_date, is_user_deleted, total_score, current_score) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?)";
		ResultSet rs = null;
		
		try (
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			){
			
			stmt.setString(1, userRow.getUserFirstName());
			stmt.setString(2, userRow.getUserLastName());
			stmt.setString(3, userRow.getUserImage());
			stmt.setDate(4, (Date) userRow.getUserCreatedDate());
			stmt.setBoolean(5, userRow.isUserDeleted());
			stmt.setInt(6, userRow.getTotalScore());
			stmt.setInt(7, userRow.getCurrentScore());
			
			int affected_rows = stmt.executeUpdate();
			
			if (affected_rows == 1) {
				rs = stmt.getGeneratedKeys();
				rs.next();
				
				int userId = rs.getInt(1);
				userRow.setUserId(userId);
				
				return true;
			} else {
				return false;
			}
			
		} finally {
			if (rs != null) rs.close(); 
		}
	}
	
	/**
	 * 
	 * Method to update existing User row
	 * 
	 * @param userRow
	 * @return boolean of whether the row is updated successfully
	 * @throws SQLException
	 * 
	 */
	public static boolean updateUser(User userRow) throws SQLException {
		
		String sql = "UPDATE Users SET " +
				"user_first_name = ?, user_last_name = ?, user_image = ?, " +
				"user_created_date = ?, is_user_deleted = ?, total_score = ?, current_score = ?" +
				" WHERE user_id = ?";
		
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
			
			stmt.setString(1, userRow.getUserFirstName());
			stmt.setString(2, userRow.getUserLastName());
			stmt.setString(3, userRow.getUserImage());
			stmt.setDate(4, (Date) userRow.getUserCreatedDate());
			stmt.setBoolean(5, userRow.isUserDeleted());
			stmt.setInt(6, userRow.getTotalScore());
			stmt.setInt(7, userRow.getCurrentScore());
			stmt.setInt(8, userRow.getUserId());
			
			int affected_rows = stmt.executeUpdate();
			
			if (affected_rows == 1) {
				return true;
			} else {
				return false;
			}
		}
	}
}
