package com.example.hijazitransport.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hijazitransport.R;
import com.example.hijazitransport.activity.BookBus;
import com.example.hijazitransport.activity.Login;
import com.example.hijazitransport.model.CardNumber;
import com.example.hijazitransport.model.UserBookingInformation;
import com.example.hijazitransport.model.UserRegisterData;
import com.example.hijazitransport.util.UserLoginFlag;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MyReservationRecyclerAdapter extends RecyclerView.Adapter<MyReservationRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<UserBookingInformation> userBookingInformations;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef;
    private String from,to,dateLast,timeLast;
    private UserRegisterData userRegisterData=new UserRegisterData();
    private int bus,passengerCount;

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
    public void onBindViewHolder(@NonNull MyReservationRecyclerAdapter.ViewHolder holder, final int position) {
        holder.from.setText(userBookingInformations.get(position).getFrom());
        holder.to.setText(userBookingInformations.get(position).getTo());
        holder.date.setText(userBookingInformations.get(position).getDate());
        holder.time.setText(userBookingInformations.get(position).getTime());
        holder.passenger.setText(userBookingInformations.get(position).getNumberOfPassenger());
        holder.payment.setText(userBookingInformations.get(position).getPayment());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confrimRemove(position,userBookingInformations.get(position).getNumberOfPassenger());
            }
        });

    }

    @Override
    public int getItemCount() {
        return userBookingInformations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView from, to, date, time, passenger, payment;
        LinearLayout remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            passenger = itemView.findViewById(R.id.passenger);
            payment = itemView.findViewById(R.id.payment);
            remove = itemView.findViewById(R.id.remove);
        }
    }

    private void confrimRemove(final int position,final String passnegrCount2) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(context.getResources().getString(R.string.are_you_sure));
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                context.getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String currentMonth = new SimpleDateFormat("M", Locale.getDefault()).format(new Date());
                        String currentDay = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                        String currentYear = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());

                        String lastDate = userBookingInformations.get(position).getDate();
                        String[] strings = lastDate.split("-");

                        strings[strings.length - 1] = strings[strings.length - 1].trim();

                        if (Integer.parseInt(currentYear) > Integer.parseInt(strings[2])) {
                            Toast.makeText(context, "Can't Be Remove", Toast.LENGTH_LONG).show();
                        } else if (Integer.parseInt(currentMonth) > Integer.parseInt(strings[0]) && Integer.parseInt(currentYear) <= Integer.parseInt(strings[2])) {
                            Toast.makeText(context, "Can't Be Remove", Toast.LENGTH_LONG).show();
                        } else if (Integer.parseInt(currentDay) > Integer.parseInt(strings[1]) && Integer.parseInt(currentYear) <= Integer.parseInt(strings[2]) && Integer.parseInt(currentMonth) <= Integer.parseInt(strings[1])) {
                            Toast.makeText(context, "Can't Be Remove", Toast.LENGTH_LONG).show();
                        } else {
                            Calendar c = new GregorianCalendar();
                            int hour = c.get(Calendar.HOUR_OF_DAY);
                            int minute = c.get(Calendar.MINUTE);
                            String itemSelected = userBookingInformations.get(position).getTime();
                            String Hour = itemSelected.substring(0, itemSelected.indexOf(":"));
                            String Min = itemSelected.substring(itemSelected.indexOf(":") + 1);
                            Log.d("Hour", Hour);
                            Log.d("Min", Min);

                            if (Min.substring(0, 1).equals("0")) {
                                Min = Min.substring(1);
                            }
                            if (hour == Integer.parseInt(Hour) && Integer.parseInt(currentDay) ==Integer.parseInt(strings[1]) ) {
                                if ((minute >= Integer.parseInt(Min))) {
                                    Toast.makeText(context, "Can't Be Remove", Toast.LENGTH_LONG).show();
                                }
                            } else if (hour > Integer.parseInt(Hour) && Integer.parseInt(currentDay) ==Integer.parseInt(strings[1])) {
                                Toast.makeText(context, "Can't Be Remove", Toast.LENGTH_LONG).show();
                            } else {
                                dateLast=userBookingInformations.get(position).getDate();
                                timeLast= userBookingInformations.get(position).getTime();
                                myRef = database.getReference().child("Users").child(mAuth.getUid()).child("Reservation").child(dateLast+ " " +timeLast);
                                myRef.removeValue();

                                if (userBookingInformations.get(position).getFrom().equals("Yarmouk-University")){
                                    from=userBookingInformations.get(position).getFrom().substring(0, userBookingInformations.get(position).getFrom().indexOf("-"));
                                    from=from.toLowerCase();
                                    from+="_to_";
                                }else{
                                    from=userBookingInformations.get(position).getFrom();
                                    from=from.toLowerCase();
                                    from+="_to_";
                                }

                                if (userBookingInformations.get(position).getTo().equals("Yarmouk-University")){
                                    to=userBookingInformations.get(position).getTo().substring(0, userBookingInformations.get(position).getTo().indexOf("-"));
                                    to=to.toLowerCase();

                                }else{
                                    to=userBookingInformations.get(position).getTo();
                                    to=to.toLowerCase();
                                }

                                myRef = database.getReference().child("Hijazi").child(from+to);

                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final String dateAndTime=dateLast+" "+timeLast;
                                        for (final DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                            if (dateAndTime.equals(dataSnapshot1.getKey())){
                                                for (final DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()){
                                                    myRef=database.getReference().child("Users").child(Objects.requireNonNull(mAuth.getUid())).child("Information");
                                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            userRegisterData=dataSnapshot.getValue(UserRegisterData.class);
                                                            if (dataSnapshot2.getKey().equals(userRegisterData.getName()+" "+userRegisterData.getPhoneNumber())){
                                                                myRef=database.getReference().child("Hijazi").child(from+to).child(dateAndTime).child("BusPassenger");
                                                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        bus=Integer.parseInt(dataSnapshot.getValue(String.class));
                                                                        passengerCount=Integer.parseInt(passnegrCount2);
                                                                        bus-=passengerCount;
                                                                        myRef=database.getReference().child("Hijazi").child(from+to).child(dateAndTime).child(userRegisterData.getName()+" "+userRegisterData.getPhoneNumber()).child("payment");
                                                                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                if (dataSnapshot.getValue().toString().equals("Cash")) {
                                                                                    myRef = database.getReference().child("Hijazi").child(from + to).child(dataSnapshot1.getKey()).child(dataSnapshot2.getKey());
                                                                                    myRef.removeValue();
                                                                                    myRef = database.getReference().child("Hijazi").child(from + to).child(dataSnapshot1.getKey()).child("BusPassenger");
                                                                                    myRef.setValue(String.valueOf(bus));
                                                                                }else{
                                                                                    myRef = database.getReference().child("Hijazi").child(from + to).child(dataSnapshot1.getKey()).child(dataSnapshot2.getKey());
                                                                                    myRef.removeValue();
                                                                                    myRef = database.getReference().child("Hijazi").child(from + to).child(dataSnapshot1.getKey()).child("BusPassenger");
                                                                                    myRef.setValue(String.valueOf(bus));

                                                                                    int cardCount=Integer.parseInt(userRegisterData.getHijaziCard().getCardCount());
                                                                                    cardCount+=passengerCount;

                                                                                    CardNumber cardNumber=new CardNumber();
                                                                                    cardNumber.setCardCount(String.valueOf(cardCount));
                                                                                    cardNumber.setCardNumber(userRegisterData.getHijaziCard().getCardNumber());

                                                                                    userRegisterData.setHijaziCard(cardNumber);
                                                                                    myRef=database.getReference().child("Users").child(mAuth.getUid()).child("Information");
                                                                                    myRef.setValue(userRegisterData);
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                            }
                                                                        });

                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });

                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        }
                                                    });

                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                userBookingInformations.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Reservation Is Removed", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });

        builder1.setNegativeButton(
                context.getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
