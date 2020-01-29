package com.example.hijazitransport.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.hijazitransport.R;
import com.example.hijazitransport.model.UserRegisterData;
import com.example.hijazitransport.util.UserLoginFlag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Registration extends Base {

    private EditText fullName, phone, address, birthdate, gender, email, password, confirmPassword;
    private Button register;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private final Calendar myCalendar = Calendar.getInstance();
    private int checkedItem = 0;
    private UserRegisterData userRegisterData = new UserRegisterData();
    private ProgressBar progressBar;
    private UserLoginFlag userLoginFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        prepareView();
        birthdate.setFocusable(false);
        birthdate.setClickable(true);
        birthdate.setKeyListener(null);
        birthdate.onEditorAction(EditorInfo.IME_ACTION_DONE);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCalender();
                new DatePickerDialog(Registration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    userRegisterData.setEmail(email.getText().toString());
                    userRegisterData.setName(fullName.getText().toString());
                    userRegisterData.setPhoneNumber(phone.getText().toString());
                    userRegisterData.setAddress(address.getText().toString());
                    userRegisterData.setGender(gender.getText().toString());
                    userRegisterData.setBirthdate(birthdate.getText().toString());
                    userRegisterData.setHijaziCardd("");
                    createAccount();
                }
            }
        });
    }


    private void prepareView() {
        fullName = findViewById(R.id.register_full_name);
        phone = findViewById(R.id.register_phone_number);
        birthdate = findViewById(R.id.register_birthdate);
        gender = findViewById(R.id.register_Gender);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        address = findViewById(R.id.register_address);
        confirmPassword = findViewById(R.id.register_confirm_password);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.register_progress_bar);
    }

    private void createAccount() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        userLoginFlag=new UserLoginFlag(this);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            myRef.child("Users").child(user.getUid()).setValue(userRegisterData);
                            Toast.makeText(Registration.this, "Registration is successful",
                                    Toast.LENGTH_SHORT).show();
                            userLoginFlag.setYourFlag(true);
                            progressBar.setVisibility(View.GONE);

                            Intent intent=new Intent(Registration.this,BookBus.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Registration.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Registration.this);
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


    private boolean validate() {

        if (fullName.getText().toString().isEmpty()) {
            fullName.setError(getResources().getString(R.string.name_is_required));
            return false;
        }
        if (phone.getText().toString().isEmpty()) {
            phone.setError(getResources().getString(R.string.phone_is_required));
            return false;
        }
        if (phone.getText().toString().length() < 10) {
            phone.setError(getResources().getString(R.string.phone_is_not_match));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(getResources().getString(R.string.email_is_not_match));
            return false;
        }
        if (email.getText().toString().isEmpty()) {
            email.setError(getResources().getString(R.string.email_is_required));
            return false;
        }

        if (password.getText().toString().length() < 6) {
            password.setError(getResources().getString(R.string.password_must_be_at_least_6_characters));
            return false;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError(getResources().getString(R.string.password_is_required));
            return false;
        }

        if (confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError(getResources().getString(R.string.password_is_required));
            return false;
        }

        if (confirmPassword.getText().toString().length() < 6) {
            confirmPassword.setError(getResources().getString(R.string.password_must_be_at_least_6_characters));
            return false;
        }

        if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
            confirmPassword.setError(getResources().getString(R.string.password_is_wrong));
            return false;
        }

        return true;
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.registration);
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
}
