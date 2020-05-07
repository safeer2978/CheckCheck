package com.checkcheck.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity(tableName = "task_data")
public class Task implements Serializable {
    //attributes

    @SerializedName("title")
    @Expose
    @NonNull
    @PrimaryKey
    private String title;

   /* @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
*/
    public Task() {
    }

 /*   @SerializedName("title")
    @Expose
    @NonNull
    private String id;*/

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("check")
    @Expose
    private boolean check =false;

   /* @SerializedName("creation_date")
    @Expose
    @Ignore
    @TypeConverters({DateTypeConverter.class})
    private Date creationDate;
*/
    @SerializedName("deadline")
    @Expose
    @TypeConverters({DateTypeConverter.class})
    private Date deadline; //TODO use timestamp to assign these
/*

    @SerializedName("category")
    @Expose
    private String category;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @SerializedName("priority")
    @Expose
    private int priority;
*/

    @SerializedName("subTasks")
    @Expose
    @TypeConverters(SubTaskTypeConverter.class)
    public List<SubTask> subTasks = new ArrayList<>();


  @Ignore
    public Task(@NotNull String title, String description, Date deadline){
        this.title = title;
        this.deadline = deadline;
        this.description = description;
    }


    @TypeConverters(SubTaskTypeConverter.class)
    public List<SubTask> getSubTasks() {
        return subTasks;
    }
    @TypeConverters(SubTaskTypeConverter.class)
    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks=  subTasks;
    }




  /*  public Task(@NotNull String title,  Date deadlineDate) {
        this.title = title;
        //this.creationDate = creationDate;
        this.deadline = deadlineDate;
    }*/

    public void addSubTask(SubTask task){
        subTasks.add(task);
    }
/*
    public boolean hasSubTasks(){
        return subTasks.size() > 0;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

   /* @NotNull
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(@NotNull Date creationDate) {
        this.creationDate = creationDate;
    }
*/
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
/*
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }*/
}
