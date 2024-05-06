package com.csci571.yelpproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusinessDetails extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter myViewPagerAdapter;
    String business_id, lat, lon, twitterUrl, facebookUrl;
    TextView title;
    ImageView business_back, facebook, twitter;

    public RequestQueue bQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_details);
        bQueue = Volley.newRequestQueue(this);
        business_back = findViewById(R.id.backimage);
        twitter = findViewById(R.id.twitter);
        facebook = findViewById(R.id.facebook);

        title = findViewById(R.id.busniess_title);
        business_id = getIntent().getStringExtra("id");
        System.out.println("business_id=" + business_id);

        business_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusinessDetails.super.onBackPressed();
            }
        });


        String url = "https://assignment8csci.wl.r.appspot.com/search/business?id=" + business_id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject coord = response.getJSONObject("coordinates");
                            lat = coord.getString("latitude");
                            lon = coord.getString("longitude");

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    // yourMethod();
                                }
                            }, 5000);
                            String name = response.getString("name");
                            String url = response.getString("url");

                            twitterUrl = "https://twitter.com/intent/tweet?text=" + name + "!&url=" + url;
                            facebookUrl = "https://www.facebook.com/sharer/sharer.php?u=" + url + "&quote=" + name;


//                            System.out.println("Lat and lon=" + lat + lon);
                            title.setText(response.getString("name"));
                            //                            title.setText("working");
//                            System.out.println("hello");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //                     Toast.makeText(getApplicationContext(), "error inside jsonparse", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        bQueue.add(request);
        System.out.println("inside on map ready function");
//        callMap(url);

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                https://twitter.com/intent/tweet?text={{businessData[0]}}!&url={{businessData[4]}}
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(twitterUrl));
                startActivity(i);
                System.out.println("twitter clicked");
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                https://www.facebook.com/sharer/sharer.php?u={{businessData[4]}}&quote={{businessData[0]}}
                Intent i = new Intent(Intent.ACTION_VIEW);
                System.out.println("facebook = " + facebookUrl);
                i.setData(Uri.parse(facebookUrl));
                startActivity(i);
            }
        });

        System.out.println("Lat and lon=" + lat + lon);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        System.out.println("location of lat lon=" + 34 + -118);
        myViewPagerAdapter = new ViewPagerAdapter(this, business_id, "34", "-118");
        viewPager2.setAdapter(myViewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void callMap(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject coord = response.getJSONObject("coordinates");
                            lat = coord.getString("latitude");
                            lon = coord.getString("longitude");

                            title.setText(response.getString("name"));
                            //                            title.setText("working");
                            System.out.println("hello");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //                     Toast.makeText(getApplicationContext(), "error inside jsonparse", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        bQueue.add(request);

    }
}