package com.rku.blinksales.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.rku.blinksales.R;

public class Purchase_return_form extends AppCompatActivity {

    TextView id_pur_re_ven_name,id_pur_re_purAmount,id_pur_re_pending_amount,id_pur_re_net_amount,id_pur_re_purdate,id_pur_re_redate;
    ImageView img_pur_reciept;
    Button id_pur_re_btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_return_form);

        id_pur_re_ven_name = findViewById(R.id.id_pur_re_ven_name);
        id_pur_re_purAmount = findViewById(R.id.id_pur_re_purAmount);
        id_pur_re_pending_amount = findViewById(R.id.id_pur_re_pending_amount);
        id_pur_re_net_amount = findViewById(R.id.id_pur_re_net_amount);
        id_pur_re_purdate = findViewById(R.id.id_pur_re_purdate);
        id_pur_re_redate = findViewById(R.id.id_pur_re_redate);
        img_pur_reciept = findViewById(R.id.img_pur_reciept);
        id_pur_re_btn_save = findViewById(R.id.id_pur_re_btn_save);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker().setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
        MaterialDatePicker materialDatePicker =builder.build();
        builder.setTitleText("SELECT DATE");

        id_pur_re_purdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                id_pur_re_purdate.setText(materialDatePicker.getHeaderText());
            }
        });


        id_pur_re_redate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                id_pur_re_redate.setText(materialDatePicker.getHeaderText());
            }
        });


    }
}