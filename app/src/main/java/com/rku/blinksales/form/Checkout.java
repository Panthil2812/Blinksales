package com.rku.blinksales.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.rku.blinksales.Fragment.ADDFragment;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;

public class Checkout extends AppCompatActivity {
    ImageButton id_back_arrow;
    TextView checkout_total_item,checkout_total_amount,
            checkout_total_gst,
            checkout_pay_method,checkout_save,checkout_print;
    TextInputEditText checkout_packing_charge,checkout_delivery_charge,checkout_discount,checkout_grand_total
            ,checkout_change_amount,checkout_customer_name,checkout_customer_number,checkout_customer_gst,checkout_return_amount;
    DatabaseDao db;
    Double total_amount,total_gst,grand_total_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        id_back_arrow= findViewById(R.id.id_back_arrow);
        checkout_total_item= findViewById(R.id.checkout_total_item);
        checkout_total_amount= findViewById(R.id.checkout_total_amount);
        checkout_total_gst= findViewById(R.id.checkout_total_gst);
        checkout_grand_total= findViewById(R.id.checkout_grand_total);
        checkout_return_amount= findViewById(R.id.checkout_return_amount);
        checkout_pay_method= findViewById(R.id.checkout_pay_method);
        checkout_save= findViewById(R.id.checkout_save);
        checkout_print= findViewById(R.id.checkout_print);
        checkout_packing_charge= findViewById(R.id.checkout_packing_charge);
        checkout_delivery_charge= findViewById(R.id.checkout_delivery_charge);
        checkout_discount= findViewById(R.id.checkout_discount);
        checkout_change_amount= findViewById(R.id.checkout_change_amount);
        checkout_customer_name= findViewById(R.id.checkout_customer_name);
        checkout_customer_number= findViewById(R.id.checkout_customer_number);
        checkout_customer_gst= findViewById(R.id.checkout_customer_gst);
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();
        //static date get in database
        if(db.findActivityIdCart() !=0)
        {
            int id = db.findActivityIdCart();

             total_amount = db.totalCartAmount(id);
             total_gst = db.totalGstAmount(id);
             grand_total_amount = total_amount + total_gst;
            checkout_total_item.setText(String.valueOf(db.totalCartItem(id)));
            checkout_total_amount.setText(String.valueOf(total_amount));
            checkout_total_gst.setText(String.valueOf(total_gst));
            checkout_grand_total.setText(String.valueOf(grand_total_amount));
            checkout_return_amount.setText("0.00");
            checkout_return_amount.setTextColor(getResources().getColor(R.color.colorAccent));
            disableEditText(checkout_return_amount);
            checkout_grand_total.setTextColor(getResources().getColor(R.color.colorAccent));
            disableEditText(checkout_grand_total);
            checkout_delivery_charge.setText("0.00");
            checkout_packing_charge.setText("0.00");

            checkout_customer_name.setText("Common");
            checkout_customer_number.setText("XXXXXXXXXX");

        }
        //bottom sheet of pat method
        checkout_pay_method.setOnClickListener(v -> {
            new ADDFragment(checkout_pay_method, 33).show(getSupportFragmentManager(), "Dialog");
        });

        //onBack button click event
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });

        //discount edittext after calcultor
        checkout_discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!checkout_discount.getText().toString().trim().isEmpty()) {
                 Double dis = Double.valueOf( checkout_discount.getText().toString().trim()).doubleValue();
                     Double discount_amount = (total_amount * (dis / 100));
                     Double total = (total_amount - discount_amount) + total_gst;
                     checkout_grand_total.setText(String.valueOf(total));
                 }
            }
        });
        checkout_change_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!checkout_change_amount.getText().toString().trim().isEmpty()) {
                    Double c_amount = Double.valueOf(checkout_change_amount.getText().toString().trim()).doubleValue();
                    Double total = Double.valueOf(checkout_grand_total.getText().toString().trim()).doubleValue();
                    Double r_amount = c_amount - total;
                    checkout_return_amount.setText(String.valueOf(r_amount));
                }


            }

        });
    }
    // CLOSE KEYBOARD
    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // EDIT TEXT ENABLE
    private void enableEditText(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
        editText.setEnabled(true);
    }

    // EDIT TEXT DISABLE
    private void disableEditText(EditText editText) {
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
}