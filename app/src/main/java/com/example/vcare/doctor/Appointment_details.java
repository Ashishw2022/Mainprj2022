package com.example.vcare.doctor;

public class Appointment_details {

    private String transaction, dname, phone,name, date, time, email,pemail;
    private int status, payment;

    public Appointment_details() {
    }

    public Appointment_details(String transaction, String dname, String email,String pemail,String phone, String name, int status, String date, String time, int payment) {
        this.transaction = transaction;
        this.dname = dname;
        this.email = email;
        this.pemail = pemail;

        this.phone = phone;
        this.name = name;
        this.status = status;
        this.date = date;
        this.time = time;
        this.payment = payment;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }
}
