package com.example.vcare.doctor;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class Doctor_appointments_approved extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseUser user;
    private DatabaseReference reference;
    private ArrayList<Appointment_details> previous_payment;
    private String email;
    private Date d1, d2;
    private Doctor_Appointment_Show_Adapter adapter;
    private EditText search;
    private Context mcontext;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    public Doctor_appointments_approved() {

    }

    public static Doctor_appointments_approved getInstance() {
        Doctor_appointments_approved previousFragment = new Doctor_appointments_approved();

        return previousFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.row_previous, container, false);
        user=firebaseAuth.getCurrentUser();
        search = (EditText) view.findViewById(R.id.editTextSearch_previous);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        previous_payment = new ArrayList<>();

        reference = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Appointment");
        reference.child("appointment_approved").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    previous_payment = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                            for (DataSnapshot snapshot3 : snapshot2.getChildren()) {
                                Appointment_details payment_data = snapshot3.getValue(Appointment_details.class);
                                payment_data.getEmail();
                                 if(payment_data.getEmail().equals(user.getEmail().replace(".",",")))
                                {
                                    previous_payment.add(payment_data);
                                }

                            }
                        }
                   }
                    adapter = new Doctor_Appointment_Show_Adapter(previous_payment);
                    recyclerView.setAdapter(adapter);
                } else {
                    if (mcontext != null) {
                        Toast.makeText(getActivity(), "There are no Completed Appointments!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void filter(String text) {

        ArrayList<Appointment_details> filterdNames = new ArrayList<>();
        for (Appointment_details data : previous_payment) {
            //if the existing elements contains the search input
            if (data.getDate().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(data);
            }
        }
        adapter.filterList(filterdNames);
    }
}
