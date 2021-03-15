package com.rku.blinksales.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rku.blinksales.MainActivity;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.CartTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.ProductTable;
import com.rku.blinksales.mainfragment.Dashboard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.NameHolder> {
    private List<CartTable> notes = new ArrayList<>();
    private CartRecyclerViewAdapter.OnItemClickListener listener;
    Context context;
    FragmentActivity manager;
    DatabaseDao db;
    TextView total;

    public CartRecyclerViewAdapter(Context context, FragmentActivity manager,TextView total) {
        this.context = context;
        this.manager = manager;
        this.total = total;
        db = MainRoomDatabase.getInstance(context).getDao();

    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_view, parent, false);
        return new CartRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.NameHolder holder, int position) {
        CartTable currentNote = notes.get(position);
        holder.Product_Name.setText(currentNote.getProduct_name());
        holder.product_price.setText(currentNote.getProduct_selling_price().toString());
        holder.product_unit.setText(" ₹/" + currentNote.getProduct_unit());
        holder.Product_selected_qty.setText(currentNote.getSelected_qty().toString());
        holder.Product_amount.setText(currentNote.getTotal_amount().toString());
        holder.cart_item_id.setText(String.valueOf(currentNote.getCart_item_id()));
        File f = new File(currentNote.getProduct_image_uri());
        Glide.with(context).load(f).autoClone().placeholder(R.drawable.p1).into(holder.Product_Image);
        total.setText(db.totalCartAmount(currentNote.getCart_id()).toString()+" ₹ /-");
        Dashboard.id_dashboard_total_items.setText(db.totalCartItem(currentNote.getCart_id()).toString());
        Dashboard.id_dashboard_total_amount.setText(db.totalCartAmount(currentNote.getCart_id()).toString() + " ₹ /-");
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<CartTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public CartTable getNoteAt(int position) {
        return notes.get(position);
    }


     class NameHolder extends RecyclerView.ViewHolder {
        public TextView Product_Name, product_unit, Product_amount, product_price, cart_item_id;
        public ImageView Product_Image;
        public EditText Product_selected_qty;
        ImageButton cart_btn_plus, cart_btn_min;

        public NameHolder(View itemView) {
            super(itemView);
            Product_Name = itemView.findViewById(R.id.cart_pro_name);
            product_unit = itemView.findViewById(R.id.product_unit);
            product_price = itemView.findViewById(R.id.product_price);
            Product_Image = itemView.findViewById(R.id.cart_pro_image);
            Product_selected_qty = itemView.findViewById(R.id.cart_pro_Quantity);
            Product_amount = itemView.findViewById(R.id.cart_pro_amount);
            cart_btn_plus = itemView.findViewById(R.id.cart_btn_plus);
            cart_btn_min = itemView.findViewById(R.id.cart_btn_min);
            cart_item_id = itemView.findViewById(R.id.id_item);
            cart_btn_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.plus_btn(notes.get(position),Product_selected_qty,Product_amount);
                    }
                }
            });
            cart_btn_min.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.min_btn(notes.get(position),Product_selected_qty,Product_amount);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void plus_btn(CartTable note,EditText qty,TextView amount);
        void min_btn(CartTable note,EditText qty,TextView amount);
    }

    public void setOnItemClickListener(CartRecyclerViewAdapter.OnItemClickListener listener) {
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
}
