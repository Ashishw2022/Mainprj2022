package com.example.vcare.patient;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vcare.R;
import com.example.vcare.doctor.Doctor_Images;
import com.example.vcare.doctor.Session_Mangement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Patient_viewprofile extends AppCompatActivity {
    private TextView name,gender,email,speciality,bio,phone, fees;
    private String email_id;
    private ImageView update,sign;
    private CircleImageView circle_image;
    private Doctor_Images doctor_images, sign_images;
    private DatabaseReference reference_user, reference_patient;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_viewprofile);
        progressBar=(ProgressBar) findViewById(R.id.progressbar_view_profile);
        name = (TextView) findViewById(R.id.name);
        gender = (TextView) findViewById(R.id.gender);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone3);
        bio = (TextView) findViewById(R.id.bio);
        update = (ImageView) findViewById(R.id.imageView5);
        //sign = (ImageView) findViewById(R.id.signImage);
        circle_image = (CircleImageView) findViewById(R.id.profileImage);

        Session_Mangement _session_mangement = new Session_Mangement(this);
        email_id = _session_mangement.getDoctorSession()[0].replace(".",",");

        progressBar.setVisibility(View.VISIBLE);
        reference_patient = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User_data");
        reference_user = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        reference_user.child(email_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    name.setText(snapshot.child("name").getValue(String.class));
                    email.setText(snapshot.child("email").getValue(String.class));
                    //phone.setText(snapshot.child("phMain").getValue(String.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference_patient.child(email_id).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    gender.setText(datasnapshot.child("gender").getValue(String.class));
                    //speciality.setText(datasnapshot.child("type").getValue(String.class));
                    bio.setText(datasnapshot.child("desc").getValue(String.class));
                    phone.setText(datasnapshot.child("phMain").getValue(String.class));
                   //fees.setText("Rs. " + datasnapshot.child("fees").getValue(String.class));
                    doctor_images = datasnapshot.child("doc_pic").getValue(Doctor_Images.class);
                    //sign_images = datasnapshot.child("sign_pic").getValue(Doctor_Images.class);
                    if(doctor_images != null) {
                        Picasso.with(Patient_viewprofile.this).load(doctor_images.getUrl()).into(circle_image);
                    }
//                    if(sign_images != null){
//                        Picasso.with(Patient_viewprofile.this).load(sign_images.getUrl()).into(sign);
//                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Patient_viewprofile.this, Patient_Update_Profile.class));
            }
        });

    }
}

