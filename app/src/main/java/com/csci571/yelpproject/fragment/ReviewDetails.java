package com.csci571.yelpproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.csci571.yelpproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReviewDetails extends Fragment {
    public String buss_id;

    public ReviewDetails(String bussiness_id) {buss_id = bussiness_id;}

    public RequestQueue Rqueue;
    TextView name1, name2, name3, rating1, rating2, rating3, review1, review2, review3, date1, date2, date3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_details, container, false);
        Rqueue = Volley.newRequestQueue(getContext());

        name1 = view.findViewById(R.id.name1);
        name2 = view.findViewById(R.id.name2);
        name3 = view.findViewById(R.id.name3);
        rating1 = view.findViewById(R.id.rating1);
        rating2 = view.findViewById(R.id.rating2);
        rating3 = view.findViewById(R.id.rating3);
        review1 = view.findViewById(R.id.review1);
        review2 = view.findViewById(R.id.review2);
        review3 = view.findViewById(R.id.review3);
        date1 = view.findViewById(R.id.date1);
        date2 = view.findViewById(R.id.date2);
        date3 = view.findViewById(R.id.date3);


        String url = "https://assignment8csci.wl.r.appspot.com/search/reviews?id=" + buss_id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("inside revire details");
                            JSONArray reviews = response.getJSONArray("reviews");

                            JSONObject reviews1 = reviews.getJSONObject(0);
                            review1.setText(reviews1.getString("text"));
                            date1.setText(reviews1.getString("time_created").substring(0,10));
                            String ra1 = reviews1.getString("rating");
                            rating1.setText("Rating :" + ra1 + "/5");
                            JSONObject user1 = reviews1.getJSONObject("user");
                            name1.setText(user1.getString("name"));

                            JSONObject reviews2 = reviews.getJSONObject(1);
                            review2.setText(reviews2.getString("text"));
                            date2.setText(reviews2.getString("time_created").substring(0,10));
                            String ra2 = reviews2.getString("rating");
                            rating2.setText("Rating :" + ra2 + "/5");
                            JSONObject user2 = reviews2.getJSONObject("user");
                            name2.setText(user2.getString("name"));

                            JSONObject reviews3 = reviews.getJSONObject(2);
                            review3.setText(reviews3.getString("text"));
                            date3.setText(reviews3.getString("time_created").substring(0,10));
                            String ra = reviews3.getString("rating");
                            rating3.setText("Rating :" + ra + "/5");
                            JSONObject user = reviews3.getJSONObject("user");
                            name3.setText(user.getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "error inside jsonparse", Toast.LENGTH_LONG).show();
                            System.out.println("error inside review");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Rqueue.add(request);
        return view;
    }
}