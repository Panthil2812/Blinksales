package com.rku.blinksales;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rku.blinksales.Roomdatabase.BillTable;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.SoldItemTable;

import java.text.SimpleDateFormat;
import java.util.List;

public class BillingDetails extends AppCompatActivity {

    TableLayout item_table;
    DatabaseDao db;
    TextView billing_details_id, billing_details_total_item, billing_details_date, billing_details_c_name, billing_details_phone, billing_details_discount,
            billing_details_get_amount, billing_details_grand_total, billing_details_net_amount;
    ImageButton id_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);
        db = MainRoomDatabase.getInstance(getApplicationContext()).getDao();
        id_back_arrow = findViewById(R.id.id_back_arrow);
        billing_details_id = findViewById(R.id.billing_details_id);
        billing_details_total_item = findViewById(R.id.billing_details_total_item);
        billing_details_date = findViewById(R.id.billing_details_date);
        billing_details_c_name = findViewById(R.id.billing_details_c_name);
        billing_details_phone = findViewById(R.id.billing_details_phone);
        billing_details_discount = findViewById(R.id.billing_details_discount);
        billing_details_get_amount = findViewById(R.id.billing_details_get_amount);
        billing_details_grand_total = findViewById(R.id.billing_details_grand_total);
        billing_details_net_amount = findViewById(R.id.billing_details_net_amount);

        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });

        Intent intent = getIntent();
        if (intent.hasExtra("bill_id")) {
            int bill_id = intent.getIntExtra("bill_id", -1);
            addItems(bill_id);
        }

    }

    @SuppressLint("ResourceAsColor")
    private void addItems(int id) {
        item_table = findViewById(R.id.item_table);
        if (id != -1) {
            BillTable billTable = db.getBillTable(id);
            billing_details_id.setText(String.valueOf(billTable.getBill_id()));
            billing_details_total_item.setText(String.valueOf(billTable.getTotalItem().intValue()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = simpleDateFormat.format(billTable.getBill_date());
            billing_details_date.setText(date);
            billing_details_c_name.setText(billTable.getCustomer_name());
            billing_details_phone.setText(billTable.getCustomer_number());
            billing_details_discount.setText(billTable.getTotal_discount().intValue() + " %");
            billing_details_get_amount.setText(String.valueOf(billTable.getTotal_get().intValue()));
            billing_details_grand_total.setText(String.valueOf(billTable.getAmount().intValue()));
            billing_details_net_amount.setText(String.valueOf(billTable.getBill_amount()));

            List<SoldItemTable> soldItemTables = db.getFilterSoldItemTable(id);

            for (int i = 0; i < soldItemTables.size(); i++) {
                SoldItemTable note = soldItemTables.get(i);
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


                TextView txt_item = new TextView(this);
                txt_item.setText(note.getProduct_name());
                txt_item.setTextSize(18);
                txt_item.setTextColor(R.color.colorPrimary);
                txt_item.setGravity(Gravity.START);
                txt_item.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                row.addView(txt_item);

                TextView txt_qty = new TextView(this);
                txt_qty.setText(String.valueOf(note.getSelected_qty()));
                txt_qty.setTextSize(18);
                txt_qty.setTextColor(R.color.colorAccent);
                txt_qty.setGravity(Gravity.START);
                txt_qty.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                row.addView(txt_qty);

                TextView txt_unit = new TextView(this);
                txt_unit.setText(note.getProduct_price_unit());
                txt_unit.setTextSize(18);
                txt_unit.setTextColor(R.color.colorAccent);
                txt_unit.setGravity(Gravity.START);
                txt_unit.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                row.addView(txt_unit);

                TextView txt_total = new TextView(this);
                txt_total.setText(String.valueOf(note.getTotal_amount().intValue()));
                txt_total.setTextSize(18);
                txt_total.setTextColor(R.color.colorAccent);
                txt_total.setGravity(Gravity.END);
                txt_total.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                row.addView(txt_total);

                item_table.addView(row);
            }

        }
    }


}