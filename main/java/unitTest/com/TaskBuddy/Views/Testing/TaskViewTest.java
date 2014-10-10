package com.TaskBuddy.Views.Testing;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;

import org.junit.Test;

public class TaskViewTest {
	@Test
	public void getUserByIdShouldReturnCorrectUser(){
		expect().
				body("taskId", equalTo("1")).
				body("taskTitle", equalTo("Dishwash")).
				body("taskDescription", equalTo("")).
				body("taskPointValue", equalTo("")).
				body("taskCreatedBy", equalTo("2014-10-07 01:02:49.0")).
				body("taskCreatedDate", equalTo("")).
				body("taskDueDate", equalTo("")).
				body("isTaskCompleted", equalTo("")).
				body("isTaskDeleted", equalTo("false")).
		when().
				get("/tasks/1");
	}
	
}
