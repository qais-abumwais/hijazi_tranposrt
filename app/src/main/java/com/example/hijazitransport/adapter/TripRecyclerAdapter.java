package com.example.hijazitransport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.model.TripSchedule;

import java.util.List;

public class TripRecyclerAdapter extends RecyclerView.Adapter<TripRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<TripSchedule> tripScheduleModels;

    public TripRecyclerAdapter(Context context, List<TripSchedule> tripScheduleModels) {
        this.context = context;
        this.tripScheduleModels = tripScheduleModels;
    }

    @NonNull
    @Override
    public TripRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_schedule_card_view_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripRecyclerAdapter.ViewHolder holder, final int position) {
        holder.title.setText(tripScheduleModels.get(position).getTitle());
        holder.times.setText(tripScheduleModels.get(position).getTimes());
    }

    @Override
    public int getItemCount() {
        return tripScheduleModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,times;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.trip_title);
            times=itemView.findViewById(R.id.trip_time);
        }
    }
}
