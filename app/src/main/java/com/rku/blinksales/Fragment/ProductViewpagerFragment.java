package com.rku.blinksales.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.Adapter.ProductRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.ProductTable;

import java.util.List;

public class ProductViewpagerFragment extends Fragment {

    DatabaseDao db;
    RecyclerView myrv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = MainRoomDatabase.getInstance(getContext()).getDao();
        View view = inflater.inflate(R.layout.recyclerview_mainfragment, container, false);
        String str = getArguments().getString("message");
//       // Toast.makeText(getContext(),"Category Name : "+str,Toast.LENGTH_LONG).show();
//
        myrv = view.findViewById(R.id.id_recyclerView);
        final ProductRecyclerViewAdapter recyclerViewAdapter =new ProductRecyclerViewAdapter();
//       // final CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter();
//        myrv.setLayoutManager(new GridLayoutManager(getContext(), Utility.calculateNoOfColumns(getContext(),180)));
        myrv.setLayoutManager(new LinearLayoutManager(getContext()));
        myrv.setHasFixedSize(true);
        myrv.setAdapter(recyclerViewAdapter);
        db.getCategoryProducts(str).observe(this, new Observer<List<ProductTable>>() {
            @Override
            public void onChanged(@Nullable List<ProductTable> notes) {
                recyclerViewAdapter.setNotes(notes);
            }
        });

//
        //Toast.makeText(getContext(),String.valueOf(db.getAllData(str)),Toast.LENGTH_SHORT).show();
        return  view;
    }
}
