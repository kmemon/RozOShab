package com.ibrahim.rozoshab.CustomClasses;

/**
 * Created by w-t- on 23-Oct-17.
 */
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.Databases.CategoryTableHelper;
import com.ibrahim.rozoshab.Databases.TaskTableHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel {


    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
   // private String inputFile;
    Context context;
    TaskTableHelper taskTableHelper;
    CategoryTableHelper categoryTableHelper;
    File file;
    int count1 = 0,count2 = 1, count3 = 1;

    WritableWorkbook workbook ;
    WritableSheet excelSheet ;
    WorkbookSettings wbSettings;

    public WriteExcel(Context context,String inputFile){
        this.context =  context;
        taskTableHelper = new TaskTableHelper(context);
        categoryTableHelper = new CategoryTableHelper(context);
        File file = new File(Environment.getExternalStorageDirectory(),inputFile);
        this.file = file;

        wbSettings = new WorkbookSettings();
        //prepareData();

    }

    public void prepareData(){


        try {
            workbook = Workbook.createWorkbook(file, wbSettings);

        } catch (IOException e) {
            e.printStackTrace();
        }
        workbook.createSheet("Report", 0);
        excelSheet = workbook.getSheet(0);

        
        ArrayList<String> distinctDates = taskTableHelper.getAllDays();
        ArrayList<CategoryBean> categories = categoryTableHelper.getCategories();

        try {
            createLabel(excelSheet);
        } catch (WriteException e) {
            e.printStackTrace();
        }


        for (int i = 0; i <distinctDates.size(); i++) {

            try {

                addCaption(excelSheet,i+2,0,"0"+(i+1));

                Log.i("addcaptioon","column 0 Row"+count1 +"Value ="+distinctDates.get(i));
                count1++;

            } catch (WriteException e) {
                e.printStackTrace();
            }

        }

        for (int j = 0;j< categories.size()  ; j++) {
            //add categoryName Here
            try {
                // createLabel(excelSheet);
                addLabel(excelSheet, 0, count2, categories.get(j).getCategoryName());
                Log.i("addLabel", "column 0 Row" + count2 + "Value =" + categories.get(j).getCategoryName());

                // count2++;
                //   if (count2<categories.size())

            } catch (WriteException e) {
                e.printStackTrace();
            }

            ArrayList<TaskBean> tasks = taskTableHelper.getTasksByDate(Integer.parseInt(categories.get(j).getCategoryId()),
                    CustomMethods.getCurrentDate());

            for (int k = 0; k < tasks.size(); k++) {
                //write Tasks here
                try {
                    addLabel(excelSheet, 1, count3, tasks.get(k).getTaskName());

                    Log.i("addLabel", "column 0 Row" + count3 + "Value =" + tasks.get(k).getTaskName());
                    //   if (count3<tasks.size())
                    count2 = count3+1;
                    count3++;
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }


        for (int i = 0; i < distinctDates.size(); i++) {
            count3 = 1;

            for (int j = 0;j< categories.size()  ; j++) {

                ArrayList<TaskBean> tasks = taskTableHelper.getTasksByDate(Integer.parseInt(categories.get(j).getCategoryId()),
                        distinctDates.get(i));

                for (int k = 0; k <tasks.size() ; k++) {
                    //write Tasks here
                    try {

                        String stringToWrite=" ";

                        if (tasks.get(k).getStatus()==1){
                            stringToWrite="";

                            switch (tasks.get(k).getSubTask()){

                                case 1:{
                                    stringToWrite = "J";
                                    break;
                                }
                                case 2:{
                                    stringToWrite = "I";
                                    break;
                                }
                                case 3:{
                                    stringToWrite = "Q";
                                    break;
                                }
                                case 4:{
                                    stringToWrite = "PR";
                                    break;
                                }
                                case 5:{
                                    stringToWrite = "O";
                                    break;
                                }
                                case 6:{
                                    stringToWrite = "PH";
                                    break;
                                }

                                case 7:{
                                    stringToWrite = " ";
                                    break;
                                }
                                case 8:{
                                    stringToWrite = "T";//"AFTER TAHAJJUD";
                                    break;
                                }
                                case 9:{
                                    stringToWrite = "R";//"AFTER ESHA";
                                    break;
                                }
                                case 10:{
                                    stringToWrite = "R";
                                    break;
                                }
                                case 11:{
                                    stringToWrite = "Y";
                                    break;
                                }
                                case 12:{
                                    stringToWrite = "Y";
                                    break;
                                }
                                case 13:{
                                    stringToWrite = "Y";
                                    break;
                                }
                                case 14:{
                                    stringToWrite = "Y";
                                    break;
                                }
                                case 15:{
                                    stringToWrite = "Y";
                                    break;
                                }
                            }
                        }

                        if (!tasks.get(k).getTaskExtras().equals(""))
                            stringToWrite+= ","+tasks.get(k).getTaskExtras();
                        if (!tasks.get(k).getQuantity().equals(""))
                            stringToWrite+= ","+tasks.get(k).getQuantity();

//                        if (tasks.get(k).getTaskName().equalsIgnoreCase("Quran") ||
//                                tasks.get(k).getTaskName().equalsIgnoreCase("Seerat")||
//                                tasks.get(k).getTaskName().equalsIgnoreCase("Book")){
//
//                            stringToWrite+= ","+tasks.get(k).getTaskExtras()+","+tasks.get(k).getQuantity();
//
//                        }else {
//
//
//                        }

                        addLabel(excelSheet,i+2,count3,stringToWrite);

                        Log.i("addLabel","column 0 Row"+count3 +"Value ="+tasks.get(k).getTaskName());
                         count3++;

                    } catch (WriteException e) {
                        e.printStackTrace();
                    }

                }


            }

        }

        try {
            workbook.write();
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }


    }


//    public void setOutputFile(String inputFile) {
//        this.inputFile = inputFile;
//    }

    public void write() throws IOException, WriteException {

//        File file = new File(Environment.getExternalStorageDirectory(),inputFile);
//        WorkbookSettings wbSettings = new WorkbookSettings();
//
//        wbSettings.setLocale(new Locale("en", "EN"));
//
//        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
//        workbook.createSheet("Report", 0);
//        WritableSheet excelSheet = workbook.getSheet(0);
//
//        createLabel(excelSheet);
//        createContent(excelSheet);
//
//        workbook.write();
//        workbook.close();


        Log.i("created","FileCreated" + file.getPath());
    }

    private void createLabel(WritableSheet sheet)
            throws WriteException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(
                WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);

        // Write a few headers
      //  addCaption(sheet, 0, 0, "Header 1");
       // addCaption(sheet, 1, 0, "This is another header");


    }

    private void createContent(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        // Write a few number
        for (int i = 1; i < 100; i++) {
            // First column
            addNumber(sheet, 0, i, i + 10);
            // Second column
            addNumber(sheet, 1, i, i * i);
        }
        // Lets calculate the sum of it
        StringBuffer buf = new StringBuffer();
        buf.append("SUM(A2:A10)");
        Formula f = new Formula(0, 10, buf.toString());
        sheet.addCell(f);
        buf = new StringBuffer();
        buf.append("SUM(B2:B10)");
        f = new Formula(1, 10, buf.toString());
        sheet.addCell(f);

        // now a bit of text
        for (int i = 12; i < 20; i++) {
            // First column
            addLabel(sheet, 0, i, "Boring text " + i);
            // Second column
            addLabel(sheet, 1, i, "Another text");
        }
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);

        sheet.addCell(label);




    }

    private void addNumber(WritableSheet sheet, int column, int row,
                           Integer integer) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }


}
