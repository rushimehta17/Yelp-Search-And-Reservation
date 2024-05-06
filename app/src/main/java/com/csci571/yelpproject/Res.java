package com.csci571.yelpproject;

public class Res {
    public String number, reservename, date, time, email;

    public Res(String number, String reservename, String date, String time, String email) {
        this.number = number;
        this.reservename = reservename;
        this.date = date;
        this.time = time;
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReservename() {
        return reservename;
    }

    public void setReservename(String reservename) {
        this.reservename = reservename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
