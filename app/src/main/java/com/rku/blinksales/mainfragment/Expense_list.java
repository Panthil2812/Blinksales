package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rku.blinksales.Adapter.CategoryRecyclerViewAdapter;
import com.rku.blinksales.Adapter.ListRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CategoryTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.ExpenseTable;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.form.Expense_list_form;
import com.rku.blinksales.form.Product_form;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

public class Expense_list extends Fragment {
    FloatingActionButton id_add_expense;
    DatabaseDao db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        db = MainRoomDatabase.getInstance(getContext()).getDao();
        id_add_expense = view.findViewById(R.id.id_add_expense);
        RecyclerView recyclerView = view.findViewById(R.id.expense_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ListRecyclerViewAdapter adapter = new ListRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        db.getAllExpenseList().observe(this, new Observer<List<ExpenseTable>>() {

            @Override
            public void onChanged(@Nullable List<ExpenseTable> notes) {
                adapter.setNotes(notes);
            }
        });

        //  add new Expense form in click event.....
        id_add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(), Expense_list_form.class));
            }
        });


        adapter.setOnItemClickListener(new ListRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExpenseTable note) {
                Intent intent = new Intent(getContext(), Expense_list_form.class);
                intent.putExtra("id", note.getId());
                intent.putExtra("name", note.getExp_name());
                intent.putExtra("amount", note.getExp_amount());
                intent.putExtra("type", note.getExp_type());
                DateFormat df_medium_us = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
                String df_medium_us_str = df_medium_us.format(note.getExp_date());
                intent.putExtra("date", df_medium_us_str);
                startActivity(intent);
            }
        });

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
                        db.deleteExpenseList(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                        // db.deleteCategory(adapter.getNoteAt(viewHolder.getAdapterPosition()));
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
                        p.setColor(getResources().getColor(R.color.colorPrimary));
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);
                    } else {
                        p.setColor(getResources().getColor(R.color.colorPrimary));
                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom(), p);
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        }).attachToRecyclerView(recyclerView);
        return view;
    }


}
