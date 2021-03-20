package com.rku.blinksales.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.SalesReturnTable;
import com.rku.blinksales.Roomdatabase.SoldItemTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SalesReturnRecyclerViewAdapter extends RecyclerView.Adapter<SalesReturnRecyclerViewAdapter.NameHolder> {
    private List<SalesReturnTable> notes = new ArrayList<>();
    private SalesReturnRecyclerViewAdapter.OnItemClickListener listener;
    Context context;
    DatabaseDao db;


    public SalesReturnRecyclerViewAdapter(Context context) {
        this.context = context;
        db = MainRoomDatabase.getInstance(context).getDao();

    }

    @NonNull
    @Override
    public SalesReturnRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_list_view, parent, false);
        return new SalesReturnRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesReturnRecyclerViewAdapter.NameHolder holder, int position) {
        SalesReturnTable currentNote = notes.get(position);
        holder.bill_id.setText(String.valueOf(currentNote.getBill_id()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = simpleDateFormat.format(currentNote.getCreate_date());
        holder.bill_date.setText(date);
        holder.bill_total.setText("T.A :" + currentNote.getBill_amount().toString() + " ₹/-");
        holder.bill_return_total.setText("R.A :" + currentNote.getReturn_amount().toString() + " ₹/-");
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<SalesReturnTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public SalesReturnTable getNoteAt(int position) {
        return notes.get(position);
    }


    class NameHolder extends RecyclerView.ViewHolder {
        public TextView bill_id, bill_date, bill_total, bill_return_total;

        public NameHolder(View itemView) {
            super(itemView);
            bill_id = itemView.findViewById(R.id.sales_item_1);
            bill_date = itemView.findViewById(R.id.sales_item_2);
            bill_total = itemView.findViewById(R.id.sales_item_3);
            bill_return_total = itemView.findViewById(R.id.sales_item_4);
//        return_checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = getAdapterPosition();
//
//                if (listener != null && position != RecyclerView.NO_POSITION) {
//                    listener.checkbox_true(notes.get(position),return_checkbox,cart_btn_plus,cart_btn_min,Product_selected_qty,Product_amount);
//                }
//
//            }
//        });


        }
    }

    public interface OnItemClickListener {
        // void checkbox_true(SoldItemTable note, CheckBox checkBox, ImageButton cart_btn_plus, ImageButton cart_btn_min, EditText qty, TextView amount);
        //  void plus_btn(SoldItemTable note,EditText qty,TextView amount,CheckBox checkBox);
        //  void min_btn(SoldItemTable note,EditText qty,TextView amount,CheckBox checkBox);
    }

    public void setOnItemClickListener(SalesReturnRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
