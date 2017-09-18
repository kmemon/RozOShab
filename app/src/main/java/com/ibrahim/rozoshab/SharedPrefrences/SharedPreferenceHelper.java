package com.ibrahim.rozoshab.SharedPrefrences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class SharedPreferenceHelper {

    private Context context;
    private static SharedPreferenceHelper instance = null;
    public final static String STORAGE_NAME = "userInfo";
    private String todayDateInserted = "todayDateInserted";
    private String dateToday = "dateToday";


    private SharedPreferenceHelper(Context context) {
        this.context = context;
    }

    public static SharedPreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceHelper(context);

        }
        return instance;
    }

    public boolean isFirstRun() {

        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getBoolean("isFirstRun", true);
    }

    public void setFirstRun(boolean isFirtRun) {

        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putBoolean("isFirstRun", isFirtRun);
        sp.commit();
    }


    public void setTodayTasks(boolean isInserted) {

        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putBoolean(todayDateInserted, isInserted);
        sp.commit();
    }


    public Boolean getTodayTaskInserted() {

        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getBoolean(todayDateInserted, false);

    }

    public void setTasksInsertedDate(String date) {

        SharedPreferences.Editor sp = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit();
        sp.putString(dateToday, date);
        sp.commit();
    }


    public String getTasksInsertedDate() {

        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getString(dateToday, "");

    }

}
