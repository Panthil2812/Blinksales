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
import com.rku.blinksales.form.Purchase_list_form;
import com.rku.blinksales.form.Purchase_return_form;

public class Purchase_return  extends Fragment {
    FloatingActionButton id_add_return;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_return, container, false);
        id_add_return = view.findViewById(R.id.id_add_return);

        id_add_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(), Purchase_return_form.class));
            }
        });
        return view;
    }

}