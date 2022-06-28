package com.example.vcare.appointments;

public class Appointment_notif {

    private String appointment_text,date,time,Questions,phone,pname,token;

    public Appointment_notif(String appointment_text, String date, String time, String questions, String phone, String pname,String token) {
        this.appointment_text = appointment_text;
        this.date = date;
        this.time = time;
        this.Questions = questions;
        this.phone = phone;
        this.pname = pname;
        this.token=token;

    }



    public Appointment_notif() {
    }

    public String getAppointment_text() {
        return appointment_text;
    }

    public void setAppointment_text(String appointment_text) {
        this.appointment_text = appointment_text;
    }
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public String getQuestions() {
        return Questions;
    }

    public void setQuestions(String questions) {
        Questions = questions;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}