package com.checkcheck.ui.task_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.checkcheck.model.Task;
import com.checkcheck.repository.TaskRepository;

import java.util.Date;
import java.util.List;

public class TaskListViewModel extends AndroidViewModel {
    TaskRepository repository;
    public TaskListViewModel(@NonNull Application application) {
        super(application);
        repository = TaskRepository.getInstance(application);

    }

    public void addTask(Task task){
        repository.addTask(task);
    }

    public LiveData<List<Task>> getTasks(){
        return repository.getTaskList();
    }

    public LiveData<List<Task>> getTasks(Date date){
        return repository.getTaskList(date);
    }


    public void updateTask(Task task) {
        repository.updateTask(task);
    }

    /*public LiveData<List<Task>> getTasksDate(Date dateAt, Date dateAt1) {

        List<Task> data = (List<Task>) repository.getTaskList();
        List<Task> output = new ArrayList<>();
        for (Task a:data) {
            if(a.getDeadline().after(dateAt) && a.getDeadline().before(dateAt1))
                output.add(a);
        }
        return (LiveData<List<Task>>) output;
    }*/
}
