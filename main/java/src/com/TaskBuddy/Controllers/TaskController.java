package com.TaskBuddy.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.TaskBuddy.Models.Task;
import com.TaskBuddy.db.ConnectionManager;

/**
 * @author Siddhardha
 *
 * Controller class for Tasks table
 *
 */
@XmlRootElement
public class TaskController {

	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	private static String selectSQL = "SELECT " +
			"task_id, task_title, task_description, task_point_value, task_created_by, " +
			"task_created_date, task_due_date, is_task_completed, is_task_deleted" +
			" FROM Tasks ";
	
	private TaskController() {
	}
	
	/**
	 * 
	 * Method to return all Tasks
	 * 
	 * @return ArrayList of all Tasks
	 * @throws SQLException
	 * 
	 */
	public static ArrayList<Task> getAllTasks() throws SQLException {
		
		String sql = selectSQL;
		
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			){
			
			ArrayList<Task> tasksList = new ArrayList<Task>();
			
			while (rs.next()) {
				tasksList.add(processResultSetIntoTaskRow(rs));
			}
			
			return tasksList;
		}
	}
	
	/**
	 * 
	 * Method to return the Task for the given taskId
	 * 
	 * @param taskId
	 * @return Task instance
	 * @throws SQLException
	 * 
	 */
	public static Task getTaskById(int taskId) throws SQLException {
		
		String sql = selectSQL +
				" WHERE task_id = ? ";
		ResultSet rs = null;
		
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
			
			stmt.setInt(1, taskId);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return processResultSetIntoTaskRow(rs);
			} else {
				return null;
			}
			
		} finally {
			if (rs != null) rs.close(); 
		}
	}
	
	/**
	 * 
	 * Method to insert new Task row
	 * 
	 * @param taskRow
	 * @return boolean of whether the row is inserted successfully
	 * @throws SQLException
	 * 
	 */
	private static boolean insertTask(Task taskRow) throws SQLException {
		
		String sql = "INSERT INTO Tasks (task_title, task_description, task_point_value, task_created_by, " +
				"task_created_date, task_due_date, is_task_completed, is_task_deleted) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		ResultSet rs = null;
		
		try (
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			){
			
			stmt.setString(1, taskRow.getTaskTitle());
			stmt.setString(2, taskRow.getTaskDescription());
			stmt.setInt(3, taskRow.getTaskPointValue());
			stmt.setInt(4, taskRow.getTaskCreatedBy());
			stmt.setTimestamp(5, new  Timestamp(taskRow.getTaskCreatedDate().getTime()));
			stmt.setTimestamp(6, new Timestamp(taskRow.getTaskDueDate().getTime()));
			stmt.setBoolean(7, taskRow.isTaskCompleted());
			stmt.setBoolean(8, taskRow.isTaskDeleted());
			
			int affected_rows = stmt.executeUpdate();
			
			if (affected_rows == 1) {
				rs = stmt.getGeneratedKeys();
				rs.next();
				
				int taskId = rs.getInt(1);
				taskRow.setTaskId(taskId);
				
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
	 * Method to update existing Task row
	 * 
	 * @param taskRow
	 * @return boolean of whether the row is updated successfully
	 * @throws SQLException
	 * 
	 */
	private static boolean updateTask(Task taskRow) throws SQLException {
		
		String sql = "UPDATE Tasks SET " +
				"task_title = ?, task_description = ?, task_point_value = ?, task_created_by = ?, " +
				"task_created_date = ?, task_due_date = ?, is_task_completed = ?, is_task_deleted = ?" +
				" WHERE task_id = ?";
		
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
			
			stmt.setString(1, taskRow.getTaskTitle());
			stmt.setString(2, taskRow.getTaskDescription());
			stmt.setInt(3, taskRow.getTaskPointValue());
			stmt.setInt(4, taskRow.getTaskCreatedBy());
			stmt.setTimestamp(5, new Timestamp(taskRow.getTaskCreatedDate().getTime()));
			stmt.setTimestamp(6, new Timestamp(taskRow.getTaskDueDate().getTime()));
			stmt.setBoolean(7, taskRow.isTaskCompleted());
			stmt.setBoolean(8, taskRow.isTaskDeleted());
			stmt.setInt(9, taskRow.getTaskId());
			
			int affected_rows = stmt.executeUpdate();
			
			if (affected_rows == 1) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * 
	 * Method to save Task row
	 * 
	 * @param taskRow
	 * @return boolean of whether or not the row is saved successfully
	 * @throws SQLException
	 * 
	 */
	public static boolean save(Task taskRow) throws SQLException {
		return taskRow.getTaskId() > 0 ? updateTask(taskRow) : insertTask(taskRow);
	}
	
	/**
	 * 
	 * Method to Convert ResultSet into Task instance
	 * 
	 * @param ResultSet instance rs
	 * @return Task instance
	 * @throws SQLException
	 * 
	 */
	protected static Task processResultSetIntoTaskRow(ResultSet rs) throws SQLException {
		Task taskRow = new Task();
		
		taskRow.setTaskId(rs.getInt("task_id"));
		taskRow.setTaskTitle(rs.getString("task_title"));
		taskRow.setTaskDescription(rs.getString("task_description"));
		taskRow.setTaskPointValue(rs.getInt("task_point_value"));
		taskRow.setTaskCreatedBy(rs.getInt("task_created_by"));
		taskRow.setTaskCreatedDate(rs.getTimestamp("task_created_date"));
		taskRow.setTaskDueDate(rs.getTimestamp("task_due_date"));
		taskRow.setTaskCompleted(rs.getBoolean("is_task_completed"));
		taskRow.setTaskDeleted(rs.getBoolean("is_task_deleted"));
		
		return taskRow;
	}
}
