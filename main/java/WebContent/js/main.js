/*
 * Lets initialize the variables we need
 */
var $tasks 			= $('#tasks'),
	$users 			= $('#users'),
	tasks 			= null,
	users 			= null,
	currentUserId 	= null,
	userListView 	= null,
	taskListView 	= null,
	today 			= new Date(),
	tomorrow		= new Date(),
	assignedUser    = null,
	serverUrl		= 'http://localhost:8080/TaskBuddy/site/';
/*
 * We will add one day to the current date to pre-fill Due Date
 */
tomorrow.setDate(today.getDate() + 1);

var months = {
		'1' : 'Jan', '2' : 'Feb', '3' : 'March', '4' : 'April', '5' : 'May', '6' : 'June', '7' : 'July', '8' : 'Aug', '9' : 'Sept', '10' : 'Oct', '11' : 'Nov', '12' : 'Dec'
};


/*
 * User model
 */
User = Backbone.Model.extend({
	defaults: {
		userId: null,
		userFirstName: '',
		userLastName: '',
		userImage: null,
		totalScore: 0,
		currentScore: 0,
		userCreatedDate: ''
	},
	urlRoot: serverUrl + 'users'
});

/*
 * User collection
 */
UserCollection = Backbone.Collection.extend({
	model: User,
	url: serverUrl + 'users' 
});

/*
 * View for single user item (li)
 */
UserItem = Backbone.View.extend({
	/*
	 * Defining the parent tag that will be created
	 */
	tagName: 'li',
	/*
	 * Constructor like method where we can do needed intializations
	 */
	initialize: function() {
		/*
		 * We are attaching context to render method
		 */
		console.log('user item created');
		this.render = _.bind(this.render, this);

		/*
		 * Defining template, we are loading underscore template here
		 */
		this.template = _.template($('#user-item').html());

		/*
		 * Bind any change in the model to render method
		 */
		this.model.bind('change', this.render);
	},
	/*
	 * Defining events which will trigger our methods
	 */
	events: {
		'dblclick a': 'edit',
		'click a': 'loadUsers'
	},
	/*
	 * Rendering template
	 */
	render: function() {
		/*
		 * Filling template with model attributes
		 */
		this.$el.html(this.template(this.model.attributes));
		return this;
	},
	/*
	 * Triggers when user double clicks on user
	 */
	edit: function() {
		/*
		 * Opening user dialog and filling it with current model (the one that was clicked)
		 */
		new UserDialog({model: this.model}).show();
	},
	/*
	 * Method which loads currently selected user tasks
	 */
	loadUsers: function() {
		/*
		 * First lets handle visual side of selecting a user, we'll remove active class for user that was selected earlier and 
		 * add class to currently selected user
		 */
		$users.find('li.active').removeClass('active');
		this.$el.addClass('active');
		
		/*
		 * We also need to update user title (above the tasks table)
		 */
		$('#user-title span').html(this.model.get('userFirstName'));
		//$('#user-title h3#score').html(this.model.get('totalScore'));

		/*
		 * Cleaning bit, we'll remove tasks that was tied to user selected earlier
		 */
		$tasks.empty();

		currentUserId = this.model.get('userId');

		console.log("user id "+ currentUserId);

		/*
		 * We'll initialize new task collection
		 */

		tasks = new TaskCollection({id: currentUserId});

		/*
		 * We'll assign current user ID to "global" variable as we need it on several other places
		 */

		//console.log("user id "+ currentUserId);
		/*
		 * Lets fetch tasks for currently selected user (we can access currently selected user through this.model)
		 * processData param here only informs the system that params provided through data param needs to be added to URL as GET params
		 */
		tasks.fetch({data: {user: this.model.taskId}, processData: true, success: function() {
			/*
			 * Initializing task list view and passing tasks collection to it
			 */
			taskListView = new TaskList({
				collection: tasks,
				/*
				 * Telling view to which DOM elements it needs to attach itself
				 */
				el: $tasks
			});
			/*
			 * Rendering list view
			 */
			taskListView.render();
		}});
	}
});

