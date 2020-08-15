# CheckCheck - A TODO list application

The application aims to increase the productivity of the user by organizing daily and monthly tasks into doable subtasks and by providing a clean, smooth and responsive User Interface, to ensure the user doesn’t waste time setting up the application and focuses on doing the tasks

It uses the following libraries:
1. AndroidX jetpack
2. Room persistence library
3. HorizontalCalender – devs.mulham.horizontalcalender
4. FirebaseAuth
5. Firebase Database
6. AndroidX LiveData

This application was built as part of the Java Programming course at SIT by [`safeer2978`](https://github.com/safeer2978) and [`imNachi`](https://github.com/imNachi). This is an android app written in java, thus an ideal choice for a capstone project for illustrating JAVA skills.

### Features

1. Log in with Google.
3. Create Tasks and add Subtasks to them.
2. Set Reminders and Notifications.
3. Sync Tasks across devices.


## Application Walkthrough:

Logging in with Your Gmail account helps in seamless cloud synchronization across all your devices.
After Login it brings us to the Home Page.

![alt text](https://github.com/safeer2978/CheckCheck/blob/master/Diagrams/main.png)

### Home Page:
The Home page Displays all the Pending and Completed Tasks Of That Particular Day. There is also an option to Add Subtasks to your main task which Helps in better management of your tasks.

### Calendar Page:
The Calendar Page Helps You to organize your tasks and Plan your Tasks Properly.
You can assign a task to any particular Date with a deadline. The App will notify you accordingly.

The following images will guide you through the functionality of the App.

![alt text](https://github.com/safeer2978/CheckCheck/blob/master/Diagrams/addTasks.png)

![alt text](https://github.com/safeer2978/CheckCheck/blob/master/Diagrams/subTasks.png)

### The Model: 
The Checklist application has only one fundamental unit - The TASK. Naturally, we build the task model. The main attributes we set of a task was title, description, deadline, check, and subtasks. The subtask was originally an ArrayList of Task itself, though as mentioned in challenges, we had to change it later.

![alt text](https://github.com/safeer2978/CheckCheck/blob/master/Diagrams/model.png)
Our class initially had attributes, such as the date and a list of task objects which are subtasks, which are POJOs. Room persistence library doesn’t directly allow the storage of objects, so we ended up making type converters of them. For Date class, it was easy to find one online, though, for the subtask list, we ended up making a separate model for that. Thus we then had to make a separate Typeconverter, which converts the list of subtask objects to a JSON string. 


#### Room Persistence:

The Room needs a Dao for each of its entities. So, we have written a Task Dao which allows us to run queries on the task table. 
The Dao is basically an interface that fires SQL queries through Room. this is used in the repository to call these functions.

#### Repository and Firebase:

Finally, we had to make all the connections to the repository. Since the firebase database code is short, we integrated it directly into the repository. So, with this, we can finally start implementing the view models and the activities. 

#### Login and Firebase Authentication:

Besides the model setup, we also completed the authentication module using the firebase auth model. This allows user to authenticate themselves using google sign in which makes use of OAuth2.0


### User Interface:
The UI consists of two activities and three fragments along with four dialog boxes. The following section explains its code and functionalities.

#### Start Activity
This is the first activity that starts when the application is launched. The purpose of this activity is to authenticate the user of the app. For this purpose, the app uses the sign in with google option. This is implemented using FirebaseAuth. This activity follows the official firebase documentation for the implementation. 
The `onCreate()` function creates an instance of GoogleSignInOptions and binds the sign-in button to the sign-in function.
The `signin()` function calls the `signinClientIntent` which then returns the status of login along with the account. The account is then passed to firebase auth and thus the user is authenticated. The UI then switches to Main Activity.

#### Main Activity
The main activity is a simple activity which only implements the bottomNavigationView. The navigation has 3 pages which are the following fragments.

##### Task List Fragment
Tasklist Fragment is the page that navigates to the calendar page. This fragment features a Horizontal calendar, a Recycler view, and a floating Action button. It makes use of `tasklistViewModel` for its data handling.
The `onCreateView` initializes the view component mentioned.
The `onViewCreated` method first initializes the calendar, the recycler view, and the ViewModel.
The recycler view uses the TaskList Adapter for its data. The method then sets an `onSelectListener` on the calendar which gets the date selected. The function then calls the view model to get a list of tasks for that date then load it to the adaptor.
The floating action button initialized and we set an `onclicklistener` on that. The `onclick` function calls the `addDialog` fragment. This Fragment implements the `addDialogListener` which then uses the ViewModel to add a task to the datastore. 
This fragment also implements `TaskListAdapterListene`r which calls the ViewModel to update the task changes like subtask adds or checks. 

![alt text](https://github.com/safeer2978/CheckCheck/blob/master/Diagrams/taskList.png)

##### Home Fragment

Home fragment is very similar to the task list fragment. It has two recycler views. It also makes use of the `tasklistViewModel` and the `TasklistAdapter`.
The `onViewCreated` method initializes the adapters for both the recycleler views and initializes the viewmodel. The viewmodel then updates the data on the adapters.

##### Settings Fragment

Settings Fragment consists of only two switches and a button.
The switches are for toggling notifications and firebase Sync. These switches toggle the flags stored as SharedPreferences so there are no persistence issues with this data. These SharedPreferences are then used by the `notificationPublisher` and the repository so as to make the appropriate changes.

### Dialog boxes

#### Add Dialog

This dialog box is used to add a task to the tasklist. The dialog consists of two Text Fields for the title and description respectively. The dialog also has a textview to select the deadline for the task. The textView has an `onCLikclistener` which then calls the Time and Date dialogs. This fragment implements the `timelistner` and the date listener. These are used to set a deadline date and time. The `onclick` then uses a DialogListener object to call the `addTask` method which is implemented in the Tasklist Fragment.

#### Time Picker and Date Picker

These are simple dialog fragments implemented as specified in the android documentation.
Notification Publisher
This is used to schedule the notifications Intents. The UI consist of only a title and the message. Its called whenever a task is added. The toggle switch in the settings fragment and cancel or add the pending intents for the notifications.