package com.csci571.yelpproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.csci571.yelpproject.fragment.BusinessDetail;
import com.csci571.yelpproject.fragment.MapsFragment;
import com.csci571.yelpproject.fragment.ReviewDetails;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public String buss_id;
    public String latitude, longitude;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String business_id, String lat, String lon) {

        super(fragmentActivity);
//        System.out.println("view pager location=" + lat + lon);
        buss_id = business_id;
        latitude = lat;
        longitude = lon;
        System.out.println("view pager location=" + latitude + longitude);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BusinessDetail(buss_id);
            case 1:
                return new MapsFragment(latitude, longitude);
            case 2:
                return new ReviewDetails(buss_id);
//            default:
//                return new BusinessDetail(buss_id);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
