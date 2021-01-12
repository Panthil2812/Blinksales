package com.rku.blinksales.form;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.rku.blinksales.R;

public class Expense_list_form extends AppCompatActivity {

    EditText id_exp_date,id_exp_to_whom,id_exp_amount;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list_form);

        id_exp_date = findViewById(R.id.id_pur_lst_date);
        id_exp_to_whom = findViewById(R.id.id_exp_name);
        id_exp_amount = findViewById(R.id.id_exp_amount);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker().setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
        MaterialDatePicker materialDatePicker =builder.build();
        builder.setTitleText("SELECT DATE");

        id_exp_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE PICKER");
            }
        });
//        id_exp_date.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                return false;
//            }
//        });
//        id_exp_date.setOnHoverListener(new View.OnHoverListener() {
//            @Override
//            public boolean onHover(View v, MotionEvent event) {
//                materialDatePicker.show(getSupportFragmentManager(),"DATE PICKER");
//                return true;
//            }
//        });



        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                id_exp_date.setText(materialDatePicker.getHeaderText());
            }
        });


    }
}