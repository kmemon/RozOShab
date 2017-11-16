package com.ibrahim.rozoshab.Main.MainActivity.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.compat.BuildConfig;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ibrahim.rozoshab.Bean.CategoryBean;
import com.ibrahim.rozoshab.Bean.TaskBean;
import com.ibrahim.rozoshab.CustomClasses.Constants;
import com.ibrahim.rozoshab.Main.MainActivity.MainActivityContractor;
import com.ibrahim.rozoshab.Main.MainActivity.model.MainModel;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.CategoryViewHolder;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.DistributionFamiylViewHolder;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.MainAdapter;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.SalatViewHolder;
import com.ibrahim.rozoshab.Main.MainActivity.view.adapter.StudyViewHolder;
import com.ibrahim.rozoshab.Main.SummaryActivity.view.SummaryActivity;
import com.ibrahim.rozoshab.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by w-t- on 17-Sep-17.
 */

public class MainPresenter implements MainActivityContractor.ViewToPresenter,
        MainActivityContractor.ModelToPresenter {

    WeakReference<MainActivityContractor.PresenterToView> mView;
    Context weakContext;
    MainModel model;
    ArrayList<Object> categoryDataBeanArrayList ;
    MainAdapter mainAdapter;
    int position;


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


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(weakContext, "Permission Granted", Toast.LENGTH_SHORT).show();
                Log.i("permission", "onPermissionGranted: " );
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                Toast.makeText(weakContext, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                Log.i("permission", "onPermissionDenied: " );


                ((Activity)weakContext).finish();

            }


        };

        TedPermission.with(weakContext)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();


    }

    @Override
    public void viewResumed() {

    }

    @Override
    public void viewbuttonClicked(int id) {

        model.saveData(categoryDataBeanArrayList);
        Toast.makeText(weakContext, "Data saved", Toast.LENGTH_SHORT).show();


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
                case 3 :
                    View salatView = inflater.inflate(R.layout.layout_each_salah,parent,false);
                    viewHolder = new SalatViewHolder(salatView,this);
                    break;

            case 2:
                View studyView = inflater.inflate(R.layout.layout_each_study,parent,false);
                viewHolder = new StudyViewHolder(studyView);
                break;


            case 4:
            case 5:
            case 6:

                View distributionView = inflater.inflate(R.layout.layout_distribution_family,parent,false);
                viewHolder = new DistributionFamiylViewHolder(distributionView);
                break;




        }
        return viewHolder;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        this.position = position;

        if(holder.getItemViewType()==Constants.VIEW_TYPE_CATEGORY){

            CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
            CategoryBean category = (CategoryBean) categoryDataBeanArrayList.get(position);
            categoryViewHolder.categoryName.setText(category.getCategoryName());


        }else if(holder.getItemViewType()==Constants.TASK_TYPE_SALAH || holder.getItemViewType()==Constants.TASK_TYPE_MEETING){

            SalatViewHolder salatViewHolder = (SalatViewHolder) holder;
            TaskBean taskBean = (TaskBean) categoryDataBeanArrayList.get(position);
            salatViewHolder.salatName.setText( taskBean.getTaskName());

            updateViews(salatViewHolder,position);

            salatViewHolder.salartRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                   onTaskCheckChange(checkedId,position);

                }
            });

            if (holder.getItemViewType()==Constants.TASK_TYPE_MEETING){
                salatViewHolder.radioButtonJamat.setButtonDrawable(weakContext.getResources().
                        getDrawable(R.drawable.radio_button_personal_selector));

                salatViewHolder.radioButtonIndividual.setButtonDrawable(weakContext.getResources().
                        getDrawable(R.drawable.radio_button_online_selector));

                salatViewHolder.radioButtonQaza.setButtonDrawable(weakContext.getResources().
                        getDrawable(R.drawable.radio_button_phone_selector));


                salatViewHolder.salartRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                        onTaskCheckChange(checkedId,position);

                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (int i = 0; i < 10; i++) {

                            arrayList.add("" + i + 1);
                        }

                        if (categoryDataBeanArrayList.get(position) instanceof TaskBean){

                            String taskName = ((TaskBean) categoryDataBeanArrayList.get(position)).getTaskName();

                                if(taskName.equalsIgnoreCase("Member") ||
                                        taskName.equalsIgnoreCase("Volunteer")){
                                    populateMeetingData(position);
                                }
                        }

                    }
                });



            }

        } else if(holder.getItemViewType()==Constants.TASK_TYPE_STUDY){

            final StudyViewHolder studyViewHolder = (StudyViewHolder) holder;
            TaskBean taskBean = (TaskBean) categoryDataBeanArrayList.get(position);
            studyViewHolder.studyTitle.setText( taskBean.getTaskName());

            updateStudyViews(studyViewHolder,position);

            if (categoryDataBeanArrayList.get(position) instanceof TaskBean){

                if (((TaskBean) categoryDataBeanArrayList.get(position)).getStatus() == 1){
                    studyViewHolder.studyCheckBox.setVisibility(View.VISIBLE);
                    studyViewHolder.studyCheckBox.setChecked(true);

               }
            }

            final String taskName = ((TaskBean) categoryDataBeanArrayList.get(position)).getTaskName();
            if (taskName.equalsIgnoreCase("Quran")){

            }

            else if (taskName.equalsIgnoreCase("Seerat")){
                studyViewHolder.radioButtonTafseer.setVisibility(View.INVISIBLE);
                studyViewHolder.radioButtonRecitation.setVisibility(View.GONE);
                studyViewHolder.getRadioButtonReadOnly.setVisibility(View.VISIBLE);

            }

            else if (taskName.equalsIgnoreCase("Book")){
                studyViewHolder.radioButtonTafseer.setVisibility(View.INVISIBLE);
                studyViewHolder.radioButtonRecitation.setVisibility(View.GONE);
                studyViewHolder.getRadioButtonReadOnly.setVisibility(View.VISIBLE);


            }


            studyViewHolder.materialRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                    onTaskCheckChange(checkedId,position);

                    if (checkedId!=-1 ){
                        studyViewHolder.studyCheckBox.setVisibility(View.VISIBLE);
                        studyViewHolder.studyCheckBox.setChecked(true);
                    }
                    ArrayList<String> arrayList = new ArrayList<String>();
                    for (int i = 0; i < 10; i++) {

                        arrayList.add("" + i + 1);
                    }

                    if (categoryDataBeanArrayList.get(position) instanceof TaskBean){

                        String taskName = ((TaskBean) categoryDataBeanArrayList.get(position)).getTaskName();

                        if (checkedId!=R.id.radio_not_read) {
                            if (taskName.equalsIgnoreCase("Quran")) {
                                populateQuanData(position);

                            } else if (taskName.equalsIgnoreCase("Seerat")) {
                                populateHadeesData(position);
                            } else if (taskName.equalsIgnoreCase("Book")) {
                                populateBookData(position);

                            }else if(taskName.equalsIgnoreCase("Member")){
                                populateMeetingData(position);
                            }
                        }
                    }

                }
            });

            studyViewHolder.studyCheckBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                    if (isChecked){
                        onTaskChange(taskName,checkBox,isChecked,position);
                    }else {
                        studyViewHolder.materialRadioGroup.clearCheck();
                    }
                }
            });

            studyViewHolder.materialRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                    onTaskCheckChange(checkedId,position);

                    if (checkedId!=-1 ){
                        studyViewHolder.studyCheckBox.setVisibility(View.VISIBLE);
                        studyViewHolder.studyCheckBox.setChecked(true);
                    }
                    ArrayList<String> arrayList = new ArrayList<String>();
                    for (int i = 0; i < 10; i++) {

                        arrayList.add("" + i + 1);
                    }

                    if (categoryDataBeanArrayList.get(position) instanceof TaskBean){

                        String taskName = ((TaskBean) categoryDataBeanArrayList.get(position)).getTaskName();

                        if (checkedId!=R.id.radio_not_read) {
                            if (taskName.equalsIgnoreCase("Quran")) {
                                populateQuanData(position);

                            } else if (taskName.equalsIgnoreCase("Seerat")) {
                                populateHadeesData(position);
                            } else if (taskName.equalsIgnoreCase("Book")) {
                                populateBookData(position);

                            }else if(taskName.equalsIgnoreCase("Member")){
                                populateMeetingData(position);
                            }
                        }
                    }

                }
            });


        }else if(holder.getItemViewType()==Constants.TASK_TYPE_DISTRIBUTION ||
                holder.getItemViewType()==Constants.TASK_TYPE_FAMILYPROGRAM ||
                holder.getItemViewType()==Constants.TASK_TYPE_ATTENDANCE) {

            DistributionFamiylViewHolder ditributionFamilyViewHolder = (DistributionFamiylViewHolder) holder;
            final TaskBean taskBean = (TaskBean) categoryDataBeanArrayList.get(position);
            ditributionFamilyViewHolder.text_distribution_family.setText( taskBean.getTaskName());

            if (categoryDataBeanArrayList.get(position) instanceof TaskBean){

                if (((TaskBean) categoryDataBeanArrayList.get(position)).getStatus() == 1){
                    ditributionFamilyViewHolder.smoothCheckBox.setChecked(true);

                }
            }

            ditributionFamilyViewHolder.smoothCheckBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                    onTaskChange(taskBean.getTaskName(),checkBox,isChecked,position);
                }
            });

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

        }else
            return ((TaskBean) categoryDataBeanArrayList.get(position)).getTaskType();

    }

    @Override
    public void onOptionItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_calendar){

            mView.get().showDatePicker();

        }else if(item.getItemId() == R.id.item_send) {

            mView.get().navigateActivity(new Intent(weakContext, SummaryActivity.class));
        }
}

    @Override
    public void onDateSelected(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Toast.makeText(weakContext, ""+year, Toast.LENGTH_SHORT).show();

        Calendar calender = Calendar.getInstance();
        calender.set(year,monthOfYear,dayOfMonth);

        String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(calender.getTime());


        model.getTasks(formattedDate);



    }

    @Override
    public void studyDetailSelected(String selectedItem, String selectedItem2, int dataPosition,
                                    int selectedPositionOne, int selectedPositionTwo) {

        if (categoryDataBeanArrayList.get(dataPosition) instanceof  TaskBean) {

            if (selectedPositionOne !=0){
                ((TaskBean) categoryDataBeanArrayList.get(dataPosition)).setTaskExtras(selectedItem);
            }

            if (selectedPositionTwo !=0){
                ((TaskBean) categoryDataBeanArrayList.get(dataPosition)).setQuantity(selectedItem2);
            }







        }

    }


    public void onTaskCheckChange(int checkedId, int position) {

        MaterialRadioButton rb= (MaterialRadioButton) ((Activity)weakContext).findViewById(checkedId);

        if (categoryDataBeanArrayList.get(position) instanceof  TaskBean) {
            ((TaskBean) categoryDataBeanArrayList.get(position)).setStatus(1);

            if (((TaskBean) categoryDataBeanArrayList.get(position)).getTaskType() == Constants.TASK_TYPE_SALAH) {

                if (checkedId == R.id.radiobutton_jamat) {
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.JAMAT);
                } else if (checkedId == R.id.radiobutton_individual) {
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.INDIVIDUAL);
                } else if (checkedId == R.id.radiobutton_qaza) {
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.QAZA);
                }

            }else if (((TaskBean) categoryDataBeanArrayList.get(position)).getTaskType() == Constants.TASK_TYPE_MEETING){

                if (checkedId == R.id.radiobutton_jamat) {
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.PERSONAL);
                } else if (checkedId == R.id.radiobutton_individual) {
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.ONLINE);
                } else if (checkedId == R.id.radiobutton_qaza) {
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.PHONE);
                }


            }
            else if (((TaskBean) categoryDataBeanArrayList.get(position)).getTaskType() == Constants.TASK_TYPE_STUDY){

                if (checkedId == R.id.radio_tafseer) {
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.READ_TAFSEER);
                   // Toast.makeText(weakContext, "not tafseer", Toast.LENGTH_SHORT).show();


                } else if (checkedId == R.id.radio_recitation) {
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.READ_RECITATION);

                  //  Toast.makeText(weakContext, "not recitation", Toast.LENGTH_SHORT).show();


                } else if (checkedId == R.id.radio_not_read) {

                    Toast.makeText(weakContext, "not read", Toast.LENGTH_SHORT).show();

                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.NOT_READ);
                }else  if(checkedId == R.id.radio_read_only){

                    Toast.makeText(weakContext, "read", Toast.LENGTH_SHORT).show();

                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.READ_ONLY);


                }
            }
        }

    }

    public void onTaskChange(String taskName,SmoothCheckBox checBox,boolean isChecked,int position){
        //Toast.makeText(weakContext, "check change"+isChecked+position, Toast.LENGTH_SHORT).show();
        if (categoryDataBeanArrayList.get(position) instanceof  TaskBean){

            ((TaskBean) categoryDataBeanArrayList.get(position)).setStatus(1);

            switch (taskName){
                case "BookDistribution":
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.BOOK_DISTRIBUTION);
                    if (isChecked)
                        populateDistributionData(position);
                    break;
                case "Family Program":
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.FAMILY_PROGRAM);
                    break;
                case "Quran Circle":
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.QURAN_CIRCLE);
                    break;
                case "Study Circle":
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.STUDY_CIRCLE);
                    break;
                case  "Members Meeting":
                    ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(Constants.MEMBERS_MEETING);
                    break;


            }
