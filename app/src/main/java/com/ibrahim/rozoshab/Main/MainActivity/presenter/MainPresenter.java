package com.ibrahim.rozoshab.Main.MainActivity.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.compat.BuildConfig;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.Main.MainActivity.MainActivityContractor;
import com.ibrahim.rozoshab.Main.MainActivity.model.MainModel;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class MainPresenter implements MainActivityContractor.ViewToPresenter,
        MainActivityContractor.ModelToPresenter {

    WeakReference<MainActivityContractor.PresenterToView> mView;
    Context weakContext;
    MainModel model;


    public MainPresenter(MainActivityContractor.PresenterToView view){

        mView = new WeakReference<>(view);
        weakContext = mView.get().getActivityContext();
        model = new MainModel(weakContext,this);
        showDebugDBAddressLogToast(weakContext);

    }

    public static void showDebugDBAddressLogToast(Context context) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                Log.i("------", "ip_address: " + value);
            } catch (Exception ignore) {

            }
        }
    }


    @Override
    public void onOptionItemSelected(int item) {

    }

    @Override
    public void viewCreated() {

    }

    @Override
    public void viewResumed() {

    }

    @Override
    public void viewbuttonClicked(int id) {

    }

    @Override
    public void passIntentData(int moduleId, String visitID, String moduleName, String storeDataVariable) {

    }

    @Override
    public void dispatchIntentCallBack(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void dispatchItemClicked(AdapterView<?> parent, View v, int position, long id) {

    }

    @Override
    public void processData(ArrayList<CategoryBean> categoryList, ArrayList<TaskBean> tasksList) {
            mView.get().populateData(categoryList,tasksList);

    }
}



