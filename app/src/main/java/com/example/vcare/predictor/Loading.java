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
                String str2=intent1.getStringExtra("pname");
                String str3=intent1.getStringExtra("disease_category");
                String str4=intent1.getStringExtra("Symptoms");
                String str5=intent1.getStringExtra("age");

                Intent intent3 = new Intent(Loading.this, Result.class);
                String dis = str;
                String pname=str2;
                String dc=str3;
                String syp=str4;
                intent3.putExtra("Disease_name", dis);
                intent3.putExtra("name", pname);
                intent3.putExtra("disease_category", dc);
                intent3.putExtra("Symptoms", syp);
                intent3.putExtra("age",str5);


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