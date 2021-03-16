package com.rku.blinksales.mainfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rku.blinksales.Adapter.PurchaseRecyclerViewAdapter;
import com.rku.blinksales.Adapter.PurchaseReturnRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PurchaseReturnTable;
import com.rku.blinksales.Roomdatabase.PurchaseTable;
import com.rku.blinksales.form.Purchase_list_form;
import com.rku.blinksales.form.Purchase_return_form;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Purchase_return  extends Fragment
{
    FloatingActionButton id_add_return;
    ImageButton filter_Calendar;
    LinearLayout layout_from, layout_to;
    Date startSelectDate, endSelectDate;
    TextView id_from_date, id_to_date, Dialog_cancel, Dialog_done;
    CalendarView date_picker_actions;
    RecyclerView purchase_return_recyclerView;
    SearchView simpleSearchView;
    DatabaseDao db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_return, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_purchase_return);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh =getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);


        db = MainRoomDatabase.getInstance(getContext()).getDao();
        id_add_return = view.findViewById(R.id.id_add_return);


        purchase_return_recyclerView = view.findViewById(R.id.purchase_return_recyclerView);
        simpleSearchView = view.findViewById(R.id.simpleSearchView);
        int searchCloseButtonId = simpleSearchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.simpleSearchView.findViewById(searchCloseButtonId);



        purchase_return_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        purchase_return_recyclerView.setHasFixedSize(true);
        final PurchaseReturnRecyclerViewAdapter adapter = new PurchaseReturnRecyclerViewAdapter(getContext());
        purchase_return_recyclerView.setAdapter(adapter);
        try {
            db.getAllPurchaseReturnTable().observe(this, new Observer<List<PurchaseReturnTable>>() {

                @Override
                public void onChanged(@Nullable List<PurchaseReturnTable> notes) {
                    adapter.setNotes(notes);
                }
            });

        } catch (Exception e)
        {
            e.getStackTrace();
        }
        id_add_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(), Purchase_return_form.class));
            }
        });

        // delete bill
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(getContext(),R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background)
                        .setTitle("Delete Pending Cart")
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    PurchaseReturnTable note = adapter.getNoteAt(viewHolder.getAdapterPosition());
                                    File file = new File(note.getBill_image());
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                    db.deletePurchaseReturnTable(note);
                                } catch (Exception e) {
                                    e.getStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        })
                        .show();
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
        }).attachToRecyclerView(purchase_return_recyclerView);

        //search bill
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    Toast.makeText(getContext(), "Search : " + query, Toast.LENGTH_LONG).show();
                    GetFilterData(query);
                }
                closeKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  adapter.getFilter().filter(newText);
                if (newText != null) {
                    //Toast.makeText(getContext(),"OnSearch : "+newText,Toast.LENGTH_LONG).show();
                    GetFilterData(newText);
                }
                return true;
            }

            private void GetFilterData(String str) {

                str = "%" + str + "%";
                db.getFilterPurchaseReturnTable(str).observe(getViewLifecycleOwner(), new Observer<List<PurchaseReturnTable>>() {
                    @Override
                    public void onChanged(List<PurchaseReturnTable> notes) {
                        adapter.setNotes(notes);
                    }
                });

            }
        });


        //view main code
        filter_Calendar = view.findViewById(R.id.purchase_filter_Calendar);
        filter_Calendar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            ViewGroup viewGroup = getView().findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout.from_to_dialog, viewGroup, false);
            builder.setView(dialogView);
            layout_from = dialogView.findViewById(R.id.layout_from);
            layout_to = dialogView.findViewById(R.id.layout_to);
            id_from_date = dialogView.findViewById(R.id.id_from_date);
            id_to_date = dialogView.findViewById(R.id.id_to_date);
            Dialog_cancel = dialogView.findViewById(R.id.Dialog_cancel);
            Dialog_done = dialogView.findViewById(R.id.Dialog_done);
            date_picker_actions = dialogView.findViewById(R.id.date_picker_actions);
            date_picker_actions.setMaxDate(System.currentTimeMillis());


            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            date_picker_actions.setOnDateChangeListener((calendarView, year, monthOfYear, dayOfMonth) -> {
                layout_from.setBackground(getContext().getDrawable(R.drawable.dialog_true));
                layout_to.setBackground(getContext().getDrawable(R.drawable.dialog_false));
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                startSelectDate = cal.getTime();
                DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                String startDate = formatter.format(startSelectDate).trim();
                id_from_date.setText(startDate);
                Toast.makeText(getContext(), startDate, Toast.LENGTH_SHORT).show();

            });
            //from date set
            layout_from.setOnClickListener(v1 -> {
                layout_from.setBackground(getContext().getDrawable(R.drawable.dialog_true));
                layout_to.setBackground(getContext().getDrawable(R.drawable.dialog_false));
                if (!id_from_date.getText().toString().trim().equals("dd-MMM-yyyy")) {
                    date_picker_actions.setMinDate(startSelectDate.getTime() - startSelectDate.getTime());
                }
                if (id_from_date.getText().toString().trim().equals("dd-MMM-yyyy")) {
                    date_picker_actions.setDate(System.currentTimeMillis());
                } else {
                    date_picker_actions.setDate(startSelectDate.getTime());
                }

                date_picker_actions.setOnDateChangeListener((calendarView, year, monthOfYear, dayOfMonth) -> {

                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(0);
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    startSelectDate = cal.getTime();
                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    String startDate = formatter.format(startSelectDate).trim();
                    id_from_date.setText(startDate);
                    Toast.makeText(getContext(), startDate, Toast.LENGTH_SHORT).show();

                });
            });

            //to date set
            layout_to.setOnClickListener(v1 -> {
                layout_to.setBackground(getContext().getDrawable(R.drawable.dialog_true));
                layout_from.setBackground(getContext().getDrawable(R.drawable.dialog_false));
                if (id_to_date.getText().toString().trim().equals("dd-MMM-yyyy")) {
                    date_picker_actions.setDate(System.currentTimeMillis());
                } else {
                    date_picker_actions.setDate(endSelectDate.getTime());
                }
                if (!id_from_date.getText().toString().trim().equals("dd-MMM-yyyy")) {
                    date_picker_actions.setMinDate(startSelectDate.getTime());
                }
                date_picker_actions.setOnDateChangeListener((calendarView, year, monthOfYear, dayOfMonth) -> {

                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(0);
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    endSelectDate = cal.getTime();
                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    String endDate = formatter.format(endSelectDate).trim();
                    id_to_date.setText(endDate);

                    Toast.makeText(getContext(), endDate, Toast.LENGTH_SHORT).show();

                });
            });

            //dialog done click event
            Dialog_done.setOnClickListener(v1 -> {
                if (id_from_date.getText().toString().trim().equals("dd-MMM-yyyy")) {
                    Toast.makeText(getContext(), "Please Select From Date", Toast.LENGTH_SHORT).show();
                } else if (id_to_date.getText().toString().trim().equals("dd-MMM-yyyy")) {
                    Toast.makeText(getContext(), "Please Select To Date", Toast.LENGTH_SHORT).show();
                } else {
                    simpleSearchView.setQuery("filter",false);
                    db.getFilterPurchaseReturnTable(startSelectDate.getTime(),endSelectDate.getTime()).observe(getViewLifecycleOwner(), new Observer<List<PurchaseReturnTable>>() {
                        @Override
                        public void onChanged(List<PurchaseReturnTable> notes) {
                            adapter.setNotes(notes);
                        }
                    });
                    // Toast.makeText(getContext(), startSelectDate.getTime() + " TO " + endSelectDate.getTime(), Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            });

            Dialog_cancel.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });

        });
        return view;
    }
    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            simpleSearchView.clearFocus();
        }
    }
}