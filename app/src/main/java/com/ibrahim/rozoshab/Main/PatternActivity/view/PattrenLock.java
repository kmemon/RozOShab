package com.ibrahim.rozoshab.Main.PatternActivity.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.ibrahim.rozoshab.Main.SplashActivity.view.Splash;
import com.ibrahim.rozoshab.R;

import java.util.List;

import io.paperdb.Paper;

public class PattrenLock extends AppCompatActivity {

    String save_pattern_key = "pattern key";
    PatternLockView patternLockView;
    String final_pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_pattren_lock);
//        getSupportActionBar().hide();
        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);

        if (save_pattern != null && !(save_pattern.equals("null"))){

            setContentView(R.layout.patternscreen);
            patternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view2);
            patternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);
                    if (final_pattern.equals(save_pattern)) {
                        startActivity(new Intent(PattrenLock.this, Splash.class));
                        finish();
                        //Toast.makeText(PattrenLock.this, "password correct", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(PattrenLock.this, "Incorrect Pattern", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onCleared() {

                }
            });
        }
        else {
            setContentView(R.layout.activity_pattren_lock);
            patternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
            patternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);
                }

                @Override
                public void onCleared() {

                }
            });

            Button button = (Button) findViewById(R.id.set_pattern);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(save_pattern_key,final_pattern);
                    Toast.makeText(PattrenLock.this, "save pattern ", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

        }
    }
}
