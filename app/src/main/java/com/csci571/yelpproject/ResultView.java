package com.csci571.yelpproject;

import android.widget.ImageView;

public class ResultView {
    public String sno, business_name, ratings, distance;
    public String image;
    public String id;

    public ResultView(String srno, String business_name, String ratings, String distance, String image, String id) {
        this.sno = srno;
        this.business_name = business_name;
        this.ratings = ratings;
        this.distance = distance;
        this.image = image;
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