/*
 * View for user list, collection of users
 */
UserList = Backbone.View.extend({
	initialize: function() {
		_(this).bindAll('add', 'remove');

		/*
		 * Holder of single user views
		 */
		this._users = [];
		
		/*
		 * For each element in collection we run the 'add' method
		 */
		this.collection.each(this.add);
		
		/*
		 * Binding collection events to our methods
		 */
		this.collection.bind('add', this.add);
    	this.collection.bind('remove', this.remove);
	},
	render: function() {
		/*
		 * Initializing and setting flag from which we'll know if our view was rendered or not
		 */
		this._rendered = true;

		/*
		 * We render single user items and append it to DOM element
		 */
		_(this._users).each(function(item) {
			$users.append(item.render().el);
		});
	},
	/*
	 * Method that fires when user item is added (either from collection after fetching or creating a new one)
	 */
	add: function(user) {
		var userItem = new UserItem({model: user});
		/*
		 * Adding user item view to the list
		 */
		this._users.push(userItem);

		/*
		 * If view is rendered then we add our rendered item to this view
		 */
		if (this._rendered) {
			this.$el.append(userItem.render().el);
		}
	},
	/*
	 * Fires when removing user item
	 */
	remove: function(user) {
		/*
		 * Determining which view we need to remove from markup
		 */
		var view = _(this._users).select(function(cv) { return cv.model === user; })[0];
		if (this._rendered) {
			$(view.el).remove();
		}

		/*
		 * Triggering click to the first user item
		 */
		$users.find('li:nth-child(2)').find('a').trigger('click');
	}
});

/*
 * User dialog view, form for creating and editing users
 */
UserDialog = Backbone.View.extend({
	/*
	 * Events which we are listening and their respective selectors that triggers them
	 */
	events: {
		'click .save-action': 'save',
		'click .close,.close-action': 'close',
		'change input': 'modify'
	},
	initialize: function() {
		this.template = _.template($('#user-dialog').html());
	},
	render: function() {
		this.$el.html(this.template(this.model.toJSON()));
		return this;
	},
	/*
	 * Displaying the dialog
	 */
	show: function() {
		$(document.body).append(this.render().el);
	},
	/*
	 * Removing the dialog
	 */
	close: function() {
		this.remove();
	},
	/*
	 * Fires when we click save on the form
	 */
	save: function() {
		/*
		 * If this is new user it won't have the ID attribute defined
		 */
		
		var that = this;
		 
		$.each(this.$el.find('input'), function(i, item) {
			var attribute = {};
			/*
			 * Matching name and value
			 */
			attribute[item.name] = item.value;
			that.model.set(attribute);
			
		});
		
		
		if (null == this.model.userId) {
			/*
			 * We are creating our model through its collection (this way we'll automatically update its views and persist it to DB)
			 */	
			var todaydate = new Date(); 
			 var milliseconds = todaydate.getTime();
			 
			 this.model.set({userCreatedDate: milliseconds});
			users.create(this.model);
		} else {
			/*
			 * Simple save will persist the model to the DB and update its view
			 */
			this.model.save();
		}
		/*
		 * Hiding modal dialog window
		 */
		this.remove();
	},
	/*
	 * We listen to every change on forms input elements and as they have the same name as the model attribute we can easily update our model
	 */
	modify: function(e) {
		var attribute = {};
		/*
		 * We'll fetch name and value from element that triggered "change" event
		 */
		attribute[e.currentTarget.name] = e.currentTarget.value;
		this.model.set(attribute);
	}
});



/*
 * This is our Backbone model representation (as you can see attributes are the same as in the database table)
 */
