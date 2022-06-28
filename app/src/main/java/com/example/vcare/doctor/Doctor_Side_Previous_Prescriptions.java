package com.example.vcare.doctor;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.patient.Get__Previous_Prescription_Details;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class Doctor_Side_Previous_Prescriptions extends AppCompatActivity {
    private RecyclerView rv;
    private String[] gender;
    private String email,dname;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference prescription_doctor;
    private ArrayAdapter<String> gender_adapter;
    private ArrayList<Get__Previous_Prescription_Details> presc;
    private com.example.vcare.doctor.Doctor_Previous_Prescription_Adapter adapter;

    String name,pemail,date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__side__previous__prescriptions);

        rv=findViewById(R.id.recycler_previous_prescription);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        presc = new ArrayList<>();

        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(this);
        email = doctors_session_mangement.getDoctorSession()[0].replace(".",",");
        name = getIntent().getStringExtra("pname");
        pemail = getIntent().getStringExtra("email");

        prescription_doctor = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Prescription_By_Doctor");
        prescription_doctor.child(email).child(pemail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presc = new ArrayList<>();
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        date=dataSnapshot.getKey();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            time=dataSnapshot1.getKey();
                            Get__Previous_Prescription_Details gpd= new Get__Previous_Prescription_Details(name,date,time,pemail);
                            presc.add(gpd);

                        }


                    }
                    adapter = new com.example.vcare.doctor.Doctor_Previous_Prescription_Adapter(presc, Doctor_Side_Previous_Prescriptions.this);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}