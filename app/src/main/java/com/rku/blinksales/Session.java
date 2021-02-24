package com.rku.blinksales;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public Session(Context context) {

        preferences = context.getSharedPreferences("session",0);
        editor = preferences.edit();

    }

    public String getUsername() {
        return preferences.getString("username", "");
    }

    public void setUsername(String username) {
        editor.putString("username", username);
        editor.commit();
    }


}