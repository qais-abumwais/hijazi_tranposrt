package com.example.hijazitransport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hijazitransport.R;
import com.example.hijazitransport.util.UserLoginFlag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private TextView forgetPassword,register;
    private EditText email,password;
    private Button login;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private UserLoginFlag userLoginFlag;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prepareView();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });

        userLoginFlag=new UserLoginFlag(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    signIn();
                }
            }
        });


        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,ResetPassword.class);
                startActivity(intent);
            }
        });
    }

    private void prepareView(){
        forgetPassword=findViewById(R.id.login_forget_password);
        register=findViewById(R.id.login_register);
        login=findViewById(R.id.login);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        progressBar=findViewById(R.id.login_progress_bar);
    }


    private void signIn(){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLoginFlag.setYourFlag(true);
                            progressBar.setVisibility(View.GONE);
                            Intent intent=new Intent(Login.this,BookBus.class);
                            startActivity(intent);

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Email Or Password Is Wrong",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validate() {
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
        return true;
    }


}
