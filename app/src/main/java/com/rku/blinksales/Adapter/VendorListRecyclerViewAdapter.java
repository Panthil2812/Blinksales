package com.rku.blinksales.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.VendorTable;
import java.util.ArrayList;
import java.util.List;

public class VendorListRecyclerViewAdapter extends RecyclerView.Adapter<VendorListRecyclerViewAdapter.NameHolder>{
    private List<VendorTable> notes = new ArrayList<>();
    private VendorListRecyclerViewAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public VendorListRecyclerViewAdapter.NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new VendorListRecyclerViewAdapter.NameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorListRecyclerViewAdapter.NameHolder holder, int position) {
        VendorTable currentNote = notes.get(position);
        holder.id_list_item_1.setText(currentNote.getVendor_name());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<VendorTable> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public VendorTable getNoteAt(int position) {
        return notes.get(position);
    }

    class NameHolder extends RecyclerView.ViewHolder {
        private TextView id_list_item_1;
        private ImageButton EditCategoryName;
        public NameHolder(View itemView) {
            super(itemView);
            id_list_item_1 = itemView.findViewById(R.id.txtCategoryName);
            EditCategoryName = itemView.findViewById(R.id.EditCategoryName);
            EditCategoryName.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(notes.get(position));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(VendorTable note);
    }
    public void setOnItemClickListener(VendorListRecyclerViewAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

//    @Override
//    public Filter getFilter() {
//        return SearchFilter;
//    }
//    private Filter SearchFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<VendorTable> filterList = new ArrayList<>();
//            if(constraint == null || constraint.length() == 0){
//                filterList.addAll(notesAll);
//            }
//            return null;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//        }
//    };
}
