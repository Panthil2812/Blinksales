package com.rku.blinksales;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BillingDetails extends AppCompatActivity {

    TableLayout item_table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);
        addItems();
    }

    @SuppressLint("ResourceAsColor")
    private void addItems(){
        item_table = findViewById(R.id.item_table);

        for (int i =0;i<3;i++ ){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView txt_item = new TextView(this);
            txt_item.setText("item "+ i);
            txt_item.setTextSize(18);
            txt_item.setTextColor(R.color.colorAccent);
            txt_item.setGravity(Gravity.CENTER);
            txt_item.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f));
            row.addView(txt_item);

            TextView txt_qty = new TextView(this);
            txt_qty.setText(" " + i);
            txt_qty.setTextSize(18);
            txt_qty.setTextColor(R.color.colorAccent);
            txt_qty.setGravity(Gravity.CENTER);
            txt_qty.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f));
            row.addView(txt_qty);

            TextView txt_unit = new TextView(this);
            txt_unit.setText(" " + (i * 10));
            txt_unit.setTextSize(18);
            txt_unit.setTextColor(R.color.colorAccent);
            txt_unit.setGravity(Gravity.CENTER);
            txt_unit.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f));
            row.addView(txt_unit);

            TextView txt_total = new TextView(this);
            txt_total.setText(" "+ (i*i*10));
            txt_total.setTextSize(18);
            txt_total.setTextColor(R.color.colorAccent);
            txt_total.setGravity(Gravity.CENTER);
            txt_total.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f));
            row.addView(txt_total);

            item_table.addView(row);
        }
    }


}