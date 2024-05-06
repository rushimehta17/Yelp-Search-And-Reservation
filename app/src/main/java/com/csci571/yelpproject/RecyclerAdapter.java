package com.csci571.yelpproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    public ArrayList<ResultView> resultList;
    public String business_id;
    public Context mainContext;

    public RecyclerAdapter(ArrayList<ResultView> resultList, Context mainContext) {
        this.resultList = resultList;
        this.mainContext = mainContext;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView sno;
        public TextView business_name;
        public TextView ratings;
        public TextView distance;
        public ImageView business_image;

        public MyViewHolder(final View view){
            super(view);
            sno = view.findViewById(R.id.srNo);
            business_image = view.findViewById(R.id.image);
            business_name = view.findViewById(R.id.businessName);
            ratings = view.findViewById(R.id.rating);
            distance = view.findViewById(R.id.resultDistance);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_contents, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainContext, BusinessDetails.class);
                intent.putExtra("id",business_id);
                mainContext.startActivity(intent);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String sno = resultList.get(position).getSno();
        holder.sno.setText(sno);
        String business_name = resultList.get(position).getBusiness_name();
        holder.business_name.setText(business_name);
        String business_dist = resultList.get(position).getDistance();
        holder.distance.setText(business_dist);
        String business_rating = resultList.get(position).getRatings();
        holder.ratings.setText(business_rating);
        business_id = resultList.get(position).getId();
        String b_image = resultList.get(position).getImage();
        Picasso.get().load(b_image).into(holder.business_image);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}
