package com.rku.blinksales.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.ProductTable;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.NameHolder> {
    private List<ProductTable> notes = new ArrayList<>();
    private ProductRecyclerViewAdapter.OnItemClickListener listener;
    Context context;
    public ProductRecyclerViewAdapter(Context context) {
        this.context =context;
    }

    @NonNull
    @Override
    public ProductRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_card_item, parent, false);
        return new ProductRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.NameHolder holder, int position) {
        ProductTable currentNote = notes.get(position);
        holder.Product_Name.setText(currentNote.getProduct_name());
        holder.Product_price_unit.setText(currentNote.getProduct_price_unit());
//        //holder.Product_Image.setText(currentNote.getExp_amount()+" ₹");
        String str = currentNote.getProduct_image_uri();
        File f = new File(str);
//        Picasso.get().load(f).centerInside().placeholder(R.drawable.p1).into(holder.Product_Image);

        Glide.with(context).load(f).autoClone().placeholder(R.drawable.p1).into(holder.Product_Image);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<ProductTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public ProductTable getNoteAt(int position) {
        return notes.get(position);
    }


    class NameHolder extends RecyclerView.ViewHolder {
        private TextView Product_Name;
        private TextView Product_price_unit;
        private ImageView Product_Image;
        private ImageButton EditProduct;
        public NameHolder(View itemView) {
            super(itemView);
            Product_Name = itemView.findViewById(R.id.product_name);
            Product_price_unit = itemView.findViewById(R.id.product_price_unit);
            Product_Image = itemView.findViewById(R.id.product_image);
            EditProduct = itemView.findViewById(R.id.EditProduct);

            EditProduct.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(notes.get(position));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ProductTable note);
    }
    public void setOnItemClickListener(ProductRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    private Bitmap loadImageFromStorage(String path) {

        Bitmap bitmap = null;
        try {
            File f = new File(path);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
