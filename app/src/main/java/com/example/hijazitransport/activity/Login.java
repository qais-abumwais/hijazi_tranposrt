package com.example.hijazitransport.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.hijazitransport.R;

public class Login extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.login);
        setSupportActionBar(toolbar);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return 0;
    }
}
