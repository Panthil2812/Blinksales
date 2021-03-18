package com.rku.blinksales.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rku.blinksales.Fragment.ADDFragment;
import com.rku.blinksales.MainActivity;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.BillTable;
import com.rku.blinksales.Roomdatabase.CartTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.SoldItemTable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Checkout extends AppCompatActivity {
    ImageButton id_back_arrow;
    TextView checkout_total_item, checkout_total_amount,
            checkout_total_gst,
            checkout_pay_method, checkout_save, checkout_print;
    TextInputEditText checkout_packing_charge, checkout_delivery_charge, checkout_discount, checkout_grand_total, checkout_change_amount, checkout_customer_name, checkout_customer_number, checkout_customer_gst, checkout_return_amount;
    DatabaseDao db;
    Double discount_amount = 0.0, total_amount = 0.0, total_gst = 0.0, grand_total_amount = 0.0, total_item = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        id_back_arrow = findViewById(R.id.id_back_arrow);
        checkout_total_item = findViewById(R.id.checkout_total_item);
        checkout_total_amount = findViewById(R.id.checkout_total_amount);
        checkout_total_gst = findViewById(R.id.checkout_total_gst);
        checkout_grand_total = findViewById(R.id.checkout_grand_total);
        checkout_return_amount = findViewById(R.id.checkout_return_amount);
        checkout_pay_method = findViewById(R.id.checkout_pay_method);
        checkout_save = findViewById(R.id.checkout_save);
        checkout_print = findViewById(R.id.checkout_print);
        checkout_packing_charge = findViewById(R.id.checkout_packing_charge);
        checkout_delivery_charge = findViewById(R.id.checkout_delivery_charge);
        checkout_discount = findViewById(R.id.checkout_discount);
        checkout_change_amount = findViewById(R.id.checkout_change_amount);
        checkout_customer_name = findViewById(R.id.checkout_customer_name);
        checkout_customer_number = findViewById(R.id.checkout_customer_number);
        checkout_customer_gst = findViewById(R.id.checkout_customer_gst);
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();
        //static date get in database
        if (db.findActivityIdCart() != 0) {
            int id = db.findActivityIdCart();
            total_item = db.totalCartItem(id);
            total_amount = db.totalCartAmount(id);
            total_gst = db.totalGstAmount(id);
            grand_total_amount = total_amount + total_gst;
            checkout_total_item.setText(String.valueOf(total_item.intValue()));
            checkout_total_amount.setText(String.valueOf(total_amount));
            checkout_total_gst.setText(String.valueOf(total_gst));
            checkout_grand_total.setText(String.valueOf(grand_total_amount.intValue()));
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
                if (!s.toString().trim().isEmpty()) {
                    Double dis = Double.valueOf(s.toString().trim()).doubleValue();
                    discount_amount = (total_amount * (dis / 100));

                    Double total = (total_amount - discount_amount) + total_gst;

                    checkout_grand_total.setText(String.valueOf(total.intValue()));
                } else if (s.toString().trim().isEmpty()) {
                    checkout_grand_total.setText(String.valueOf(grand_total_amount.intValue()));
                }
            }
        });

        //change amount using discount
        checkout_change_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    Double c_amount = Double.valueOf(s.toString().trim()).doubleValue();
                    Double total = Double.valueOf(checkout_grand_total.getText().toString().trim()).doubleValue();
                    Double r_amount = c_amount - total;
                    //checkout_change_amount.setText(String.valueOf(c_amount));
                    checkout_return_amount.setText(String.valueOf(r_amount));
                } else if (s.toString().trim().isEmpty()) {
                    checkout_return_amount.setText("0.0");

                }


            }

        });


        //only save bill onclick method
        checkout_save.setOnClickListener(v -> {
            SaveBill();
            Toast.makeText(getApplicationContext(), "save bill", Toast.LENGTH_SHORT).show();
            closeKeyboard();
        });

        // save and print bill onclick method
        checkout_print.setOnClickListener(v -> {
            SaveBill();
            Toast.makeText(getApplicationContext(), "save and print bill ", Toast.LENGTH_SHORT).show();
            closeKeyboard();
        });
    }

    private void SaveBill() {
        String unique_id = UUID.randomUUID().toString();
        Date bill_date = new Date();
        Double totalItem = total_item;
        Double amount = total_amount;
        Double bill_amount = Double.valueOf(checkout_grand_total.getText().toString().trim()).doubleValue();
        Double total_discount = 0.0, dis_amount = 0.0, packing_charge = 0.0, delivery_charge = 0.0;
        if (!checkout_discount.getText().toString().trim().isEmpty()) {
            total_discount = Double.valueOf(checkout_discount.getText().toString().trim()).doubleValue();
            dis_amount = (amount * (total_discount / 100));
        }
        Double total_get = total_gst;
        String bill_method = checkout_pay_method.getText().toString().trim();
        String customer_name = checkout_customer_name.getText().toString().trim();
        String customer_number = checkout_customer_number.getText().toString().trim();
        String customer_gst = checkout_customer_gst.getText().toString().trim();
        ;
        if (!checkout_packing_charge.getText().toString().trim().isEmpty()) {
            packing_charge = Double.valueOf(checkout_packing_charge.getText().toString().trim()).doubleValue();
        }
        if (!checkout_delivery_charge.getText().toString().trim().isEmpty()) {
            delivery_charge = Double.valueOf(checkout_delivery_charge.getText().toString().trim()).doubleValue();
        }

        BillTable billTable = new BillTable(unique_id, bill_date, totalItem, amount, bill_amount, total_discount, dis_amount, total_get,
                bill_method, customer_name, customer_number, customer_gst, packing_charge, delivery_charge);
        db.insertBillTable(billTable);
        if (db.getBillId(unique_id) != 0) {
            int bill_id = db.getBillId(unique_id);
            if (db.findActivityIdCart() != 0) {
                List<CartTable> cartTableList = db.getAllDateCartTable(db.findActivityIdCart());

                for (int i = 0; i < cartTableList.size(); i++) {
                    CartTable note = cartTableList.get(i);
                    SoldItemTable soldItemTable = new SoldItemTable(bill_id, note.getProduct_id(), note.getProduct_image_uri(),
                            note.getProduct_name(), note.getProduct_category(), note.getProduct_mrp(), note.getProduct_selling_price()
                            , note.getProduct_qty(), note.getSelected_qty(), note.getProduct_unit(), note.getProduct_price_unit()
                            , note.getProduct_barcode(), note.getProduct_stock(), note.getProduct_is_include(), note.getGst()
                            , note.getGst_amount(), note.getTotal_amount(), note.getDiscount(), note.getHSN());
                    db.insertSoldItemTable(soldItemTable);
                    db.deleteCartTable(note);
                }
                db.DeletePendingCartTable(db.findActivityIdCart());
                closeKeyboard();
            }
        }
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

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