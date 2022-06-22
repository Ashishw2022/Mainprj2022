package com.example.vcare.predictor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.vcare.R;

public class Loading extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1=getIntent();
                String str=intent1.getStringExtra("Disease_name");

                Intent intent3 = new Intent(Loading.this, Result.class);
                String dis = str;
                intent3.putExtra("Disease_name", dis);
                startActivity(intent3);
                //Intent i=new Intent(Loading.this,
                        //Result.class);
                //Intent is used to switch from one activity to another.

                //startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

}