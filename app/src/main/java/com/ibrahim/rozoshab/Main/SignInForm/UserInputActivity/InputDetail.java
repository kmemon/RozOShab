package com.ibrahim.rozoshab.Main.SignInForm.UserInputActivity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ibrahim.rozoshab.CustomClasses.Constants;
import com.ibrahim.rozoshab.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import io.paperdb.Paper;

public class InputDetail extends AppCompatActivity {
    EditText editText_name, editText_email, editText_mobilenumber, editText_age, profession;
    Button btn_submit;

    SearchableSpinner country;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_detail);
        Paper.init(this);
        editText_name = (EditText) findViewById(R.id.et_name);
        editText_email = (EditText) findViewById(R.id.et_fathername);
        editText_age = (EditText) findViewById(R.id.et_city);
        editText_mobilenumber = (EditText) findViewById(R.id.et_mobile_number);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        profession = (EditText) findViewById(R.id.et_profession);
        country = (SearchableSpinner) findViewById(R.id.spinner_country);
        list = new ArrayList<String>();
        list.add("Select Country");
        list.add("India");
        list.add("Pakistan");
        list.add("China");
        list.add("SriLanka");
        list.add("Australia");
        list.add("England");
        list.add("Japan");
        list.add("Bangladesh");
        list.add("USA");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        country.setAdapter(adapter);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_name.getText().toString().equals("") || editText_email.getText().toString().equals("") || profession.getText().toString().equals("") ||
                        editText_age.getText().toString().equals("") || editText_mobilenumber.getText().toString().equals("")  || country.getSelectedItem().toString().equals("Select Country")) {
                    Snackbar.make(v, "Fill all information", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    Paper.book().write(Constants.PROFESSION,editText_name.getText().toString());



                }


            }
        });


    }
}
