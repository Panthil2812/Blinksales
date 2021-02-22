package com.rku.blinksales.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;

import com.rku.blinksales.Fragment.MainViewpagerFragment;
import com.rku.blinksales.InstanceClass.List_Category;
import com.rku.blinksales.Roomdatabase.CategoryTable;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private List<CategoryTable> arr = new ArrayList<>();
    //LiveData<List<String>> arr;
    public MainViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        MainViewpagerFragment objFragment = new MainViewpagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message",arr.get(position).getCategory_name());
        objFragment.setArguments(bundle);
        return objFragment;

    }
    public void setNotes(List<CategoryTable> notes) {
        this.arr = notes;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arr.get(position).getCategory_name();
    }
}

