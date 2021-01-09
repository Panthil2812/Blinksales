package com.rku.blinksales.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.InstanceClass.CardInstance;
import com.rku.blinksales.R;

import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.MyViewHolder>{

    private final List<CardInstance> Data;

    public ProductsRecyclerViewAdapter(List<CardInstance> data) {
        this.Data = data;
    }

    @NonNull
    @Override
    public ProductsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        ProductsRecyclerViewAdapter.MyViewHolder holder = new ProductsRecyclerViewAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsRecyclerViewAdapter.MyViewHolder holder, int position) {
        try {
            holder.card_name.setText(Data.get(position).getP_name());

            holder.card_price.setText("Rs: "+Data.get(position).getP_price());
            holder.card_img.setImageResource(Data.get(position).getP_image());
            holder.itemView.setOnClickListener((View.OnClickListener) v -> {

               Toast.makeText(v.getContext(),Data.get(position).getP_name()+"  "+Data.get(position).getP_price(),Toast.LENGTH_LONG).show();
            });

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
}
