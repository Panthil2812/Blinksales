package com.rku.blinksales.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.BillTable;
import com.rku.blinksales.Roomdatabase.PurchaseReturnTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillListRecyclerViewAdapter extends RecyclerView.Adapter<BillListRecyclerViewAdapter.NameHolder> {
    private List<BillTable> notes = new ArrayList<>();
    private BillListRecyclerViewAdapter.OnItemClickListener listener;
    private Context context;
    public BillListRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BillListRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_return_card, parent, false);
        return new BillListRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BillListRecyclerViewAdapter.NameHolder holder, int position) {
        BillTable currentNote = notes.get(position);
        holder.id_list_item_1.setText(String.valueOf(currentNote.getBill_id()));
        holder.id_list_item_2.setText(currentNote.getCustomer_name());
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

            String date = simpleDateFormat.format(currentNote.getBill_date());
            holder.id_list_item_3.setText(date);
        } catch (Exception e) {

            e.printStackTrace();
        }
        holder.id_list_item_4.setText(currentNote.getBill_method());
        holder.id_list_item_5.setText("T.Amount : "+currentNote.getBill_amount()+" ₹");

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<BillTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public BillTable getNoteAt(int position) {
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
//            id_list_item_4.setOnClickListener(v -> {
//
//                int position = getAdapterPosition();
//                if (listener != null && position != RecyclerView.NO_POSITION ) {
//                    listener.onItemClick(notes.get(position));
//                }
//            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(BillTable note);
    }
    public void setOnItemClickListener(BillListRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
