package com.rku.blinksales.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.PendingCartTable;
import com.rku.blinksales.Roomdatabase.PurchaseTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseRecyclerViewAdapter extends RecyclerView.Adapter<PurchaseRecyclerViewAdapter.NameHolder> {
    private List<PurchaseTable> notes = new ArrayList<>();
    private  OnItemClickListener listener;
    private  Context context;
    public PurchaseRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_card, parent, false);
        return new NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NameHolder holder, int position) {
        PurchaseTable currentNote = notes.get(position);
        holder.id_list_item_1.setText(String.valueOf(currentNote.getPurchase_id()));
        holder.id_list_item_2.setText(currentNote.getVendor_name());
        //   holder.id_list_item_3.setText(currentNote.getDate().toString());
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

            String date = simpleDateFormat.format(currentNote.getDate());
            holder.id_list_item_3.setText(date);
        } catch (Exception e) {

            e.printStackTrace();
        }
//        holder.id_list_item_4.setText(String.valueOf(currentNote.getCart_status()));
        if(currentNote.getPending_amount() == 0.0)
        {
            holder.id_list_item_4.setBackgroundResource(R.drawable.cart_background);
            holder.id_list_item_4.setText("T.A:"+currentNote.getAmount()+" ₹");

        }else{
            holder.id_list_item_4.setBackgroundResource(R.drawable.card_view_background);
//            holder.id_list_item_4.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
//            holder.id_list_item_4.setTextColor(ContextCompat.getColor(context,R.color.primary_light));
////            holder.id_list_item_4.setTextColor(Color.parseColor("#FCE4EC"));
            holder.id_list_item_4.setText("P.A:"+currentNote.getPending_amount()+" ₹");
        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<PurchaseTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public PurchaseTable getNoteAt(int position) {
        return notes.get(position);
    }


    class NameHolder extends RecyclerView.ViewHolder {
        private TextView id_list_item_1, id_list_item_2,id_list_item_3,id_list_item_4;
        public NameHolder(View itemView) {
            super(itemView);
            id_list_item_1 = itemView.findViewById(R.id.id_card_id);
            id_list_item_2 = itemView.findViewById(R.id.id_card_1);
            id_list_item_3 = itemView.findViewById(R.id.id_card_2);
            id_list_item_4 = itemView.findViewById(R.id.id_card_3);
//
            id_list_item_4.setPadding(0,8,0,8);
            id_list_item_4.setGravity(Gravity.CENTER);
            itemView.setOnClickListener(v -> {

                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION ) {
                    listener.onItemClick(notes.get(position));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(PurchaseTable note);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
