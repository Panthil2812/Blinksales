package com.rku.blinksales.mainfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rku.blinksales.Adapter.MainViewPagerAdapter;
import com.rku.blinksales.Database.DatabaseHelper;
import com.rku.blinksales.InstanceClass.CardInstance;
import com.rku.blinksales.R;
import com.rku.blinksales.Utility;

import java.util.ArrayList;
import java.util.List;

//implements GestureDetector.OnDoubleTapListener
public class Dashboard extends Fragment {
    ViewPager viewPager;
    MainViewPagerAdapter ViewPagerAdapter;
    TabLayout layout;
    DatabaseHelper db;
    TextView id_dashborad_total;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        db = new DatabaseHelper(getContext());
//        db.insertCategory("Fruit");
//        db.insertCategory("Dairy");
//        db.insertCategory("Vegetable");
//        db.insertCategory("Grocery");
//            db.insertData("Apple",50,"Fruit",R.drawable.p1);
//            db.insertData("Grapes",30,"Fruit",R.drawable.p2);
//            db.insertData("Pineapple",100,"Fruit",R.drawable.p3);
//            db.insertData("Kiwi",80,"Fruit",R.drawable.p4);
//            db.insertData("CusterdApple",50,"Fruit",R.drawable.p5);
//            db.insertData("Ladyfinger",50,"Vegetable",R.drawable.p5);
//            db.insertData("Onion",150,"Vegetable",R.drawable.p4);
//            db.insertData("Patato",50,"Vegetable",R.drawable.p2);
//            db.insertData("Tomatoes",60,"Vegetable",R.drawable.p3);
//            db.insertData("milk",20,"Dairy",R.drawable.p2);
//            db.insertData("butter",20,"Dairy",R.drawable.p3);
//            db.insertData("cheese",120,"Dairy",R.drawable.p5);
        id_dashborad_total = view.findViewById(R.id.id_dashborad_total);
        ArrayList<String> str = db.ALL_Category();
        Toast.makeText(getContext(),String.valueOf(str.size()),Toast.LENGTH_SHORT).show();
        viewPager = view.findViewById(R.id.viewpager);
        ViewPagerAdapter = new MainViewPagerAdapter(getFragmentManager(),str);
        viewPager.setAdapter(ViewPagerAdapter);
        layout = view.findViewById(R.id.tabs);
        layout.setupWithViewPager(viewPager);
        return view;
    }
}

