package com.ibrahim.rozoshab.Main.SummaryActivity.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ibrahim.rozoshab.CustomClasses.Constants;
import com.ibrahim.rozoshab.CustomClasses.CustomMethods;
import com.ibrahim.rozoshab.CustomClasses.WriteExcel;
import com.ibrahim.rozoshab.Databases.TaskTableHelper;
import com.ibrahim.rozoshab.Main.MainActivity.view.MainActivity;
import com.ibrahim.rozoshab.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;


import jxl.write.WriteException;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {

    Button export,send;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        export = (Button) findViewById(R.id.btn_export);
        send = (Button) findViewById(R.id.btn_send);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        export.setOnClickListener(this);
        send.setOnClickListener(this);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
//        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
//        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v.getId()== R.id.btn_export){
            exportReport(false);


        }else if (v.getId() == R.id.btn_send){
            //sendReport();
            exportReport(true);

        }

    }
    void exportReport(boolean shouldSend){
        Log.i("currentYear", "exportReport: " + CustomMethods.getCurrentYear());
//        saveExcelFile(this, Constants.FILE_NAME);
//        Toast.makeText(this, "Report Exported in files", Toast.LENGTH_SHORT).show();

        int currentYear = CustomMethods.getCurrentYear();
        ArrayList<String> yearList = new ArrayList<>();
        yearList.add(currentYear+"");
        yearList.add((currentYear-1)+"");
        yearList.add((currentYear-2)+"");


        ArrayList<String> monthList = new ArrayList<>();
        for (int a=1; a<13; a++){
            monthList.add(CustomMethods.getMonthName(a));
        }

        showSpinnerDialog(yearList,monthList,"Select Month and Year",
                shouldSend);


    }


    public String showSpinnerDialog(final ArrayList<String> data1, final ArrayList<String> data2, String title, final boolean shouldSend) {

        final String slectItem = "";

        final AlertDialog.Builder builder = new AlertDialog.Builder(SummaryActivity.this);
        builder.setTitle(title);
        View mview = getLayoutInflater().inflate(R.layout.spinner_layout, null);

        final Spinner spinner = (Spinner) mview.findViewById(R.id.dialogapinner);
        final Spinner spinner2 = (Spinner) mview.findViewById(R.id.dialogapinnerTwo);

        if (data1!=null && data2!=null){
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
            ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2);

            spinner.setAdapter(stringArrayAdapter);
            spinner2.setAdapter(stringArrayAdapter2);
        }else if (data1==null){
            spinner.setVisibility(View.GONE);
            ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2);
            spinner2.setAdapter(stringArrayAdapter2);

        }else if (data2==null){
            spinner2.setVisibility(View.GONE);
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
            spinner.setAdapter(stringArrayAdapter);

        }
        builder.setView(mview);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.i("selection", "onClick: " + spinner.getSelectedItemPosition()
                        +"\t"+spinner2.getSelectedItemPosition()  + "\t" + spinner.getSelectedItem() + "\t" + spinner2.getSelectedItem());

                    int month = spinner2.getSelectedItemPosition()+1;
                    int year =  Integer.parseInt(spinner.getSelectedItem().toString());

                     saveExcelFile(SummaryActivity.this, Constants.FILE_NAME,month,year,shouldSend);



            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return slectItem;
    }





    private void saveExcelFile(SummaryActivity summaryActivity, String sampleExcel,int month, int year,boolean shouldSend) {

        WriteExcel test = new WriteExcel(this,sampleExcel);
        ArrayList<String> distinctDates  = new TaskTableHelper(this).getDaysByMonthYear(month,year);

        if (distinctDates.size()<1){
                createDialog("Rozoshab","No data to export");
        }else {

            //test.setOutputFile(sampleExcel);
            try {
                test.prepareData(distinctDates,CustomMethods.getMonthName(month));
                test.write();
                if (shouldSend) sendReport();
                else createDialog("Rozoshab","Report Generated \n Please Find RozoShab in files");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

        }

    }

    void createDialog(String title,String message){

         LovelyStandardDialog dialog = new LovelyStandardDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorPrimary)
                .setIcon(R.drawable.ic_save_white)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, null)
                .setNegativeButton(android.R.string.no, null);


            dialog.show();

    }


    void sendReport(){

        //exportReport();

        File filelocation = new File(Environment.getExternalStorageDirectory(),Constants.FILE_NAME);

        Uri path = Uri.fromFile(filelocation);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // set the type to 'email'
        emailIntent.setType("vnd.android.cursor.dir/email");
        String to[] = {"ibrahimarain15@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        // the mail subject
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RozoShab Report");
        String desc = "Monthly Roz o shab";
        emailIntent.putExtra(Intent.EXTRA_TEXT, desc);

        startActivity(Intent.createChooser(emailIntent, "Send email..."));


    }




    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }



}
