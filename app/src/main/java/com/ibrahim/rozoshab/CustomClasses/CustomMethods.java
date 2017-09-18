package com.ibrahim.rozoshab.CustomClasses;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by w-t- on 18-Sep-17.
 */

public class CustomMethods {


    public static String getCurrentDate(){

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todayAsString = dateFormat.format(today);
        Log.i("dateToday",todayAsString);

        return todayAsString;
    }
    public static String getDateYesterday(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todayAsString = dateFormat.format(today);
        Log.i("dateToday",todayAsString);

        return todayAsString;
    }
    public static String getDateTommorow(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, +1);

        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todayAsString = dateFormat.format(today);
        Log.i("dateToday",todayAsString);

        return todayAsString;
    }

    public static String getAnyDate(int num){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, num);

        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todayAsString = dateFormat.format(today);
        Log.i("dateToday",todayAsString);

        return todayAsString;
    }



}
