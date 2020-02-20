package com.example.hijazitransport.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.example.hijazitransport.R;
import com.example.hijazitransport.model.CardNumber;
import com.example.hijazitransport.model.HijaziBookingInformation;
import com.example.hijazitransport.model.UserBookingInformation;
import com.example.hijazitransport.model.UserRegisterData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
    private List<String> times = new ArrayList<>();
    private int month, day, year;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef;
    private UserRegisterData userRegisterData = new UserRegisterData();
    private boolean flag = true;
    private String buscount;
    private int passnger, busCount2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_bus);

        prepareView();

        setSpinnerData();

        timeSpn.setAlpha(0.1f);
        timeSpn.setEnabled(false);

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

        minusPassenger.setAlpha(0.5f);
        minusPassenger.setEnabled(false);
        plusPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(passnegerCount.getText().toString()) <= 49) {
                    if (Integer.parseInt(passnegerCount.getText().toString()) > 0) {
                        minusPassenger.setAlpha(1f);
                        minusPassenger.setEnabled(true);
                    }
                    int count = Integer.parseInt(passnegerCount.getText().toString());
                    count++;
                    if (count == 50) {
                        plusPassenger.setAlpha(0.5f);
                        plusPassenger.setEnabled(false);
                    }
                    passnegerCount.setText(String.valueOf(count));
                } else {
                    plusPassenger.setAlpha(0.5f);
                    plusPassenger.setEnabled(false);
                }
            }
        });
        minusPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(passnegerCount.getText().toString()) > 1) {
                    if (Integer.parseInt(passnegerCount.getText().toString()) <= 50) {
                        plusPassenger.setAlpha(1f);
                        plusPassenger.setEnabled(true);
                    }
                    int count;
                    count = Integer.parseInt(passnegerCount.getText().toString());
                    count--;
                    passnegerCount.setText(String.valueOf(count));

                    if (count == 1) {
                        minusPassenger.setAlpha(0.5f);
                        minusPassenger.setEnabled(false);
                    }
                } else if (Integer.parseInt(passnegerCount.getText().toString()) == 1) {
                    minusPassenger.setAlpha(0.5f);
                    minusPassenger.setEnabled(false);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
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

    private void validation() {
        if (fromSpn.getSelectedItem().toString().equals("Select One")) {
            fromSpn.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));
            return;
        } else {
            fromSpn.setBackground(getResources().getDrawable(R.drawable.spinner_black_boarder));
        }

        if (toSpn.getSelectedItem().toString().equals("Select One")) {
            toSpn.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));
            return;
        } else {
            toSpn.setBackground(getResources().getDrawable(R.drawable.spinner_black_boarder));
        }

        if (fromSpn.getSelectedItem().toString().equals(toSpn.getSelectedItem().toString())) {
            toSpn.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));
            return;
        } else {
            toSpn.setBackground(getResources().getDrawable(R.drawable.spinner_black_boarder));
        }

        if (fromSpn.getSelectedItem().toString().equals("Irbid") && toSpn.getSelectedItem().toString().equals("Yarmouk-University")) {
            toSpn.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));
            return;
        } else {
            toSpn.setBackground(getResources().getDrawable(R.drawable.spinner_black_boarder));
        }

        if (fromSpn.getSelectedItem().toString().equals("Yarmouk-University") && toSpn.getSelectedItem().toString().equals("Irbid")) {
            toSpn.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));
            return;
        } else {
            toSpn.setBackground(getResources().getDrawable(R.drawable.spinner_black_boarder));
        }


        if (date.getText().toString().equals("Select One")) {
            date.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));

            return;
        } else {
            date.setBackground(getResources().getDrawable(R.drawable.spinner_black_boarder));

        }
        if (timeSpn.getSelectedItem().toString().equals("Select One")) {
            timeSpn.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));
            return;
        } else {
            timeSpn.setBackground(getResources().getDrawable(R.drawable.spinner_black_boarder));
        }

        if (paymentSpn.getSelectedItem().toString().equals("Select One")) {
            paymentSpn.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));
            return;
        } else {
            paymentSpn.setBackground(getResources().getDrawable(R.drawable.spinner_black_boarder));
        }

        Calendar c = new GregorianCalendar();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String currentDate = new SimpleDateFormat("M-dd-yyyy", Locale.getDefault()).format(new Date());
        currentDate+=" ";

        if (currentDate.equals(date.getText().toString())) {
            String itemSelected = timeSpn.getSelectedItem().toString();
            String Hour = itemSelected.substring(0, itemSelected.indexOf(":"));
            String Min = itemSelected.substring(itemSelected.indexOf(":") + 1);
            Log.d("Hour", Hour);
            Log.d("Min", Min);

            if (Min.substring(0, 1).equals("0")) {
                Min = Min.substring(1);
            }
            if (hour == Integer.parseInt(Hour)) {
                if ((minute >= Integer.parseInt(Min))) {
                    timeSpn.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));
                    return;
                }
            } else if (hour > Integer.parseInt(Hour)) {
                timeSpn.setBackground(getResources().getDrawable(R.drawable.spinner_red_boarder));
                return;
            } else {
                timeSpn.setBackground(getResources().getDrawable(R.drawable.spinner_black_boarder));
            }
        }

            String fromAndTo = "";
            if (!fromSpn.getSelectedItem().toString().equals("Yarmouk-University")) {
                fromAndTo = fromSpn.getSelectedItem().toString().toLowerCase();
                fromAndTo += "_to_";

                if (!toSpn.getSelectedItem().toString().equals("Yarmouk-University")) {
                    fromAndTo += toSpn.getSelectedItem().toString().toLowerCase();
                } else {
                    String To = toSpn.getSelectedItem().toString().toLowerCase();
                    fromAndTo += To.substring(0, To.indexOf("-"));
                }
            } else {
                String from = fromSpn.getSelectedItem().toString().toLowerCase();
                fromAndTo += from.substring(0, from.indexOf("-"));
                fromAndTo += "_to_";
                if (!toSpn.getSelectedItem().toString().equals("Yarmouk-University")) {
                    fromAndTo += toSpn.getSelectedItem().toString().toLowerCase();
                } else {
                    String To = toSpn.getSelectedItem().toString().toLowerCase();
                    fromAndTo += To.substring(0, To.indexOf("-"));
                }
            }

        progressBar.setVisibility(View.VISIBLE);
        myRef = database.getReference().child("Hijazi").child(fromAndTo);
        final String finalFromAndTo = fromAndTo;
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if (!dataSnapshot.hasChildren()) {
                    myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Information");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            userRegisterData = dataSnapshot.getValue(UserRegisterData.class);
                            if (paymentSpn.getSelectedItem().toString().equals("Hijazi Card")) {
                                if (userRegisterData.getHijaziCard().getCardCount().equals("")) {
                                    Toast.makeText(BookBus.this, "Please Enter Your Hijazi Card", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    int hijaziCardCount = Integer.parseInt(userRegisterData.getHijaziCard().getCardCount());
                                    if (hijaziCardCount < Integer.parseInt(passnegerCount.getText().toString())) {
                                        Toast.makeText(BookBus.this, "Hijazi Card Can Not Be enough ,Please Recharge Your Card", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        CardNumber cardNumber = new CardNumber();
                                        int passneger = Integer.parseInt(passnegerCount.getText().toString());

                                        cardNumber.setCardCount(String.valueOf(hijaziCardCount - passneger));
                                        cardNumber.setCardNumber(userRegisterData.getHijaziCard().getCardNumber());
                                        userRegisterData.setHijaziCard(cardNumber);
                                    }
                                }
                            }

                            HijaziBookingInformation hijaziBookingInformation = new HijaziBookingInformation();
                            UserBookingInformation userBookingInformation = new UserBookingInformation();
                            hijaziBookingInformation.setUserRegisterData(userRegisterData);


                            hijaziBookingInformation.setDate(date.getText().toString());
                            hijaziBookingInformation.setPayment(paymentSpn.getSelectedItem().toString());
                            hijaziBookingInformation.setFrom(fromSpn.getSelectedItem().toString());
                            hijaziBookingInformation.setTo(toSpn.getSelectedItem().toString());
                            hijaziBookingInformation.setNumberOfPassenger(passnegerCount.getText().toString());
                            hijaziBookingInformation.setTime(timeSpn.getSelectedItem().toString());

                            userBookingInformation.setDate(date.getText().toString());
                            userBookingInformation.setPayment(paymentSpn.getSelectedItem().toString());
                            userBookingInformation.setFrom(fromSpn.getSelectedItem().toString());
                            userBookingInformation.setTo(toSpn.getSelectedItem().toString());
                            userBookingInformation.setNumberOfPassenger(passnegerCount.getText().toString());
                            userBookingInformation.setTime(timeSpn.getSelectedItem().toString());

                            myRef = database.getReference().child("Hijazi").child(finalFromAndTo).child(date.getText().toString() + " " + timeSpn.getSelectedItem()).child(userRegisterData.getName() + " " + userRegisterData.getPhoneNumber());
                            myRef.setValue(hijaziBookingInformation);

                            myRef = database.getReference().child("Hijazi").child(finalFromAndTo).child(date.getText().toString() + " " + timeSpn.getSelectedItem()).child("BusPassenger");
                            myRef.setValue(passnegerCount.getText().toString());

                            myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Reservation").child(date.getText().toString() + " " + timeSpn.getSelectedItem());
                            myRef.setValue(userBookingInformation);

                            myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Information");
                            myRef.setValue(userRegisterData);
                            Toast.makeText(BookBus.this, "Your Booking Is Confirmed", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String value = dataSnapshot1.getKey();

                        String dateAndValue = date.getText().toString() + " " + timeSpn.getSelectedItem();
                        assert value != null;
                        if (value.equals(dateAndValue)) {
                            buscount = dataSnapshot1.child("BusPassenger").getValue(String.class);
                            assert buscount != null;
                            busCount2 = Integer.parseInt(buscount);
                            passnger = Integer.parseInt(passnegerCount.getText().toString());
                            if (busCount2 == 50) {
                                Toast.makeText(BookBus.this, "The Bus is Full", Toast.LENGTH_LONG).show();
                                return;
                            } else if (busCount2 + passnger > 50) {
                                Toast.makeText(BookBus.this, "Number Of Seats Available Is " + (50 - busCount2), Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Information");
                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        userRegisterData = dataSnapshot.getValue(UserRegisterData.class);
                                        if (paymentSpn.getSelectedItem().toString().equals("Hijazi Card")) {
                                            if (userRegisterData.getHijaziCard().getCardCount().equals("")) {
                                                Toast.makeText(BookBus.this, "Please Enter Your Hijazi Card", Toast.LENGTH_LONG).show();
                                                return;
                                            } else {
                                                int hijaziCardCount = Integer.parseInt(userRegisterData.getHijaziCard().getCardCount());
                                                if (hijaziCardCount < Integer.parseInt(passnegerCount.getText().toString())) {
                                                    Toast.makeText(BookBus.this, "Hijazi Card Can Not Be enough ,Please Recharge Your Card", Toast.LENGTH_LONG).show();
                                                    return;
                                                } else {
                                                    CardNumber cardNumber = new CardNumber();
                                                    int passneger = Integer.parseInt(passnegerCount.getText().toString());

                                                    cardNumber.setCardCount(String.valueOf(hijaziCardCount - passneger));
                                                    cardNumber.setCardNumber(userRegisterData.getHijaziCard().getCardNumber());
                                                    userRegisterData.setHijaziCard(cardNumber);
                                                }
                                            }
                                        }

                                        HijaziBookingInformation hijaziBookingInformation = new HijaziBookingInformation();
                                        UserBookingInformation userBookingInformation = new UserBookingInformation();
                                        hijaziBookingInformation.setUserRegisterData(userRegisterData);


                                        hijaziBookingInformation.setDate(date.getText().toString());
                                        hijaziBookingInformation.setPayment(paymentSpn.getSelectedItem().toString());
                                        hijaziBookingInformation.setFrom(fromSpn.getSelectedItem().toString());
                                        hijaziBookingInformation.setTo(toSpn.getSelectedItem().toString());
                                        hijaziBookingInformation.setNumberOfPassenger(passnegerCount.getText().toString());
                                        hijaziBookingInformation.setTime(timeSpn.getSelectedItem().toString());

                                        userBookingInformation.setDate(date.getText().toString());
                                        userBookingInformation.setPayment(paymentSpn.getSelectedItem().toString());
                                        userBookingInformation.setFrom(fromSpn.getSelectedItem().toString());
                                        userBookingInformation.setTo(toSpn.getSelectedItem().toString());
                                        userBookingInformation.setNumberOfPassenger(passnegerCount.getText().toString());
                                        userBookingInformation.setTime(timeSpn.getSelectedItem().toString());

                                        myRef = database.getReference().child("Hijazi").child(finalFromAndTo).child(date.getText().toString() + " " + timeSpn.getSelectedItem()).child(userRegisterData.getName() + " " + userRegisterData.getPhoneNumber());
                                        myRef.setValue(hijaziBookingInformation);

                                        myRef = database.getReference().child("Hijazi").child(finalFromAndTo).child(date.getText().toString() + " " + timeSpn.getSelectedItem()).child("BusPassenger");
                                        myRef.setValue(String.valueOf(busCount2 + passnger));

                                        myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Reservation").child(date.getText().toString() + " " + timeSpn.getSelectedItem());
                                        myRef.setValue(userBookingInformation);

                                        myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Information");
                                        myRef.setValue(userRegisterData);
                                        Toast.makeText(BookBus.this, "Your Booking Is Confirmed", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        } else {
                            myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Information");
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    userRegisterData = dataSnapshot.getValue(UserRegisterData.class);
                                    if (paymentSpn.getSelectedItem().toString().equals("Hijazi Card")) {
                                        if (userRegisterData.getHijaziCard().getCardCount().equals("")) {
                                            Toast.makeText(BookBus.this, "Please Enter Your Hijazi Card", Toast.LENGTH_LONG).show();
                                            return;
                                        } else {
                                            int hijaziCardCount = Integer.parseInt(userRegisterData.getHijaziCard().getCardCount());
                                            if (hijaziCardCount < Integer.parseInt(passnegerCount.getText().toString())) {
                                                Toast.makeText(BookBus.this, "Hijazi Card Can Not Be enough ,Please Recharge Your Card", Toast.LENGTH_LONG).show();
                                                return;
                                            } else {
                                                CardNumber cardNumber = new CardNumber();
                                                int passneger = Integer.parseInt(passnegerCount.getText().toString());

                                                cardNumber.setCardCount(String.valueOf(hijaziCardCount - passneger));
                                                cardNumber.setCardNumber(userRegisterData.getHijaziCard().getCardNumber());
                                                userRegisterData.setHijaziCard(cardNumber);
                                            }
                                        }
                                    }

                                    HijaziBookingInformation hijaziBookingInformation = new HijaziBookingInformation();
                                    UserBookingInformation userBookingInformation = new UserBookingInformation();
                                    hijaziBookingInformation.setUserRegisterData(userRegisterData);


                                    hijaziBookingInformation.setDate(date.getText().toString());
                                    hijaziBookingInformation.setPayment(paymentSpn.getSelectedItem().toString());
                                    hijaziBookingInformation.setFrom(fromSpn.getSelectedItem().toString());
                                    hijaziBookingInformation.setTo(toSpn.getSelectedItem().toString());
                                    hijaziBookingInformation.setNumberOfPassenger(passnegerCount.getText().toString());
                                    hijaziBookingInformation.setTime(timeSpn.getSelectedItem().toString());

                                    userBookingInformation.setDate(date.getText().toString());
                                    userBookingInformation.setPayment(paymentSpn.getSelectedItem().toString());
                                    userBookingInformation.setFrom(fromSpn.getSelectedItem().toString());
                                    userBookingInformation.setTo(toSpn.getSelectedItem().toString());
                                    userBookingInformation.setNumberOfPassenger(passnegerCount.getText().toString());
                                    userBookingInformation.setTime(timeSpn.getSelectedItem().toString());

                                    myRef = database.getReference().child("Hijazi").child(finalFromAndTo).child(date.getText().toString() + " " + timeSpn.getSelectedItem()).child(userRegisterData.getName() + " " + userRegisterData.getPhoneNumber());
                                    myRef.setValue(hijaziBookingInformation);

                                    myRef = database.getReference().child("Hijazi").child(finalFromAndTo).child(date.getText().toString() + " " + timeSpn.getSelectedItem()).child("BusPassenger");
                                    myRef.setValue(passnegerCount.getText().toString());

                                    myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Reservation").child(date.getText().toString() + " " + timeSpn.getSelectedItem());
                                    myRef.setValue(userBookingInformation);

                                    myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Information");
                                    myRef.setValue(userRegisterData);
                                    Toast.makeText(BookBus.this, "Your Booking Is Confirmed", Toast.LENGTH_LONG).show();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });

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

        times.add("Select One");
        times.add("6:30");
        times.add("7:00");
        times.add("7:30");
        times.add("8:00");
        times.add("8:30");
        times.add("9:00");
        times.add("9:30");
        times.add("10:00");
        times.add("10:30");
        times.add("11:00");
        times.add("11:30");
        times.add("12:00");
        times.add("12:30");
        times.add("13:00");
        times.add("13:30");
        times.add("14:00");
        times.add("14:30");
        times.add("15:00");
        times.add("15:30");
        times.add("16:00");
        times.add("16:30");
        times.add("17:00");
        times.add("17:30");
        times.add("18:00");
        times.add("18:30");
        times.add("19:00");
        times.add("19:30");

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

            timeSpn.setAlpha(1f);
            timeSpn.setEnabled(true);

        }
    };

}
