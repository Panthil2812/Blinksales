package com.rku.blinksales.mainfragment;


import android.os.Bundle;
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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.rku.blinksales.Adapter.MainViewPagerAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;

import java.util.List;

//implements GestureDetector.OnDoubleTapListener
public class Dashboard extends Fragment {
    DatabaseDao db;
    ImageButton go_to_cart;
    NavigationView navigationView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
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

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        db = MainRoomDatabase.getInstance(getContext()).getDao();
        List<String> Data = db.getCategory();
        go_to_cart = view.findViewById(R.id.go_to_cart);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        TabLayout layout = view.findViewById(R.id.tabs);
        layout.setupWithViewPager(viewPager);
        MainViewPagerAdapter ViewPagerAdapter = new MainViewPagerAdapter(getFragmentManager(), Data);
        viewPager.setAdapter(ViewPagerAdapter);

        //go to cart
        go_to_cart.setOnClickListener(v -> {
           getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Cart())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_cart);

        });

        return view;
    }

}

