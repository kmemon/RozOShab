package com.ibrahim.rozoshab.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.ibrahim.rozoshab.Bean.CategoryBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class CategoryTableHelper {

    Context context;
    SQLiteDatabase db;

    public CategoryTableHelper(Context context){
        this.context = context;
        db = DatabaseHelper.getInstance(context).getWritableDatabase();

    }


    public boolean insert (ArrayList<CategoryBean> categoryBeanArrayList){

        for(CategoryBean category:categoryBeanArrayList) {

            ContentValues cv = new ContentValues();

            cv.put(DatabaseHelper.COLUMN_CATEGORY_NAME, category.getCategoryName());
            cv.put(DatabaseHelper.COLUMN_CATEGORY_STATUS, 1);

            try {
                db.insert(DatabaseHelper.TABLE_CATEGORIES, null, cv);
                Log.i("CategoryTableHelper","Inserting Categories");

            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public ArrayList<CategoryBean> getCategories (){

        ArrayList<CategoryBean> categoryList = new ArrayList<>();
        String queryGetCategories ="select * from "+DatabaseHelper.TABLE_CATEGORIES;
        Cursor cursor;
        cursor = db.rawQuery(queryGetCategories,null);


            while (cursor.moveToNext()){
                categoryList.add(new CategoryBean(cursor.getInt(0)+"",cursor.getString(1)));



        }
            return categoryList;
    }


    public boolean insertAsync(ArrayList<CategoryBean> categoryBeanArrayList){

        final boolean[] res = {false};

        Disposable disposable = Observable.just(insert(categoryBeanArrayList)).
                subscribeOn(Schedulers.io()).subscribe(aBoolean ->{
             Log.i("CategoryTableHelper","CategoryAdded Response"+aBoolean);
                res[0] = aBoolean;

        });


        return res[0];
    }

}
