package com.example.vcare.patient;

public class User_Profile {
    private String name,email;
    public User_Profile()
    {

    }
    public User_Profile(String name, String email)
    {
        this.name = name;
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

}
