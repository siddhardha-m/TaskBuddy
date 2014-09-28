package com.TaskBuddy.Models;

import java.util.Date;

public class GroupMembership {
	private int groupId;
	private int userId;
	private Date userJoinedDate;
	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getUserJoinedDate() {
		return userJoinedDate;
	}
	public void setUserJoinedDate(Date userJoinedDate) {
		this.userJoinedDate = userJoinedDate;
	}
}
