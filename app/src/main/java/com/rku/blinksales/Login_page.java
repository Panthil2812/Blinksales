package com.rku.blinksales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Login_page extends AppCompatActivity {
    TextInputEditText  Username,Password;
    Button login;
    TextView InToUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Username = findViewById(R.id.id_login_username);
        Password = findViewById(R.id.id_login_password);
        login = findViewById(R.id.id_login_btn);
        InToUp = findViewById(R.id.id_login_signup);
        login.setOnClickListener(v -> {
            String txtusername = Username.getText().toString().trim();
            String txtpassword = Password.getText().toString().trim();
            if(txtusername.equals("Admin") && txtpassword.equals("admin"))
            {
                startActivity(new Intent(Login_page.this,MainActivity.class));
            }else{
                Toast.makeText(Login_page.this,"Login failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void LoginToSignUp(View view) {
        startActivity(new Intent(Login_page.this,Signup_page.class));
    }

}