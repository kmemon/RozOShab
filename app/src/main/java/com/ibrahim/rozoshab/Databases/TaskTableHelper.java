package com.ibrahim.rozoshab.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.CustomClasses.Constants;
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
        HashMap<String,ArrayList<TaskBean>> weekTasks = calculateWeeksData();

        int totalQazaSalat=0;

        ArrayList<TaskBean> week0 = weekTasks.get("0");
        ArrayList<TaskBean> week1 = weekTasks.get("1");
        ArrayList<TaskBean> week2 = weekTasks.get("2");
        ArrayList<TaskBean> week3 = weekTasks.get("3");
        ArrayList<TaskBean> week4 = weekTasks.get("4");


        if (week0!=null)
        totalQazaSalat+= countQazaSalatinWeek(week0);
        if (week1!=null)
        totalQazaSalat+= countQazaSalatinWeek(week1);
        if (week2!=null)
        totalQazaSalat+= countQazaSalatinWeek(week2);
        if (week3!=null)
        totalQazaSalat+= countQazaSalatinWeek(week3);
        if (week4!=null)
        totalQazaSalat+= countQazaSalatinWeek(week4);


        return totalQazaSalat;
    }

    private int countQazaSalatinWeek(ArrayList<TaskBean> weekTasks){
        int countQazaSalatInweek =0;

        for (TaskBean task:weekTasks) {
            if (task.getTaskType()== Constants.TASK_TYPE_SALAH){
                if (task.getStatus() == 0) {
                    countQazaSalatInweek++;
                }
            }
        }

        return countQazaSalatInweek;

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


        if (monthData.size()>29){
            for (int i = 30; i <monthData.size(); i++) {
                monthData.remove(i);
            }
        }

        if (monthData.size()>20){
            weekData = (ArrayList<String>) monthData.subList(21,monthData.size());
            weekWiseMap.put("4",extractWeakTasks(weekData));

        } if(monthData.size()>13 && monthData.size()>19){
            weekData = (ArrayList<String>) monthData.subList(14,20);
            weekWiseMap.put("3",extractWeakTasks(weekData));
        }
        if(monthData.size()>6 && monthData.size()>12){
            weekData = (ArrayList<String>) monthData.subList(7,13);
            weekWiseMap.put("2",extractWeakTasks(weekData));
        }
        if(monthData.size()>0 && monthData.size()>5){
            weekData = (ArrayList<String>) monthData.subList(0,6);
            weekWiseMap.put("1",extractWeakTasks(weekData));
        }
        if (monthData.size()>0 && monthData.size()<6){

//            weekData = (ArrayList<String>) monthData.subList(0,monthData.size());
//            weekWiseMap.put("0",extractWeakTasks(weekData));

            for (int i=0; i< monthData.subList(0,monthData.size()).size(); i++){

                Log.i("lis",""+monthData.subList(0,monthData.size()).get(i));
            }

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
