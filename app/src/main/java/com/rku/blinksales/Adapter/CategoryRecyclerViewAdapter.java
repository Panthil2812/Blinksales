package com.rku.blinksales.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rku.blinksales.Database.DatabaseHelper;
import com.rku.blinksales.R;
import com.rku.blinksales.Utility;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder>{

    private final List<String> Data;
    Context context;
    ProductsRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    DatabaseHelper db ;
    View v1;
    public CategoryRecyclerViewAdapter(View v1,Context context, List<String> data) {
        this.Data = data;
        this.v1 =v1;
        this.context = context;
        db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.txtName.setText(Data.get(position));
            holder.txtName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,holder.txtName.getText().toString()+" "+position,Toast.LENGTH_LONG).show();
                    recyclerView = v1.findViewById(R.id.id_products_recyclerView);
                    recyclerViewAdapter =new ProductsRecyclerViewAdapter(db.getAllData(holder.txtName.getText().toString()));
                    recyclerView.setLayoutManager(new GridLayoutManager(context, Utility.calculateNoOfColumns(context,180)));
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
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
        TextView txtName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.category_item_id);
        }
    }
}
