package com.example.hijazitransport.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.hijazitransport.R;
import com.example.hijazitransport.model.UserRegisterData;
import com.example.hijazitransport.util.UserLoginFlag;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private UserRegisterData userRegisterData=new UserRegisterData("qays","qays","qays","qays","qays","qays","qays");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserLoginFlag userLoginFlag=new UserLoginFlag(SplashScreen.this);
                if (userLoginFlag.getYourFlag()){
                    Intent splashIntent=new Intent(SplashScreen.this, BookBus.class);
                    SplashScreen.this.startActivity(splashIntent);
                    SplashScreen.this.finish();
                }else {
                    Intent splashIntent=new Intent(SplashScreen.this, Login.class);
                    SplashScreen.this.startActivity(splashIntent);
                    SplashScreen.this.finish();
                }

            }
        }, 2000);
    }
}
