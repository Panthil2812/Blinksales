package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rku.blinksales.MainActivity;
import com.rku.blinksales.R;

public class Cart  extends Fragment {
    Button id_add_products;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        id_add_products = view.findViewById(R.id.id_add_products);
        id_add_products.setOnClickListener(v -> startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class)));
        return view;
    }
    
}