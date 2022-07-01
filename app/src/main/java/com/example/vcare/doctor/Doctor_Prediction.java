package com.example.vcare.doctor;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.vcare.R;
import com.example.vcare.admin.FragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Doctor_Prediction extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_appoapproval);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        Toast.makeText(Doctor_Prediction.this, "Swipe to mark the Appointment!", Toast.LENGTH_LONG).show();
        getTabs();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void getTabs()
    {
        final FragmentPagerAdapter fragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                fragmentPagerAdapter.addFragment(Doctor_prediction_waiting_approval.getInstance(),"Completed");
                fragmentPagerAdapter.addFragment(Doctor_appointments_waiting_approval.getInstance(),"Upcoming");

                viewPager.setAdapter(fragmentPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}
