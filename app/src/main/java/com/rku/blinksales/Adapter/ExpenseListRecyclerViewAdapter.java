package com.rku.blinksales.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.ExpenseTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpenseListRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseListRecyclerViewAdapter.NameHolder> {
    private List<ExpenseTable> expenseTables = new ArrayList<>();
    private ExpenseListRecyclerViewAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public ExpenseListRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_view, parent, false);
        return new ExpenseListRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListRecyclerViewAdapter.NameHolder holder, int position) {
        ExpenseTable currentNote = expenseTables.get(position);
        holder.id_list_item_1.setText(currentNote.getExp_name());
        holder.id_list_item_2.setText(currentNote.getExp_type());
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String df_medium_us_str = formatter.format(currentNote.getExp_date());
        holder.id_list_item_3.setText(df_medium_us_str);
        holder.id_list_item_4.setText(currentNote.getExp_amount()+" ₹");
    }

    @Override
    public int getItemCount() {
        return expenseTables.size();
    }

    public void setNotes(List<ExpenseTable> notes) {
        this.expenseTables = notes;
        notifyDataSetChanged();
    }

    public ExpenseTable getNoteAt(int position) {
        return expenseTables.get(position);
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
                    listener.onItemClick(expenseTables.get(position));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ExpenseTable note);
    }
    public void setOnItemClickListener(ExpenseListRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
