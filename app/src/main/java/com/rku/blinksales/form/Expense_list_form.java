package com.rku.blinksales.form;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rku.blinksales.Fragment.ADDFragment;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.ExpenseTable;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Expense_list_form extends AppCompatActivity {
    TextView id_exp_date,id_exp_type,pageTite;
    EditText id_exp_to_whom,id_exp_amount;
    ImageButton id_back_arrow;
    Button id_exp_btn_save;
    final int add = -1;
    int id = -1;
    DatabaseDao db;
    Date chosenDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list_form);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        db = MainRoomDatabase.getInstance(this).getDao();
        id_back_arrow = findViewById(R.id.id_back_arrow);
        pageTite = findViewById(R.id.pageTite);
        id_exp_date = findViewById(R.id.id_exp_date);
        id_exp_type = findViewById(R.id.id_exp_type);
        id_exp_to_whom = findViewById(R.id.id_exp_name);
        id_exp_amount = findViewById(R.id.id_exp_amount);
        id_exp_btn_save = findViewById(R.id.id_exp_btn_save);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            pageTite.setText("Edit Expense");
            id_exp_to_whom.setText(intent.getStringExtra("name"));
            id_exp_amount.setText(intent.getStringExtra("amount"));
            try {
                String strDate = intent.getStringExtra("date");
                DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                chosenDate = formatter.parse(strDate);
                String dateStr = formatter.format(chosenDate);
                id_exp_date.setText(dateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            id_exp_type.setText(intent.getStringExtra("type"));
            id = intent.getIntExtra("id",-1);

        } else {
            pageTite.setText("Add Expense");
        }

        id_back_arrow.setOnClickListener(v -> {
            onBackPressed();
        });

        // Show DatePickerDialog box Onclick Method
        id_exp_date.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int  mYear = c.get(Calendar.YEAR);
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
                            id_exp_date.setText(df_medium_us_str);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        // Show Bottom Sheet For Add and Select Expense type
        id_exp_type.setOnClickListener(v -> {
            new ADDFragment(id_exp_type,13).show(getSupportFragmentManager(),"Dialog");
        });

        // Submit all date for add and edit expense list
        id_exp_btn_save.setOnClickListener(v ->{
            String exp_name = id_exp_to_whom.getText().toString().trim() ;
            String exp_amount = id_exp_amount.getText().toString().trim() ;
            String exp_date =id_exp_date.getText().toString().trim();
            String exp_type  = id_exp_type.getText().toString().trim();

            if(exp_name.isEmpty() )
            {
                Toast.makeText(getApplicationContext(),"enter name",Toast.LENGTH_SHORT).show();
            }else if(exp_amount.isEmpty()){

                Toast.makeText(getApplicationContext(),"enter amount",Toast.LENGTH_SHORT).show();

            }else if(exp_date.equals("Select Date")){

                Toast.makeText(getApplicationContext(),"select date ",Toast.LENGTH_SHORT).show();

            }else if(exp_type.equals("Select Expense Type")){

                Toast.makeText(getApplicationContext(),"select type",Toast.LENGTH_SHORT).show();
            }
            else{
                ExpenseTable expenseTable = new ExpenseTable(exp_name,exp_amount,chosenDate,exp_type);
                try{
                    if(add != id)
                    {
                        Toast.makeText(getApplicationContext(),"edit expense .........",Toast.LENGTH_SHORT).show();
                        try{
                            expenseTable.setId(id);
                            db.updateExpenseTable(expenseTable);
                        }catch (Exception e)
                        {
                            e.getStackTrace();
                        }

                    }else {
                        try{
                            db.insertExpenseTable(expenseTable);
                        }catch (Exception e)
                        {
                            e.getStackTrace();
                        }

                        Toast.makeText(getApplicationContext(),"insertDate",Toast.LENGTH_SHORT).show();
                    }

                    onBackPressed();
                }catch (Exception e){e.getStackTrace();}



            }


        });



    }
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
}