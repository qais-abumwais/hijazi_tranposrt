package com.example.hijazitransport.activity;


import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hijazitransport.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookBus extends Base {

    private ProgressBar progressBar;
    private Spinner fromSpn, toSpn, timeSpn, paymentSpn;
    private Button search;
    private EditText date;
    private TextView passnegerCount;
    private ImageView plusPassenger, minusPassenger;
    private static final int DATE_DIALOG_ID = 999;
    private List<String> places = new ArrayList<>();
    private List<String> paymentMethods = new ArrayList<>();
    private int month, day, year;
    private String[] times = {"6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "17:30", "17:00", "17:30", "18:00", "18:30", "19:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_bus);

        prepareView();

        setSpinnerData();

        //delete keyboard when pressed on date edt and show calender
        date.setKeyListener(null);
        date.onEditorAction(EditorInfo.IME_ACTION_DONE);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentDateOnView();
                showDialog(DATE_DIALOG_ID);
            }
        });

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

    private void prepareView() {
        progressBar = findViewById(R.id.book_bus_progress_bar);
        plusPassenger = findViewById(R.id.plus);
        minusPassenger = findViewById(R.id.minus);
        fromSpn = findViewById(R.id.spinner_from);
        timeSpn = findViewById(R.id.spinner_time);
        toSpn = findViewById(R.id.spinner_to);
        date = findViewById(R.id.departure_date);
        search = findViewById(R.id.search_bus);
        paymentSpn = findViewById(R.id.spinner_payment);
        passnegerCount = findViewById(R.id.passenger_count);

    }

    private void setSpinnerData() {
        //set data to spinners

        places.add("Select One");
        places.add("Amman");
        places.add("Irbid");
        places.add("Yarmouk-University");

        paymentMethods.add("Select One");
        paymentMethods.add("Cash");
        paymentMethods.add("Hijazi Card");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BookBus.this,
                android.R.layout.simple_spinner_item, places);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(BookBus.this,
                android.R.layout.simple_spinner_item, paymentMethods);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(BookBus.this,
                android.R.layout.simple_spinner_item, times);

        fromSpn.setAdapter(dataAdapter);
        toSpn.setAdapter(dataAdapter);
        paymentSpn.setAdapter(dataAdapter2);
        timeSpn.setAdapter(dataAdapter3);
    }

    private void setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_DIALOG_ID) {// set date picker as current date
            DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, year, month,
                    day) {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    if (year < BookBus.this.year)
                        view.updateDate(BookBus.this.year, month, day);

                    if (monthOfYear < month && year == BookBus.this.year)
                        view.updateDate(BookBus.this.year, month, day);

                    if (dayOfMonth < day && year == BookBus.this.year && monthOfYear == month)
                        view.updateDate(BookBus.this.year, month, day);

                }
            };
            return _date;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            date.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        }
    };
}
