package com.rku.blinksales.Reports;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rku.blinksales.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Sales_report extends AppCompatActivity {

    TextView id_from_date,id_to_date;
    LinearLayout layout_from, layout_to;
    ImageButton btn_search;
    Date chosenDate,temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);

        id_from_date = findViewById(R.id.id_from_date);
        id_to_date = findViewById(R.id.id_to_date);
        btn_search = findViewById(R.id.btn_search);
        layout_from = findViewById(R.id.layout_from);
        layout_to = findViewById(R.id.layout_to);

        //DATE DIALOG BOX SELECT
        layout_from.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(0);
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    chosenDate = cal.getTime();
                    temp = chosenDate;

                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    String df_medium_us_str = formatter.format(chosenDate);
                    id_from_date.setText(df_medium_us_str);
                }

            }, mYear, mMonth, mDay);
            if (!id_to_date.getText().toString().trim().equals("dd-MMM-yyyy")){
                datePickerDialog.getDatePicker().setMaxDate(chosenDate.getTime());
            }
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        //DATE DIALOG BOX SELECT
        layout_to.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(0);
                    cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);

                    chosenDate = cal.getTime();
                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    String df_medium_us_str = formatter.format(chosenDate);
                    id_to_date.setText(df_medium_us_str);
                }

            }, mYear, mMonth, mDay);
            if (!id_from_date.getText().toString().trim().equals("dd-MMM-yyyy")){
                datePickerDialog.getDatePicker().setMinDate(temp.getTime());
            }
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id_from_date.getText().toString().trim().equals("dd-MMM-yyyy") && !id_to_date.getText().toString().trim().equals("dd-MMM-yyyy")) {
                    Toast.makeText(Sales_report.this, "From : " + id_from_date.getText().toString() + " To : " + id_to_date.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Sales_report.this, "Please select date", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}