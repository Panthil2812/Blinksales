package com.rku.blinksales.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.ExpenseTable;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;
import com.rku.blinksales.Roomdatabase.UserTable;

import java.util.ArrayList;

public class Signup_page extends AppCompatActivity{
    TextInputEditText txtUsername,txtPassword,txtEmail,txtRePassword;
    Button signUp;
    TextView UpToIn;
    DatabaseDao db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        txtUsername = findViewById(R.id.id_signup_username);
        txtEmail = findViewById(R.id.id_signup_email);
        txtPassword = findViewById(R.id.id_signup_password);
        txtRePassword = findViewById(R.id.id_signup_repassword);
        signUp = findViewById(R.id.id_signup_btn);
        UpToIn = findViewById(R.id.id_login_signup);
//        db = new DatabaseHelper(getApplicationContext());
        db = MainRoomDatabase.getInstance(this).getDao();
    }

    public void SignUpToLogin(View view) {
        startActivity(new Intent(Signup_page.this,Login_page.class));
    }

    public void RegisterUser(View view) {
        String user_name=txtUsername.getText().toString().trim();;
        String user_email=txtEmail.getText().toString().trim();;
        String user_password=txtPassword.getText().toString().trim();;
        String re_password = txtRePassword.getText().toString().trim();


        if(user_name.isEmpty() )
        {
            Toast.makeText(getApplicationContext(),"enter username",Toast.LENGTH_SHORT).show();
        }else if(user_email.isEmpty()){
            Toast.makeText(getApplicationContext(),"enter email id",Toast.LENGTH_SHORT).show();

        }else if(!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
            Toast.makeText(getApplicationContext(),"enter valid email id",Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(user_password) || user_password.length() < 8){

            Toast.makeText(getApplicationContext(),"You must have 8 characters in your password",Toast.LENGTH_SHORT).show();

        }else if(!re_password.equals(user_password)){

            Toast.makeText(getApplicationContext(),"not equal password",Toast.LENGTH_SHORT).show();
        }
        else{
            UserTable userTable = new UserTable(user_name,user_email,user_password);
            db.insertUser(userTable);
              SignUpToLogin(view);
        }

    }
}