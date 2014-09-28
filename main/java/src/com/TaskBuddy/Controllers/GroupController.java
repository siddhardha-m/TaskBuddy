package com.TaskBuddy.Controllers;

import java.sql.Connection;

import com.TaskBuddy.db.ConnectionManager;

/**
 * @author Siddhardha
 *
 * Controller class for Groups table
 *
 */
public class GroupController {

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	private GroupController() {
	}
}
