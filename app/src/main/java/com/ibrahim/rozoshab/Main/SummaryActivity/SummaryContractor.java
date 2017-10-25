package com.ibrahim.rozoshab.Main.SummaryActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.MainAdapter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;

/**
 * Created by w-t- on 22-Oct-17.
 */

public interface SummaryContractor {

    //Through which Presenter communicate to view , View should implement these Methods
    interface PresenterToView {
        void navigateActivity(Context context, Class<?> clss);

        Activity getActivityContext();

        void initNisabScreen();

        void iniSummaryScreen();

        void iniReportScreen();

        void populateData(ArrayList<Object> categoryDataBeen, MainAdapter mainAdapter);

        void showDatePicker();

        void navigateActivity(Intent intent);

//        void showProgressDialog(String message);
//
//        void dismissDialog();
//
//        void showDialog(String title, String message);
//
//        void dismissProgressDialog();
//
//        void setImage(Bitmap bmp);
//
//        void setViews(Typeface typeFace);
//
//        void showToast(String message, int length);
//

//
//        void setInstructionSet();
//
//        void getIntentData();
//
//        void includeImageLayout();
//
//        void dispatchTakePictureIntent(Intent intentToLaunch, int REQUEST_CODE);
//
//        void setImageBitmap(Bitmap bitmap);
//
//        void finishActivity();
//
//        void showImageError(String Message, String erroMessage);


    }

    //Through which View communicate to presenter , Presenter should implement these Methods
    interface ViewToPresenter {
        void onOptionItemSelected(int item);

        void viewCreated();

        void viewResumed();

        void viewbuttonClicked(int id);

        void passIntentData(int moduleId, String visitID, String moduleName, String storeDataVariable);

        void dispatchIntentCallBack(int requestCode, int resultCode, Intent data);

        void dispatchItemClicked(AdapterView<?> parent, View v, int position, long id);

        void nisabScreenSelected();

        void summaryScreenSelected();

        void reportScreenSelected();

        RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int viewType);

        void bindViewHolder(RecyclerView.ViewHolder holder, int position);

        int getItemCount();

        int getItemViewType(int position);

        void onOptionItemSelected(MenuItem item);

        void onDateSelected(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);



    }


    //Through which Presenter communicate to model , Model should implement these Methods
    interface PresenterToModel {

        int prepareSummaryData();

        void saveData(ArrayList<Object> categoryDataBeanArrayList);

        void getTasks(String date);


//        String getRealPathFromUri(Uri uri);
//
//        void setVisitComplete();
//
//        void setImageData(int position);
//
//        void processBitmapImage(Bitmap bitmap, int imageIndex, String imagePath);

    }

    //Through which Model communicate to presenter , Presenter should implement these Methods
    interface ModelToPresenter {

        void processData(ArrayList<CategoryBean> categoryList, ArrayList<TaskBean> tasksList);

//
//        void processSavedData(Bitmap bmp, String imageString);
//
//        void setExistingImageName(String imageName);
//
//        void dataInserted();
//
//        void reportImageError(Exception e);
//
//        void reportDataError();


    }




}
