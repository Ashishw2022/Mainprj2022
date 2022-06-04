package com.example.vcare.model;

public class Users {
    private String name,email, status, User_Type,u_active,phMain;

    public Users() {
    }

    public Users(String name, String email, String status, String User_Type,String u_active,String phMain) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.User_Type = User_Type;
        this.u_active=u_active;
        this.phMain = phMain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getuser_type() {
        return User_Type;
    }

    public void setuser_type(String user_Type) {
        this.User_Type = User_Type;
    }

    public String getU_active() {
        return u_active;
    }

    public void setU_active(String u_active) {
        this.u_active = u_active;
    }

    public String getPhMain() {
        return phMain;
    }

    public void setPhMain(String phMain) {
        this.phMain = phMain;
    }

//    public String getphMain() {
//        return phMain;
//    }
//
//    public void setphMain(String user_Type) {
//        this.phMain = phMain;
//    }
}
