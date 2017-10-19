package com.ibrahim.rozoshab.Bean;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class TaskBean {

    private int taskId;
    private int catId;
    private int subTask;
    private int status;



    public int getTaskType() {
       return taskType;




    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    private int taskType;
    private String taskName,taskDate;


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getSubTask() {
        return subTask;
    }

    public void setSubTask(int subTask) {
        this.subTask = subTask;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public TaskBean(int catId, int subTask, int status, String taskName, String taskDate,int taskType) {
        this.catId = catId;
        this.subTask = subTask;
        this.status = status;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskType = taskType;
    }

    public TaskBean(){


    }



}




