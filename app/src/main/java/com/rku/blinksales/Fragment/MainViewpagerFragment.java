package com.rku.blinksales.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.Adapter.MainRecyclerViewAdapter;
import com.rku.blinksales.Adapter.ProductRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.ProductTable;
import com.rku.blinksales.Utility;

import java.util.List;

public class MainViewpagerFragment extends Fragment {

    DatabaseDao db;
    RecyclerView myrv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = MainRoomDatabase.getInstance(getContext()).getDao();
        View view = inflater.inflate(R.layout.recyclerview_mainfragment, container, false);
        String str = getArguments().getString("message");
        myrv = view.findViewById(R.id.id_recyclerView);

        final MainRecyclerViewAdapter recyclerViewAdapter =new MainRecyclerViewAdapter(getContext());
        db.getAllProduct(str).observe(this, notes -> recyclerViewAdapter.setNotes(notes));

        myrv.setLayoutManager(new GridLayoutManager(getContext(), Utility.calculateNoOfColumns(getContext(),180)));
        myrv.setHasFixedSize(true);

        myrv.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener(note -> {
            Toast.makeText(getContext(),note.getProduct_name(),Toast.LENGTH_SHORT).show();
        });
        return  view;
    }
}
