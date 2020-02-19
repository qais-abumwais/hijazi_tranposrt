package com.example.hijazitransport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.model.UserBookingInformation;

import java.util.List;

public class MyReservationRecyclerAdapter extends RecyclerView.Adapter<MyReservationRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<UserBookingInformation> userBookingInformations;

    public MyReservationRecyclerAdapter(Context context, List<UserBookingInformation> userBookingInformations) {
        this.context = context;
        this.userBookingInformations = userBookingInformations;
    }

    @NonNull
    @Override
    public MyReservationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_layout_my_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReservationRecyclerAdapter.ViewHolder holder, int position) {
        holder.from.setText(userBookingInformations.get(position).getFrom());
        holder.to.setText(userBookingInformations.get(position).getTo());
        holder.date.setText(userBookingInformations.get(position).getDate());
        holder.time.setText(userBookingInformations.get(position).getTime());
        holder.passenger.setText(userBookingInformations.get(position).getNumberOfPassenger());
        holder.payment.setText(userBookingInformations.get(position).getPayment());
    }

    @Override
    public int getItemCount() {
        return userBookingInformations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView from,to,date,time,passenger,payment;
        LinearLayout remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            from=itemView.findViewById(R.id.from);
            to=itemView.findViewById(R.id.to);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            passenger=itemView.findViewById(R.id.passenger);
            payment=itemView.findViewById(R.id.payment);
            remove=itemView.findViewById(R.id.remove);
        }
    }
}
