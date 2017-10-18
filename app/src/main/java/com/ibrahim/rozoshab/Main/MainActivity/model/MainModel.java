package com.ibrahim.rozoshab.Main.MainActivity.model;

import android.content.Context;
import android.util.Log;

import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.Databases.CategoryTableHelper;
import com.ibrahim.rozoshab.Databases.TaskTableHelper;
import com.ibrahim.rozoshab.Main.MainActivity.MainActivityContractor;
import com.ibrahim.rozoshab.SharedPrefrences.SharedPreferenceHelper;

import java.util.ArrayList;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class MainModel implements MainActivityContractor.PresenterToModel  {

    Context weakContext;
    MainActivityContractor.ModelToPresenter presenter;
    CategoryTableHelper categoryTableHelper;
    SharedPreferenceHelper sharedPreferenceHelper;
    TaskTableHelper taskTableHelper;
    ArrayList<TaskBean> taskList;
    ArrayList<CategoryBean> categoryList;


    public MainModel(Context weakContext, MainActivityContractor.ModelToPresenter presenter){

        this.weakContext= weakContext;
        this.presenter = presenter;
        categoryTableHelper = new CategoryTableHelper(weakContext);
        taskTableHelper = new TaskTableHelper(weakContext);
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(weakContext);
        taskList = new ArrayList<>();
        categoryList = new ArrayList<>();
        getTodayTasks();
    }


    void getTodayTasks(){

        categoryList = categoryTableHelper.getCategories();

        for (CategoryBean categoryBean: categoryList){
            Log.i("cat",categoryBean.getCategoryName());

            for (TaskBean task : taskTableHelper.getCurrentDayTasks(Integer.parseInt(categoryBean.getCategoryId())) ){
                 taskList.add(task);
                Log.i("tasks: ",""+task.getTaskName());

            }
        }

        presenter.processData(categoryList,taskList);
    }


    @Override
    public int prepareSummaryData() {
        int qazaSalat = taskTableHelper.calculateQazaSalat();

        return qazaSalat;
    }
}
