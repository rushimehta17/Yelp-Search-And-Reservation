package com.csci571.yelpproject.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.csci571.yelpproject.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class BusinessDetail extends Fragment {
    public String buss_id;
    public BusinessDetail(String business_id) {
        buss_id = business_id;
    }
    AlertDialog.Builder builder;
    Button reserverButton;
    ImageView imgage1, imgage2, imgage3;
    String title;
    EditText email, time, date;
    String correctEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public RequestQueue Bqueue;
    TextView address, price, phone, status, category, link;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_business_detail, container, false);
        Bqueue = Volley.newRequestQueue(getContext());

        builder = new AlertDialog.Builder(getContext());
        reserverButton = view.findViewById(R.id.reserve_button);
        address = view.findViewById(R.id.editaddress);
        price = view.findViewById(R.id.editprice);
        phone = view.findViewById(R.id.editphone);
        status = view.findViewById(R.id.editstatus);
        category = view.findViewById(R.id.editcategory);
        link = view.findViewById(R.id.editlink);
        imgage1 = view.findViewById(R.id.img1);
        imgage2 = view.findViewById(R.id.img2);
        imgage3 = view.findViewById(R.id.img3);


        String url = "https://assignment8csci.wl.r.appspot.com/search/business?id=" + buss_id;
        System.out.println("bid=" + buss_id);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject address1 = response.getJSONObject("location");
                            title = response.getString("name");
                            String price1 = response.getString("price");
                            String phone1 = response.getString("display_phone");
                            String status1 = response.getString("is_closed");
                            JSONArray categoryarray = response.getJSONArray("categories");
                            JSONObject categorybject = categoryarray.getJSONObject(1);
                            String category1 = categorybject.getString("title");
                            String url1 = response.getString("url");

                            if (status1.equals("true")){
                                status.setTextColor(Color.parseColor("#FFBD0404"));
                                status.setText("Closed");
                            }
                            else {
                                status.setTextColor(Color.parseColor("#FF08DF11"));
                                status.setText("Open Now");
                            }
                            address.setText(address1.getString("address1"));
                            price.setText(price1);
                            phone.setText(phone1);
//                            status.setText(status1);
                            category.setText(category1);
                            link.setMovementMethod(link.getMovementMethod());
                            link.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String urlText = url1;
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(urlText));
                                    startActivity(i);
                                }
                            });
//                            String urlText = "<a href=" + url1 +">Business Link</a>";
//                            link.setText(Html.fromHtml(urlText));
//                            link.setClickable(true);
//                            System.out.println("url = " + urlText);
//                            link.setText("Business Link");
                            JSONArray images = response.getJSONArray("photos");
//                            System.out.println("images = " + images.get(0));
                            Picasso.get().load(String.valueOf(images.get(0))).into(imgage1);
                            Picasso.get().load(String.valueOf(images.get(1))).into(imgage2);
                            Picasso.get().load(String.valueOf(images.get(2))).into(imgage3);

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
        Bqueue.add(request);
        // Inflate the layout for this fragment

        reserverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View alert = inflater.inflate(R.layout.booking, container, false);
                builder.setView(alert);
                builder.setTitle(title);
                email = alert.findViewById(R.id.user_email);
                time = alert.findViewById(R.id.time_business_get);
                date = alert.findViewById(R.id.user_date);

                Calendar myCalander = Calendar.getInstance();

                int year = myCalander.get(Calendar.YEAR);
                int month = myCalander.get(Calendar.MONTH);
                int day = myCalander.get(Calendar.DAY_OF_MONTH);

                int minute1 = myCalander.get(Calendar.MINUTE);

                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int hour;
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        time.setText(hourOfDay + ":" + minute);
                                        hour = hourOfDay;
                                        minute1 = minute;
                                    }
                                }, hour, minute1, false);
                        timePickerDialog.show();
                    }
                });

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    }
                                },
                                year, month, day);
                        datePickerDialog.show();
                    }
                });
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String stringEmail = email.getText().toString();
                                String time1 = time.getText().toString();
                                int hour = Integer.parseInt(time1.split(":")[0]);
                                if(!stringEmail.matches(correctEmail)) {
                                    Toast.makeText(getContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
                                }
                                else if(hour<10 || hour>17){
//                                    System.out.println("time=" + hour + minute);
                                    Toast.makeText(getContext(), "Time should be between 10AM AND 5PM", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getContext(), "Reservation Booked", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });
        return view;
    }
}