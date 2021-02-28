package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rku.blinksales.R;
import com.rku.blinksales.form.Expense_list_form;
import com.rku.blinksales.form.Sales_return_form;

public class Sales_return extends Fragment {
    FloatingActionButton id_add_sales;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales_return, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_sales_return);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh =getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.GONE);
        id_btn_refresh.setVisibility(View.GONE);
        id_add_sales = view.findViewById(R.id.id_add_sales);

        id_add_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(), Sales_return_form.class));
//                Toast.makeText(getContext(), "Open sales return form", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}