package com.rku.blinksales.form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.rku.blinksales.R;
import com.rku.blinksales.mainfragment.Customers;

public class Customers_form extends AppCompatActivity {


    EditText id_cus_name,id_cus_job,id_cus_contact,id_cus_email,id_cusB_gst_num,id_cusB_address,id_cusB_city,id_cusB_region,id_cusB_country,id_cusB_pos_code,id_cusB_description;
    Switch id_cus_switch_loyalty;
    Button id_cus_btn_save;
    ImageButton id_back_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_form);

        id_cus_name = findViewById(R.id.id_cus_name);
        id_cus_job = findViewById(R.id.id_cus_job);
        id_cus_contact = findViewById(R.id.id_cus_contact);
        id_cus_email = findViewById(R.id.id_cus_email);
        id_cusB_gst_num = findViewById(R.id.id_cusB_gst_num);
        id_cusB_address = findViewById(R.id.id_cusB_address);
        id_cusB_city = findViewById(R.id.id_cusB_city);
        id_cusB_region = findViewById(R.id.id_cusB_region);
        id_cusB_country = findViewById(R.id.id_cusB_country);
        id_cusB_pos_code = findViewById(R.id.id_cusB_pos_code);
        id_cusB_description = findViewById(R.id.id_cusB_description);
        id_cus_btn_save = findViewById(R.id.id_cus_btn_save);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}