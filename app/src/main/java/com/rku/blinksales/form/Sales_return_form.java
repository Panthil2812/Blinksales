package com.rku.blinksales.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.rku.blinksales.R;

public class Sales_return_form extends AppCompatActivity {
    ImageButton id_back_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_return_form);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}