package com.example.vcare.patient;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vcare.model.Patient_email_id;

public class Patient_Session_Management {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String SHARED_PREF_NAME="session";
    private String SESSION_KEY="session_user";

    public Patient_Session_Management(Context context) {
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public  void saveSession(Patient_email_id patient){

        String phone_no= patient.getPhone_no();
        String email= patient.getEmail_id();
        editor.putString(SESSION_KEY,email);
      //  editor.putString(SESSION_KEY,phone_no);
        editor.commit();

    }

    public String getSession(){

      return sharedPreferences.getString(SESSION_KEY, String.valueOf(-1));

    }

    public  void removeSession(){
        editor.putString(SESSION_KEY, String.valueOf(-1)).commit();
    }
}
