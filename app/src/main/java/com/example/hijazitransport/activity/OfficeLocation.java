package com.example.hijazitransport.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hijazitransport.R;
import com.example.hijazitransport.adapter.LocationRecyclerAdapter;
import com.example.hijazitransport.adapter.TripRecyclerAdapter;
import com.example.hijazitransport.model.LocationModel;
import com.example.hijazitransport.model.PermissionCallBack;
import com.example.hijazitransport.model.TripScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class OfficeLocation extends Base implements PermissionCallBack {
    private RecyclerView recyclerView;
    private List<LocationModel> locationModelList=new ArrayList<>();
    private LocationRecyclerAdapter locationRecyclerAdapter;
    private String phone;

    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_location);

        recyclerView=findViewById(R.id.location_recycler_view);

        locationModelList.add(new LocationModel("https://www.google.com/maps/place/Hijazi+Transportation+Co./@32.5337796,35.8692332,382m/data=!3m1!1e3!4m12!1m6!3m5!1s0x151c7693a7e0896f:0x828051716b3c6a8!2sProfessional+Associations+Complex!8m2!3d32.5419513!4d35.8572927!3m4!1s0x151c76f510f31db1:0xef4c102607ba4d78!8m2!3d32.5336969!4d35.8696619"
                ,"Irbid","0795610755","027101760","Every Day From 6:00 Am To 7:00 Pm"));
        locationModelList.add(new LocationModel("https://www.google.com/maps/place/Y.Uni.+Eastern+Gate/@32.5394604,35.8564659,642m/data=!3m1!1e3!4m12!1m6!3m5!1s0x151c7693a7e0896f:0x828051716b3c6a8!2sProfessional+Associations+Complex!8m2!3d32.5419513!4d35.8572927!3m4!1s0x151c76932cd68559:0xe9d00fb2ceb48af5!8m2!3d32.5393731!4d35.8574874"
                ,"Yarmook University","0796050609","","Every Day From 6:00 Am To 7:00 Pm"));
        locationModelList.add(new LocationModel("https://www.google.com/maps/place/Hijazi+Transportation+Buses+Co/@31.9946151,35.9195458,198m/data=!3m1!1e3!4m12!1m6!3m5!1s0x151b600341e83361:0x41ca7d195668f8df!2sNorth+Park!8m2!3d31.9952478!4d35.92006!3m4!1s0x151b600314bf737f:0xf89e613ae76f32ac!8m2!3d31.9941411!4d35.9202846"
                ,"Amman","0799990404","065054661","Every Day From 6:00 Am To 7:00 Pm"));

        createRecyclerViewOfLocations(locationModelList);
    }

    @Override
    protected void initScreen(Toolbar toolbar) {
        toolbar.setTitle(R.string.office_location);
        setSupportActionBar(toolbar);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.office_location;
    }

    private void createRecyclerViewOfLocations(List<LocationModel> locationModels){
        locationRecyclerAdapter = new LocationRecyclerAdapter(this, locationModels,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OfficeLocation.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(locationRecyclerAdapter);
    }

    @Override
    public void returnPhoneNumber(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(OfficeLocation.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OfficeLocation.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        } else if (ContextCompat.checkSelfPermission(OfficeLocation.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {

            Toast.makeText(this, R.string.permissin_denied, Toast.LENGTH_SHORT).show();

        } else {
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                returnPhoneNumber(phone);
            } else {
                Toast.makeText(this, R.string.permissin_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
