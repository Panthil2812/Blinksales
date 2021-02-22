package com.rku.blinksales.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rku.blinksales.MainActivity;
import com.rku.blinksales.R;
import com.rku.blinksales.Roomdatabase.DatabaseDao;
import com.rku.blinksales.Roomdatabase.MainRoomDatabase;

public class Login_page extends AppCompatActivity {
    TextInputEditText  Username,Password;
    Button login;
    TextView InToUp;
    DatabaseDao db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Username = findViewById(R.id.id_login_username);
        Password = findViewById(R.id.id_login_password);
        login = findViewById(R.id.id_login_btn);
        InToUp = findViewById(R.id.id_login_signup);
        db = MainRoomDatabase.getInstance(this).getDao();
        login.setOnClickListener(v -> {
            String txtusername = Username.getText().toString().trim();
            String txtpassword = Password.getText().toString().trim();
            if(db.ValidateUser(txtusername,txtpassword))
            {
                startActivity(new Intent(Login_page.this, MainActivity.class));
            }else{
                Toast.makeText(Login_page.this,"Login failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void LoginToSignUp(View view) {
        startActivity(new Intent(Login_page.this,Signup_page.class));
    }

}