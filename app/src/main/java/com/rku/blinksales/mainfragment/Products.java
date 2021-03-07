package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.tabs.TabLayout;
import com.rku.blinksales.Adapter.MainViewPagerAdapter;
import com.rku.blinksales.Adapter.ProductRecyclerViewAdapter;
import com.rku.blinksales.Adapter.ViewPagerAdapter;
import com.rku.blinksales.InstanceClass.List_Category;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CategoryTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.ProductTable;
import com.rku.blinksales.form.Expense_list_form;
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
        myrv = view.findViewById(R.id.id_product_recyclerView);
        final ProductRecyclerViewAdapter recyclerViewAdapter =new ProductRecyclerViewAdapter(getContext());
        myrv.setLayoutManager(new LinearLayoutManager(getContext()));
        myrv.setHasFixedSize(true);
        myrv.setAdapter(recyclerViewAdapter);
        db.getCategoryProducts().observe(this, new Observer<List<ProductTable>>() {
            @Override
            public void onChanged(@Nullable List<ProductTable> notes) {
                recyclerViewAdapter.setNotes(notes);
            }
        });
        recyclerViewAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(getContext(), Product_form.class);
            intent.putExtra("id", note.getProduct_id());
            intent.putExtra("image", note.getProduct_image_uri());
            intent.putExtra("pro_name", note.getProduct_name());
            intent.putExtra("category", note.getProduct_category());
            intent.putExtra("mrp", note.getProduct_mrp().toString());
            intent.putExtra("price", note.getProduct_selling_price().toString());
            intent.putExtra("qty", note.getProduct_qty());
            intent.putExtra("unit", note.getProduct_unit());
            intent.putExtra("barcode", note.getProduct_barcode());
            intent.putExtra("gst", note.getGst().toString());
            intent.putExtra("hsn", note.getHSN());
            intent.putExtra("stock", note.getProduct_stock().booleanValue());
            startActivity(intent);
        });
        id_add_products.setOnClickListener(v -> {
              startActivity(new Intent(getContext().getApplicationContext(), Product_form.class));
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout.delete_dialog, viewGroup, false);
                builder.setView(dialogView);
                Button OK = dialogView.findViewById(R.id.Dialog_ok);
                Button Cancel = dialogView.findViewById(R.id.Dialog_cancel);
                TextView title = dialogView.findViewById(R.id.Dialog_title);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            db.deleteProductTable(recyclerViewAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(getActivity(), "Product  deleted", Toast.LENGTH_SHORT).show();
                            alertDialog.cancel();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }

                    }
                });
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            recyclerViewAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            Toast.makeText(getActivity(), "Product Not deleted", Toast.LENGTH_SHORT).show();
                            alertDialog.cancel();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    View itemView = viewHolder.itemView;
                    Paint p = new Paint();
                    try {
                        if (dX > 0) {
                            p.setColor(getResources().getColor(R.color.colorPrimary));
                            c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                    (float) itemView.getBottom(), p);
                        } else {
                            p.setColor(getResources().getColor(R.color.colorPrimary));
                            c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                    (float) itemView.getRight(), (float) itemView.getBottom(), p);
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        }).attachToRecyclerView(myrv);
        return view;

    }

}