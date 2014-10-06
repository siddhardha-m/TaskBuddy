package com.TaskBuddy.Views;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.TaskBuddy.Controllers.TaskController;
import com.TaskBuddy.Models.Task;

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
	public ArrayList<Task> getAllTasks() {
		try {
			
			return TaskController.getAllTasks();
			
		} catch (SQLException e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
}
