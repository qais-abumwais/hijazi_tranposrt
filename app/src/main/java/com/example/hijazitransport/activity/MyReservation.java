package com.example.hijazitransport.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.adapter.LocationRecyclerAdapter;
import com.example.hijazitransport.adapter.MyReservationRecyclerAdapter;
import com.example.hijazitransport.model.Location;
import com.example.hijazitransport.model.UserBookingInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyReservation extends Base {
    private RecyclerView recyclerView;
    private MyReservationRecyclerAdapter myReservationRecyclerAdapter;
    private ProgressBar progressBar;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef;
    private List<UserBookingInformation> userBookingInformations=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);

        prepareView();
        recyclerView=findViewById(R.id.my_reservation_recycler);

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        myRef=database.getReference().child("Users").child(mAuth.getUid()).child("Reservation");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    userBookingInformations.add(dataSnapshot1.getValue(UserBookingInformation.class));
                }

                createRecyclerViewOfReservation(userBookingInformations);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createRecyclerViewOfReservation(List<UserBookingInformation> userBookingInformations){
        myReservationRecyclerAdapter = new MyReservationRecyclerAdapter(this, userBookingInformations);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyReservation.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myReservationRecyclerAdapter);
    }

    private void prepareView(){
        recyclerView=findViewById(R.id.my_reservation_recycler);
        progressBar=findViewById(R.id.my_reservation_progress_bar);

    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.my_reservation);
        setSupportActionBar(toolbar);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.my_reservation;
    }
}
