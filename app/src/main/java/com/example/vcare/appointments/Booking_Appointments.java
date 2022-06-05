package com.example.vcare.appointments;

public class Booking_Appointments {

    private String phone;
    private int value;
    private String email;

    public Booking_Appointments() {
    }

    public Booking_Appointments(int value, String phone,String email) {
        this.value = value;
        this.phone = phone;
        this.email = email;
    }

    public Booking_Appointments( int value,String email) {
        this.email = email;
        this.value = value;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
