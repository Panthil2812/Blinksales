package com.rku.blinksales.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.rku.blinksales.MainActivity;
import com.rku.blinksales.R;

public class Cart  extends Fragment {
    Button id_add_products;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_cart);
        TextView id_weight = getActivity().findViewById(R.id.id_weight);
        ImageButton id_btn_refresh =getActivity().findViewById(R.id.id_btn_refresh);
        id_weight.setVisibility(View.VISIBLE);
        id_btn_refresh.setVisibility(View.VISIBLE);

        id_add_products = view.findViewById(R.id.id_add_products);
        id_add_products.setOnClickListener(v -> startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class)));
        return view;
    }
    
}