//            ((TaskBean) categoryDataBeanArrayList.get(position)).setSubTask(1);
        }



    }


    @Override
    public void processData(ArrayList<CategoryBean> categoryList, ArrayList<TaskBean> tasksList) {
        categoryDataBeanArrayList = new ArrayList<>();
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

    void updateViews(SalatViewHolder salatViewHolder,int position){

        if (categoryDataBeanArrayList.get(position) instanceof TaskBean){

            if (((TaskBean) categoryDataBeanArrayList.get(position)).getStatus() == 1){

                int subTask = ((TaskBean) categoryDataBeanArrayList.get(position)).getSubTask();

                    if (subTask == Constants.JAMAT || subTask == Constants.PERSONAL)
                        salatViewHolder.radioButtonJamat.setChecked(true);
                     else if  (subTask == Constants.INDIVIDUAL ||subTask == Constants.ONLINE)
                             salatViewHolder.radioButtonIndividual.setChecked(true);
                      else if  (subTask == Constants.QAZA  || subTask == Constants.PHONE)
                            salatViewHolder.radioButtonQaza.setChecked(true);
            }
        }


    }

    void updateStudyViews(StudyViewHolder studyViewHolder,int position){

        if (categoryDataBeanArrayList.get(position) instanceof TaskBean){

            if (((TaskBean) categoryDataBeanArrayList.get(position)).getStatus() == 1){

                int subTask = ((TaskBean) categoryDataBeanArrayList.get(position)).getSubTask();

                if (subTask == Constants.READ_TAFSEER)
                    studyViewHolder.radioButtonTafseer.setChecked(true);
                else if  (subTask == Constants.READ_RECITATION)
                    studyViewHolder.radioButtonRecitation.setChecked(true);
                else if  (subTask == Constants.NOT_READ)
                    studyViewHolder.radioButtonNotRead.setChecked(true);
                else if (subTask == Constants.READ_ONLY)
                    studyViewHolder.getRadioButtonReadOnly.setChecked(true);
            }
        }
    }

    void populateQuanData(int position){


        ArrayList<String> rukuList =  new ArrayList<>();
        ArrayList<String> numPagesData =  new ArrayList<>();


        rukuList.add("Select Ruku");
        rukuList.add("1 ruku");
        rukuList.add("2 ruku");
        rukuList.add("3 ruku");
        rukuList.add("More than 5 ruku");


        numPagesData.add("Select Num of Pages");
        numPagesData.add("1");
        numPagesData.add("2");
        numPagesData.add("3");
        numPagesData.add("4");
        numPagesData.add("5+");

        mView.get().showSpinnerDialog(rukuList,numPagesData,"Please select",position);


    }

    void populateHadeesData(int position){

        ArrayList<String> hadeesBooks =  new ArrayList<>();
        ArrayList<String> numPagesData =  new ArrayList<>();


        hadeesBooks.add("Select Hadees book");
        hadeesBooks.add("Hadith-1 Nabavi");
        hadeesBooks.add("Zaad e Raah");
        hadeesBooks.add("Other");


        numPagesData.add("Select Num of Pages");
        numPagesData.add("1");
        numPagesData.add("2");
        numPagesData.add("3");
        numPagesData.add("4");
        numPagesData.add("5+");

        mView.get().showSpinnerDialog(hadeesBooks,numPagesData,"Please select",position);

    }

    void populateBookData(int position){
        ArrayList<String> nisabBooks =  new ArrayList<>();
        ArrayList<String> numPagesData =  new ArrayList<>();

        nisabBooks.add("Select Book");
        nisabBooks.add("Deenyaat");
        nisabBooks.add("Khutbaat");
        nisabBooks.add("Karkunon ky Bahami Taaluqaat");
        nisabBooks.add("Islami Tehzeeb ki Usool o Mubadi");
        nisabBooks.add("Rasail o Masail part 1");
        nisabBooks.add("Debaacha + Muqadma ");
        nisabBooks.add(" Nabi SAW ki seerat k numayan pehlu");
        nisabBooks.add("Shahadat e Haq");
        nisabBooks.add("Don’t Judge, Spread Salam");
        nisabBooks.add("Apni Tarbeyat Kaisay Karain");
        nisabBooks.add("Hayat-e-Tayyaba (saw)");
        nisabBooks.add("Other");



        numPagesData.add("Select Num of Pages");
        numPagesData.add("1");
        numPagesData.add("2");
        numPagesData.add("3");
        numPagesData.add("4");
        numPagesData.add("5+");


        mView.get().showSpinnerDialog(nisabBooks,numPagesData,"Please select", position);

    }

    void populateMeetingData(int position){
        ArrayList<String> personal =  new ArrayList<>();
        ArrayList<String> online =  new ArrayList<>();

        personal.add("Personal");
        personal.add("1");
        personal.add("2");
        personal.add("3");
        personal.add("4");
        personal.add("5+");


        online.add("Online");
        online.add("1");
        online.add("2");
        online.add("3");
        online.add("4");
        online.add("5+");


        mView.get().showSpinnerDialog(personal,online,"Number of members/Volunteer contacted?", position);

    }

    void populateDistributionData(int position){

        ArrayList<String> bookDistribution =  new ArrayList<>();

        bookDistribution.add("Number of books");
        bookDistribution.add("1");
        bookDistribution.add("2");
        bookDistribution.add("3");
        bookDistribution.add("4");
        bookDistribution.add("5+");


        mView.get().showSpinnerDialog(null,bookDistribution,"Number of books", position);

    }

}



