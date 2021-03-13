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
import com.rku.blinksales.Roomdatabase.CartTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PendingCartTable;
import com.rku.blinksales.Roomdatabase.ProductTable;
import com.rku.blinksales.Utility;

import java.util.Calendar;
import java.util.Date;
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

        final MainRecyclerViewAdapter recyclerViewAdapter = new MainRecyclerViewAdapter(getContext());
        db.getAllProduct(str, true).observe(this, notes -> recyclerViewAdapter.setNotes(notes));

        myrv.setLayoutManager(new GridLayoutManager(getContext(), Utility.calculateNoOfColumns(getContext(), 180)));
        myrv.setHasFixedSize(true);

        myrv.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener(note -> {

            if(db.countCart() == 0) {
                // all cart is pending cart and zero cart created
                PendingCartTable pendingCartTable = new PendingCartTable(1, null, new Date());
                db.insertPendingCartTable(pendingCartTable);

                int cartId = db.findActivityIdCart();
                CartTable cartTable = new CartTable(cartId,note.getProduct_id(),note.getProduct_image_uri(),note.getProduct_name(),
                note.getProduct_category(),note.getProduct_mrp(),note.getProduct_selling_price(),note.getProduct_qty(),
                        1.0,note.getProduct_unit(),note.getProduct_price_unit(),note.getProduct_barcode(),
                        note.getProduct_stock(),note.getProduct_is_include(),note.getGst(),note.getGst_amount(),
                        note.getProduct_selling_price(),note.getDiscount(),note.getHSN());
                db.insertCartTable(cartTable);
                Toast.makeText(getContext(),"add product in cart", Toast.LENGTH_SHORT).show();
            } else if(db.countCart()  == 1){
                //activity cart
                int cartId = db.findActivityIdCart();
                CartTable cartTable = new CartTable(cartId,note.getProduct_id(),note.getProduct_image_uri(),note.getProduct_name(),
                        note.getProduct_category(),note.getProduct_mrp(),note.getProduct_selling_price(),note.getProduct_qty(),
                        1.0,note.getProduct_unit(),note.getProduct_price_unit(),note.getProduct_barcode(),
                        note.getProduct_stock(),note.getProduct_is_include(),note.getGst(),note.getGst_amount(),
                        note.getProduct_selling_price(),note.getDiscount(),note.getHSN());
                db.insertCartTable(cartTable);
                Toast.makeText(getContext(),"add product in cart", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
