package com.example.vcare.patient;

public class User_Profile {
    private String name,email,phMain;
    public User_Profile()
    {

    }
    public User_Profile(String name, String email,String phMain)
    {
        this.name = name;
        this.email = email;
        this.phMain=phMain;
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


    public String getPhMain() {
        return phMain;
    }

    public void setPhMain(String phMain) {
        this.phMain = phMain;
    }
}

