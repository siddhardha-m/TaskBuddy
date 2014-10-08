package com.TaskBuddy.Views;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.TaskBuddy.Controllers.TaskController;
import com.TaskBuddy.Controllers.UserTaskController;
import com.TaskBuddy.Models.Task;
import com.TaskBuddy.Models.UserTask;
import com.TaskBuddy.ViewObjects.TaskViewObject;

/**
 * @author Siddhardha
 *
 * View class for Tasks table
 *
 */
@Path("/tasks")
public class TaskView {
	
	private static final Logger log = Logger.getLogger(TaskView.class);

	public TaskView() {
	}
	
	private static TaskViewObject createTaskViewObject(Task taskRow, UserTask userTaskRow) {
		TaskViewObject taskViewRow = new TaskViewObject();
		
		taskViewRow.setTaskId(taskRow.getTaskId());
		taskViewRow.setTaskTitle(taskRow.getTaskTitle());
		taskViewRow.setTaskDescription(taskRow.getTaskDescription());
		taskViewRow.setTaskPointValue(taskRow.getTaskPointValue());
		taskViewRow.setTaskCreatedBy(taskRow.getTaskCreatedBy());
		taskViewRow.setTaskCreatedDate(taskRow.getTaskCreatedDate());
		taskViewRow.setTaskDueDate(taskRow.getTaskDueDate());
		taskViewRow.setTaskCompleted(taskRow.isTaskCompleted());
		taskViewRow.setTaskDeleted(taskRow.isTaskDeleted());
		taskViewRow.setUserId(userTaskRow.getUserId());
		taskViewRow.setTaskAssignedDate(userTaskRow.getTaskAssignedDate());
		taskViewRow.setTaskAssigned(userTaskRow.isTaskAssigned());
		
		return taskViewRow;
	}
	
	private static Task getTaskFromTaskViewObject(TaskViewObject taskViewRow) {
		Task taskRow = new Task();
		
		taskRow.setTaskId(taskViewRow.getTaskId());
		taskRow.setTaskTitle(taskViewRow.getTaskTitle());
		taskRow.setTaskDescription(taskViewRow.getTaskDescription());
		taskRow.setTaskPointValue(taskViewRow.getTaskPointValue());
		taskRow.setTaskCreatedBy(taskViewRow.getTaskCreatedBy());
		taskRow.setTaskCreatedDate(taskViewRow.getTaskCreatedDate());
		taskRow.setTaskDueDate(taskViewRow.getTaskDueDate());
		taskRow.setTaskCompleted(taskViewRow.isTaskCompleted());
		taskRow.setTaskDeleted(taskViewRow.isTaskDeleted());
		
		return taskRow;
	}
	
	private static UserTask getUserTaskFromTaskViewObject(TaskViewObject taskViewRow) {
		UserTask userTaskRow = new UserTask();
		
		userTaskRow.setUserId(taskViewRow.getUserId());
		userTaskRow.setTaskId(taskViewRow.getTaskId());
		userTaskRow.setTaskAssignedDate(taskViewRow.getTaskAssignedDate());
		userTaskRow.setTaskAssigned(taskViewRow.isTaskAssigned());
		
		return userTaskRow;
	}
	
	/**
	 * 
	 * This method is invoked on GET
	 * @return ArrayList of all TaskViewObjects in JSON format
	 * 
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public static ArrayList<TaskViewObject> getAllTaskViews() {
		try {
			
			ArrayList<TaskViewObject> taskViewList = new ArrayList<TaskViewObject>();
			
			ArrayList<Task> tasksList = TaskController.getAllTasks();
			
			for (Task taskRow : tasksList) {
				
				ArrayList<UserTask> userTasksList = UserTaskController.getAllUsersByTaskId(taskRow.getTaskId());
				
				for (UserTask  userTaskRow : userTasksList) {
					taskViewList.add(createTaskViewObject(taskRow, userTaskRow));
				}
				
			}
			
			return taskViewList;
			
		} catch (Exception e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
	
	@GET @Path("user/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public static ArrayList<TaskViewObject> getAllTaskViewsByUserId(@PathParam("userId") int userId) {
		try {
			
			ArrayList<UserTask> userTasksList = UserTaskController.getAllTasksByUserId(userId);
			
			ArrayList<TaskViewObject> taskViewList = new ArrayList<TaskViewObject>();
			
			for (UserTask userTaskRow : userTasksList) {
				
				Task taskRow = TaskController.getTaskById(userTaskRow.getTaskId());
				
				taskViewList.add(createTaskViewObject(taskRow, userTaskRow));
			}
			
			return taskViewList;
			
		} catch (Exception e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
	
	@GET @Path("{taskId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public static TaskViewObject getTaskViewByTaskId(@PathParam("taskId") int taskId) {
		try {
			
			Task taskRow = TaskController.getTaskById(taskId);
			
			ArrayList<UserTask> userTasksList = UserTaskController.getAllUsersByTaskId(taskId);
			
			UserTask userTaskRow = new UserTask();
			
			for (UserTask userTaskRowInternal : userTasksList) {
				userTaskRow = userTaskRowInternal;
			}
			
			return createTaskViewObject(taskRow, userTaskRow);
			
		} catch (Exception e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public static boolean insertTaskView(TaskViewObject taskViewRow) {
		try {
			Task taskRow = getTaskFromTaskViewObject(taskViewRow);
			UserTask userTaskRow = getUserTaskFromTaskViewObject(taskViewRow);
			
			boolean taskSaved = TaskController.save(taskRow);
			boolean userTaskSaved = UserTaskController.save(userTaskRow);
			
			if(taskSaved && userTaskSaved) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			
			log.error("Error message: " + e.getMessage());
			
			return false;
		}
	}
	
	@PUT @Path("{taskId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public static boolean updateTaskView(TaskViewObject taskViewRow) {
		try {
			Task taskRow = getTaskFromTaskViewObject(taskViewRow);
			UserTask userTaskRow = getUserTaskFromTaskViewObject(taskViewRow);
			
			boolean taskSaved = TaskController.save(taskRow);
			boolean userTaskSaved = UserTaskController.save(userTaskRow);
			
			if(taskSaved && userTaskSaved) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			
			log.error("Error message: " + e.getMessage());
			
			return false;
		}
	}
}
