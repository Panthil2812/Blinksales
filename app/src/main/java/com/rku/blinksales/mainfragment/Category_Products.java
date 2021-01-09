package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rku.blinksales.Adapter.CategoryRecyclerViewAdapter;
import com.rku.blinksales.Database.DatabaseHelper;
import com.rku.blinksales.R;
import com.rku.blinksales.form.Product_form;
import java.util.ArrayList;

public class Category_Products extends Fragment {
    DatabaseHelper db;
    RecyclerView myrv;
    RecyclerView.LayoutManager layoutManager;
    CategoryRecyclerViewAdapter ra;
    FloatingActionButton id_add_products;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_products, container, false);
        id_add_products = view.findViewById(R.id.id_add_products);
        db = new DatabaseHelper(getContext());
        ArrayList<String> str =  db.ALL_Category();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myrv = view.findViewById(R.id.id_category_recyclerView);
        ra = new CategoryRecyclerViewAdapter(view,getContext(),str);
        myrv.setLayoutManager(layoutManager);
        myrv.setAdapter(ra);
        id_add_products.setOnClickListener(v -> {
              startActivity(new Intent(getContext().getApplicationContext(), Product_form.class));
        });
        return view;

    }

}