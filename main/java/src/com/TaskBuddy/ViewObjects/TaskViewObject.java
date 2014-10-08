package com.TaskBuddy.ViewObjects;

import java.util.Date;

/**
 * @author Siddhardha
 * 
 * ViewObject class for the tables 'Tasks' and 'UserTasks'.
 * Each private member represents a column of the tables.
 * The private members must be accessed using the public getter and setter methods. 
 *
 */
public class TaskViewObject {
	private int taskId;
	private String taskTitle;
	private String taskDescription;
	private int taskPointValue;
	private int taskCreatedBy;
	private Date taskCreatedDate;
	private Date taskDueDate;
	private boolean isTaskCompleted;
	private boolean isTaskDeleted;
	private int userId;
	private Date taskAssignedDate;
	private boolean isTaskAssigned;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public int getTaskPointValue() {
		return taskPointValue;
	}
	public void setTaskPointValue(int taskPointValue) {
		this.taskPointValue = taskPointValue;
	}
	public int getTaskCreatedBy() {
		return taskCreatedBy;
	}
	public void setTaskCreatedBy(int taskCreatedBy) {
		this.taskCreatedBy = taskCreatedBy;
	}
	public Date getTaskCreatedDate() {
		return taskCreatedDate;
	}
	public void setTaskCreatedDate(Date taskCreatedDate) {
		this.taskCreatedDate = taskCreatedDate;
	}
	public Date getTaskDueDate() {
		return taskDueDate;
	}
	public void setTaskDueDate(Date taskDueDate) {
		this.taskDueDate = taskDueDate;
	}
	public boolean isTaskCompleted() {
		return isTaskCompleted;
	}
	public void setTaskCompleted(boolean isTaskCompleted) {
		this.isTaskCompleted = isTaskCompleted;
	}
	public boolean isTaskDeleted() {
		return isTaskDeleted;
	}
	public void setTaskDeleted(boolean isTaskDeleted) {
		this.isTaskDeleted = isTaskDeleted;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getTaskAssignedDate() {
		return taskAssignedDate;
	}
	public void setTaskAssignedDate(Date taskAssignedDate) {
		this.taskAssignedDate = taskAssignedDate;
	}
	public boolean isTaskAssigned() {
		return isTaskAssigned;
	}
	public void setTaskAssigned(boolean isTaskAssigned) {
		this.isTaskAssigned = isTaskAssigned;
	}
}
