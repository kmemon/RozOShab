package com.ibrahim.rozoshab.Main.MainActivity.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ibrahim.rozoshab.Main.MainActivity.MainActivityContractor;
import com.ibrahim.rozoshab.Main.MainActivity.presenter.MainPresenter;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.MainAdapter;
import com.ibrahim.rozoshab.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.soulwolf.widget.materialradio.MaterialRadioButton;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        MainActivityContractor.PresenterToView,DatePickerDialog.OnDateSetListener{

    private TextView mTextMessage;
    MainPresenter presenter;
    BottomNavigationView navigation ;
    //MaterialRadioGroup radioGroup;
    MaterialRadioButton materialRadioButton;
    RecyclerView recyclerView;
    DatePickerDialog datePickerDialog;
    int year =2017, month =0, day =1;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar =  Calendar.getInstance();

        year =  calendar.get(Calendar.YEAR);
        month =  calendar.get(Calendar.MONTH);
        day =  calendar.get(Calendar.DAY_OF_MONTH);




        showDebugDBAddressLogToast();

        mTextMessage = (TextView) findViewById(R.id.message);
       // radioGroup = (MaterialRadioGroup) findViewById(R.id.radiogroupfajar);
        recyclerView = (RecyclerView) findViewById(R.id.mainRecycler);



        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar));








        iniReportScreen();
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        presenter= new MainPresenter(this);
        presenter.viewCreated();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        presenter.onOptionItemSelected(item);

        return true;
    }

    public void showDebugDBAddressLogToast() {
       // if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);

                Toast.makeText(MainActivity.this, ""+value, Toast.LENGTH_SHORT).show();

                Log.i("------", "ip_address: " + value);
            } catch (Exception ignore) {
                    ignore.printStackTrace();
            }
//        }else {
//            Toast.makeText(MainActivity.this, "debug not", Toast.LENGTH_SHORT).show();
//
//        }
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
    public void showDatePicker() {
        datePickerDialog = DatePickerDialog.newInstance(MainActivity.this, year, month, day);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(Color.parseColor("#009688"));
        datePickerDialog.setTitle("Select Date From DatePickerDialog");
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void navigateActivity(Intent intent) {
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.viewbuttonClicked(0);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        presenter.onDateSelected(view,year,monthOfYear,dayOfMonth);
    }
    @Override
    public String showSpinnerDialog(ArrayList<String> data1,ArrayList<String> data2,String title, final int position) {

        String slectItem = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        View mview = getLayoutInflater().inflate(R.layout.spinner_layout, null);

        final Spinner spinner = (Spinner) mview.findViewById(R.id.dialogapinner);
        final Spinner spinner2 = (Spinner) mview.findViewById(R.id.dialogapinnerTwo);



        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
        ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2);


        spinner.setAdapter(stringArrayAdapter);
        spinner2.setAdapter(stringArrayAdapter2);



        builder.setView(mview);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedItem1 = spinner.getSelectedItem().toString();
                String selectedItem2 = spinner2.getSelectedItem().toString();

                int selectedPositionOne = spinner.getSelectedItemPosition();
                int selectedPositionTwo = spinner2.getSelectedItemPosition();

                presenter.studyDetailSelected(selectedItem1,selectedItem2,position,selectedPositionOne,selectedPositionTwo);

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return slectItem;
    }

}
