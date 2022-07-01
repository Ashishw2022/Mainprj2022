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
import com.example.vcare.predictor.DiseasePrediction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


public class Doctor_prediction_approved extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseUser user;
    private DatabaseReference reference,referencedoc;
    private ArrayList<DiseasePrediction> previous_prediction;
    private String email,demail;
    private Date d1, d2;
    private Doctor_Prediction_Show_Adapter adapter;
    private EditText search;
    private Context mcontext;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    public Doctor_prediction_approved() {

    }

    public static Doctor_prediction_approved getInstance() {
        Doctor_prediction_approved previousFragment = new Doctor_prediction_approved();

        return previousFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext = context;
    }


  //  @Nullable
 //   @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(getContext());
//        demail = doctors_session_mangement.getDoctorSession()[0].replace(".",",");
//        View view = inflater.inflate(R.layout.row_previous, container, false);
//        user=firebaseAuth.getCurrentUser();
//        search = (EditText) view.findViewById(R.id.editTextSearch_previous);
//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//
//            }
//        });
//
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        previous_prediction = new ArrayList<>();
//
//
//        referencedoc = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
//        //doctor approved appointment can be view fragment
//        reference = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("disease_prediction");
//     //   referencedoc.
//        referencedoc.child(demail).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                previous_prediction = new ArrayList<DiseasePrediction>();
//                Doctors_Profile doctors_Profile = snapshot.getValue(Doctors_Profile.class);
//                reference.child("Doctor_verified").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                                for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
//
//                                    DiseasePrediction diseasePrediction = snapshot2.getValue(DiseasePrediction.class);
//                                    if (diseasePrediction.getDiscategory().equals(doctors_Profile.getType())) {
//                                        previous_prediction.add(diseasePrediction);
//                                    }
//
//                                }
//                            }
//                            adapter = new Doctor_Prediction_Show_Adapter(previous_prediction);
//                            recyclerView.setAdapter(adapter);
//                        }else {
//                            if (mcontext != null) {
//                                Toast.makeText(getActivity(), "There are no verifed predictions!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//                });
//                }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//        return view;
//    }

//    private void filter(String text) {
//
//        ArrayList<DiseasePrediction> filterdNames = new ArrayList<>();
//        for (DiseasePrediction data : previous_prediction) {
//            //if the existing elements contains the search input
//
//        }
//        adapter.filterList(filterdNames);
//    }
}
