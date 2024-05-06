package com.csci571.yelpproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.csci571.yelpproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
    public String buss_id;
    public RequestQueue mapqueue;
    public Double lati;
    public Double longi;

    public MapsFragment(String lat, String lon) {
        this.buss_id = buss_id;
        this.lati = Double.parseDouble(lat);
        this.longi = Double.parseDouble(lon);
//        mapqueue = Volley.newRequestQueue(getContext());
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            System.out.println("map fragment location=" + lati + longi);
            LatLng sydney = new LatLng(lati, longi);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
            googleMap.setMinZoomPreference(15);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.setMinZoomPreference(13);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}