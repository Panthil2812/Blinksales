package com.rku.blinksales.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rku.blinksales.Fragment.ProductViewpagerFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<String> arr;
    public ViewPagerAdapter(@NonNull FragmentManager fm, List<String> arr) {
        super(fm);
        this.arr = arr;

    }
//    private String[]  Category(){
//        String array[] ={"Fruit","vegetables","Dairy","Heart-healthy oils","Elective"};
//        return array;
//    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        ProductViewpagerFragment objFragment = new ProductViewpagerFragment();
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
