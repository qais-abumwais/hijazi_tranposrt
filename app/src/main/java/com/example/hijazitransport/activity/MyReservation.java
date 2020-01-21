package com.example.hijazitransport.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.hijazitransport.R;

public class MyReservation extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.my_reservation);
        setSupportActionBar(toolbar);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.my_reservation;
    }
}
