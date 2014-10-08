package com.TaskBuddy.Controllers.Testing;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.TaskBuddy.Controllers.TaskController;

public class TaskControllerTest {

	@Test
	public void getAllTasksShouldReturnArrayList() throws SQLException{
		assertNotNull("getAllTasks() is returning null", TaskController.getAllTasks());
	}
	
	
}
