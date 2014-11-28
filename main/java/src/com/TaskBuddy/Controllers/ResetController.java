package com.TaskBuddy.Controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlRootElement;

import com.TaskBuddy.db.ConnectionManager;

/**
 * @author Siddhardha
 *
 * Controller class to call reset stored procedure
 *
 */
@XmlRootElement
public class ResetController {
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	private static String SQL = "{CALL reset()}";
	
	private ResetController() {
	}
	
	public static boolean reset() throws SQLException {
		
		String sql = SQL;
		
		try (
				CallableStatement cstmt = conn.prepareCall (sql);
			){
			
			cstmt.execute();
			
			return true;
		}
	}
}
