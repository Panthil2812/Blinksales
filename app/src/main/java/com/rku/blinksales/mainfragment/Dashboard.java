package com.rku.blinksales.mainfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.rku.blinksales.Adapter.MainViewPagerAdapter;
import com.rku.blinksales.InstanceClass.List_Category;
import com.rku.blinksales.MainActivity;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CategoryTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import java.util.ArrayList;
import java.util.List;

//implements GestureDetector.OnDoubleTapListener
public class Dashboard extends Fragment {

    TabLayout layout;
    DatabaseDao db;
    TextView id_dashborad_total;
    InputMethodManager imm;
    ViewGroup viewContainer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        db = MainRoomDatabase.getInstance(getContext()).getDao();
        id_dashborad_total = view.findViewById(R.id.id_dashborad_total);

//        list_text = db.getAllCategory();
//        Toast.makeText(getContext(),"Category : "+list_text, Toast.LENGTH_SHORT).show();
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        MainViewPagerAdapter ViewPagerAdapter = new MainViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(ViewPagerAdapter);
        db.getAllCategory().observe(this, new Observer<List<CategoryTable>>() {
            @Override
            public void onChanged(@Nullable List<CategoryTable> notes) {
                ViewPagerAdapter.setNotes(notes);
            }
        });
        TabLayout layout = view.findViewById(R.id.tabs);
        layout.setupWithViewPager(viewPager);
        return view;
    }

}

