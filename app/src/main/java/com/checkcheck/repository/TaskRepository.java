package com.checkcheck.repository;

import android.app.Application;
import android.app.ListActivity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.checkcheck.db.TaskDao;
import com.checkcheck.db.TaskDatabase;
import com.checkcheck.model.DateTypeConverter;
import com.checkcheck.model.SubTask;
import com.checkcheck.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;

//import static StartActivity.mAuth;
//import static StartActivity.user;

public class TaskRepository {

    private DatabaseReference databaseTable;
    private static final String TAG = "TaskRepository";
    private TaskDao taskDao;
    public static boolean flag_fire=true;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    private static TaskRepository REPOSITORY;

    private TaskRepository(Application application) {
        if(user!=null) {
            String userId = user.getUid();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            databaseTable = FirebaseDatabase.getInstance().getReference("tasks").child(userId);
        }

        Log.d("FIRE","THIS IS "+user.getDisplayName());
        taskDao = TaskDatabase.getDatabase(application).taskDao();
        if(flag_fire)
            getFirebaseData();
    }

    public void addTask(Task task){
        //String id = databaseTable.push().getKey();
        //assert id != null;
        //task.setId(id);
        if(flag_fire)
            databaseTable.child(task.getTitle()).setValue(task);

        Log.d(TAG, "addTask: "+taskDao.addTask(task));
    }

    public void getFirebaseData(){
        final List<Task> tasks = new ArrayList<>();
        databaseTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Task task = postSnapshot.getValue(Task.class);
                    //tasks.add(task);
                    taskDao.addTask(task);
                }
                //taskDao.addTasks(tasks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    static public TaskRepository getInstance(Application application){
        if(REPOSITORY == null){
            REPOSITORY = new TaskRepository(application);
        }
        return REPOSITORY;
    }

    public LiveData<List<Task>> getTaskList(){
        return taskDao.getTasks();
    }

    public LiveData<List<Task>> getTaskList(Date date){
        return taskDao.getTasks(date);
    }

    public Completable updateTasks(List<Task> tasks){
        return taskDao.updateTasks(tasks);
    }

    public void updateTask(Task task) {
        if(flag_fire)
            databaseTable.child(task.getTitle()).setValue(task);
        taskDao.updateTask(task);
    }

    public LiveData<List<Task>> getTaskDate(Date dateAt, Date dateAt1) {
       return taskDao.getTaskDate(DateTypeConverter.dateToTimestamp(dateAt),DateTypeConverter.dateToTimestamp(dateAt1));
    }
}
