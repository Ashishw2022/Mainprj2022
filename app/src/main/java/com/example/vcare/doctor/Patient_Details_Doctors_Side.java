package com.example.vcare.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.vcare.R;
import com.example.vcare.chat.Doctor_MessageActivity;
import com.example.vcare.doctor.Doctors_Session_Mangement;
import com.example.vcare.model.Users;
import com.example.vcare.patient.PrescriptionActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Patient_Details_Doctors_Side extends AppCompatActivity {

    private TextView Quest, name, phone_no, date_booked, time_booked,trans;
    private String date,tran, time, pname, Questions, phone, email,pemail;
    private String[] gender;
    private ArrayAdapter<String> gender_adapter;
    private DatabaseReference feedback,userdetails;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__details__doctors__side);

        date = (String) getIntent().getSerializableExtra("date");
        time = (String) getIntent().getSerializableExtra("time");
        pname = (String) getIntent().getSerializableExtra("name");
        Questions = (String) getIntent().getSerializableExtra("Questions");
        phone = (String) getIntent().getSerializableExtra("phone");
        tran = (String) getIntent().getSerializableExtra("tran");

        feedback = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Feedback");
        Quest = findViewById(R.id.detaildesc_ques);
        name = findViewById(R.id.detailhead);
        trans = findViewById(R.id.detailtrans);
        date_booked = findViewById(R.id.detail_date);
        phone_no = findViewById(R.id.detailaddress);
        time_booked = findViewById(R.id.detail_time);
        btn = findViewById(R.id.show_feedback);
        btn.setVisibility(View.INVISIBLE);

        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(this);
        email = doctors_session_mangement.getDoctorSession()[0].replace(".", ",");

        if (!Questions.equals("")) {
            Quest.setText(Questions);
        } else {
            Quest.setText("No Questions");
        }

        name.setText(pname);
        phone_no.setText(phone);
        date_booked.setText(date);
        time_booked.setText(time);
        trans.setText(tran);

        userdetails = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User_data");
        userdetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Users userdata = snapshot1.getValue(Users.class);
                    if(userdata.getPhMain().equals(phone) ){
                        pemail = snapshot1.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        feedback.child(email).child(date).child(time).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    btn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //todo change the commented line
    public void upload_prescription(View view) {

        Intent intent = new Intent(Patient_Details_Doctors_Side.this, PrescriptionActivity.class);
        intent.putExtra("name", pname);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("email", pemail);


        startActivity(intent);

    }

    public void Previous_Prescriptions(View view) {
        Intent intent = new Intent(Patient_Details_Doctors_Side.this, Doctor_Side_Previous_Prescriptions.class);
        intent.putExtra("name", pname);
        intent.putExtra("email", pemail);
        startActivity(intent);


//        public void available_feedbacks (View view){
//            Intent intent = new Intent(Patient_Details_Doctors_Side.this, Doctors_Show_Feedback.class);
//            intent.putExtra("name", pname);
//            intent.putExtra("phone", phone);
//            intent.putExtra("date", date);
//            intent.putExtra("time", time);
//            startActivity(intent);
//        }

//    public void open_chat(View view) {
//        Intent intent = new Intent(Patient_Details_Doctors_Side.this, Doctor_MessageActivity.class);
//        intent.putExtra("phone",phone);
//        startActivity(intent);
//    }
    }
}