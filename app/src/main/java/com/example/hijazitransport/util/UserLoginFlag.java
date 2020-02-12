package com.example.hijazitransport.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class UserLoginFlag {
    private Context ctx;
    private SharedPreferences defaultPreference;

    public UserLoginFlag(Context context) {
        this.ctx = context;
        this.defaultPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFlag(boolean accessToken) {
        defaultPreference.edit().putBoolean("login",accessToken ).apply();
    }

    public boolean getFlag() {
        return defaultPreference.getBoolean("login",false);
    }

}
