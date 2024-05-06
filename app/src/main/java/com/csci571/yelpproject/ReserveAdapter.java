package com.csci571.yelpproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.MyViewHolder> {
    public ArrayList<Res> reservationList;

    public ReserveAdapter(ArrayList<Res> reservationList) {
        this.reservationList = reservationList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView number, reservename, date, time, email;

        public MyViewHolder(final View view){
            super(view);
//            number = view.findViewById(R.id.number);
//            reservename = view.findViewById(R.id.reservename);
//            date = view.findViewById(R.id.date);
//            time = view.findViewById(R.id.time);
            email = view.findViewById(R.id.user_email);
        }
    }

    @NonNull
    @Override
    public ReserveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reserveView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_view_contents, parent, false);
        return new MyViewHolder(reserveView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReserveAdapter.MyViewHolder holder, int position) {
//        String number = reservationList.get(position).getNumber();
//        holder.number.setText(number);
//        String reservename = reservationList.get(position).getReservename();
//        holder.reservename.setText(reservename);
//        String date = reservationList.get(position).getDate();
//        holder.date.setText(date);
//        String time = reservationList.get(position).getTime();
//        holder.time.setText(time);
//        String email = reservationList.get(position).getEmail();
//        holder.email.setText(email);


    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }
}
