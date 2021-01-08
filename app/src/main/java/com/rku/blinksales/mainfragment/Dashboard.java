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
//   private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
//
//        private List<CardInstance> Data;
//
//        public RecyclerViewAdapter( List<CardInstance> data) {
//            this.Data = data;
//        }
//
//        @NonNull
//        @Override
//        public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
//            RecyclerViewAdapter.MyViewHolder holder = new RecyclerViewAdapter.MyViewHolder(view);
//            return holder;
//        }
//
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
//            try {
//                holder.card_name.setText(Data.get(position).getP_name());
//
//                holder.card_price.setText("Rs: "+Data.get(position).getP_price());
//                holder.card_img.setImageResource(Data.get(position).getP_image());
//                holder.itemView.setOnClickListener((View.OnClickListener) v -> {
//
//                    Toast.makeText(v.getContext(),Data.get(position).getP_name()+"  "+Data.get(position).getP_price(),Toast.LENGTH_LONG).show();
////               TextView txt = (TextView)v.findViewById(R.id.id_dashborad_total);
////               txt.setText(txt.getText().toString()+" "+Data.get(position).getP_price());
//                });
//
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return Data.size();
//        }
//
//        public  class MyViewHolder extends RecyclerView.ViewHolder{
//            TextView card_name,card_price;
//            ImageView card_img;
//            public MyViewHolder(@NonNull View itemView) {
//                super(itemView);
//                card_name = itemView.findViewById(R.id.card_name);
//                card_price= itemView.findViewById(R.id.card_price);
//                card_img = itemView.findViewById(R.id.card_img);
//            }
//        }
//    }
//
//    private class MainViewpagerFragment extends Fragment {
//
//        DatabaseHelper db;
//        RecyclerViewAdapter recyclerViewAdapter;
//        RecyclerView myrv;
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            // Inflate the layout for this fragment
////        db = new DatabaseHelper(getContext());
//            View view = inflater.inflate(R.layout.recyclerview_mainfragment, container, false);
//            String str = getArguments().getString("message");
//            myrv = view.findViewById(R.id.id_recyclerView);
//            recyclerViewAdapter =new RecyclerViewAdapter(db.getAllData(str));
//            myrv.setLayoutManager(new GridLayoutManager(getContext(), Utility.calculateNoOfColumns(getContext(),180)));
//            myrv.setAdapter(recyclerViewAdapter);
////        Toast.makeText(getContext(),String.valueOf(db.getAllData(str)),Toast.LENGTH_SHORT).show();
//            return  view;
//        }
//    }
//
//    private class ViewPagerAdapter extends FragmentPagerAdapter {
//
//        ArrayList<String> arr;
//        public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<String> arr) {
//            super(fm);
//            this.arr = arr;
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            MainViewpagerFragment objFragment = new MainViewpagerFragment();
////        position = position + 1;
//            Bundle bundle = new Bundle();
//            bundle.putString("message",arr.get(position));
//            objFragment.setArguments(bundle);
//            return objFragment;
//        }
//
//        @Override
//        public int getCount() {
//            return arr.size();
//        }
//
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return arr.get(position);
//        }
//    }
}

