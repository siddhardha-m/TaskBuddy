package com.TaskBuddy.Views;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

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
	
	private static final Logger log = Logger.getLogger(UserView.class);

	public UserView() {
	}
	
	/**
	 * 
	 * This method is invoked on GET
	 * @return ArrayList of all Users in JSON format
	 * 
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public static ArrayList<User> getAllUsers() {
		try {
			
			return UserController.getAllUsers();
			
		} catch (SQLException e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
}
