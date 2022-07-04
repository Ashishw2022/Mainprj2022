package com.example.vcare.patient;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.doctor.Doctors_Profile;
import com.example.vcare.doctor.Session_Mangement;
import com.example.vcare.predictor.DiseasePrediction;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient_Status_ListView extends AppCompatActivity {
    private RecyclerView rv;
    private TextView textView_appointments;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private DatabaseReference reference_doc,patient;
    private int flag;
    private String encodedemail;
    private Patient_Status_Adapter adapter ;
    private EditText search;
    private Toast backToast;
    private long backPressedTime;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private List<DiseasePrediction> listData;
    private List<String> emaildata;
    private int studentCounter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_predico_detailview);
      //  search= findViewById(R.id.editTextSearch_appointment);
        Session_Mangement _session_mangement = new Session_Mangement(this);
        encodedemail= _session_mangement.getDoctorSession()[0].replace(".",",");
        rv=(RecyclerView)findViewById(R.id.recycler_prediction);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        listData=new ArrayList<>();
        emaildata=new ArrayList<>();
      //  drawerLayout = findViewById(R.id.drawer_layout);
     //   navigationView = findViewById(R.id.nav_view);
      //  toolbar = findViewById(R.id.toolbar);
        textView_appointments=findViewById(R.id.textView_prediction);
        final DatabaseReference doctors_reference = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        Map<String, Doctors_Profile> emailNewList = new HashMap<String,Doctors_Profile>();


        final DatabaseReference nm= FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("disease_prediction");
        nm.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                studentCounter = studentCounter + 1;

                //Convert counter to string
                String strCounter = String.valueOf(studentCounter);

                //Showing the user counter in the textview
                textView_appointments.setText("Predictions : " +strCounter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nm.child(encodedemail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listData=new ArrayList<>();
                emaildata=new ArrayList<>();
                if (dataSnapshot.exists()){
                  //  for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            DiseasePrediction l = snapshot.getValue(DiseasePrediction.class);
                           /// if (dp.getType().equals(l.getDiscategory())) {

                                listData.add(l);
                                emaildata.add(l.getPatientEmail());



                    }

                    adapter=new Patient_Status_Adapter(listData,emaildata, Patient_Status_ListView.this);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

}