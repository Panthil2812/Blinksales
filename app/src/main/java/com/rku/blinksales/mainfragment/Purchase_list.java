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
import com.rku.blinksales.Adapter.PendingRecyclerViewAdapter;
import com.rku.blinksales.Adapter.PurchaseRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PendingCartTable;
import com.rku.blinksales.Roomdatabase.ProductTable;
import com.rku.blinksales.Roomdatabase.PurchaseTable;
import com.rku.blinksales.form.Purchase_list_form;
import com.rku.blinksales.form.Vendor_list_form;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Purchase_list  extends Fragment {
    FloatingActionButton id_add_purchase;
    ImageButton filter_Calendar;
    LinearLayout layout_from, layout_to;
    Date startSelectDate, endSelectDate;
    TextView id_from_date, id_to_date, Dialog_cancel, Dialog_done;
    CalendarView date_picker_actions;
    RecyclerView purchase_list_recyclerview;
    SearchView purchase_search_view;
    DatabaseDao db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_purchase_list);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh =getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);

        db = MainRoomDatabase.getInstance(getContext()).getDao();

        //view main code
        id_add_purchase = view.findViewById(R.id.id_add_purchase);
        filter_Calendar = view.findViewById(R.id.purchase_list_filter_Calendar);
        purchase_list_recyclerview = view.findViewById(R.id.purchase_list_recyclerview);
        purchase_search_view = view.findViewById(R.id.purchase_search_view);
        int searchCloseButtonId = purchase_search_view.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.purchase_search_view.findViewById(searchCloseButtonId);


        purchase_list_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        purchase_list_recyclerview.setHasFixedSize(true);
        final PurchaseRecyclerViewAdapter adapter = new PurchaseRecyclerViewAdapter(getContext());
        purchase_list_recyclerview.setAdapter(adapter);
        try {
            db.getAllPurchaseTable().observe(this, new Observer<List<PurchaseTable>>() {

                @Override
                public void onChanged(@Nullable List<PurchaseTable> notes) {
                    adapter.setNotes(notes);
                }
            });

        } catch (Exception e)
        {
            e.getStackTrace();
        }
        adapter.setOnItemClickListener(new PurchaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PurchaseTable note) {
                Intent intent_edit = new Intent(getActivity(),Purchase_list_form.class);
                intent_edit.putExtra("id",note.getPurchase_id());
                startActivity(intent_edit);
                purchase_list_recyclerview.setAdapter(adapter);

            }
        });
        // add new purchase bill
        id_add_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(), Purchase_list_form.class));
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
                        .setTitle("Delete Purchase list")
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    PurchaseTable note = adapter.getNoteAt(viewHolder.getAdapterPosition());
                                    File file = new File(note.getBill_image());
                                    if (file.exists()) {
                                        file.delete();
                                    }
                                    db.deletePurchaseTable(note);
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
        }).attachToRecyclerView(purchase_list_recyclerview);

        // calendar dialog
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
                    purchase_search_view.setQuery("filter",false);
                    db.getFilterPurchaseTable(startSelectDate.getTime(),endSelectDate.getTime()).observe(getViewLifecycleOwner(), new Observer<List<PurchaseTable>>() {
                        @Override
                        public void onChanged(List<PurchaseTable> notes) {
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


        //search bill
        purchase_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                db.getFilterPurchaseTable(str).observe(getViewLifecycleOwner(), new Observer<List<PurchaseTable>>() {
                    @Override
                    public void onChanged(List<PurchaseTable> notes) {
                        adapter.setNotes(notes);
                    }
                });

            }
        });

        //SearchView Close Button Event
        closeButton.setOnClickListener(v -> {
            purchase_search_view.setQuery("", false);
            purchase_search_view.clearFocus();
            purchase_list_recyclerview.setAdapter(adapter);
            closeKeyboard();
        });
        return view;
    }
    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            purchase_search_view.clearFocus();
        }
    }
}
