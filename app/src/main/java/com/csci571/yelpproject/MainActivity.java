package com.csci571.yelpproject;

import static android.R.layout.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//  Variable declaration
    public ArrayList<ResultView> resultList;
    public Spinner category;
    public RequestQueue mQueue;
    public boolean flag = false;

    AutoCompleteTextView keyword;
    EditText distance, location;
    Button submit, clear;
    String categorySelected, lat = "", lon = "", vollyResponse;
    ImageView calendar;
    CheckBox locationCheck;
    RecyclerView resultsView;
    TextView noResult;
//    StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Define variable


        resultList = new ArrayList<>();
        keyword = findViewById(R.id.keyword);
        noResult = findViewById(R.id.noresult);
        distance = findViewById(R.id.distance);
        location = findViewById(R.id.location);
        clear = findViewById(R.id.clear);
        calendar = findViewById(R.id.backimage);
        locationCheck = findViewById(R.id.checkbox);
        resultsView = findViewById(R.id.resultsview);
        mQueue = Volley.newRequestQueue(this);

        noResult.setVisibility(View.INVISIBLE);

//      Calendar on click
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Reservation.class);
                startActivity(intent);
            }
        });

//     setting view
       category = findViewById(R.id.category_selector);
       submit = findViewById(R.id.submit);

//       Clear Logic
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                keyword.getText().clear();
                distance.getText().clear();
                location.getText().clear();
                locationCheck.setChecked(false);
                noResult.setVisibility(View.INVISIBLE);
                setAdapter();
            }
        });

        //      Autocomplete
        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                System.out.println("typing=" + keyword.getText().toString());
                String url = "https://assignment8csci.wl.r.appspot.com/autocomplete?keyword=" + keyword.getText().toString();

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String[] suggestions = new String[6];
                                    JSONArray suggesArray = response.getJSONArray("categories");
                                    for(int i=0; i< suggesArray.length();i++) {
                                        JSONObject obj = suggesArray.getJSONObject(i);
//                                        System.out.println(obj.get("alias"));
                                        suggestions[i] = obj.get("alias").toString();
                                    }
                                    suggesArray = response.getJSONArray("terms");
                                    for(int i=0; i< suggesArray.length();i++) {
                                        JSONObject obj = suggesArray.getJSONObject(i);
//                                        System.out.println(obj.get("alias"));
                                        suggestions[i + 3] = obj.get("text").toString();
                                    }
//                                    System.out.println("list=" + suggestions[3] + suggestions[4] + suggestions[5]);
                                    ArrayAdapter<String> suggestAdapter = new ArrayAdapter<String>(MainActivity.this, simple_list_item_1, suggestions);
                                    keyword.setAdapter(suggestAdapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "error inside jsonparse", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                mQueue.add(request);
            }
        });


//     validation logic
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(keyword.getText().toString())){
                   keyword.setError("This field is required");
                   return;
                }
                if (TextUtils.isEmpty(distance.getText().toString())){
                    distance.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(location.getText().toString())){
                    location.setError("This field is required");
                }
                System.out.println(keyword.getText().toString() + distance.getText().toString() + categorySelected + location.getText().toString() );
                if (TextUtils.isEmpty(location.getText().toString()) && location.getVisibility() == View.INVISIBLE){
                    callIpinfo();
                }
                if(TextUtils.isEmpty(location.getText().toString()) == false){
//                    callGeo();
                    callYelp();
                }
//                setResultInfo();
//                setAdapter();
            }
        });

//      Check-box logic
        locationCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    location.setVisibility(View.INVISIBLE);
                }
                if (!b){
                    location.setVisibility(View.VISIBLE);
                }
            }
        });


