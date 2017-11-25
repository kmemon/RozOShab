package com.ibrahim.rozoshab.Main.SummaryActivity.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ibrahim.rozoshab.CustomClasses.Constants;
import com.ibrahim.rozoshab.CustomClasses.WriteExcel;
import com.ibrahim.rozoshab.R;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
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
            exportReport();


        }else if (v.getId() == R.id.btn_send){
            sendReport();

        }

    }
    void exportReport(){

        saveExcelFile(this, Constants.FILE_NAME);

    }

    private void saveExcelFile(SummaryActivity summaryActivity, String sampleExcel) {

        WriteExcel test = new WriteExcel(this,sampleExcel);
        //test.setOutputFile(sampleExcel);
        try {
            test.prepareData();
           test.write();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }


    }

    void sendReport(){

        exportReport();

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
