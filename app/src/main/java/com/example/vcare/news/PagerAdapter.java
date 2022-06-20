package com.example.vcare.news;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class PagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
                return new HealthFragment();
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
