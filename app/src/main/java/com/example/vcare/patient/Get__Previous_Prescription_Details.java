package com.example.vcare.patient;

public class Get__Previous_Prescription_Details {

    private String name,date,time,email;

    public Get__Previous_Prescription_Details() {
    }

    public Get__Previous_Prescription_Details(String name, String date, String time, String email) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
