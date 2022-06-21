package com.example.vcare.patient;

public class Patient_Details {

    private String pemail, name;

    public Patient_Details() {
    }


    public Patient_Details(String pemail, String name) {
        this.pemail = pemail;
        this.name = name;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
