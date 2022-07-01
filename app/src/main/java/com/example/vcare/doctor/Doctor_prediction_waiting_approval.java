package com.example.vcare.doctor;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.appointments.Appointment_notif;
import com.example.vcare.appointments.Booking_Appointments;
import com.example.vcare.patient.Patient_Details;
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
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Doctor_prediction_waiting_approval extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseUser user;
    private DatabaseReference reference,doctorDataReference, reference_user, reference_doctor, reference_booking, reference_patient, reference_details, reference_doctor_appt;
    private ArrayList<DiseasePrediction> current_appoint;
    private ArrayList<DataSnapshot> data_appoint;
    private String email ,demail;
    private Date d1, d2;
    private Doctor_Prediction_Show_Adapter adapter;
    private EditText search;
    private Context mcontext;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public Doctor_prediction_waiting_approval() {

    }

    public static Doctor_prediction_waiting_approval getInstance() {

        Doctor_prediction_waiting_approval currentFragment = new Doctor_prediction_waiting_approval();
        return currentFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext = context;
    }


//    private void senddocmail(String rvmai,String msg) {
//        try {
//            //add current email
//            final String frommail = "ashishvwprj@gmail.com";
//            final String fpass = "gqezrowmplvieddv";
//            String subject = "Appointment Status";
//            String message=msg;
//            String stringHost = "smtp.gmail.com";
//
//            Properties properties = System.getProperties();
//
//            properties.put("mail.smtp.host", stringHost);
//            properties.put("mail.smtp.port", "465");
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.auth", "true");
//
//            Session session = Session.getInstance(properties,
//                    new javax.mail.Authenticator() {
//                        @Override
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(frommail,fpass);
//                        }
//                    });
//            MimeMessage mimeMessage = new MimeMessage(session);
//            mimeMessage.setFrom(new InternetAddress(frommail));
//            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(rvmai));
//            mimeMessage.setSubject(subject);
//            mimeMessage.setText(message);
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Transport.send(mimeMessage);
//                    } catch (MessagingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thread.start();
//
//        } catch (AddressException e) {
//            e.printStackTrace();
//        }
//        catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(getContext());
        demail = doctors_session_mangement.getDoctorSession()[0].replace(".",",");
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.row_current, container, false);
        user=firebaseAuth.getCurrentUser();
//        search = (EditText) view.findViewById(R.id.editTextSearch_current);
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

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        current_appoint = new ArrayList<>();
        data_appoint = new ArrayList<>();
       reference = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("disease_prediction");
        doctorDataReference = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctor_Data");

        doctorDataReference.child(demail).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    current_appoint = new ArrayList<DiseasePrediction>();
                    Doctors_Profile doctors_Profile = snapshot.getValue(Doctors_Profile.class);
                    reference.child("pending").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                                        DiseasePrediction diseasePrediction = snapshot2.getValue(DiseasePrediction.class);
                                        if (diseasePrediction.getDiscategory().equals(doctors_Profile.getType())) {
                                            current_appoint.add(diseasePrediction);
                                        }

                                    }
                                }
                                adapter = new Doctor_Prediction_Show_Adapter(current_appoint);
                                recyclerView.setAdapter(adapter);
                                ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                                    @Override
                                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                        return false;
                                    }


                                    @Override
                                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                        new AlertDialog.Builder(viewHolder.itemView.getContext())
                                                .setMessage("Do you want to mark this prediction as approved as verified? or Cancel this prediction")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        int position = viewHolder.getAdapterPosition();
                                                        DataSnapshot data = data_appoint.get(position);
                                                        DiseasePrediction disepred = current_appoint.get(position);
                                                        reference.child("pending").child(demail).addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                if(snapshot.exists()){
                                                                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                                        for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                                                                            String date = snapshot2.getKey();
                                                                            DiseasePrediction dieaseprediction = snapshot1.getValue(DiseasePrediction.class);
                                                                            reference.child("Doctor_verified").child(demail).child(date).setValue(disepred);
                                                                            reference.child("Doctor_verified").child(demail).child(date).child("approvalstatus").setValue(1);
                                                                            data.getRef().removeValue();
                                                                        }
                                                                    }
                                                                    }
                                                                }


                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                                    }
                                                })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        int position = viewHolder.getAdapterPosition();
                                                        DataSnapshot data = data_appoint.get(position);
                                                        DiseasePrediction disepred = current_appoint.get(position);
                                                        reference.child("pending").child(demail).addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                if (snapshot.exists()) {
                                                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                                        for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                                                                            String date = snapshot2.getKey();
                                                                            DiseasePrediction dieaseprediction = snapshot1.getValue(DiseasePrediction.class);
                                                                            reference.child("Doctor_verified").child(demail).child(date).setValue(dieaseprediction);
                                                                            reference.child("Doctor_verified").child(demail).child(date).child("approvalstatus").setValue(2);
                                                                            data.getRef().removeValue();
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                                    }
                                                })
                                                .create()
                                                .show();

                                    }
                                };
                                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
                                itemTouchHelper.attachToRecyclerView(recyclerView);
                            } else {
                                if (mcontext != null) {
                                    Toast.makeText(mcontext, "There are no Predictions created by user!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }


                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        return recyclerView;
    }

//    private void filter(String text) {
//
//        ArrayList<DiseasePrediction> filterdNames = new ArrayList<>();
//       /* for (DiseasePrediction data : current_appoint) {
//            //if the existing elements contains the search input
//           *//* if (data.getDate().toLowerCase().contains(text.toLowerCase())) {
//                //adding the element to filtered list
//                filterdNames.add(data);
//            }*//*
//        }*/
//        adapter.filterList(filterdNames);
//    }

}
