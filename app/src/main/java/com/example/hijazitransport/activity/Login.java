package com.example.hijazitransport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hijazitransport.R;

public class Login extends AppCompatActivity {

    private TextView forgetPassword,register;
    private EditText email,password;
    private Button login;
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
    }

    private void prepareView(){
        forgetPassword=findViewById(R.id.login_forget_password);
        register=findViewById(R.id.login_register);
        login=findViewById(R.id.login);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
    }


}
