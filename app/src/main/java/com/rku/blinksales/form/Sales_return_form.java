package com.rku.blinksales.form;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rku.blinksales.Adapter.SalesReturnItemRecyclerViewAdapter;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.BillTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.ReturnItemsTable;
import com.rku.blinksales.Roomdatabase.SalesReturnTable;
import com.rku.blinksales.Roomdatabase.SoldItemTable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Sales_return_form extends AppCompatActivity {
    ImageButton id_back_arrow, search_bill_id;
    TextView bill_return_id, bill_return_total_good, bill_return_date, bill_return_discount, bill_return_gst, bill_return_grand_total, bill_return_p_charge,
            bill_return_d_charge, bill_return_c_name, bill_return_phone;
    TextInputEditText bill_return_note;
    EditText bill_search;
    Button bill_return_save;
    ScrollView sales_return_scrollview;
    RecyclerView bill_return_recyclerView;
    DatabaseDao db;
    SalesReturnItemRecyclerViewAdapter salesReturnRecyclerViewAdapter;
    DecimalFormat df = new DecimalFormat("#.###");
    int bill_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_return_form);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();
        bill_search = findViewById(R.id.bill_search);
        search_bill_id = findViewById(R.id.search_bill_id);

        bill_return_id = findViewById(R.id.bill_return_id);
        bill_return_total_good = findViewById(R.id.bill_return_total_good);
        bill_return_date = findViewById(R.id.bill_return_date);
        bill_return_discount = findViewById(R.id.bill_return_discount);
        bill_return_gst = findViewById(R.id.bill_return_gst);
        bill_return_grand_total = findViewById(R.id.bill_return_grand_total);
        bill_return_p_charge = findViewById(R.id.bill_return_p_charge);
        bill_return_d_charge = findViewById(R.id.bill_return_d_charge);
        bill_return_c_name = findViewById(R.id.bill_return_c_name);
        bill_return_phone = findViewById(R.id.bill_return_phone);
        bill_return_note = findViewById(R.id.bill_return_note);
        bill_return_save = findViewById(R.id.bill_return_save);
        sales_return_scrollview = findViewById(R.id.sales_return_scrollview);
        bill_return_recyclerView = findViewById(R.id.bill_return_recyclerView);
        sales_return_scrollview.setVisibility(View.GONE);

        //onBackPressed method
        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });
        bill_return_save.setOnClickListener(v -> {
            int count = db.getTotalReturnItemsTable(1, bill_no);
            List<ReturnItemsTable> returnItemsTables = db.getOneReturnItemsTable(1, bill_no);
            for (int i = 0; i < returnItemsTables.size(); i++) {
                ReturnItemsTable item = returnItemsTables.get(i);
                SoldItemTable soldItemTable = db.getOneSoldItemTable(item.getBill_id(),item.getProduct_id());
                SoldItemTable ItemTable = new SoldItemTable(soldItemTable.getBill_id(),soldItemTable.getProduct_id(),soldItemTable.getProduct_image_uri(),
                        soldItemTable.getProduct_name(),soldItemTable.getProduct_category(),soldItemTable.getProduct_mrp(),soldItemTable.getProduct_selling_price(),
                        soldItemTable.getGood_value(),soldItemTable.getProduct_qty(),item.getQty(),soldItemTable.getProduct_unit(),soldItemTable.getProduct_price_unit(),
                        soldItemTable.getProduct_barcode(),soldItemTable.getProduct_stock(),soldItemTable.getProduct_is_include(),soldItemTable.getGst(),soldItemTable.getGst_amount(),
                        item.getAmount(),item.getGood_amount(),soldItemTable.getDiscount(),soldItemTable.getHSN());
                ItemTable.setSold_id(soldItemTable.getSold_id());
                db.updateSoldItemTable(ItemTable);
            }
            BillTable table = db.getBillTable(bill_no);
            Double return_amount =table.getGrand_total()-db.totalCartAmountSoldItem(bill_no);
            Double return_good_amount =table.getGood_amount()-db.totalGoodAmountSoldItem(bill_no);
            Double return_gst_amount =table.getTotal_get()-db.totalGstAmountSoldItem(bill_no);
            //new bill value
            Double total_return_amount =(table.getGrand_total()-return_amount);
            Double total_return_good_amount =(table.getGood_amount()-return_good_amount);
            Double total_gst_amount =(table.getTotal_get()-return_gst_amount) ;
            Double total_discount_amount =(total_return_amount * (table.getTotal_discount() / 100));
            Double total_bill_amount = (total_return_amount -total_discount_amount);

            BillTable updatedBill = new BillTable(table.getUnique_id(),table.getBill_date(),table.getTotalItem(),total_return_good_amount,total_return_amount,
                    total_bill_amount,table.getTotal_discount(),total_discount_amount,total_gst_amount,table.getBill_method(),table.getCustomer_name(),
                    table.getCustomer_number(),table.getCustomer_gst(),table.getPacking_charge(),table.getDelivery_charge());
            updatedBill.setBill_id(table.getBill_id());
            db.updateBillTable(updatedBill);
            SalesReturnTable salesReturnTable = new SalesReturnTable(table.getBill_id(),(total_bill_amount+table.getPacking_charge()+table.getDelivery_charge())
                    ,return_amount,bill_return_note.getText().toString().trim(),new Date());
            db.insertSalesReturnTable(salesReturnTable);
           onBackPressed();
        });
        //bill Search button method
        search_bill_id.setOnClickListener(v -> {
            closeKeyboard();
            bill_search.clearFocus();
            String search = bill_search.getText().toString().trim();
            if (!search.isEmpty()) {
                int id = Integer.valueOf(search);
                if (db.getCountBillTable(id) == 1) {
                    BillTable note = db.getBillTable(id);
                    sales_return_scrollview.setVisibility(View.VISIBLE);
                    bill_no = note.getBill_id();
                    bill_return_id.setText(String.valueOf(note.getBill_id()));
                    bill_return_total_good.setText(String.valueOf(note.getGood_amount()));
                    bill_return_grand_total.setText(String.valueOf(note.getBill_amount() + note.getPacking_charge().intValue() + note.getDelivery_charge().intValue()));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String date = simpleDateFormat.format(note.getBill_date());
                    bill_return_date.setText(date);
                    bill_return_discount.setText(String.valueOf(note.getDiscount_amount()));
                    bill_return_gst.setText(String.valueOf(note.getTotal_get()));
                    bill_return_p_charge.setText(String.valueOf(note.getPacking_charge().intValue()));
                    bill_return_d_charge.setText(String.valueOf(note.getDelivery_charge().intValue()));
                    bill_return_c_name.setText(note.getCustomer_name());
                    bill_return_phone.setText(note.getCustomer_number());
                    DisplayItem(id);
//                     Toast.makeText(getApplicationContext(),note.getCustomer_name(),Toast.LENGTH_SHORT).show();
                } else {
                    sales_return_scrollview.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Id Not Found", Toast.LENGTH_SHORT).show();
                }
            } else {
                sales_return_scrollview.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "Please Enter Bill Id", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void DisplayItem(int id) {
        //List<SoldItemTable> soldItemTableList =db.getFilterSoldItemTable(id);
        salesReturnRecyclerViewAdapter = new SalesReturnItemRecyclerViewAdapter(getApplicationContext(), null, null);
        bill_return_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        bill_return_recyclerView.setHasFixedSize(true);
        bill_return_recyclerView.setAdapter(salesReturnRecyclerViewAdapter);
        db.getReturnSoldItemTable(id).observe(this, new Observer<List<SoldItemTable>>() {
            @Override
            public void onChanged(@Nullable List<SoldItemTable> notes) {

                salesReturnRecyclerViewAdapter.setNotes(notes);
            }
        });

        salesReturnRecyclerViewAdapter.setOnItemClickListener(new SalesReturnItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void checkbox_true(SoldItemTable note, CheckBox checkBox, ImageButton cart_btn_plus, ImageButton cart_btn_min, EditText qty, TextView amount) {
                if (checkBox.isChecked()) {
                    enableEditText(qty);
                    cart_btn_plus.setOnClickListener(v -> {
                        closeKeyboard();
                        qty.clearFocus();
                        String str = qty.getText().toString().trim();
                        String value = note.getProduct_selling_price().toString();
                        Double number = Double.valueOf(str).doubleValue();
                        Double total_amount = Double.valueOf(value).doubleValue();
                        if(number < note.getSelected_qty())
                        {
                            number++;
                            total_amount = (number * total_amount);
                            Double total_good_value = (number * note.getGood_value());
                            Double total_gst_amount = (number * note.getGst_amount());
                            qty.setText(String.valueOf(number));
                            amount.setText(String.valueOf(total_amount));
                            if (db.getCountReturnItemsTable(note.getProduct_id(), note.getBill_id()) == 0) {
                                ReturnItemsTable returnItemsTable = new ReturnItemsTable(note.getBill_id(), note.getProduct_id(), 1, number, total_amount, total_good_value, total_gst_amount);
                                db.insertReturnItemsTable(returnItemsTable);
                                Toast.makeText(getApplicationContext(), "Insert", Toast.LENGTH_SHORT).show();

                            } else {
                                ReturnItemsTable returnItemsTable = new ReturnItemsTable(note.getBill_id(), note.getProduct_id(), 1, number, total_amount, total_good_value, total_gst_amount);
                                returnItemsTable.setR_item_id(db.getReturnItemsIdTable(note.getProduct_id(), note.getBill_id()));
                                db.updateReturnItemsTable(returnItemsTable);
                                Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    cart_btn_min.setOnClickListener(v -> {
                        closeKeyboard();
                        qty.clearFocus();
                        String str = qty.getText().toString().trim();
                        String value = note.getProduct_selling_price().toString();
                        Double number = Double.valueOf(str).doubleValue();
                        Double total_amount = Double.valueOf(value).doubleValue();
                        number--;
                        if (number < 0.0) {
                            // qty.setText(String.valueOf(number));
                            Toast.makeText(getApplicationContext(), "qty not less than 0", Toast.LENGTH_SHORT).show();
                        } else {
                            qty.setText(String.valueOf(number));
                            total_amount = (number * total_amount);
                            Double total_good_value = (number * note.getGood_value());
                            Double total_gst_amount = (number * note.getGst_amount());
                            amount.setText(String.valueOf(total_amount));
                            if (db.getCountReturnItemsTable(note.getProduct_id(), note.getBill_id()) == 0) {
                                ReturnItemsTable returnItemsTable = new ReturnItemsTable(note.getBill_id(), note.getProduct_id(), 1, number, total_amount, total_good_value, total_gst_amount);
                                db.insertReturnItemsTable(returnItemsTable);
                                Toast.makeText(getApplicationContext(), "Insert", Toast.LENGTH_SHORT).show();

                            } else {

                                ReturnItemsTable returnItemsTable = new ReturnItemsTable(note.getBill_id(), note.getProduct_id(), 1, number, total_amount, total_good_value, total_gst_amount);
                                returnItemsTable.setR_item_id(db.getReturnItemsIdTable(note.getProduct_id(), note.getBill_id()));
                                db.updateReturnItemsTable(returnItemsTable);
                                Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                } else {
                    String str = String.valueOf(note.getSelected_qty());
                    String value = note.getProduct_selling_price().toString();
                    Double number = Double.valueOf(str).doubleValue();
                    Double total_amount = Double.valueOf(value).doubleValue();
                    number++;
                    total_amount = (number * total_amount);
                    Double total_good_value = (number * note.getGood_value());
                    Double total_gst_amount = (number * note.getGst_amount());
                    qty.setText(String.valueOf(note.getSelected_qty()));
                    amount.setText(String.valueOf(note.getTotal_amount()));
                    db.updateItemTable(note.getProduct_id(), note.getBill_id());
                    disableEditText(qty);
                }
            }
        });

    }

    public void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            bill_search.clearFocus();
        }
    }

    // EDIT TEXT ENABLE
    public void enableEditText(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
        editText.setEnabled(true);
    }

    // EDIT TEXT DISABLE
    public void disableEditText(EditText editText) {
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
}