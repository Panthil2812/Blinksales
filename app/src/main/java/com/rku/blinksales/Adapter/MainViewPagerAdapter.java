package com.rku.blinksales.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rku.blinksales.Fragment.MainViewpagerFragment;

import java.util.List;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    List<String> arr;
    public MainViewPagerAdapter(@NonNull FragmentManager fm, List<String> arr)
    {
        super(fm);
        this.arr = arr;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        MainViewpagerFragment objFragment = new MainViewpagerFragment();
//        position = position + 1;
        Bundle bundle = new Bundle();
        bundle.putString("message", arr.get(position));
        objFragment.setArguments(bundle);
        return objFragment;

    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arr.get(position);
    }
}

