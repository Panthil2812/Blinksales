package com.rku.blinksales.mainfragment;

import android.content.DialogInterface;
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

import com.rku.blinksales.Adapter.CategoryRecyclerViewAdapter;
import com.rku.blinksales.Adapter.PendingRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CartTable;
import com.rku.blinksales.Roomdatabase.CategoryTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.PendingCartTable;

import java.util.List;

public class Pending_cart  extends Fragment {
    DatabaseDao db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_cart, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_pending_cart);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh =getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);

        db = MainRoomDatabase.getInstance(getContext()).getDao();
        RecyclerView recyclerView = view.findViewById(R.id.PendingRecycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final PendingRecyclerViewAdapter adapter = new PendingRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        try {
            db.getAllPendingCartTable().observe(this, new Observer<List<PendingCartTable>>() {

                @Override
                public void onChanged(@Nullable List<PendingCartTable> notes) {
                    adapter.setNotes(notes);
                }
            });

        } catch (Exception e)
        {
            e.getStackTrace();
        }

        adapter.setOnItemClickListener(new PendingRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PendingCartTable note,TextView txt) {
                if(note.getCart_status() == 0) {
                    new AlertDialog.Builder(getContext(),R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background)
                            .setTitle("Active Cart")
                            .setMessage("Are you sure you want to make Active Cart?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        db.updateALLPendingCartTable();
                                        adapter.notifyDataSetChanged();
                                        db.updateActiveCartTable(note.getCart_id());
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.getStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }else{
                    new AlertDialog.Builder(getContext(),R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background)
                            .setTitle("Pending Cart")
                            .setMessage("Are you sure you want to make Pending Cart?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        db.updateALLPendingCartTable();
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.getStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });


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
                                    PendingCartTable note = adapter.getNoteAt(viewHolder.getAdapterPosition());
                                    db.DeleteExpireCart(note.getCart_id());
                                    db.deletePendingCartTable(note);
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
        }).attachToRecyclerView(recyclerView);

        return view;
    }

}