Task = Backbone.Model.extend({

	/*
	 * We need to define default values which will be used in model creation (and to give Backbone some info what our model look like)
	 */
	defaults: {
		taskId: null,
		taskCreatedDate: 1407385965000,
		taskDueDate: tomorrow.getFullYear() + '-' + (1 + tomorrow.getMonth()) + '-' + tomorrow.getDate() + ' ' + tomorrow.getHours() + ':' + tomorrow.getMinutes() + ':' + tomorrow.getSeconds(),
		userId: null,
		taskCreatedBy: 1,
		taskAssignedDate: 1407385965000,
		taskDeleted: false,
		taskAssigned: '',
		taskCompleted: false,
		taskTitle: '',
		taskPointValue: 0,
		taskDescription: ''
	},
	
	/*
	 * This is the URI where the Backbone will communicate with our server part
	 */
	urlRoot : serverUrl + 'tasks' 

});

/*
 * We also need a collection object which will hold our models (so it will be able to communicate with Backbone views more easily)
 */
TaskCollection = Backbone.Collection.extend({
	
	initialize: function(options) {
		console.log(options.id);
		this.url = serverUrl + 'tasks' + '/user/' + options.id
	},
	
	/*
	 * Model which this collection will hold and manipulate
	 */
	model: Task

});


/*
 * Single task view
 */
TaskItem = Backbone.View.extend({
	tagName: 'tr',
	initialize: function() {
		this.render = _.bind(this.render, this);
		this.template = _.template($('#task-item').html());
		this.model.bind('change', this.render);
	},
	events: {
		'dblclick': 'edit',
		'change input': 'modify',
		'click a.delete-action': 'delete'
	},
	render: function() {
		var currentTime = new Date(parseInt(this.model.get("taskDueDate") ));

		console.log(" due date is "+currentTime);
		var day = currentTime.getDate();
		var month = currentTime.getMonth() + 1;
		var year = currentTime.getFullYear();
        var hour = currentTime.getHours();
        var mins = currentTime.getMinutes();
		var date = (day < 10 ? '0' : '') + day  + "-" + months[month] + "-" + year + "  " + hour + ":" + (mins < 10 ? '0' : '') + mins ;

		this.model.attributes["taskDueDate"] = date;

		this.$el.html(this.template(this.model.attributes));
		return this;
	},
	edit: function() {
		new TaskDialog({model: this.model}).show();
	},
	/*
	 * We are listening for status checkbox, it updates the model and presist status to the DB
	 */
	modify: function(e) {
		var status = e.currentTarget.checked ? 1 : 0;
		this.model.set({status: status});
		this.model.save();
		/*
		 * We'll add strikethrough class to the title and date just to visually distinguish finished from unfinished task
		 */
		if (status === 1) {
			this.$el.find('td').addClass('finished');
		} else {
			this.$el.find('td').removeClass('finished');
		}
	},
	/*
	 * Handling the deletion of item
	 */

});

/*
 * Task list/collection view
 */
TaskList = Backbone.View.extend({
	initialize: function() {
		_(this).bindAll('add');

		this._tasks = [];
		
		this.collection.each(this.add);
		
		this.collection.bind('add', this.add);
	},
	render: function() {
		this._rendered = true;
		this.$el.empty();
		_(this._tasks).each(function(item) {
			$tasks.append(item.render().el);
		});
	},
	add: function(task) {
		var taskItem = new TaskItem({model: task});

		
		this._tasks.push(taskItem);

		if (this._rendered) {
			this.$el.append(taskItem.render().el);
		}
	}
});

/*
 * Modal dialog/form for creating or editing single task
 */
