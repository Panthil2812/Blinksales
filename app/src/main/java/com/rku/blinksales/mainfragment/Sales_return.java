package com.rku.blinksales.mainfragment;

import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rku.blinksales.Adapter.BillListRecyclerViewAdapter;
import com.rku.blinksales.Adapter.SalesReturnRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.BillTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.SalesReturnTable;
import com.rku.blinksales.form.Expense_list_form;
import com.rku.blinksales.form.Sales_return_form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Sales_return extends Fragment {
    FloatingActionButton id_add_sales;
    ImageButton filter_Calendar;
    LinearLayout layout_from, layout_to;
    Date startSelectDate, endSelectDate;
    TextView id_from_date, id_to_date, Dialog_cancel, Dialog_done;
    CalendarView date_picker_actions;
    RecyclerView sales_return_recyclerView;
    SearchView search_sales_return;
    DatabaseDao db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales_return, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_sales_return);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh =getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);
        db = MainRoomDatabase.getInstance(getContext()).getDao();

        //main code
        id_add_sales = view.findViewById(R.id.id_add_sales);
        sales_return_recyclerView = view.findViewById(R.id.sales_return_recyclerView);
        search_sales_return = view.findViewById(R.id.search_sales_return);
        filter_Calendar = view.findViewById(R.id.sales_filter_Calendar);
        int searchCloseButtonId = search_sales_return.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.search_sales_return.findViewById(searchCloseButtonId);

        final SalesReturnRecyclerViewAdapter recyclerViewAdapter = new SalesReturnRecyclerViewAdapter(getContext());
        sales_return_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sales_return_recyclerView.setHasFixedSize(true);
        sales_return_recyclerView.setAdapter(recyclerViewAdapter);
        db.getAllSalesReturnTable().observe(this, new Observer<List<SalesReturnTable>>() {
            @Override
            public void onChanged(@Nullable List<SalesReturnTable> notes) {
                recyclerViewAdapter.setNotes(notes);
            }
        });

        //search bill
        search_sales_return.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
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
                db.getFilterSalesReturnTable(str).observe(getViewLifecycleOwner(), new Observer<List<SalesReturnTable>>() {
                    @Override
                    public void onChanged(List<SalesReturnTable> notes) {
                        recyclerViewAdapter.setNotes(notes);
                    }
                });

            }
        });
        id_add_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(), Sales_return_form.class));
//                Toast.makeText(getContext(), "Open sales return form", Toast.LENGTH_SHORT).show();
            }
        });
        //SearchView Close Button Event
        closeButton.setOnClickListener(v -> {
            search_sales_return.setQuery("", false);
            search_sales_return.clearFocus();
            closeKeyboard();
        });

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
                    search_sales_return.setQuery("filter",false);
                    db.getFilterSalesReturnTable(startSelectDate.getTime(),endSelectDate.getTime()).observe(getViewLifecycleOwner(), new Observer<List<SalesReturnTable>>() {
                        @Override
                        public void onChanged(List<SalesReturnTable> notes) {
                            recyclerViewAdapter.setNotes(notes);
                        }
                    });
                    // Toast.makeText(getContext(), startSelectDate.getTime() + " TO " + endSelectDate.getTime(), Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();                }
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
            search_sales_return.clearFocus();
        }
    }
}