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
import com.rku.blinksales.Roomdatabase.SoldItemTable;

import java.util.ArrayList;
import java.util.List;

public class SalesReturnItemRecyclerViewAdapter extends RecyclerView.Adapter<SalesReturnItemRecyclerViewAdapter.NameHolder> {
    private List<SoldItemTable> notes = new ArrayList<>();
    private SalesReturnItemRecyclerViewAdapter.OnItemClickListener listener;
    Context context;
    FragmentActivity manager;
    DatabaseDao db;
    TextView total;

    public SalesReturnItemRecyclerViewAdapter(Context context, FragmentActivity manager, TextView total) {
        this.context = context;
        this.manager = manager;
        this.total = total;
        db = MainRoomDatabase.getInstance(context).getDao();

    }

    @NonNull
    @Override
    public SalesReturnItemRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_return_view, parent, false);
        return new SalesReturnItemRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesReturnItemRecyclerViewAdapter.NameHolder holder, int position) {
        SoldItemTable currentNote = notes.get(position);
        holder.Product_Name.setText(currentNote.getProduct_name());
        holder.product_price.setText(currentNote.getProduct_selling_price().toString());
        holder.product_unit.setText(" ₹/" + currentNote.getProduct_unit());
        holder.Product_selected_qty.setText(currentNote.getSelected_qty().toString());
        holder.Product_amount.setText(currentNote.getTotal_amount().toString());
//        total.setText(db.totalCartAmount(currentNote.getCart_id()).toString()+" ₹ /-");
//        Dashboard.id_dashboard_total_items.setText(db.totalCartItem(currentNote.getCart_id()).toString());
//        Dashboard.id_dashboard_total_amount.setText(db.totalCartAmount(currentNote.getCart_id()).toString() + " ₹ /-");
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<SoldItemTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public SoldItemTable getNoteAt(int position) {
        return notes.get(position);
    }


    class NameHolder extends RecyclerView.ViewHolder {
        public TextView Product_Name, product_unit, Product_amount, product_price;
         public EditText Product_selected_qty;
        public ImageButton cart_btn_plus, cart_btn_min;
        public CheckBox return_checkbox;
        public NameHolder(View itemView) {
            super(itemView);
            Product_Name = itemView.findViewById(R.id.return_pro_name);
            product_unit = itemView.findViewById(R.id.return_product_unit);
            product_price = itemView.findViewById(R.id.return_product_price);
            Product_selected_qty = itemView.findViewById(R.id.return_pro_Quantity);
            Product_amount = itemView.findViewById(R.id.return_pro_amount);
            cart_btn_plus = itemView.findViewById(R.id.return_btn_plus);
            cart_btn_min = itemView.findViewById(R.id.return_btn_min);
            return_checkbox = itemView.findViewById(R.id.return_checkbox);
            disableEditText(Product_selected_qty);
            return_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.checkbox_true(notes.get(position),return_checkbox,cart_btn_plus,cart_btn_min,Product_selected_qty,Product_amount);
                    }

                }
            });


        }
    }

    public interface OnItemClickListener {
        void checkbox_true(SoldItemTable note,CheckBox checkBox,ImageButton cart_btn_plus,ImageButton cart_btn_min,EditText qty,TextView amount);
      //  void plus_btn(SoldItemTable note,EditText qty,TextView amount,CheckBox checkBox);
      //  void min_btn(SoldItemTable note,EditText qty,TextView amount,CheckBox checkBox);
    }

    public void setOnItemClickListener(SalesReturnItemRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public void closeKeyboard() {
        View view = manager.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) manager.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            // Product_selected_qty.clearFocus();
        }
    }
    public void disableEditText(EditText editText) {
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
}