TaskDialog = Backbone.View.extend({
	/*
	 * As you may see we don't listen for change on input elements. We'll show a different strategy for fetching data here
	 */
		
	events: {
		'click .save-action': 'save',
		"click a#userli": "selectUser",
	    "click button#close": "closeModal",
		'click .close,.close-action': 'close'
	},
	initialize: function() {
		this.template = _.template($('#task-dialog').html());
	},
	render: function(list_of_users) {

		this.$el.html(this.template(this.model.toJSON() , list_of_users ));
		/*
		 * We'll initialize datetime picker
		 */
		
		
		this.$el.find('#dp1').datetimepicker();
		return this;
	},

	  closeModal: function(e){
		  console.log("close");
		  this.remove();
		  
	  },
	  selectUser: function(e) {
		  e.preventDefault();
		  var selectedUserName = $(e.currentTarget).html();
		  assignedUser = parseInt(e.currentTarget.attributes.title.value);
	    this.$el.find('h4#displayUserName').html(selectedUserName);   
	  },
	  
	/*  submit: function(e){
		  console.log("inside save");

		  var data = JSON.stringify($('taskForm').serializeObject());
		  console.log("save butooon clicked"+data);
		  this.remove();
		
		  var plainObject = new Object();
		  myObject.name = "John";
		  
		  
		  $.ajax({
			    url:serverUrl,
			    type:'POST',
			    dataType:"json",
			    data: plainObject,
			    success:function (data) {             
			    if(data.error) {  // If there is an error, show the error messages
			            $('.alert-error').text(data.error.text).show();
			        }            
			    }
			});
		  
	  },
	 */ 
	  
	show: function(list_of_users) {
		$(document.body).append(this.render(list_of_users).el);
	},
	close: function() {
		this.remove();
	},
	/*
	 * Handling the save click, adding item to collection and persisting data to DB
	 */
	save: function() {
		/*
		 * We'll save a reference to current context
		 */
		var that = this;

		/*
		 * Traversing input elements in current dialog
		 */
		
		$.each(this.$el.find('input'), function(i, item) {
			var attribute = {};
			/*
			 * Matching name and value
			 */
			if(item.name !== "taskDueDate"){
			attribute[item.name] = item.value;
			that.model.set(attribute);
			}
			
			if(item.name === 'taskDueDate'){
				 var date = new Date(item.value); 
				 var milliseconds = date.getTime();
				 that.model.set({taskDueDate: milliseconds});
			}
			
		});

		/*
		 * Same logic as in the user dialog, different approach for new and modified task
		 */
		if (null == this.model.taskId) {
			/*
			 * Adding user ID information read from "global" variable
			 */
			
			this.model.set({userId: assignedUser,taskAssigned: true, taskCreatedBy: currentUserId });
			
			tasks.create(this.model,{ wait: true});
		} else {
			this.model.save();
		}
		this.remove();
	}
});



users = new UserCollection();
/*
 * Fetching users from DB
 */
users.fetch({success: function() {
	userListView = new UserList({
		collection: users,
		el: $users
	});
	userListView.render();

	/*
	 * Triggering click on first user which will load its tasks
	 */
	$users.find('li:nth-child(2)').find('a').trigger('click');
}});



/*
 * Attaching to "Add User" button
 */
$('#add-user').click(function(e) {
	var view = new UserDialog({model: new User()});
	view.show();
	return false;
});

/*
 * Attaching to "Delete User" button
 */
$('#delete-user').click(function(e) {
	users.get(currentUserId).destroy();
	return false;	
});

/*
 * Attaching to "Add Task" button
 */
$('#add-task').click(function(e) {
	list_of_users=new Array();
	var view = new TaskDialog({model: new Task()});

	$.ajax({
        url: serverUrl + 'users',
        success:function(result){
            //console.log(" user list after making ajax call"+JSON.stringify(result));
           
        	
            result.forEach(function(entry) {
            	 var eachUser = {};
            	 eachUser['userId'] = entry.userId;
            	//console.log("entry "+entry.userFirstName);
            	eachUser['name'] = entry.userFirstName+" "+entry.userLastName;
            	list_of_users.push(eachUser);             	
            	
            });

            view.show(list_of_users);
        	
        }
    });
	
	return false;
	
});
