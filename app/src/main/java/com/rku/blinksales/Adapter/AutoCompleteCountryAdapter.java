package com.rku.blinksales.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.ProductTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AutoCompleteCountryAdapter extends ArrayAdapter<ProductTable> {
    private List<ProductTable> notes;
    public AutoCompleteCountryAdapter(@NonNull Context context, @NonNull List<ProductTable> countryList) {
        super(context, 0, countryList);
        notes = new ArrayList<>(countryList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_design, parent, false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.text_view_name);
        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);
        ProductTable productTable = getItem(position);
        if (productTable != null) {
            textViewName.setText(productTable.getProduct_name());
            String str = productTable.getProduct_image_uri();
            File f = new File(str);
            Glide.with(getContext()).load(f).autoClone().placeholder(R.drawable.p1).into(imageViewFlag);
        }
        return convertView;
    }
    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ProductTable> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(notes);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ProductTable item : notes) {
                    if (item.getProduct_barcode().toLowerCase().contains(filterPattern)
                            ||item.getProduct_name().toLowerCase().contains(filterPattern)
                            ||item.getProduct_category().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
}