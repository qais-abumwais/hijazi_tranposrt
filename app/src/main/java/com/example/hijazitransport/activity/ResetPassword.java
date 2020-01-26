package com.example.hijazitransport.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hijazitransport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText email;
    private Button reset;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        prepareView();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    resetPassword();
                }
            }
        });
    }

    private void  prepareView(){
        email=findViewById(R.id.reset_email);
        reset=findViewById(R.id.reset);
        progressBar=findViewById(R.id.reset_progress_bar);
    }

    private void resetPassword(){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ResetPassword.this,"Please Check Your Email",Toast.LENGTH_LONG).show();
                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ResetPassword.this,"Email Is Wrong",Toast.LENGTH_LONG).show();
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

        return true;
    }
}
