package com.ibrahim.rozoshab.Main.SplashActivity.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.ibrahim.rozoshab.Main.MainActivity.view.MainActivity;
import com.ibrahim.rozoshab.Main.SplashActivity.SplashContractor;
import com.ibrahim.rozoshab.Main.SplashActivity.model.SplashModel;

import java.lang.ref.WeakReference;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class SplashPresenter implements SplashContractor.ViewToPresenter,SplashContractor.ModelToPresenter {


    Context weakContext;
    WeakReference<SplashContractor.PresenterToView> mView;
    SplashModel model ;

   public SplashPresenter(SplashContractor.PresenterToView view){

       mView = new WeakReference<>(view);
       this.weakContext = mView.get().getActivityContext();
       model = new SplashModel(this,weakContext);


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
    public void dataInsertionComplete(boolean success) {

        if (success){

            mView.get().navigateActivity(weakContext, MainActivity.class);
            Log.i("succes","Data initialized");

        }else{
            mView.get().showToast("Data Failed Kindly Restart Application");

        }
    }
}
