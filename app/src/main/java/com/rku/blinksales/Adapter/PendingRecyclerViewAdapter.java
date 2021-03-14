package com.rku.blinksales.Adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CategoryTable;
import com.rku.blinksales.Roomdatabase.PendingCartTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PendingRecyclerViewAdapter extends RecyclerView.Adapter<PendingRecyclerViewAdapter.NameHolder> {
    private List<PendingCartTable> notes = new ArrayList<>();
    private  PendingRecyclerViewAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public PendingRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_view, parent, false);
        return new PendingRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingRecyclerViewAdapter.NameHolder holder, int position) {
        PendingCartTable currentNote = notes.get(position);
        holder.id_list_item_1.setText("Cart :"+currentNote.getCart_id());
        holder.id_list_item_2.setText("");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

            String date = simpleDateFormat.format(currentNote.getCart_create());
            holder.id_list_item_3.setText(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        holder.id_list_item_4.setText(String.valueOf(currentNote.getCart_status()));
        if(currentNote.getCart_status() == 0)
        {
            holder.id_list_item_4.setBackgroundResource(R.drawable.cart_background);
            holder.id_list_item_4.setText("Pending");

        }else{
            holder.id_list_item_4.setBackgroundResource(R.drawable.cardview_background);
//            holder.id_list_item_4.setTextColor(Color.parseColor("#FCE4EC"));
            holder.id_list_item_4.setText("Active");
        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<PendingCartTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public PendingCartTable getNoteAt(int position) {
        return notes.get(position);
    }


    class NameHolder extends RecyclerView.ViewHolder {
        private TextView id_list_item_1, id_list_item_2,id_list_item_3,id_list_item_4;
        public NameHolder(View itemView) {
            super(itemView);
            id_list_item_1 = itemView.findViewById(R.id.id_list_item_1);
            id_list_item_2 = itemView.findViewById(R.id.id_list_item_2);
            id_list_item_3 = itemView.findViewById(R.id.id_list_item_3);
            id_list_item_4 = itemView.findViewById(R.id.id_list_item_4);

            id_list_item_4.setPadding(0,8,0,8);
           id_list_item_4.setGravity(Gravity.CENTER);
           id_list_item_4.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION ) {
                    listener.onItemClick(notes.get(position),id_list_item_4);
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(PendingCartTable note,TextView txt);
    }
    public void setOnItemClickListener(PendingRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
