package com.example.hijazitransport.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.hijazitransport.R;

public class OfficeLocation extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_location);
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.office_location);
        setSupportActionBar(toolbar);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.my_reservation;
    }
}
