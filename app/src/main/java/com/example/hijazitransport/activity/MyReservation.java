package com.example.hijazitransport.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

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
