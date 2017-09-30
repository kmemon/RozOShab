package com.ibrahim.rozoshab.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.CustomClasses.CustomMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class TaskTableHelper {

    Context context;
    SQLiteDatabase db;

    public TaskTableHelper(Context context){
        this.context = context;
        db = DatabaseHelper.getInstance(context).getWritableDatabase();

    }


    public boolean insert (ArrayList<TaskBean> taskBeanArrayList){

        for(TaskBean task:taskBeanArrayList) {

            ContentValues cv = new ContentValues();

            cv.put(DatabaseHelper.COLUMN_CAT_ID,task.getCatId());
            cv.put(DatabaseHelper.COLUMN_SUB_TASK,task.getSubTask());
            cv.put(DatabaseHelper.COLUMN_TASK_DATE,task.getTaskDate());
            cv.put(DatabaseHelper.COLUMN_CATEGORY_NAME, task.getTaskName());
            cv.put(DatabaseHelper.COLUMN_TASK_TYPE,task.getTaskType());
            cv.put(DatabaseHelper.COLUMN_CATEGORY_STATUS, task.getStatus());

            try {
                db.insert(DatabaseHelper.TABLE_TASKS, null, cv);
                Log.i("CategoryTableHelper","Inserting Categories");

            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public ArrayList<TaskBean> getTaskByCategory (int catId){

        ArrayList<TaskBean> taskList =new ArrayList<>();
        String queryGetTasks ="select * from "+DatabaseHelper.TABLE_TASKS
                +" where "+DatabaseHelper.COLUMN_CAT_ID +" = "+catId;
        Cursor cursor;
        cursor = db.rawQuery(queryGetTasks,null);



            while (cursor.moveToNext()){

                TaskBean task = new TaskBean(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CAT_ID)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUB_TASK)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_STATUS)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_DATE)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_TYPE)));

                task.setTaskId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_ID)));

                taskList.add(task);



        }
        cursor.close();
        return taskList;
    }
    public ArrayList<TaskBean> getCurrentDayTasks (int catId){

        ArrayList<TaskBean> taskList =new ArrayList<>();
        String queryGetTasks ="select * from "+DatabaseHelper.TABLE_TASKS
                +" where "+DatabaseHelper.COLUMN_CAT_ID +" = "+catId + " AND " +
                DatabaseHelper.COLUMN_TASK_DATE +" = '"+ CustomMethods.getCurrentDate()+ "'";
        Log.i("query",queryGetTasks);


        Cursor cursor;
        cursor = db.rawQuery(queryGetTasks,null);



        while (cursor.moveToNext()){

            TaskBean task = new TaskBean(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CAT_ID)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUB_TASK)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_STATUS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_DATE)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_TYPE)));

            task.setTaskId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_ID)));


            taskList.add(task);
        }
        cursor.close();
        return taskList;
    }

    public ArrayList<TaskBean> getTasksByDate (int catId,String date){

        ArrayList<TaskBean> taskList =new ArrayList<>();
        String queryGetTasks ="select * from "+DatabaseHelper.TABLE_TASKS
                +" where "+DatabaseHelper.COLUMN_CAT_ID +" = "+catId + " AND " +
                DatabaseHelper.COLUMN_TASK_DATE +" = "+ date;
        Log.i("query",queryGetTasks);


        Cursor cursor;
        cursor = db.rawQuery(queryGetTasks,null);



        while (cursor.moveToNext()){

            TaskBean task = new TaskBean(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CAT_ID)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUB_TASK)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_STATUS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_DATE)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_TYPE)));

            task.setTaskId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_ID)));


            taskList.add(task);



        }
        cursor.close();
        return taskList;
    }


    public boolean insertAsync(ArrayList<TaskBean> taskList){

        final boolean[] res = {false};

        Disposable disposable = Observable.just(insert(taskList)).
                subscribeOn(Schedulers.io()).subscribe(aBoolean ->{
            Log.i("CategoryTableHelper","CategoryAdded Response"+aBoolean);
            res[0] = aBoolean;

        });


        return res[0];
    }

    public boolean updateTaskProgress(TaskBean task){

        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.COLUMN_TASK_STATUS,task.getStatus());
        cv.put(DatabaseHelper.COLUMN_SUB_TASK, task.getSubTask());

        if(!db.inTransaction()){
            db.update(DatabaseHelper.TABLE_TASKS,cv,DatabaseHelper.COLUMN_TASK_ID + " = " + task.getTaskId() ,null);
        }


        return false;
    }

    public int calculateQazaSalat(){


        return 0;
    }

    public HashMap<String,ArrayList<TaskBean>> calculateWeeksData(){

        Cursor cursor;
        int weeks;
        int days;
        HashMap<String,ArrayList<TaskBean>> weekWiseMap = new HashMap<>();
        ArrayList<String> monthData = new ArrayList<>();
        ArrayList<String> weekData = new ArrayList<>();
        String queryWeeks = "select distinct date from tbl_tasks;";
        cursor = db.rawQuery(queryWeeks,null);
        days = cursor.getCount();

            while (cursor.moveToNext()){
                monthData.add(cursor.getString(cursor.getColumnIndex("date")));
        }

        if (monthData.size()>20){
            weekData = (ArrayList<String>) monthData.subList(20,monthData.size());
            weekWiseMap.put("1",extractWeakTasks(weekData));


        }else if(monthData.size()>13){

        }
        cursor.close();
        return weekWiseMap;
    }

    private ArrayList<TaskBean> extractWeakTasks(ArrayList<String> weakDates){

        Cursor cursor = null;
        String queryWeeks;
        ArrayList<TaskBean> weektasksList = new ArrayList<>();

        for (String date: weakDates) {
            queryWeeks = "select * from tbl_tasks where date = " +date;
            cursor = db.rawQuery(queryWeeks,null);

            while (cursor.moveToNext()){

                TaskBean task = new TaskBean(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CAT_ID)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUB_TASK)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_STATUS)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_DATE)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_TYPE)));

                task.setTaskId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TASK_ID)));
                weektasksList.add(task);



            }


        }
        cursor.close();
        return weektasksList;
    }

//    public boolean updateTaskProgress(int cat,String date,TaskBean task){
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(DatabaseHelper.COLUMN_TASK_STATUS,task.getStatus());
//        cv.put(DatabaseHelper.COLUMN_SUB_TASK, task.getSubTask());
//
//        if(!db.inTransaction()){
//            db.update(DatabaseHelper.TABLE_TASKS,cv,DatabaseHelper.COLUMN_CAT_ID + " = " + cat +
//                    " AND " + DatabaseHelper.COLUMN_TASK_DATE + " = " + date ,null);
//        }
//
//
//        return false;
//    }


}
