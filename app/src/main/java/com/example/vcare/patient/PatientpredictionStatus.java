package com.example.vcare.patient;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vcare.R;
import com.example.vcare.doctor.Appointment_details;
import com.example.vcare.doctor.Doctor_Images;
import com.example.vcare.doctor.Session_Mangement;
import com.example.vcare.predictor.DiseasePrediction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class PatientpredictionStatus extends AppCompatActivity {

    private TextView patientname, symptomsdis, diseasename, specialitytype, patientage, docremark,patientgender,tdname;
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
        setContentView(R.layout.patient_prediction_status);

        patientname = (TextView) findViewById(R.id.patientname);
        symptomsdis = (TextView) findViewById(R.id.symptoms);
        diseasename = (TextView) findViewById(R.id.diseasename);
        specialitytype = (TextView) findViewById(R.id.specialitytype);
        patientage = (TextView) findViewById(R.id.patientage);
        patientgender =(TextView) findViewById(R.id.patientgender);
         docremark=(TextView) findViewById(R.id.docremark);
        tdname =(TextView) findViewById(R.id.dname);


       imageicon = (ImageView)findViewById(R.id.imageicon);
       /* doctor_slots = (TextView) findViewById(R.id.Available_text_val);
        doctor_about = (TextView) findViewById(R.id.about_doctor);
        emailid = (TextView) findViewById(R.id.email_text_val);
        doctor_image = (ImageView) findViewById(R.id.imageView_doc_display);*/

        Session_Mangement _session_mangement = new Session_Mangement(this);
        String email = _session_mangement.getDoctorSession()[0].replace(".",",");


        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        String date_val = DateFormat.format("dd MMM yyyy", startDate).toString();
        String pemail = getIntent().getSerializableExtra("Email ID").toString();
        String approvalId= getIntent().getSerializableExtra("ApprovalId").toString();
        String name = getIntent().getSerializableExtra("Patient Name").toString();
        String symptoms = getIntent().getSerializableExtra("Symptoms").toString();
        String speciality = getIntent().getSerializableExtra("speciality").toString();
        String predictedDisease = getIntent().getSerializableExtra("PredictedDisease").toString();
        String age = getIntent().getSerializableExtra("age").toString();
       // String gender = getIntent().getSerializableExtra("gender").toString();
        String remarks = getIntent().getSerializableExtra("remarks").toString();
        int approval = (int) getIntent().getSerializableExtra("approval");
        String dname = getIntent().getSerializableExtra("dname").toString();
        String demail = getIntent().getSerializableExtra("demail").toString();

//        String gender = getIntent().getSerializableExtra("gender").toString();
      /*  String age = getIntent().getSerializableExtra("age").toString();*/
        patientname.setText(name);
        symptomsdis.setText(symptoms.replace("[","").replace("]",""));
        diseasename.setText(predictedDisease);
        specialitytype.setText(speciality);
        patientage.setText(age);
        tdname.setText("Dr."+dname+"\n Email: "+demail.replace(",","."));
        if(approval==1)
        {
            imageicon.setVisibility(View.VISIBLE);
        }
        else
        {
              imageicon.setVisibility(View.GONE);

        }

        //  patientgender.setText(gender);
        docremark.setText(remarks);

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


//       reference_dispred.child(email).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for(DataSnapshot dn : snapshot.getChildren()) {
//                        //  for (DataSnapshot dnsnaspshot : dn.getChildren()) {
//                        //    for (DataSnapshot snaspshotDn : dnsnaspshot.getChildren()) {
//                        DiseasePrediction dp = dn.getValue(DiseasePrediction.class);
//                        String pid = dp.getPid();
//                        if (pid.equals(approvalId)) {
//                            int appstatus = dp.getApprovalstatus();
//                            if (appstatus == 1)
//                            {
//                                imageicon.setVisibility(View.VISIBLE);
//                            }
//                            else
//                            {
//                                imageicon.setVisibility(View.GONE);
//                                //       Toast.makeText(predictiondetails.this, "There predition approval is cancelled!", Toast.LENGTH_SHORT).show();
//                            }
//
//                            //   }
//                            //  }
//                        }
//                    }
//
//                    //   }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        /*reference_booking = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        reference_book=FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Appointment");*/

    }

}