package com.example.hijazitransport.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hijazitransport.R;
import com.example.hijazitransport.util.BottomNavigationViewHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class Base extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView mNavigationView;
    public View noConnection;

    @Override
    public void setContentView(int view) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        RelativeLayout mBaseLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout mContentLayout = mBaseLayout.findViewById(R.id.content_frame);

        mNavigationView = mBaseLayout.findViewById(R.id.navigation);
        Toolbar toolbar = mBaseLayout.findViewById(R.id.app_toolbar);
        noConnection = mBaseLayout.findViewById(R.id.no_connection);
        BottomNavigationViewHelper.removeShiftMode(mNavigationView);
        mNavigationView.setOnNavigationItemSelectedListener(this);

        getLayoutInflater().inflate(view, mContentLayout, true);

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            noConnection.setVisibility(View.GONE);
            mContentLayout.setVisibility(View.VISIBLE);
            getNavigationMenuItemId();
            initScreen(toolbar);
            updateNavigationBarState();
            super.setContentView(mBaseLayout);
        } else {
            noConnection.setVisibility(View.VISIBLE);
            mContentLayout.setVisibility(View.GONE);
            getNavigationMenuItemId();
            initScreen(toolbar);
            updateNavigationBarState();
            super.setContentView(mBaseLayout);
            return;
        }

    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        if (itemId == -1) {
            Menu menu = mNavigationView.getMenu();
            for (int i = 0, size = menu.size(); i < size; i++) {
                MenuItem item = menu.getItem(i);
                item.setChecked(false);
            }

        } else if (itemId == 0) {
            mNavigationView.setVisibility(View.GONE);
        } else {
            Menu menu = mNavigationView.getMenu();
            for (int i = 0, size = menu.size(); i < size; i++) {
                MenuItem item = menu.getItem(i);
                boolean shouldBeChecked = item.getItemId() == itemId;
                if (shouldBeChecked) {
                    item.setChecked(true);
                    break;
                }
            }
        }

    }

    protected abstract void initScreen(final Toolbar toolbar);

    protected abstract int getNavigationMenuItemId();

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

        if (item.getItemId() != mNavigationView.getSelectedItemId()) {
            switch (item.getItemId()) {
                case R.id.book_bus: {
                    startActivity(new Intent(this, BookBus.class));
                    return true;
                }
                case R.id.my_reservation: {
                    startActivity(new Intent(this, MyReservation.class));
                    return true;
                }
                case R.id.hijazi_card: {
                    startActivity(new Intent(this, HijaziCard.class));
                    return true;
                }
                case R.id.office_location: {
                    startActivity(new Intent(this, OfficeLocation.class));
                    return true;
                }
                case R.id.more: {
                    startActivity(new Intent(this, More.class));
                    return true;
                }
                default: {
                    startActivity(new Intent(this, BookBus.class));

                }
            }
        }
        return true;
    }
}
