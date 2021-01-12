package com.rku.blinksales.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rku.blinksales.R;

public class Vendor_list_form extends AppCompatActivity {

    EditText id_ven_company_name,id_ven_name,id_ven_contact,id_ven_email,id_ven_address,id_ven_gst;
    Button id_ven_btn_save;
    ImageButton id_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list_form);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });
        id_ven_company_name = findViewById(R.id.id_ven_company_name);
        id_ven_name = findViewById(R.id.id_ven_name);
        id_ven_contact = findViewById(R.id.id_ven_contact);
        id_ven_email = findViewById(R.id.id_ven_email);
        id_ven_address = findViewById(R.id.id_ven_address);
        id_ven_gst = findViewById(R.id.id_ven_gst);
        id_ven_btn_save = findViewById(R.id.id_ven_btn_save);

    }
}