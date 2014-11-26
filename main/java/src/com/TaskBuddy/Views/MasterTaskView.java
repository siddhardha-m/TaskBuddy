package com.TaskBuddy.Views;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.TaskBuddy.Controllers.TaskController;
import com.TaskBuddy.Models.Task;
import com.TaskBuddy.Models.UserTask;
import com.TaskBuddy.ViewObjects.TaskViewObject;

/**
 * @author Siddhardha
 *
 * View class for Tasks table
 *
 */
@Path("/tasks/master")
public class MasterTaskView {
	
	private static final Logger log = Logger.getLogger(TaskView.class);

	public MasterTaskView() {
	}
	
	private static TaskViewObject createTaskViewObject(Task taskRow, UserTask userTaskRow) {
		TaskViewObject taskViewRow = new TaskViewObject();
		
		taskViewRow.setTaskId(taskRow.getTaskId());
		taskViewRow.setTaskTitle(taskRow.getTaskTitle());
		taskViewRow.setTaskDescription(taskRow.getTaskDescription());
		taskViewRow.setTaskOriginalPointValue(taskRow.getTaskOriginalPointValue());
		taskViewRow.setTaskUpdatedPointValue(taskRow.getTaskUpdatedPointValue());
		taskViewRow.setTaskCreatedBy(taskRow.getTaskCreatedBy());
		taskViewRow.setTaskCreatedDate(taskRow.getTaskCreatedDate());
		taskViewRow.setTaskDueDate(taskRow.getTaskDueDate());
		taskViewRow.setTaskCompleted(taskRow.isTaskCompleted());
		taskViewRow.setTaskDeleted(taskRow.isTaskDeleted());
		taskViewRow.setTaskRepetition(taskRow.getTaskRepetition());
		taskViewRow.setTaskMaster(taskRow.isTaskMaster());
		taskViewRow.setTaskDueDuration(taskRow.getTaskDueDuration());
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
		taskRow.setTaskOriginalPointValue(taskViewRow.getTaskOriginalPointValue());
		taskRow.setTaskUpdatedPointValue(taskViewRow.getTaskUpdatedPointValue());
		taskRow.setTaskCreatedBy(taskViewRow.getTaskCreatedBy());
		taskRow.setTaskCreatedDate(taskViewRow.getTaskCreatedDate());
		taskRow.setTaskDueDate(taskViewRow.getTaskDueDate());
		taskRow.setTaskCompleted(taskViewRow.isTaskCompleted());
		taskRow.setTaskDeleted(taskViewRow.isTaskDeleted());
		taskRow.setTaskRepetition(taskViewRow.getTaskRepetition());
		taskRow.setTaskMaster(taskViewRow.isTaskMaster());
		taskRow.setTaskDueDuration(taskViewRow.getTaskDueDuration());
		
		return taskRow;
	}
	
	/**
	 * 
	 * This method is invoked on GET
	 * @return ArrayList of all TaskViewObjects in JSON format
	 * 
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public static ArrayList<TaskViewObject> getAllMasterTaskViews() {
		try {
			
			ArrayList<TaskViewObject> taskViewList = new ArrayList<TaskViewObject>();
			
			ArrayList<Task> tasksList = TaskController.getAllMasterTasks();
			
			for (Task taskRow : tasksList) {
				UserTask userTaskRow = new UserTask();
				taskViewList.add(createTaskViewObject(taskRow, userTaskRow));
			}
			
			return taskViewList;
			
		} catch (Exception e) {
			
			log.error("Error message: " + e.getMessage());
			
			return null;
			
		}
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public static boolean insertMasterTaskView(TaskViewObject taskViewRow) {
		try {
			Task taskRow = getTaskFromTaskViewObject(taskViewRow);

			boolean taskSaved = TaskController.save(taskRow);
			
			if(taskSaved) {
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
	public static boolean updateMasterTaskView(TaskViewObject taskViewRow) {
		try {
			Task taskRow = getTaskFromTaskViewObject(taskViewRow);
			
			boolean taskSaved = TaskController.save(taskRow);
			
			if(taskSaved) {
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
