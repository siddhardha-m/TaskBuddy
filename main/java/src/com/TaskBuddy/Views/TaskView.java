package com.TaskBuddy.Views;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

	public TaskView() {
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ArrayList<Task> getAllTasks(){
		try{
			return TaskController.getAllTasks();
		}catch(SQLException se){
			System.err.println("Error Message: " + se.getMessage());
			System.err.println("Error Stacktrace: " + se.getStackTrace().toString());
			return null;
		}
	}	
}
