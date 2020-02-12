package com.example.hijazitransport.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.adapter.TripRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TripSchedule extends Base {

    private RecyclerView recyclerView;
    private List<com.example.hijazitransport.model.TripSchedule> tripScheduleModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_schedule);

        //binding view
        recyclerView=findViewById(R.id.recycler_trip);

        //create recycler with trip Schedule
        createRecyclerViewOfTrips();
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.trip_schedule);
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

    private void createRecyclerViewOfTrips(){
        tripScheduleModels.add(new com.example.hijazitransport.model.TripSchedule("Irbid To Amman","6:30,7:00,7:30,8:00,8:30,9:00,9:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,17:30,17:00,17:30,18:00,18:30,19:00"));
        tripScheduleModels.add(new com.example.hijazitransport.model.TripSchedule("Amman To Irbid","6:30,7:00,7:30,8:00,8:30,9:00,9:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,17:30,17:00,17:30,18:00,18:30,19:00"));
        tripScheduleModels.add(new com.example.hijazitransport.model.TripSchedule("Yarmouk-University To Amman","6:30,7:00,7:30,8:00,8:30,9:00,9:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,17:30,17:00,17:30,18:00,18:30,19:00"));
        tripScheduleModels.add(new com.example.hijazitransport.model.TripSchedule("Amman To Yarmouk-University","6:30,7:00,7:30,8:00,8:30,9:00,9:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,17:30,17:00,17:30,18:00,18:30,19:00"));

        TripRecyclerAdapter tripRecyclerAdapter = new TripRecyclerAdapter(this, tripScheduleModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TripSchedule.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(tripRecyclerAdapter);
    }
}
