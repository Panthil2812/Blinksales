package com.rku.blinksales.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.ExpenseTable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.NameHolder> {
    private List<ExpenseTable> notes = new ArrayList<>();
    private ListRecyclerViewAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public ListRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_view, parent, false);
        return new ListRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRecyclerViewAdapter.NameHolder holder, int position) {
        ExpenseTable currentNote = notes.get(position);
        holder.id_list_item_1.setText(currentNote.getExp_name());
        holder.id_list_item_2.setText(currentNote.getExp_type());
        DateFormat df_medium_us = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
        String df_medium_us_str = df_medium_us.format(currentNote.getExp_date());
        holder.id_list_item_3.setText(df_medium_us_str);
        holder.id_list_item_4.setText(currentNote.getExp_amount()+" ₹");
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<ExpenseTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public ExpenseTable getNoteAt(int position) {
        return notes.get(position);
    }


    class NameHolder extends RecyclerView.ViewHolder {
        private TextView id_list_item_1;
        private TextView id_list_item_2;
        private TextView id_list_item_3;
        private TextView id_list_item_4;
        public NameHolder(View itemView) {
            super(itemView);
            id_list_item_1 = itemView.findViewById(R.id.id_list_item_1);
            id_list_item_2 = itemView.findViewById(R.id.id_list_item_2);
            id_list_item_3 = itemView.findViewById(R.id.id_list_item_3);
            id_list_item_4 = itemView.findViewById(R.id.id_list_item_4);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(notes.get(position));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ExpenseTable note);
    }
    public void setOnItemClickListener(ListRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
