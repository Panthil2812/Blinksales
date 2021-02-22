package com.rku.blinksales.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.Adapter.MainRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Utility;

public class MainViewpagerFragment extends Fragment {

    DatabaseDao db;
    MainRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView myrv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = MainRoomDatabase.getInstance(getContext()).getDao();
        View view = inflater.inflate(R.layout.recyclerview_mainfragment, container, false);
        String str = getArguments().getString("message");
        Toast.makeText(getContext(),"Category Name : "+str,Toast.LENGTH_LONG).show();

//        myrv = view.findViewById(R.id.id_recyclerView);
//      //  recyclerViewAdapter =new MainRecyclerViewAdapter(db.getAllData(str));
//        myrv.setLayoutManager(new GridLayoutManager(getContext(), Utility.calculateNoOfColumns(getContext(),180)));
//        myrv.setAdapter(recyclerViewAdapter);
//        Toast.makeText(getContext(),String.valueOf(db.getAllData(str)),Toast.LENGTH_SHORT).show();
        return  view;
    }
}
