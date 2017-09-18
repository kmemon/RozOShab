package com.ibrahim.rozoshab.Main.SplashActivity.model;

import android.content.Context;

import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.CustomClasses.Constants;
import com.ibrahim.rozoshab.CustomClasses.CustomMethods;
import com.ibrahim.rozoshab.Databases.CategoryTableHelper;
import com.ibrahim.rozoshab.Databases.TaskTableHelper;
import com.ibrahim.rozoshab.Main.SplashActivity.SplashContractor;
import com.ibrahim.rozoshab.SharedPrefrences.SharedPreferenceHelper;

import java.util.ArrayList;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class SplashModel implements SplashContractor.PresenterToModel {

    SplashContractor.ModelToPresenter mPresenter ;
    Context weakContext;
    CategoryTableHelper categoryTableHelper;
    TaskTableHelper taskTableHelper;
    SharedPreferenceHelper sharedPreferenceHelper;
    ArrayList<CategoryBean> categoryBeanArrayList;
    ArrayList<TaskBean> taskBeanArrayList;




    public SplashModel(SplashContractor.ModelToPresenter mPresenter, Context weakContext){

       this.mPresenter = mPresenter;
       this.weakContext = weakContext;
       categoryTableHelper = new CategoryTableHelper(weakContext);
        taskTableHelper = new TaskTableHelper(weakContext);
       sharedPreferenceHelper = SharedPreferenceHelper.getInstance(weakContext);
        categoryBeanArrayList = new ArrayList<>();
        taskBeanArrayList = new ArrayList<>();


       iniDataBase();
    }

    void iniDataBase(){

        if(sharedPreferenceHelper.isFirstRun()){
            sharedPreferenceHelper.setFirstRun(false);
               boolean insertCat =  insertCategories();
                boolean insertTasks = insertTasks();

            mPresenter.dataInsertionComplete(insertCat && insertTasks);

        }else{
            boolean insertTasks = insertTasks();
            mPresenter.dataInsertionComplete(insertTasks);

        }

    }


    boolean insertCategories(){
        categoryBeanArrayList.add(new CategoryBean("","Salah"));
        categoryBeanArrayList.add(new CategoryBean("","Study"));
        categoryBeanArrayList.add(new CategoryBean("","Meetings"));
        categoryBeanArrayList.add(new CategoryBean("","Distribution"));
        categoryBeanArrayList.add(new CategoryBean("","Family Program"));
        categoryBeanArrayList.add(new CategoryBean("","Attendance"));
        categoryTableHelper.insertAsync(categoryBeanArrayList);



        return true;
    }

    boolean insertTasks(){

            String todayAsString = CustomMethods.getCurrentDate();
            String lastInsertedTasksDate = sharedPreferenceHelper.getTasksInsertedDate();

            if(!lastInsertedTasksDate.equals(todayAsString)){

                taskBeanArrayList.add(new TaskBean(1,0,0,"Fajar",todayAsString, Constants.TASK_TYPE_SALAH));
                taskBeanArrayList.add(new TaskBean(1,0,0,"Zohor",todayAsString,Constants.TASK_TYPE_SALAH));
                taskBeanArrayList.add(new TaskBean(1,0,0,"Asar",todayAsString,Constants.TASK_TYPE_SALAH));
                taskBeanArrayList.add(new TaskBean(1,0,0,"Maghrib",todayAsString,Constants.TASK_TYPE_SALAH));
                taskBeanArrayList.add(new TaskBean(1,0,0,"Isha",todayAsString,Constants.TASK_TYPE_SALAH));
                taskBeanArrayList.add(new TaskBean(2,0,0,"Quran",todayAsString,Constants.TASK_TYPE_STUDY));
                taskBeanArrayList.add(new TaskBean(2,0,0,"Seerat",todayAsString,Constants.TASK_TYPE_STUDY));
                taskBeanArrayList.add(new TaskBean(2,0,0,"Book",todayAsString,Constants.TASK_TYPE_STUDY));
                taskBeanArrayList.add(new TaskBean(3,0,0,"Member",todayAsString,Constants.TASK_TYPE_MEETING));
                taskBeanArrayList.add(new TaskBean(3,0,0,"Volunteer",todayAsString,Constants.TASK_TYPE_MEETING));
                taskBeanArrayList.add(new TaskBean(4,0,0,"BookDistribution",todayAsString,Constants.TASK_TYPE_DISTRIBUTION));
                taskBeanArrayList.add(new TaskBean(5,0,0,"Family Program",todayAsString,Constants.TASK_TYPE_FAMILYPROGRAM));
                taskBeanArrayList.add(new TaskBean(6,0,0,"Quran Circle",todayAsString,Constants.TASK_TYPE_ATTENDANCE));
                taskBeanArrayList.add(new TaskBean(6,0,0,"Study Circle",todayAsString,Constants.TASK_TYPE_ATTENDANCE));
                taskBeanArrayList.add(new TaskBean(6,0,0,"Members Meeting",todayAsString,Constants.TASK_TYPE_ATTENDANCE));

                boolean todaytasksinserted = taskTableHelper.insertAsync(taskBeanArrayList);

                sharedPreferenceHelper.setTodayTasks(true);
                sharedPreferenceHelper.setTasksInsertedDate(todayAsString);
            }




        return true;
    }




}
