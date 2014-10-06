package com.TaskBuddy.Views;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.TaskBuddy.Controllers.LoginController;
import com.TaskBuddy.Models.Login;

/**
 * @author Siddhardha
 *
 * View class for Logins table
 *
 */
@Path("/logins")
public class LoginView {
	
	private static final Logger log = Logger.getLogger(LoginView.class);

	public LoginView() {
	}
	
	/**
	 * 
	 * This method is invoked on GET
	 * @return ArrayList of all Logins in JSON format
	 * 
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ArrayList<Login> getAllLogins() {
		try {
			
			return LoginController.getAllLogins();
			
		} catch (SQLException e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
}
