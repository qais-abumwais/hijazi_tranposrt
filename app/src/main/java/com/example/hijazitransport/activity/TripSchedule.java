package com.example.hijazitransport.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.hijazitransport.R;
import com.example.hijazitransport.adapter.TripRecyclerAdapter;
import com.example.hijazitransport.model.TripScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class TripSchedule extends Base {

    private RecyclerView recyclerView;
    private TripRecyclerAdapter tripRecyclerAdapter;
    private List<TripScheduleModel> tripScheduleModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_schedule);

        recyclerView=findViewById(R.id.recycler_trip);
        tripScheduleModels.add(new TripScheduleModel("Irbid To Amman","6:30,7:00,7:30,8:00,8:30,9:00,9:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,17:30,17:00,17:30,18:00,18:30,19:00"));
        tripScheduleModels.add(new TripScheduleModel("Amman To Irbid","6:30,7:00,7:30,8:00,8:30,9:00,9:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,17:30,17:00,17:30,18:00,18:30,19:00"));
        tripScheduleModels.add(new TripScheduleModel("Yarmouk-University To Amman","6:30,7:00,7:30,8:00,8:30,9:00,9:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,17:30,17:00,17:30,18:00,18:30,19:00"));
        tripScheduleModels.add(new TripScheduleModel("Amman To Yarmouk-University","6:30,7:00,7:30,8:00,8:30,9:00,9:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,17:30,17:00,17:30,18:00,18:30,19:00"));

        createRecyclerViewOfTrips(tripScheduleModels);
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

    private void createRecyclerViewOfTrips(List<TripScheduleModel> tripScheduleModels){
        tripRecyclerAdapter = new TripRecyclerAdapter(this, tripScheduleModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TripSchedule.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(tripRecyclerAdapter);
    }
}