//      spinner logic
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, simple_spinner_item);
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        category.setOnItemSelectedListener(this);
    } // End of Main function

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categorySelected = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(getApplicationContext(), categorySelected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setAdapter(){
        if (flag == false) {
            System.out.println("inside set adapter");
            RecyclerAdapter adapter = new RecyclerAdapter(resultList, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            resultsView.setLayoutManager(layoutManager);
            resultsView.setItemAnimator(new DefaultItemAnimator());
            resultsView.setAdapter(adapter);
//            resultList.clear();
        }
        else {
            RecyclerAdapter adapter = new RecyclerAdapter(resultList, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            resultList.clear();
            resultsView.setLayoutManager(layoutManager);
            resultsView.setItemAnimator(new DefaultItemAnimator());
            resultsView.setAdapter(adapter);
            flag = false;
        }
    }

//    public void setResultInfo(){
//        System.out.println("inside set result");
//        resultList.add(new ResultView("2", "pasta", "4", "10", "https://picsum.photos/id/237/200/300", "5"));
//        resultList.add(new ResultView("3", "pizza", "5", "1", "https://picsum.photos/id/237/200/300", "6"));
//    }

    public void callIpinfo() {
        String url = "https://ipinfo.io/?token=1a28d6f5cfe0f1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String loc = response.getString("loc");
//                            Toast.makeText(getApplicationContext(), "Inside jsonparse", Toast.LENGTH_LONG).show();
                            String[] coord = loc.split(",");
                            lat = coord[0];
                            lon = coord[1];
                            System.out.println("my output=" + lat );
                            System.out.println("my output=" + lon);
//                            callYelp();
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "error inside jsonparse", Toast.LENGTH_LONG).show();
                        }
                        callYelp();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

//    public void callGeo(){
//        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + location.getText().toString() +"&key=AIzaSyDer8pgleVTjD5Gpr9Kb6RS0STC1pL3iAo";
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray loc = response.getJSONArray("results");
//                            JSONObject obj = loc.getJSONObject(0);
//                            obj = obj.getJSONObject("geometry");
//                            obj = obj.getJSONObject("location");
//                            lat = obj.getString("lat");
//                            lon = obj.getString("lng");
//                            System.out.println(lat + lon);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
////                            Toast.makeText(getApplicationContext(), "error inside jsonparse", Toast.LENGTH_LONG).show();
//                        }
//                        callYelp();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        mQueue.add(request);
//    }

    public void callYelp(){
        noResult.setVisibility(View.INVISIBLE);
        String finalCategory = "";
        if (categorySelected.equals("Default")){
            finalCategory = "All";
        }
        else if (categorySelected.equals("Arts and Entertainment")){
            finalCategory = "arts";
        }
        else if (categorySelected.equals("Health and Medical")){
            finalCategory = "health";
        }
        else if (categorySelected.equals("Hotels and Travel")){
            finalCategory = "hotelstrave";
        }
        else if (categorySelected.equals("Food")){
            finalCategory = "food";
        }
        else if (categorySelected.equals("Professional Services")){
            finalCategory = "professional";
        }
        String url = "https://assignment8csci.wl.r.appspot.com/search/getdata?keyword=" + keyword.getText().toString() + "&category=" + finalCategory + "&distance=" + distance.getText().toString() + "&lat=" + this.lat + "&lon=" + lon + "&location=" + location.getText().toString() ;
        System.out.println("url=" + url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray res = response.getJSONArray("businesses");
                            if (res.length() == 0){
                                noResult.setVisibility(View.VISIBLE);
                            }
                            for(int i=0; i< res.length();i++){
                                JSONObject obj = res.getJSONObject(i);
                                System.out.println(obj.get("name"));
                                int dist = (int)Double.parseDouble(obj.get("distance").toString());
                                dist = dist / 1609;
                                resultList.add(new ResultView(Integer.toString(i+1), obj.get("name").toString(), obj.get("rating").toString(), Integer.toString(dist), obj.get("image_url").toString(), obj.get("id").toString()));
                            }
                            setAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();

//                            Toast.makeText(getApplicationContext(), "error inside jsonparse", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error");
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}