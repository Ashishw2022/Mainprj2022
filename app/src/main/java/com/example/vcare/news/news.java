package com.example.vcare.news;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.vcare.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class news extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem mHome, mHealth, mSports, mScience, mEntertainment, mTechnology;
    PagerAdapter pagerAdapter;
    Toolbar toolbar;

    String api = "667588f054de4f4fb72bcb20ab86fa97";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.fragment_container);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 1);
        viewPager.setAdapter(pagerAdapter);



        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));




    }
}