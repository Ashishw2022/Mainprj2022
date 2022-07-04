package com.example.vcare.chat;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.doctor.Session_Mangement;
import com.example.vcare.model.Chat;
import com.example.vcare.patient.Patient_Details;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Doctor_Chat_Display extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Doctor_UserAdapter doctorUserAdapter;
    private List<Patient_Details> mUsers;
    private DatabaseReference reference, reference_patient;
    private List<String> usersList, status_val;
    private FirebaseUser fuser;
    private String email;
    private HashSet<Patient_Details> hashSet;
    private ArrayList<String> p_email;
    private TextView chatview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_display_chats);
        chatview = (TextView) findViewById(R.id.textChat);
        recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        Session_Mangement _session_mangement = new Session_Mangement(this);
        email = _session_mangement.getDoctorSession()[0].replace(".", ",");
        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                int unread = 0;
                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Chat chat = snapshot1.getValue(Chat.class);

                        if (chat.getSender().equals(email)) {
                            usersList.add(chat.getReceiver());
                        }
                        if (chat.getReceiver().equals(email)) {
                            usersList.add(chat.getSender());
                        }
                        if (chat.getReceiver().equals(email) && !chat.isSeen()) {
                            unread++;
                        }
                    }
                    if (unread != 0) {

                        chatview.setText("Your Chats (" + unread + ")");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUsers = new ArrayList<>();
        p_email = new ArrayList<>();
        status_val = new ArrayList<>();
        reference_patient = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Details");
        reference_patient.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                p_email = new ArrayList<>();
                status_val = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Patient_Details details = snapshot1.getValue(Patient_Details.class);
                    mUsers.add(details);
                    p_email.add(details.getPemail());
                }

                reference_patient.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (String pemail : p_email) {
                            if (snapshot.child(pemail).child("status").exists()) {
                                status_val.add(snapshot.child(pemail).child("status").getValue(String.class));
                            }
                            else{
                                status_val.add("offline");
                            }
                        }

                        doctorUserAdapter = new Doctor_UserAdapter(Doctor_Chat_Display.this, mUsers, status_val, true);
                        recyclerView.setAdapter(doctorUserAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
