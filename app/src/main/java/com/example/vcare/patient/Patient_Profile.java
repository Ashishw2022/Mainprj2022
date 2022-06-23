package com.example.vcare.patient;

import com.example.vcare.doctor.Doctor_Images;

public class Patient_Profile {
    private String name, phMain, aemail, desc, gender;
    private Doctor_Images doc_pic;

    public Patient_Profile(){

    }

    public Patient_Profile(String name, String phMain,String aemail, String desc, String gender, Doctor_Images doc_pic) {
        this.name = name;
        this.aemail = aemail;
        this.desc = desc;
        this.gender = gender;
        this.doc_pic = doc_pic;
        this.phMain=phMain;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Doctor_Images getDoc_pic() {
        return doc_pic;
    }

    public void setDoc_pic(Doctor_Images doc_pic) {
        this.doc_pic = doc_pic;
    }
    public String getAemail() {
        return aemail;
    }

    public void setAemail(String aemail) {
        this.aemail = aemail;
    }

    public String getphMain() {
        return phMain;
    }

    public void setphMain(String phMain) {
        this.phMain = phMain;
    }
}
