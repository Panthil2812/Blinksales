package com.rku.blinksales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Purchase_list_form extends AppCompatActivity {

    EditText id_pur_lst_ven_name,id_pur_lst_amount,id_pur_lst_tax_amount,id_pur_lst_email,id_pur_lst_address;
    Button id_pur_lst_btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list_form);

        id_pur_lst_ven_name = findViewById(R.id.id_pur_lst_ven_name);
        id_pur_lst_amount = findViewById(R.id.id_pur_lst_amount);
        id_pur_lst_tax_amount = findViewById(R.id.id_pur_lst_tax_amount);
        id_pur_lst_email = findViewById(R.id.id_pur_lst_email);
        id_pur_lst_address= findViewById(R.id.id_pur_lst_address);
        id_pur_lst_btn_save = findViewById(R.id.id_pur_lst_btn_save);

    }
}