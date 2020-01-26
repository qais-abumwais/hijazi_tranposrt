package com.example.hijazitransport.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class UserLoginFlag {

    private Context ctx;
    private SharedPreferences default_prefence;

    public UserLoginFlag(Context context) {
        this.ctx = context;
        default_prefence = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setYourFlag(boolean accessToken) {
        default_prefence.edit().putBoolean("login",accessToken ).apply();
    }

    public boolean getYourFlag() {
        return default_prefence.getBoolean("login",false);
    }

}
