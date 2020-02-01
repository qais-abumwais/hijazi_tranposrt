package com.example.hijazitransport.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.model.LocationModel;
import com.example.hijazitransport.model.PermissionCallBack;

import java.util.List;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<LocationModel> locationModels;
    private PermissionCallBack permissionCallBack;

    public LocationRecyclerAdapter(Context context, List<LocationModel> locationModels
            , PermissionCallBack permissionCallBack) {
        this.context = context;
        this.locationModels = locationModels;
        this.permissionCallBack = permissionCallBack;
    }

    @NonNull
    @Override
    public LocationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.office_location_card_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationRecyclerAdapter.ViewHolder holder, final int position) {
        holder.label.setText(locationModels.get(position).getTitle());
        holder.description.setText(locationModels.get(position).getWorkong());
        holder.phoneNumberOne.setText(locationModels.get(position).getPhoneOne());


        if (locationModels.get(position).getPhoneTow().equals("")){
            holder.linearLayoutPhoneTwo.setVisibility(View.GONE);
        }else {
            holder.linearLayoutPhoneTwo.setVisibility(View.VISIBLE);
            holder.phoneNumberTwo.setText(locationModels.get(position).getPhoneTow());
        }

        holder.phoneNumberOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionCallBack.returnPhoneNumber(holder.phoneNumberOne.getText().toString());
            }
        });

        holder.phoneNumberTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    permissionCallBack.returnPhoneNumber(holder.phoneNumberOne.getText().toString());
            }
        });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationModels.get(position).getMap()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumberOne, phoneNumberTwo ,label, description;
        LinearLayout linearLayoutPhoneTwo;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneNumberOne = itemView.findViewById(R.id.location_phone_number_one_text_view);
            phoneNumberTwo = itemView.findViewById(R.id.location_phone_number_two_text_view);
            linearLayoutPhoneTwo = itemView.findViewById(R.id.linear_phone_two);
            label = itemView.findViewById(R.id.location_label_text_view);
            description = itemView.findViewById(R.id.working_description_text_view);
            imageView=itemView.findViewById(R.id.map_link);
        }
    }
}


