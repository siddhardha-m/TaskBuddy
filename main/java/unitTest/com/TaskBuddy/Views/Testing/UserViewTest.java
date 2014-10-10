package com.TaskBuddy.Views.Testing;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.expect;

import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;



public class UserViewTest {
	@Test
	public void getUserByIdShouldReturnCorrectUser(){
		expect().
				body("userId", equalTo("1")).
				body("userFirstName", equalTo("sa")).
				body("userLastName", equalTo("ba")).
				body("userImage", equalTo("ima")).
				body("userCreatedDate", equalTo("2014-10-07 01:02:49.0")).
				body("isUserDeleted", equalTo("false")).
				body("totalScore", equalTo("6")).
				body("currentScore", equalTo("0")).
		when().
				get("/users/1");
	}

}
