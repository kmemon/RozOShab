package com.ibrahim.rozoshab.Main.MainActivity.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.CustomClasses.Constants;
import com.ibrahim.rozoshab.Databases.TaskTableHelper;
import com.ibrahim.rozoshab.Main.MainActivity.MainActivityContractor;
import com.ibrahim.rozoshab.Main.MainActivity.presenter.MainPresenter;
import com.ibrahim.rozoshab.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MainActivityContractor.PresenterToView{

    private TextView mTextMessage;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        presenter= new MainPresenter(this);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };


    @Override
    public void navigateActivity(Context context, Class<?> clss) {

    }
//as/// aasas
    @Override
    public Activity getActivityContext() {
        return MainActivity.this;
    }

    @Override
    public void populateData(ArrayList<CategoryBean> categoryList, ArrayList<TaskBean> tasksList) {

        for (CategoryBean categoryBean: categoryList){
            mTextMessage.setText(mTextMessage.getText()+categoryBean.getCategoryName()+"\n");
            mTextMessage.setTextSize(20);
            for (TaskBean task : tasksList ){

                mTextMessage.setText(mTextMessage.getText()+task.getTaskName()+"\n");
                mTextMessage.setTextSize(12);

            }
        }
    }
}
