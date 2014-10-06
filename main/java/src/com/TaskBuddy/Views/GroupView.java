package com.TaskBuddy.Views;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.TaskBuddy.Controllers.GroupController;
import com.TaskBuddy.Models.Group;

/**
 * @author Siddhardha
 *
 * View class for Groups table
 *
 */
@Path("/groups")
public class GroupView {
	
	private static final Logger log = Logger.getLogger(GroupView.class);

	public GroupView() {
	}
	
	/**
	 * 
	 * This method is invoked on GET
	 * @return ArrayList of all Groups in JSON format
	 * 
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ArrayList<Group> getAllGroups() {
		try {
			
			return GroupController.getAllGroups();
			
		} catch (SQLException e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
}
