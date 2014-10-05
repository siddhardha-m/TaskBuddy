package com.TaskBuddy.Views;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.TaskBuddy.Controllers.UserController;
import com.TaskBuddy.Models.User;

/**
 * @author Siddhardha
 *
 * View class for Users table
 *
 */
@Path("/users")
public class UserView {

	public UserView() {
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public ArrayList<User> getAllUsers() {
		try {
			
			return UserController.getAllUsers();
			
		} catch (SQLException e) {
			
			System.err.println("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
}
