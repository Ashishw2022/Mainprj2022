package com.example.vcare.doctor;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vcare.R;
import com.example.vcare.predictor.DiseasePrediction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class predictiondetails extends AppCompatActivity {

    private TextView patientname, symptomsdis, diseasename, specialitytype, patientage, docremark,patientgender;
    private ImageView imageicon;
    private Doctor_Images doctor_images;
    private ArrayList<Appointment_details> previous_payment;
    private Button declinebtn,approvebtn,notvrf,vrf;
    private DatabaseReference reference_dispred, reference_doctor,reference_status,reference_book;
    private int start, end;
    private String encoded_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prediction_details);

        patientname = (TextView) findViewById(R.id.patientname);
        symptomsdis = (TextView) findViewById(R.id.symptoms);
        diseasename = (TextView) findViewById(R.id.diseasename);
        specialitytype = (TextView) findViewById(R.id.specialitytype);
        patientage = (TextView) findViewById(R.id.patientage);
        patientgender =(TextView) findViewById(R.id.patientgender);
        docremark =(TextView) findViewById(R.id.docremark);
         imageicon = (ImageView)findViewById(R.id.imageicon);
       /* doctor_slots = (TextView) findViewById(R.id.Available_text_val);
        doctor_about = (TextView) findViewById(R.id.about_doctor);
        emailid = (TextView) findViewById(R.id.email_text_val);
        doctor_image = (ImageView) findViewById(R.id.imageView_doc_display);*/
        declinebtn=(Button)findViewById(R.id.decline);
        approvebtn=(Button)findViewById(R.id.approve);

        notvrf=findViewById(R.id.notVrf);
        vrf=findViewById(R.id.vrf);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        String date_val = DateFormat.format("dd MMM yyyy", startDate).toString();
        String email = getIntent().getSerializableExtra("Email ID").toString();
        String approvalId= getIntent().getSerializableExtra("ApprovalId").toString();
        String name = getIntent().getSerializableExtra("Patient Name").toString();
        String symptoms = getIntent().getSerializableExtra("Symptoms").toString();
        String speciality = getIntent().getSerializableExtra("speciality").toString();
        String predictedDisease = getIntent().getSerializableExtra("PredictedDisease").toString();
//        String gender = getIntent().getSerializableExtra("gender").toString();
        String age = getIntent().getSerializableExtra("age").toString();
        patientname.setText(name);
        symptomsdis.setText(symptoms);
        diseasename.setText(predictedDisease);
        specialitytype.setText(speciality);
        //patientgender.setText(gender);
        patientage.setText(age);
        encoded_email = email.replace(".", ",");
        reference_dispred = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("disease_prediction");
        reference_doctor = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User_data");
        reference_doctor.child(email.replace(".",",")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String gender = snapshot.child("gender").getValue(String.class);
                    patientgender.setText(gender);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*reference_booking = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        reference_book=FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Appointment");*/
        getapprovalStatus(reference_dispred,approvalId,email.replace(".",","));



        approvebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String remark = docremark.getText().toString();
                if (remark.isEmpty()) {
                    docremark.setError("Please add your remarks");
                    docremark.requestFocus();
                    return;
                }

                reference_dispred.child(email.replace(".",",")).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dnsnaspshot : snapshot.getChildren()){
                              //  for(DataSnapshot dnsnaspshot : dn.getChildren()){
                                    DiseasePrediction dp = dnsnaspshot.getValue(DiseasePrediction.class);
                                    if(dp.getPid().equals(approvalId)){
                                       // reference_dispred.child("approved").child(dp.getPatientEmail().replace(".",",")).child(approvalId).setValue(dp);
                                        reference_dispred.child(dp.getPatientEmail().replace(".",",")).child(approvalId).child("approvalstatus").setValue(1);
                                        reference_dispred.child(dp.getPatientEmail().replace(".",",")).child(approvalId).child("remarks").setValue(docremark.getText().toString().trim());
                                     //   imageicon.setVisibility(view.VISIBLE);
                                        approvebtn.setVisibility(view.GONE);
                                        declinebtn.setVisibility(view.GONE);
                                        getapprovalStatus(reference_dispred, approvalId, email.replace(".", ","));
                                   //     reference_dispred.child("pending").child(dp.getPatientEmail().replace(".",",")).child(approvalId).getRef().removeValue();
                                    }
                                }
                           //}
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        declinebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String remark = docremark.getText().toString();
                if (remark.isEmpty()) {
                    docremark.setError("Please add your remarks");
                    docremark.requestFocus();
                    return;
                }
                reference_dispred.child(email.replace(".",",")).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dnsnaspshot : snapshot.getChildren()){
                              //  for(DataSnapshot dnsnaspshot : dn.getChildren()){
                                    DiseasePrediction dp = dnsnaspshot.getValue(DiseasePrediction.class);
                                    if(dp.getPid().equals(approvalId)){
                                      //  reference_dispred.child("approved").child(dp.getPatientEmail().replace(".",",")).child(approvalId).setValue(dp);
                                        reference_dispred.child(dp.getPatientEmail().replace(".",",")).child(approvalId).child("approvalstatus").setValue(2);
                                        reference_dispred.child(dp.getPatientEmail().replace(".",",")).child(approvalId).child("remarks").setValue(docremark.getText().toString().trim());

                                        //  imageicon.setVisibility(view.GONE);
                                        approvebtn.setVisibility(view.GONE);
                                        declinebtn.setVisibility(view.GONE);
                                        getapprovalStatus(reference_dispred, approvalId, email.replace(".", ","));
                                       // reference_dispred.child(dp.getPatientEmail().replace(".",",")).child(approvalId).getRef().removeValue();
                                     ///   Toast.makeText(predictiondetails.this, "There predition approval is cancelled!", Toast.LENGTH_SHORT).show();
                                    }
                             //   }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

    private void getapprovalStatus(DatabaseReference reference_dispred, String approvalId, String email) {
        reference_dispred.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dn : snapshot.getChildren()) {
                      //  for (DataSnapshot dnsnaspshot : dn.getChildren()) {
                              //    for (DataSnapshot snaspshotDn : dnsnaspshot.getChildren()) {
                                      DiseasePrediction dp = dn.getValue(DiseasePrediction.class);
                                      String pid = dp.getPid();
                                      if (pid.equals(approvalId)) {
                                          int appstatus = dp.getApprovalstatus();
                                          if (appstatus == 0) {
                                              approvebtn.setVisibility(View.VISIBLE);
                                              declinebtn.setVisibility(View.VISIBLE);
                                              imageicon.setVisibility(View.GONE);
                                          }
                                          else if (appstatus == 1) {
                                              approvebtn.setVisibility(View.GONE);
                                              declinebtn.setVisibility(View.GONE);
                                              imageicon.setVisibility(View.VISIBLE);
                                          } else {
                                              approvebtn.setVisibility(View.GONE);
                                              declinebtn.setVisibility(View.GONE);
                                              imageicon.setVisibility(View.GONE);
                                       //       Toast.makeText(predictiondetails.this, "There predition approval is cancelled!", Toast.LENGTH_SHORT).show();
                                          }

                                   //   }
                                //  }
                        }
                    }

                    //   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}