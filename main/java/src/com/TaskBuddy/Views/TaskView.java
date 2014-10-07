package com.TaskBuddy.Views;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.TaskBuddy.Controllers.TaskController;
import com.TaskBuddy.Controllers.UserTaskController;
import com.TaskBuddy.Models.Task;
import com.TaskBuddy.Models.UserTask;

/**
 * @author Siddhardha
 *
 * View class for Tasks table
 *
 */
@Path("/tasks")
public class TaskView {
	
	private static final Logger log = Logger.getLogger(TaskView.class);

	public TaskView() {
	}
	
	/**
	 * 
	 * This method is invoked on GET
	 * @return ArrayList of all Tasks in JSON format
	 * 
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public static ArrayList<Task> getAllTasks() {
		try {
			
			return TaskController.getAllTasks();
			
		} catch (SQLException e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
	
	@GET @Path("user/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public static ArrayList<Task> getAllTasksByUserId(@PathParam("userId") int userId) {
		try {
			
			ArrayList<UserTask> userTasksList = UserTaskController.getAllTasksByUserId(userId);
			
			ArrayList<Task> tasksList = new ArrayList<Task>();
			
			for (UserTask userTaskRow : userTasksList) {
				tasksList.add(TaskController.getTaskById(userTaskRow.getTaskId()));
			}
			
			return tasksList;
			
		} catch (SQLException e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
	
	@GET @Path("{taskId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public static Task getTaskById(@PathParam("taskId") int taskId) {
		try {
			
			return TaskController.getTaskById(taskId);
			
		} catch (SQLException e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public static boolean insertTask(Task taskRow) {
		try {
			
			return TaskController.save(taskRow);
			
		} catch (SQLException e) {
			
			log.error("Error message: " + e.getMessage());
			
			return false;
		}
	}
	
	@PUT @Path("{taskId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public static boolean updateTask(Task taskRow) {
		try {
			
			return TaskController.save(taskRow);
			
		} catch (SQLException e) {
			
			log.error("Error message: " + e.getMessage());
			
			return false;
		}
	}
}
