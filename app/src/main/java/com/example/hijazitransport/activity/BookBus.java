package com.example.hijazitransport.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.hijazitransport.R;

public class BookBus extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_bus);
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.book_a_bus);
        setSupportActionBar(toolbar);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.book_bus;
    }
}
