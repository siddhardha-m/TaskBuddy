package com.TaskBuddy.Controllers;

import java.sql.Connection;

import com.TaskBuddy.db.ConnectionManager;

/**
 * @author Siddhardha
 *
 * Controller class for Tasks table
 *
 */
public class TaskController {

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	private TaskController() {
	}
}
