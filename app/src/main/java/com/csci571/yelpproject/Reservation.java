package com.csci571.yelpproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Reservation extends AppCompatActivity {
    public ArrayList<Res> reservationList;
    public RecyclerView reserveAdapter;
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        reserveAdapter = findViewById(R.id.reserverecycler);
        backImage = findViewById(R.id.backimage);
        reservationList = new ArrayList<>();

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Reservation.this, MainActivity.class);
//                startActivity(intent);
                Reservation.super.onBackPressed();
            }
        });

        setReserveInfo();
        setAdapter();
    }

    public void setAdapter() {
        ReserveAdapter adapter = new ReserveAdapter(reservationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        reserveAdapter.setLayoutManager(layoutManager);
        reserveAdapter.setItemAnimator(new DefaultItemAnimator());
        reserveAdapter.setAdapter(adapter);
    }


    public void setReserveInfo(){
        reservationList.add(new Res("1", "ebaes", "07/12/2022/22", "15", "rushi@gmail.com"));
    }
}