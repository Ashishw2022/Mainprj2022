package com.example.vcare.admin;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vcare.R;
import com.example.vcare.doctor.Doctor_Images;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Admin_Doctor_Details extends AppCompatActivity {

    private TextView doctor_name, doctor_spec, doctor_experience, doctor_fee, doctor_slots, doctor_about, emailid;
    private ImageView doctor_image;
    private Doctor_Images doctor_images;
    private Button disablebtn,enablebtn;
    private DatabaseReference reference_doctor, reference_booking,reference_status;
    private int start, end;
    private String encoded_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__doctor__details);

        doctor_name = (TextView) findViewById(R.id.doc_name_textview);
        doctor_spec = (TextView) findViewById(R.id.doc_spec_textview);
        doctor_experience = (TextView) findViewById(R.id.Exp_textview_val);
        doctor_fee = (TextView) findViewById(R.id.consult_charges_text_val);
        doctor_slots = (TextView) findViewById(R.id.Available_text_val);
        doctor_about = (TextView) findViewById(R.id.about_doctor);
        emailid = (TextView) findViewById(R.id.email_text_val);
        doctor_image = (ImageView) findViewById(R.id.imageView_doc_display);
        disablebtn=findViewById(R.id.disable);
        enablebtn=findViewById(R.id.enable);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        String date_val = DateFormat.format("dd MMM yyyy", startDate).toString();
        String email = getIntent().getSerializableExtra("Email ID").toString();
        String name = getIntent().getSerializableExtra("Doctors Name").toString();
        String speciality = getIntent().getSerializableExtra("Speciality").toString();
        encoded_email = email.replace(".", ",");
        reference_status = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        reference_doctor = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        reference_booking = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        emailid.setText(email);
        reference_status.child(encoded_email).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    String status = datasnapshot.child("u_active").getValue(String.class);
                    if(status.equals("0")){
                        disablebtn.setVisibility(View.GONE);
                        enablebtn.setVisibility(View.VISIBLE);
                    }else{
                        enablebtn.setVisibility(View.GONE);
                        disablebtn.setVisibility(View.VISIBLE);
                    }




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        disablebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference_status.child(encoded_email).child("u_active").setValue("0");
                disablebtn.setVisibility(View.GONE);
                enablebtn.setVisibility(View.VISIBLE);
            }
        });
        enablebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference_status.child(encoded_email).child("u_active").setValue("1");
                enablebtn.setVisibility(View.GONE);
                disablebtn.setVisibility(View.VISIBLE);
            }
        });
        reference_doctor.child(encoded_email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    doctor_name.setText(datasnapshot.child("name").getValue(String.class));
                    doctor_spec.setText(datasnapshot.child("type").getValue(String.class));
                    doctor_about.setText(datasnapshot.child("desc").getValue(String.class));
                    doctor_experience.setText(datasnapshot.child("experience").getValue(String.class) + " years");
                    doctor_fee.setText("Rs. " + datasnapshot.child("fees").getValue(String.class));
                    doctor_images = datasnapshot.child("doc_pic").getValue(Doctor_Images.class);
                    if (doctor_images != null) {
                        Picasso.with(Admin_Doctor_Details.this).load(doctor_images.getUrl()).into(doctor_image);
                    }




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        doctor_slots.setText("");
        reference_booking.child(encoded_email).child(date_val).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String time = "";
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        start = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[0]);
                        end = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[1]);
                        time = time + String.format("%d:00 -%d:00", start, end) + "\n";
                    }
                    doctor_slots.setText(time);
                } else {
                    doctor_slots.setText("Doctor is not available today!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

}