package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectionTest {

	private static final String SERVER_NAME = "localhost";
	private static final String DB_NAME = "taskbuddy";
	private static final String USERNAME = "tb_db_user";
	private static final String PASSWORD = "tbdbuserpwd";
	
	private static final String CONN_STRING = "jdbc:mysql://" + SERVER_NAME + "/" + DB_NAME;
	
	public static void main(String[] args) throws SQLException {
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
		String sqlQuery = null;
		sqlQuery = "SELECT * FROM User";
		
		try 
			(
					Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sqlQuery);
			)
		{
//			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			
			System.out.println("Connected to DB");
			
//			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
//			rs = stmt.executeQuery(sqlQuery);
			
//			while(rs.next()){
//				System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
//			}
			
			rs.last();
			System.out.println("Number of rows: " + rs.getRow());
			
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
		} 
//		finally {
//			if (rs != null){
//				rs.close();
//			}
//			if (stmt != null){
//				stmt.close();
//			}
//			if (conn != null){
//				conn.close();
//			}
//		}
	}
}
