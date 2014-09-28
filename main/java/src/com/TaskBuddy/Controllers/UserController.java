package com.TaskBuddy.Controllers;

import java.sql.Connection;

import com.TaskBuddy.db.ConnectionManager;


/**
 * @author Siddhardha
 *
 * Controller class for Users table
 *
 */
public class UserController {

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	private UserController() {
	}
}
