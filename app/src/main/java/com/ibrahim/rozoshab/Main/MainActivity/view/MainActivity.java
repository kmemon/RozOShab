package com.ibrahim.rozoshab.Main.MainActivity.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ibrahim.rozoshab.Main.MainActivity.MainActivityContractor;
import com.ibrahim.rozoshab.Main.MainActivity.presenter.MainPresenter;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.MainAdapter;
import com.ibrahim.rozoshab.R;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityContractor.PresenterToView{

    private TextView mTextMessage;
    MainPresenter presenter;
    BottomNavigationView navigation ;
    //MaterialRadioGroup radioGroup;
    MaterialRadioButton materialRadioButton;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
       // radioGroup = (MaterialRadioGroup) findViewById(R.id.radiogroupfajar);
        recyclerView = (RecyclerView) findViewById(R.id.mainRecycler);


        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


//        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
//
//                materialRadioButton = (MaterialRadioButton) findViewById(checkedId);
//
//
//                if (checkedId == R.id.radioonefajar){
//                    Toast.makeText(MainActivity.this, "check ONe change", Toast.LENGTH_SHORT).show();
//
//                }else if (checkedId == R.id.radiotwofajar){
//                    Toast.makeText(MainActivity.this, "check Two change" , Toast.LENGTH_SHORT).show();
//
//
//                }
//
//
//            }
//        });


        iniReportScreen();
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        presenter= new MainPresenter(this);
        presenter.viewCreated();

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
    public void populateData(ArrayList<Object> categoryDataBeen, MainAdapter mainAdapter) {
        recyclerView.setAdapter(mainAdapter);



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
