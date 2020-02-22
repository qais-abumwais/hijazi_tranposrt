package com.example.hijazitransport.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.model.CardNumber;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HijaziCard extends Base {
    private EditText cardNumber;
    private Button apply;
    private ProgressBar progressBar;
    private CardView cardView;
    private TextView cardCount;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijazi_card);

        prepareView();

        cardView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        //retrieve data from firebase
        myRef= database.getReference().child("Users").child(Objects.requireNonNull(mAuth.getUid())).child("Information").child("hijaziCard");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cardNumberStr=dataSnapshot.child("cardNumber").getValue(String.class);
                assert cardNumberStr != null;
                if (cardNumberStr.equals("")){
                    cardView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else{
                    cardNumber.setText(cardNumberStr);
                    String cardCountStr=dataSnapshot.child("cardCount").getValue(String.class);
                    cardCount.setText("Card Has "+cardCountStr+" Of Tickets");
                    cardView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //send hijazi card number to firebase
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subCardNumber=cardNumber.getText().toString().substring(0,3);
                if (!(cardNumber.getText().toString().isEmpty()) && cardNumber.getText().toString().length() > 9 && subCardNumber.equals("00")) {
                    confirmUpload();
                } else {
                    cardNumber.setError(getResources().getString(R.string.card_number_is_not_match));

                }
            }
        });


    }

    private void prepareView() {
        cardNumber = findViewById(R.id.card_number);
        apply = findViewById(R.id.apply_card_number);
        progressBar = findViewById(R.id.card_number_progress_bar);
        cardView = findViewById(R.id.card_view_number);
        cardCount = findViewById(R.id.card_count);
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.hijazi_card);
        setSupportActionBar(toolbar);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.hijazi_card;
    }

    private void confirmUpload() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(HijaziCard.this);
        builder1.setMessage(HijaziCard.this.getResources().getString(R.string.are_you_sure));
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                HijaziCard.this.getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CardNumber cardNumberModel = new CardNumber(cardNumber.getText().toString(), "30");
                        progressBar.setVisibility(View.VISIBLE);
                        myRef.setValue(cardNumberModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(HijaziCard.this, "Upload Hijazi Card Number Is Successful", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(HijaziCard.this, "Upload Hijazi Card Number Is Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

        builder1.setNegativeButton(
                HijaziCard.this.getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
