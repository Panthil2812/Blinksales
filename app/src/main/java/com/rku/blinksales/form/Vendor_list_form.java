package com.rku.blinksales.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.VendorTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Vendor_list_form extends AppCompatActivity {

    EditText id_ven_company_name,id_ven_name,id_ven_contact,id_ven_email,id_ven_address,id_ven_gst;
    Button id_ven_btn_save;
    ImageButton id_back_arrow;
    final int add = -1;
    int id = -1;
    DatabaseDao db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list_form);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();
        id_back_arrow = findViewById(R.id.id_back_arrow);
        id_ven_company_name = findViewById(R.id.id_ven_company_name);
        id_ven_name = findViewById(R.id.id_ven_name);
        id_ven_contact = findViewById(R.id.id_ven_contact);
        id_ven_email = findViewById(R.id.id_ven_email);
        id_ven_address = findViewById(R.id.id_ven_address);
        id_ven_gst = findViewById(R.id.id_ven_gst);
        id_ven_btn_save = findViewById(R.id.id_ven_btn_save);
        TextView pageTitle = findViewById(R.id.title_page);
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            pageTitle.setText("Edit Vendor");
            id_ven_company_name.setText(intent.getStringExtra("c_name"));
            id_ven_name.setText(intent.getStringExtra("v_name"));
            id_ven_contact.setText(intent.getStringExtra("phone"));
            id_ven_email.setText(intent.getStringExtra("email"));
            id_ven_address.setText(intent.getStringExtra("address"));
            id_ven_gst.setText(intent.getStringExtra("get_no"));
            id = intent.getIntExtra("id",-1);

        } else {
            pageTitle.setText("Add Vendor");
        }
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });

        id_ven_btn_save.setOnClickListener(v->{
            String com_name = id_ven_company_name.getText().toString().trim();
            String vendor_name = id_ven_name.getText().toString().trim();
            String number = id_ven_contact.getText().toString().trim();
            String email = id_ven_email.getText().toString().trim();
            String address = id_ven_address.getText().toString().trim();
            String gst_number = id_ven_gst.getText().toString().trim();
            if (com_name.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter Company name", Toast.LENGTH_SHORT).show();
            }else if (vendor_name.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter vendor name", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(number) ||!Patterns.PHONE.matcher(number).matches() || number.length()!= 10){
                Toast.makeText(getApplicationContext(),"You must have 10 number in your phone",Toast.LENGTH_SHORT).show();
            } else if (email.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(getApplicationContext(),"enter valid email id",Toast.LENGTH_SHORT).show();

            }else if (address.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter address", Toast.LENGTH_SHORT).show();
            } else if (gst_number.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter gst number", Toast.LENGTH_SHORT).show();
            }  else{
                com_name = com_name. substring(0, 1). toUpperCase() +com_name.substring(1).toLowerCase();
                vendor_name = vendor_name. substring(0, 1). toUpperCase() +vendor_name.substring(1).toLowerCase();
                address = address. substring(0, 1). toUpperCase() +address.substring(1).toLowerCase();
                VendorTable vendorTable = new VendorTable(com_name,vendor_name,number,email,address,gst_number);
                try{
                    if(add != id)
                    {

                        try{
                            vendorTable.setId(id);
                            db.updateVendorTable(vendorTable);
                            Toast.makeText(getApplicationContext(),"edit Vendor .........",Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {
                            e.getStackTrace();
                        }

                    }else {
                        try{
                            db.insertVendorTable(vendorTable);
                            Toast.makeText(getApplicationContext(),"ADD Vendor....",Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {
                            e.getStackTrace();
                        }


                    }
                    onBackPressed();
                }catch (Exception e){e.getStackTrace();}

//                Toast.makeText(getApplicationContext(), "C_Name : "+com_name+"\n"
//                        +"V_NAME : "+vendor_name +"\n"
//                        +"NO :"+number+"\n"
//                        +"EMAIL : "+email +"\n"
//                        +"ADDRESS : "+address +"\n"
//                        +"GST : "+gst_number, Toast.LENGTH_LONG).show();
            }
        });
    }
}