package com.rku.blinksales.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rku.blinksales.DoubleTapListener;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.ProductTable;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
    private List<ProductTable> notes = new ArrayList<>();
    Context context;
    private MainRecyclerViewAdapter.OnItemClickListener listener;

    public MainRecyclerViewAdapter(Context context) {
       this.context = context;
    }

    @NonNull
    @Override
    public MainRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_view, parent, false);
        MainRecyclerViewAdapter.MyViewHolder holder = new MainRecyclerViewAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewAdapter.MyViewHolder holder, int position) {
        ProductTable currentNote = notes.get(position);
            holder.card_name.setText(currentNote.getProduct_name());
            holder.card_price.setText(currentNote.getProduct_price_unit());
            String str = currentNote.getProduct_image_uri();
            File f = new File(str);
            Glide.with(context).load(f).fitCenter().placeholder(R.drawable.p1).into(holder.card_img);

    }

    public void setNotes(List<ProductTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public ProductTable getNoteAt(int position) {
        return notes.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView card_name, card_price;
        ImageView card_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_name = itemView.findViewById(R.id.card_name);
            card_price = itemView.findViewById(R.id.card_price);
            card_img = itemView.findViewById(R.id.card_img);
            itemView.setOnClickListener(v -> {
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
    public void setOnItemClickListener(MainRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
