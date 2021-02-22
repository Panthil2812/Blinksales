package com.rku.blinksales.mainfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.rku.blinksales.Adapter.CategoryRecyclerViewAdapter;
import com.rku.blinksales.Adapter.MainViewPagerAdapter;
import com.rku.blinksales.InstanceClass.List_Category;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CategoryTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.form.Product_form;

import java.util.ArrayList;
import java.util.List;

public class Category extends Fragment {
    final int add = -1;
    DatabaseDao db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        FloatingActionButton id_add_category = view.findViewById(R.id.button_add_category);
        db = MainRoomDatabase.getInstance(getContext()).getDao();
        RecyclerView recyclerView = view.findViewById(R.id.CategoryRecycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        db.getAllCategory().observe(this, new Observer<List<CategoryTable>>() {

            @Override
            public void onChanged(@Nullable List<CategoryTable> notes) {
                adapter.setNotes(notes);
            }
        });

        //  Edit Category Name
        adapter.setOnItemClickListener(   note -> CategoryDialog(getView(), note.getCategory_name(), note.getId())  );

        //  Add Category Name
        id_add_category.setOnClickListener( v -> CategoryDialog(getView(), null, add ));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

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
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteCategory(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(getActivity(), "Category  deleted", Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                    }
                });
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        Toast.makeText(getActivity(), "Category Not deleted", Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                    }
                });
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    View itemView = viewHolder.itemView;

                    Paint p = new Paint();
                    if (dX > 0) {
                        /* Set your color for positive displacement */
                        p.setColor(getResources().getColor(R.color.colorAccent));

                        // Draw Rect with varying right side, equal to displacement dX
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);
                    } else {
                        /* Set your color for negative displacement */
                        p.setColor(getResources().getColor(R.color.colorAccent));
                        // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom(), p);
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        }).attachToRecyclerView(recyclerView);
        return view;
    }

    //  Dialog Box For Add And Edit Category Name
    private void CategoryDialog(View v, String category_name, int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ViewGroup viewGroup = v.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.catagory_form_bottom_sheet, viewGroup, false);
        builder.setView(dialogView);
        Button btnAdd = dialogView.findViewById(R.id.id_cat_btn_save);
        EditText editName = dialogView.findViewById(R.id.id_cat_name);
        AlertDialog alertDialog = builder.create();
        if (add != type) {
            editName.setText(category_name);
        }
        alertDialog.show();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = editName.getText().toString().trim();
                if (category.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_LONG).show();
                } else {
                    CategoryTable categoryTable = new CategoryTable(category);
                    if (add == type) {
                        db.insertCategory(categoryTable);
                        Toast.makeText(getActivity(), "ADD : " + category, Toast.LENGTH_LONG).show();
                    } else {
                        categoryTable.setId(type);
                        db.updateCategory(categoryTable);
                        Toast.makeText(getActivity(), "EDIT : " + category, Toast.LENGTH_LONG).show();
                    }
                    alertDialog.dismiss();
                }
            }
        });
    }



}