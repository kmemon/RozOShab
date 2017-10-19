package com.ibrahim.rozoshab.Main.MainActivity.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.compat.BuildConfig;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.CategoryDataBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.CustomClasses.Constants;
import com.ibrahim.rozoshab.Main.MainActivity.MainActivityContractor;
import com.ibrahim.rozoshab.Main.MainActivity.model.MainModel;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.CategoryViewHolder;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.MainAdapter;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.SalatViewHolder;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.StudyViewHolder;
import com.ibrahim.rozoshab.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class MainPresenter implements MainActivityContractor.ViewToPresenter,
        MainActivityContractor.ModelToPresenter {

    WeakReference<MainActivityContractor.PresenterToView> mView;
    Context weakContext;
    MainModel model;
    ArrayList<Object> categoryDataBeanArrayList  = new ArrayList<>();
    MainAdapter mainAdapter;

    public MainPresenter(MainActivityContractor.PresenterToView view){

        mView = new WeakReference<>(view);
        weakContext = mView.get().getActivityContext();
        model = new MainModel(weakContext,this);
        showDebugDBAddressLogToast(weakContext);

    }

    public static void showDebugDBAddressLogToast(Context context) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                Log.i("------", "ip_address: " + value);
            } catch (Exception ignore) {

            }
        }
    }


    @Override
    public void onOptionItemSelected(int item) {

    }

    @Override
    public void viewCreated() {


    }

    @Override
    public void viewResumed() {

    }

    @Override
    public void viewbuttonClicked(int id) {

    }

    @Override
    public void passIntentData(int moduleId, String visitID, String moduleName, String storeDataVariable) {

    }

    @Override
    public void dispatchIntentCallBack(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void dispatchItemClicked(AdapterView<?> parent, View v, int position, long id) {

    }

    @Override
    public void nisabScreenSelected() {

        mView.get().initNisabScreen();
    }

    @Override
    public void summaryScreenSelected() {
          int qaza =   model.prepareSummaryData();

        Toast.makeText(weakContext, ""+qaza, Toast.LENGTH_SHORT).show();
        Log.i("qaza",""+qaza);
            mView.get().iniSummaryScreen();
    }

    @Override
    public void reportScreenSelected() {
        mView.get().iniReportScreen();
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){

            case 0 :
                View categoryView = inflater.inflate(R.layout.category_strip,parent,false);
                viewHolder = new CategoryViewHolder(categoryView);
                break;

                case 1 :
                    View salatView = inflater.inflate(R.layout.layout_each_salah,parent,false);
                    viewHolder = new SalatViewHolder(salatView);
                    break;

                default:
                    View salaView = inflater.inflate(R.layout.layout_each_study,parent,false);
                    viewHolder = new StudyViewHolder(salaView);
                    break;

        }




        return viewHolder;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder.getItemViewType()==Constants.VIEW_TYPE_CATEGORY){

            CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
            CategoryBean category = (CategoryBean) categoryDataBeanArrayList.get(position);
            categoryViewHolder.categoryName.setText(category.getCategoryName());


        }else if(holder.getItemViewType()==Constants.TASK_TYPE_SALAH){
            SalatViewHolder salatViewHolder = (SalatViewHolder) holder;
            TaskBean taskBean = (TaskBean) categoryDataBeanArrayList.get(position);
            salatViewHolder.salatName.setText( taskBean.getTaskName());
        }
        else if(holder.getItemViewType()==Constants.TASK_TYPE_STUDY){
//            StudyViewHolder studyViewHolder = (StudyViewHolder) holder;
//            TaskBean taskBean = (TaskBean) categoryDataBeanArrayList.get(position);
           // studyViewHolder.salatName.setText( taskBean.getTaskName());


        }


    }

    @Override
    public int getItemCount() {
        return categoryDataBeanArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (categoryDataBeanArrayList.get(position) instanceof CategoryBean){

            return Constants.VIEW_TYPE_CATEGORY;

        }else //if(categoryDataBeanArrayList.get(position) instanceof TaskBean) {

            return ((TaskBean) categoryDataBeanArrayList.get(position)).getTaskType();
       // }

        //return -1;
    }

    @Override
    public void processData(ArrayList<CategoryBean> categoryList, ArrayList<TaskBean> tasksList) {
        mainAdapter = new MainAdapter(this);


        for (CategoryBean categoryBean: categoryList){

            categoryDataBeanArrayList.add(categoryBean);

            for (TaskBean task : tasksList ){
                if (task.getCatId() == Integer.parseInt(categoryBean.getCategoryId()))
                    categoryDataBeanArrayList.add(task);
            }
        }
            mView.get().populateData(categoryDataBeanArrayList,mainAdapter);

    }
}



