package com.rku.blinksales.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.ProductTable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder>{

    private final List<ProductTable> Data;

    public MainRecyclerViewAdapter(List<ProductTable> data) {
        this.Data = data;
    }

    @NonNull
    @Override
    public MainRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_view,parent,false);
        MainRecyclerViewAdapter.MyViewHolder holder = new MainRecyclerViewAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewAdapter.MyViewHolder holder, int position) {
        try {
            holder.card_name.setText(Data.get(position).getProduct_name());

            holder.card_price.setText(Data.get(position).getProduct_selling_price()+" ₹ /-");
            String str = Data.get(position).getProduct_image_uri();
            holder.card_img.setImageBitmap(loadImageFromStorage(str));

//            holder.card_img.setImageResource(Data.get(position).getP_image());
//            holder.itemView.setOnClickListener((View.OnClickListener) v -> {
//
//               Toast.makeText(v.getContext(),Data.get(position).getP_name()+"  "+Data.get(position).getP_price(),Toast.LENGTH_LONG).show();
//            });

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView card_name,card_price;
        ImageView card_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_name = itemView.findViewById(R.id.card_name);
            card_price= itemView.findViewById(R.id.card_price);
            card_img = itemView.findViewById(R.id.card_img);
        }
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
