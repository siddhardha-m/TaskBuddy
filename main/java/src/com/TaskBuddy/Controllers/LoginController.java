package com.TaskBuddy.Controllers;

import java.sql.Connection;

import com.TaskBuddy.db.ConnectionManager;

/**
 * @author Siddhardha
 *
 * Controller class for Logins table
 *
 */
public class LoginController {

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	private LoginController() {
	}
}
