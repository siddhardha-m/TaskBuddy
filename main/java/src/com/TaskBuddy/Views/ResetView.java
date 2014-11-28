package com.TaskBuddy.Views;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.TaskBuddy.Controllers.ResetController;

/**
 * @author Siddhardha
 *
 * View class to call reset stored procedure
 *
 */
@Path("/reset")
public class ResetView {
	
	private static final Logger log = Logger.getLogger(ResetView.class);
	
	public ResetView() {
	}
	
	/**
	 * 
	 * This method is invoked on POST
	 * @return boolean of success or failure
	 * 
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public static boolean reset() {
		try {
			
			return ResetController.reset();
			
		} catch (Exception e) {
			
			log.error("Error message: " + e.getMessage());
			
			return false;
		}
	}
}
