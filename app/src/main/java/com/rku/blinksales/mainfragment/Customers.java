package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rku.blinksales.R;
import com.rku.blinksales.form.Customers_form;

public class Customers  extends Fragment {
    FloatingActionButton id_add_customer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customers, container, false);
        id_add_customer = view.findViewById(R.id.id_add_customer);
        id_add_customer.setOnClickListener(v -> {
            startActivity(new Intent(getContext().getApplicationContext(), Customers_form.class));
        });
        return view;
    }

}