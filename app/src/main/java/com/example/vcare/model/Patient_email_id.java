package com.example.vcare.model;

public class Patient_email_id {
    private String phone_no,email_id;

    public Patient_email_id() {
    }

    public Patient_email_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
}

