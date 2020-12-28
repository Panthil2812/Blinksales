package com.rku.blinksales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Signup_page extends AppCompatActivity {
    TextInputEditText txtUsername,txtPassword,txtEmail,txtRePassword;
    Button signUp;
    TextView UpToIn;
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

    }

    public void SignUpToLogin(View view) {
        startActivity(new Intent(Signup_page.this,Login_page.class));
    }
}