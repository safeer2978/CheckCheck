package com.checkcheck.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.checkcheck.model.DateTypeConverter;
import com.checkcheck.model.Task;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;


@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addTask(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Object addTasks(List<Task> task);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Task task);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable updateTasks(List<Task> task);

    @Query("select * from task_data")
    LiveData<List<Task>> getTasks();


    @Query("select * from task_data where deadline like '%' || :deadline || '%'")
    @TypeConverters({DateTypeConverter.class})
    LiveData<List<Task>> getTasks(Date deadline);

    @Query("select * from task_data where deadline > '%' || :yesterday || '%' AND deadline < '%' || :tomorrow || '%'")

    LiveData<List<Task>> getTaskDate(long yesterday,long tomorrow);



}
