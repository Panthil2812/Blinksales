package com.rku.blinksales.mainfragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.rku.blinksales.Adapter.MainViewPagerAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CartTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;

import java.util.List;
import java.util.logging.Handler;

//implements GestureDetector.OnDoubleTapListener
public class Dashboard extends Fragment {
    DatabaseDao db;
    ImageButton go_to_cart, go_to_Pending;
    NavigationView navigationView;
    public static TextView id_dashboard_total_items, id_dashboard_total_amount;
    int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_dashboard);
        FragmentManager fm = getActivity().getSupportFragmentManager();

        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh = getActivity().findViewById(R.id.id_btn_refresh);
        navigationView = getActivity().findViewById(R.id.nav_menu);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);


        db = MainRoomDatabase.getInstance(getContext()).getDao();
        List<String> Data = db.getCategory();
        go_to_cart = view.findViewById(R.id.go_to_cart);
        go_to_Pending = view.findViewById(R.id.go_to_Pending);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        TabLayout layout = view.findViewById(R.id.tabs);
        id_dashboard_total_items = view.findViewById(R.id.id_dashboard_total_items);
        id_dashboard_total_amount = view.findViewById(R.id.id_dashboard_total_amount);
        layout.setupWithViewPager(viewPager);
        MainViewPagerAdapter ViewPagerAdapter = new MainViewPagerAdapter(getFragmentManager(), Data);
        viewPager.setAdapter(ViewPagerAdapter);
        if (db.findActivityIdCart() != 0 && db.totalCartItem(db.findActivityIdCart()) != 0) {
            id_dashboard_total_items.setText(db.totalCartItem(db.findActivityIdCart()).toString());
            id_dashboard_total_amount.setText(db.totalCartAmount(db.findActivityIdCart()).toString() + " ₹ /-");

        } else {
            id_dashboard_total_items.setText("0");
            id_dashboard_total_amount.setText("0 ₹ /-");
        }
        //go to cart
        go_to_cart.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Cart())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_cart);

        });
        //go to PendingCart
        go_to_Pending.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Pending_cart())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_pending_cart);

        });

        return view;
    }

}

