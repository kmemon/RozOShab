package com.ibrahim.rozoshab.Main.SplashActivity.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ibrahim.rozoshab.Databases.CategoryTableHelper;
import com.ibrahim.rozoshab.Main.MainActivity.view.MainActivity;
import com.ibrahim.rozoshab.Main.SplashActivity.SplashContractor;
import com.ibrahim.rozoshab.Main.SplashActivity.presenter.SplashPresenter;
import com.ibrahim.rozoshab.R;
import com.ibrahim.rozoshab.SharedPrefrences.SharedPreferenceHelper;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity implements SplashContractor.PresenterToView {

    Timer timer;
    Handler handler;
    int counter = 0;

    SplashPresenter mpresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        timer = new Timer();
        handler = new Handler();
        mpresenter = new SplashPresenter(this);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new TimerTask() {
                    @Override
                    public void run() {
                        ++counter;
                        Log.i("------", "run: " + counter);
                        if (counter >= 20) {
                            timer.cancel();
                            //startActivity(new Intent(Splash.this, MainActivity.class));
                           // finish();
                        }
                    }
                });
            }
        }, 20, 10);
    }



    @Override
    public void navigateActivity(Context context, Class<?> clss) {

        startActivity(new Intent(context,clss));
        finish();
    }

    @Override
    public Activity getActivityContext() {
        return Splash.this;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
