package com.ibrahim.rozoshab.Main.SplashActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by w-t- on 17-Sep-17.
 */

public interface SplashContractor  {


    //Through which Presenter communicate to view , View should implement these Methods
    interface PresenterToView {
        void navigateActivity(Context context, Class<?> clss);

        Activity getActivityContext();

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
        void showToast(String message);
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

    }


    //Through which Presenter communicate to model , Model should implement these Methods
    interface PresenterToModel {

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


        void dataInsertionComplete(boolean success);

//        void shouldIncludeImage();
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
