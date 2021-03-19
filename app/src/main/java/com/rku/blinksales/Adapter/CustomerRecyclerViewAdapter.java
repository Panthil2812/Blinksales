package com.rku.blinksales.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CustomerTable;
import com.rku.blinksales.Roomdatabase.PurchaseReturnTable;
import com.rku.blinksales.Roomdatabase.PurchaseTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerRecyclerViewAdapter  extends RecyclerView.Adapter<CustomerRecyclerViewAdapter.NameHolder> {
    private List<CustomerTable> notes = new ArrayList<>();
    private CustomerRecyclerViewAdapter.OnItemClickListener listener;
    private Context context;
    public CustomerRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_return_card, parent, false);
        return new CustomerRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRecyclerViewAdapter.NameHolder holder, int position) {
        CustomerTable currentNote = notes.get(position);
        holder.id_list_item_1.setText(String.valueOf(currentNote.getCustomer_id()));
        holder.id_list_item_2.setText(currentNote.getC_name());
        holder.id_list_item_5.setText(currentNote.getC_number());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<CustomerTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public CustomerTable getNoteAt(int position) {
        return notes.get(position);
    }


    class NameHolder extends RecyclerView.ViewHolder {
        private TextView id_list_item_1, id_list_item_2,id_list_item_3,id_list_item_4,id_list_item_5;
        public NameHolder(View itemView) {
            super(itemView);
            id_list_item_1 = itemView.findViewById(R.id.id_pur_1);
            id_list_item_2 = itemView.findViewById(R.id.id_pur_2);
            id_list_item_3 = itemView.findViewById(R.id.id_pur_3);
            id_list_item_4 = itemView.findViewById(R.id.id_pur_4);
            id_list_item_5 = itemView.findViewById(R.id.id_pur_5);
            id_list_item_3.setVisibility(View.GONE);
            id_list_item_4.setVisibility(View.GONE);

            itemView.setOnClickListener(v -> {

                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION ) {
                    listener.onItemClick(notes.get(position));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(CustomerTable note);
    }
    public void setOnItemClickListener(CustomerRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
