package com.rku.blinksales.form;


import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.rku.blinksales.R;

public class Product_form extends AppCompatActivity {

    EditText id_pro_name,id_pro_category,id_pro_selling_price,id_pro_mrp,id_pro_qty,id_pro_unit,id_pro_barcode,id_cgst_unit,id_sgst_unit,id_hsn_unit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        id_pro_name = findViewById(R.id.id_pro_name);
        id_pro_category = findViewById(R.id.id_pro_category);
        id_pro_selling_price = findViewById(R.id.id_pro_selling_price);
        id_pro_mrp = findViewById(R.id.id_pro_mrp);
        id_pro_qty = findViewById(R.id.id_pro_qty);
        id_pro_unit = findViewById(R.id.id_pro_unit);
        id_pro_barcode = findViewById(R.id.id_pro_barcode);
        id_cgst_unit = findViewById(R.id.id_cgst_unit);
        id_sgst_unit = findViewById(R.id.id_sgst_unit);
        id_hsn_unit = findViewById(R.id.id_hsn_unit);

    }

}