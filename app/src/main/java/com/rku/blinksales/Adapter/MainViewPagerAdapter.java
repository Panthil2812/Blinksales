package com.rku.blinksales.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.Database.DatabaseHelper;
import com.rku.blinksales.Fragment.MainViewpagerFragment;
import com.rku.blinksales.R;
import com.rku.blinksales.Utility;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<String> arr;
    public MainViewPagerAdapter(@NonNull FragmentManager fm,ArrayList<String> arr) {
        super(fm);
        this.arr = arr;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        MainViewpagerFragment objFragment = new MainViewpagerFragment();
//        position = position + 1;
        Bundle bundle = new Bundle();
        bundle.putString("message",arr.get(position));
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

