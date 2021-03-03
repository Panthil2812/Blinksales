package com.rku.blinksales.form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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

    public void Save_Customer(View view)
    {

        String cus_name=id_cus_name.getText().toString().trim();
        String cus_job=id_cus_job.getText().toString().trim();
        String cus_contact=id_cus_contact.getText().toString().trim();
        String cus_email=id_cus_email.getText().toString().trim();
        String cusB_gst_num=id_cusB_gst_num.getText().toString().trim();
        String cusB_address = id_cusB_address.getText().toString().trim();
        String cusB_city=id_cusB_city.getText().toString().trim();
        String cusB_region=id_cusB_region.getText().toString().trim();
        String cusB_country=id_cusB_country.getText().toString().trim();
        String cusB_pos_code=id_cusB_pos_code.getText().toString().trim();
        String cusB_description = id_cusB_description.getText().toString().trim();

        // validation code start here........
        if(cus_name.isEmpty())
            Toast.makeText(getApplicationContext(),"Enter name",Toast.LENGTH_SHORT).show();
        else if(cus_job.isEmpty())
            Toast.makeText(getApplicationContext(),"Enter job title",Toast.LENGTH_SHORT).show();
        else if(cus_contact.isEmpty() || cus_contact.length()!=10)
            Toast.makeText(getApplicationContext(),"You must enter 10 digits contact number",Toast.LENGTH_SHORT).show();
        else if(!Patterns.EMAIL_ADDRESS.matcher(cus_email).matches())
            Toast.makeText(getApplicationContext(),"Enter valid email id",Toast.LENGTH_SHORT).show();
        else if (cusB_gst_num.isEmpty())
            Toast.makeText(getApplicationContext(),"Enter GST number",Toast.LENGTH_SHORT).show();
        else if(cusB_address.isEmpty())
            Toast.makeText(getApplicationContext(),"Enter address",Toast.LENGTH_SHORT).show();
        else if(cusB_country.isEmpty())
            Toast.makeText(getApplicationContext(),"Enter country",Toast.LENGTH_SHORT).show();
        else if(cusB_region.isEmpty())
            Toast.makeText(getApplicationContext(),"Enter region",Toast.LENGTH_SHORT).show();
        else if(cusB_city.isEmpty())
            Toast.makeText(getApplicationContext(),"Enter city",Toast.LENGTH_SHORT).show();
        else if(cusB_pos_code.isEmpty() || cusB_pos_code.length()!=6)
            Toast.makeText(getApplicationContext(),"You must enter 6 digits postal code",Toast.LENGTH_SHORT).show();
        else if(cusB_description.isEmpty())
            Toast.makeText(getApplicationContext(),"Enter description",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"Success...",Toast.LENGTH_LONG).show();

        // validation code finished here........


    }
}