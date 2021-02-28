package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.tabs.TabLayout;
import com.rku.blinksales.Adapter.MainViewPagerAdapter;
import com.rku.blinksales.Adapter.ViewPagerAdapter;
import com.rku.blinksales.InstanceClass.List_Category;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CategoryTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.form.Product_form;
import java.util.ArrayList;
import java.util.List;

public class Products extends Fragment {
    DatabaseDao db;
    RecyclerView myrv;
    RecyclerView.LayoutManager layoutManager;
   // CategoryRecyclerViewAdapter ra;
    FloatingActionButton id_add_products;
    ArrayList<String> list_text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_products, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_products);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh =getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);
        id_add_products = view.findViewById(R.id.id_add_products);
        db = MainRoomDatabase.getInstance(getContext()).getDao();

List<String> Data = db.getCategory();
        ViewPager viewPager = view.findViewById(R.id.ProductViewPager);

        ViewPagerAdapter ViewPagerAdapter = new ViewPagerAdapter(getFragmentManager(),Data);
        viewPager.setAdapter(ViewPagerAdapter);
        TabLayout layout = view.findViewById(R.id.tabsCategory);
        layout.setupWithViewPager(viewPager);
//        db.getAllCategory().observe(this, new Observer<List<CategoryTable>>() {
//            @Override
//            public void onChanged(@Nullable List<CategoryTable> notes) {
//                ViewPagerAdapter.setNotes(notes);
//            }
//        });

//        RecyclerView recyclerView =view.findViewById(R.id.id_category_recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setHasFixedSize(true);
//        final CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(view,getContext(),list_text);
//        recyclerView.setAdapter(adapter);
//        db.getAllCategory().observe(this, new Observer<List<CategoryTable>>() {
//            @Override
//            public void onChanged(@Nullable List<CategoryTable> notes) {
//                //adapter.setNotes(notes);
//            }
//        });

//        list_text = db.getAllCategory();
//
//        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        myrv = view.findViewById(R.id.id_category_recyclerView);
//        ra = new CategoryRecyclerViewAdapter(view,getContext(),list_text);
//        myrv.setLayoutManager(layoutManager);
//        myrv.setAdapter(ra);



        id_add_products.setOnClickListener(v -> {
              startActivity(new Intent(getContext().getApplicationContext(), Product_form.class));
        });
        return view;

    }

}