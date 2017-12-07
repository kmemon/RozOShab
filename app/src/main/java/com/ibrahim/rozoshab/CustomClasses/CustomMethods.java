package com.ibrahim.rozoshab.CustomClasses;

import android.util.Log;

import com.ibrahim.rozoshab.Bean.TaskBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by w-t- on 18-Sep-17.
 */

public class CustomMethods {


    public static String getCurrentDate(){

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayAsString = dateFormat.format(today);
        Log.i("dateToday",todayAsString);

        return todayAsString;
    }

    public static int getCurrentYear(){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        return year;
    }
    public static String getMonthName (int month){

        switch (month){

            case 1:return "Jan";
            case 2:return "Feb";
            case 3:return "Mar";
            case 4:return "April";
            case 5:return "May";
            case 6:return "June";
            case 7:return "July";
            case 8:return "Aug";
            case 9:return "Sep";
            case 10:return "Oct";
            case 11:return "Nov";
            case 12:return "Dec";
            default: return "";

        }

    }




    public static String getDateYesterday(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayAsString = dateFormat.format(today);
        Log.i("dateToday",todayAsString);

        return todayAsString;
    }
    public static String getDateTommorow(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, +1);

        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayAsString = dateFormat.format(today);
        Log.i("dateToday",todayAsString);

        return todayAsString;
    }

    public static String getAnyDate(int num){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, num);

        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayAsString = dateFormat.format(today);
        Log.i("dateToday",todayAsString);

        return todayAsString;
    }


    public static ArrayList<TaskBean> getRawTasksByDate(String date){
        ArrayList<TaskBean> taskBeanArrayList;
        taskBeanArrayList = new ArrayList<>();
        String todayAsString = date;
        //ak or chees dekho

//        TaskBean task1 = new TaskBean(1,0,0,"Fajar",todayAsString, Constants.TASK_TYPE_SALAH,"","");
//       taskBeanArrayList.add(task1);
        taskBeanArrayList.add(new TaskBean(1,0,0,"Fajar",todayAsString, Constants.TASK_TYPE_SALAH,"",""));
        taskBeanArrayList.add(new TaskBean(1,0,0,"Zohor",todayAsString,Constants.TASK_TYPE_SALAH,"",""));
        taskBeanArrayList.add(new TaskBean(1,0,0,"Asar",todayAsString,Constants.TASK_TYPE_SALAH,"",""));
        taskBeanArrayList.add(new TaskBean(1,0,0,"Maghrib",todayAsString,Constants.TASK_TYPE_SALAH,"",""));
        taskBeanArrayList.add(new TaskBean(1,0,0,"Isha",todayAsString,Constants.TASK_TYPE_SALAH,"",""));
        taskBeanArrayList.add(new TaskBean(2,0,0,"Quran",todayAsString,Constants.TASK_TYPE_STUDY,"",""));
        taskBeanArrayList.add(new TaskBean(2,0,0,"Seerat",todayAsString,Constants.TASK_TYPE_STUDY,"",""));
        taskBeanArrayList.add(new TaskBean(2,0,0,"Book",todayAsString,Constants.TASK_TYPE_STUDY,"",""));
        taskBeanArrayList.add(new TaskBean(3,0,0,"Member",todayAsString,Constants.TASK_TYPE_MEETING,"",""));
        taskBeanArrayList.add(new TaskBean(3,0,0,"Volunteer",todayAsString,Constants.TASK_TYPE_MEETING,"",""));
        taskBeanArrayList.add(new TaskBean(4,0,0,"BookDistribution",todayAsString,Constants.TASK_TYPE_DISTRIBUTION,"",""));
        taskBeanArrayList.add(new TaskBean(5,0,0,"Family Program",todayAsString,Constants.TASK_TYPE_FAMILYPROGRAM,"",""));
        taskBeanArrayList.add(new TaskBean(6,0,0,"Quran Circle",todayAsString,Constants.TASK_TYPE_ATTENDANCE,"",""));
        taskBeanArrayList.add(new TaskBean(6,0,0,"Study Circle",todayAsString,Constants.TASK_TYPE_ATTENDANCE,"",""));
        taskBeanArrayList.add(new TaskBean(6,0,0,"Members Meeting",todayAsString,Constants.TASK_TYPE_ATTENDANCE,"",""));

        return taskBeanArrayList;
    }


}
