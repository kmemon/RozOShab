package com.ibrahim.rozoshab.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import static android.R.attr.version;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "RozoShab";
    private static  int DATABASE_VERSION = 1;
    private String TABLE_SUB_CATEGORIES = "subcategories";
    private static DatabaseHelper instance = null;

    //Table Category
    public static String TABLE_CATEGORIES = "tbl_categories";
    public static final String COLUMN_CATEGORY_ID = BaseColumns._ID ;
    public static String COLUMN_CATEGORY_NAME = "name";
    public static String COLUMN_CATEGORY_STATUS = "status";


    //Table Tasks
    public static String TABLE_TASKS= "tbl_tasks";
    public static final String COLUMN_TASK_ID = BaseColumns._ID ;
    public static String COLUMN_CAT_ID = "category_id";
    public static String COLUMN_SUB_TASK ="subtask";
    public static String COLUMN_TASK_NAME = "name";
    public static String COLUMN_TASK_DATE = "date";
    public static String COLUMN_TASK_TYPE = "tasks_type";
    public static String COLUMN_TASK_STATUS = "status";


    String queryCreateCategories = "CREATE TABLE `" + TABLE_CATEGORIES + "` (\n" +
            "\t`" + COLUMN_CATEGORY_ID + "`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`" + COLUMN_CATEGORY_NAME + "`\tTEXT,\n" +
            "\t`" + COLUMN_CATEGORY_STATUS + "`\tINTEGER\n" +
            ");";


    String queryCreateTasks = "CREATE TABLE `" + TABLE_TASKS + "` (\n" +
            "\t`" + COLUMN_TASK_ID + "`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`" + COLUMN_CAT_ID + "`\tINTEGER,\n" +
            "\t`" + COLUMN_SUB_TASK + "`\tINTEGER,\n" +
            "\t`" + COLUMN_TASK_NAME + "`\tTEXT,\n" +
            "\t`" + COLUMN_TASK_DATE + "`\tTEXT,\n" +
            "\t`" + COLUMN_TASK_TYPE + "`\tINTEGER,\n" +
            "\t`" + COLUMN_TASK_STATUS + "`\tINTEGER\n" +
            ");";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(queryCreateCategories);
        db.execSQL(queryCreateTasks);
        Log.i("tables","created");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        } else if (!instance.getWritableDatabase().isOpen()) {
            instance = new DatabaseHelper(context);
        }

        return instance;
    }

}
