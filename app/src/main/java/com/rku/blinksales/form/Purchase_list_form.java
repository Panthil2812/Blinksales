package com.rku.blinksales.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.rku.blinksales.R;

import java.util.List;

public class Purchase_list_form extends AppCompatActivity {

    EditText id_pur_lst_ven_name,id_pur_lst_amount,id_pur_lst_tax_amount,id_pur_lst_address,id_pur_lst_date;
    Button id_pur_lst_btn_save;
    ImageButton id_back_arrow;
//    LinearLayout linearLayout_upload_reciept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list_form);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });
        id_pur_lst_ven_name = findViewById(R.id.id_pur_re_ven_name);
        id_pur_lst_amount = findViewById(R.id.id_pur_re_purAmount);
        id_pur_lst_tax_amount = findViewById(R.id.id_pur_re_pending_amount);
        id_pur_lst_address= findViewById(R.id.id_pur_lst_note);
        id_pur_lst_btn_save = findViewById(R.id.id_pur_re_btn_save);
        id_pur_lst_date =findViewById(R.id.id_pur_lst_date);
//
//        linearLayout_upload_reciept = findViewById(R.id.linearLayout_upload_reciept);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker().setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
        MaterialDatePicker materialDatePicker =builder.build();
        builder.setTitleText("SELECT DATE");

        id_pur_lst_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                id_pur_lst_date.setText(materialDatePicker.getHeaderText());
            }
        });
    }
}