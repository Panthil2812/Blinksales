package com.rku.blinksales.form;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

import java.text.DateFormat;
import java.util.Calendar;

public class Expense_list_form extends AppCompatActivity {
    TextView id_exp_date,id_exp_type;
    EditText id_exp_to_whom,id_exp_amount;
    ImageButton id_back_arrow;
    Button id_exp_btn_save;
    int i = 0;
    DatabaseDao db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list_form);
        id_back_arrow = findViewById(R.id.id_back_arrow);
        id_exp_date = findViewById(R.id.id_exp_date);
        id_exp_type = findViewById(R.id.id_exp_type);
        id_exp_to_whom = findViewById(R.id.id_exp_name);
        id_exp_amount = findViewById(R.id.id_exp_amount);
        id_exp_btn_save = findViewById(R.id.id_exp_btn_save);
        db = MainRoomDatabase.getInstance(this).getDao();

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
                            id_exp_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
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
                ExpenseTable expenseTable = new ExpenseTable(exp_name,exp_amount,exp_date,exp_type);
                try{
                    db.insertExpenseTable(expenseTable);

                    Toast.makeText(getApplicationContext(),"insertDate",Toast.LENGTH_SHORT).show();
                    //onBackPressed();
                }catch (Exception e){e.getStackTrace();}



            }


        });
    }
}