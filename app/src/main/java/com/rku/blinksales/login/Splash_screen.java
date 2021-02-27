package com.rku.blinksales.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.rku.blinksales.MainActivity;
import com.rku.blinksales.R;
import com.rku.blinksales.Session;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_screen extends AppCompatActivity {

    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //if (session.getUsername() == ""){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash_screen.this,Login_page.class);
                    startActivity(intent);
                    finish();
                }
            },2000);
        //}else{
//            Intent intent = new Intent(Splash_screen.this, MainActivity.class);
//            startActivity(intent);
//        }
    }
}