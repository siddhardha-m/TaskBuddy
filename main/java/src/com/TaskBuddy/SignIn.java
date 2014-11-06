package com.TaskBuddy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.TaskBuddy.Controllers.UserController;
import com.TaskBuddy.Models.User;
import com.TaskBuddy.db.ConnectionManager;

public class SignIn extends HttpServlet{
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	super.doGet(req, resp);
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	super.doPost(req, resp);
			
		String receivedFbId = request.getParameter("fbId");
		String receivedFirstName = request.getParameter("firstName");
		String receivedLastName = request.getParameter("lastName");
		//response.setContentType("text/plain");
		//response.getWriter().write("Data received = " + receivedData);
		long fbId = Long.parseLong(receivedFbId);
		int userIdFromDb = 0;
		try {
			userIdFromDb = insertIntoDB(fbId, receivedFirstName, receivedLastName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(Integer.toString(userIdFromDb));
	}
	
	private static int insertIntoDB(long fbId, String firstName, String lastName) throws SQLException{
		String sql = "select user_id from users where fb_id = ?";
		ResultSet rs = null;
		
		int userIdFromDb = 0;
		try (
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
			
			stmt.setLong(1, fbId);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				userIdFromDb = rs.getInt("user_id");
			}else{
				User newUser = new User();
				newUser.setCurrentScore(0);
				newUser.setUserImage(null);
				newUser.setTotalScore(0);
				newUser.setUserCreatedDate(new Date(System.currentTimeMillis()));
				newUser.setUserDeleted(false);
				newUser.setUserFirstName(firstName);
				newUser.setUserLastName(lastName);
				newUser.setFbId(fbId);
				
				userIdFromDb =  (UserController.save(newUser)) ? (newUser.getUserId()) : (-1);
				
			}
			return userIdFromDb;
			
			
		}finally{
			if(rs != null) {
				rs.close();
			}
		}
		
	}
}
