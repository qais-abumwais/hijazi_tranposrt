package com.example.hijazitransport.activity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.hijazitransport.R;
import com.example.hijazitransport.model.UserRegisterData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class EditProfile extends Base {

    private EditText name, address, phone, birthdate, gender;
    private DatePickerDialog.OnDateSetListener date;
    private final Calendar myCalendar = Calendar.getInstance();
    private int checkedItem = 0;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef ;
    private UserRegisterData userRegisterData = new UserRegisterData();
    private Button update;
    private ScrollView scrollView;
    private ProgressBar progressBar;
    private TextView resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        prepareView();

        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        //retrieve data from firebase
        myRef=database.getReference().child("Users").child(Objects.requireNonNull(mAuth.getUid()));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scrollView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                userRegisterData = dataSnapshot.getValue(UserRegisterData.class);
                assert userRegisterData != null;

                name.setText(userRegisterData.getName());
                phone.setText(userRegisterData.getPhoneNumber());

                if (!(userRegisterData.getAddress().equals(""))) {
                    address.setText(userRegisterData.getAddress());
                }

                if (!userRegisterData.getGender().equals("")) {
                    gender.setText(userRegisterData.getGender());
                }

                if (!userRegisterData.getBirthdate().equals("")) {
                    birthdate.setText(userRegisterData.getBirthdate());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        //delete keyboard when pressed on birthdate edt and show calender
        birthdate.setFocusable(false);
        birthdate.setClickable(true);
        birthdate.setKeyListener(null);
        birthdate.onEditorAction(EditorInfo.IME_ACTION_DONE);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCalender();
                new DatePickerDialog(EditProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //delete keyboard when pressed on gender edt and show alert dialog with single choice items male,female
        gender.setFocusable(false);
        gender.setClickable(true);
        gender.setKeyListener(null);
        gender.onEditorAction(EditorInfo.IME_ACTION_DONE);
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        //send data to firebase after user update his information
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmUpdate();
            }
        });

        //reset password using reset activity
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, ResetPassword.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.edit_profile);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected int getNavigationMenuItemId() {
        return 0;
    }


    private void prepareView() {
        resetPassword = findViewById(R.id.edit_reset_password);
        name = findViewById(R.id.update_full_name);
        address = findViewById(R.id.update_address);
        phone = findViewById(R.id.update_phone_number);
        birthdate = findViewById(R.id.update_birthdate);
        gender = findViewById(R.id.update_Gender);
        update = findViewById(R.id.update);
        progressBar = findViewById(R.id.update_progress_bar);
        scrollView = findViewById(R.id.scroll_edit);
    }

    private void createCalender() {
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                birthdate.setText(sdf.format(myCalendar.getTime()));
            }
        };
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfile.this);
        alertDialog.setTitle("Select Gender");
        final String[] items = {"Male", "Female"};

        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gender.setText(items[which]);
                checkedItem = which;
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }


    private void confirmUpdate() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfile.this);
        builder1.setMessage(EditProfile.this.getResources().getString(R.string.are_you_sure));
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                EditProfile.this.getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        userRegisterData.setName(name.getText().toString());
                        userRegisterData.setAddress(address.getText().toString());
                        userRegisterData.setBirthdate(birthdate.getText().toString());
                        userRegisterData.setGender(gender.getText().toString());
                        userRegisterData.setPhoneNumber(phone.getText().toString());
                        progressBar.setVisibility(View.VISIBLE);
                        myRef.setValue(userRegisterData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(EditProfile.this, "Update Profile Is Successful", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

        builder1.setNegativeButton(
                EditProfile.this.getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
