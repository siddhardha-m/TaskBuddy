package com.TaskBuddy.Models;

/**
 * @author Siddhardha
 * 
 * Model class for the table 'UserTasks'.
 * Each private member represents a column of the table.
 * The private members must be accessed using the public getter and setter methods. 
 *
 */
public class UserTask {
	private int userId;
	private int taskId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
}
