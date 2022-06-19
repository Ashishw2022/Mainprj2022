package com.example.vcare.predictor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.vcare.R;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent1=getIntent();
        String str=intent1.getStringExtra("Disease_name");
        Log.d("main", "Main function 2"+str);
        TextView tv1 =  findViewById(R.id.predicted_disease_name);
        tv1.setText(str);

    }
}