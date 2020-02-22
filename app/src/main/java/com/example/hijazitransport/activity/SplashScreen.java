package com.example.hijazitransport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hijazitransport.R;
import com.example.hijazitransport.model.Places;
import com.example.hijazitransport.util.UserLoginFlag;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Places places=new Places("Irbid To Amman","Amman To Irbid",
                "Yarmouk To Amman","Amman To Yarmouk");
        myRef=database.getReference();
        myRef.child("Hijazi").setValue(places);


        //pause screen 2000 millisecond as an splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserLoginFlag userLoginFlag=new UserLoginFlag(SplashScreen.this);
                //check if user is login or not using UserLoginFlag class
                if (userLoginFlag.getFlag()){
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
