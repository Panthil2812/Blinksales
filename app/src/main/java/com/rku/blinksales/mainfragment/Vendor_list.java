package com.rku.blinksales.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.SearchView;
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
import com.rku.blinksales.Adapter.VendorListRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.VendorTable;
import com.rku.blinksales.form.Vendor_list_form;
import java.util.List;
public class Vendor_list extends Fragment {
    FloatingActionButton id_add_vendor;
    DatabaseDao db;
    SearchView vendor_searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendor_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_vendor_list);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh =getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);
        db = MainRoomDatabase.getInstance(getContext()).getDao();
        id_add_vendor = view.findViewById(R.id.id_add_vendor);
        vendor_searchView = view.findViewById(R.id.vendor_searchView);
        RecyclerView recyclerView = view.findViewById(R.id.vendor_list_recyclerview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final VendorListRecyclerViewAdapter adapter = new VendorListRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        db.getAllVendorTable().observe(this, new Observer<List<VendorTable>>() {

            @Override
            public void onChanged(@Nullable List<VendorTable> notes) {
                adapter.setNotes(notes);
            }
        });

        adapter.setOnItemClickListener(note -> {
                    Intent intent = new Intent(getContext(), Vendor_list_form.class);
                    intent.putExtra("id", note.getId());
                    intent.putExtra("c_name", note.getCompany_name());
                    intent.putExtra("v_name", note.getVendor_name());
                    intent.putExtra("phone", note.getPhone_number());
                    intent.putExtra("email", note.getV_email_id());
                    intent.putExtra("address", note.getV_address());
                    intent.putExtra("get_no", note.getV_gst_number());
                    startActivity(intent);
                }
        );
        id_add_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(), Vendor_list_form.class));
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
                        try {
                            db.deleteVendorTable(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(getActivity(), "Vendor  deleted", Toast.LENGTH_SHORT).show();
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
                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            Toast.makeText(getActivity(), "Vendor Not deleted", Toast.LENGTH_SHORT).show();
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
        }).attachToRecyclerView(recyclerView);
        vendor_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
              if(query != null)
                {
                   Toast.makeText(getContext(),"Search : "+query,Toast.LENGTH_LONG).show();
                    GetFilterData(query);
                }
                closeKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  adapter.getFilter().filter(newText);
                if(newText != null)
                {
                    //Toast.makeText(getContext(),"OnSearch : "+newText,Toast.LENGTH_LONG).show();
                    GetFilterData(newText);
                }
                return true;
            }

            private void GetFilterData(String str) {

                str = "%" + str + "%";
                    db.getFilterVendorTable(str).observe(Vendor_list.this, new Observer<List<VendorTable>>() {
                        @Override
                        public void onChanged(List<VendorTable> vendorTables) {
                            adapter.setNotes(vendorTables);
                        }
                    });

            }
        });

        return view;
    }
    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            vendor_searchView.clearFocus();
        }
    }

}