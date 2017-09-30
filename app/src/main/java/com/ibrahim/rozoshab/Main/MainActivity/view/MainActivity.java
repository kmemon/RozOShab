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
import android.widget.Toast;

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
    BottomNavigationView navigation ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
iniReportScreen();
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        presenter= new MainPresenter(this);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setContentView(R.layout.activity_main);
                    //iniReportScreen();
                    Toast.makeText(MainActivity.this, "report", Toast.LENGTH_SHORT).show();
                    presenter.reportScreenSelected();
                    break;

                case R.id.navigation_dashboard:
                    setContentView(R.layout.activity_main_test);
                    Toast.makeText(MainActivity.this, "nisab", Toast.LENGTH_SHORT).show();

                   // initNisabScreen();
                    presenter.nisabScreenSelected();
                    break;

                case R.id.navigation_notifications:
                    setContentView(R.layout.activity_main_test2);
                    Toast.makeText(MainActivity.this, "summary", Toast.LENGTH_SHORT).show();

                    //iniSummaryScreen();

                    presenter.summaryScreenSelected();

                    break;

            }
            return false;
        }

    };


    @Override
    public void navigateActivity(Context context, Class<?> clss) {

    }

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
    @Override
    public void initNisabScreen(){

         navigation = (BottomNavigationView) findViewById(R.id.navigationTest);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mTextMessage = (TextView) findViewById(R.id.messagetest);
        mTextMessage.setText(R.string.title_notifications);

    }
    @Override
    public void iniSummaryScreen(){

         navigation = (BottomNavigationView) findViewById(R.id.navigationTestTwo);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mTextMessage = (TextView) findViewById(R.id.messagetest2);
        mTextMessage.setText(R.string.title_dashboard);

    }

    @Override
    public void iniReportScreen(){

         navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mTextMessage.setText(R.string.title_home);

    }

}
