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

public class Doctor_appointments_waiting_approval extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseUser user;
    private DatabaseReference reference, reference_user, reference_doctor, reference_booking, reference_patient, reference_details, reference_doctor_appt;
    private ArrayList<Appointment_details> current_appoint;
    private ArrayList<DataSnapshot> data_appoint;
    private String email;
    private Date d1, d2;
    private Doctor_Appointment_Show_Adapter adapter;
    private EditText search;
    private Context mcontext;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public Doctor_appointments_waiting_approval() {

    }

    public static Doctor_appointments_waiting_approval getInstance() {
        Doctor_appointments_waiting_approval currentFragment = new Doctor_appointments_waiting_approval();
        return currentFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext = context;
    }


    private void senddocmail(String rvmai,String msg) {
        try {
            final String frommail = "ashishvwprj@gmail.com";
            final String fpass = "gqezrowmplvieddv";
            String subject = "Appointment Status";
            String message=msg;
            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        @Override
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(frommail,fpass);
                        }
                    });
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(frommail));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(rvmai));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.row_current, container, false);
        user=firebaseAuth.getCurrentUser();
        search = (EditText) view.findViewById(R.id.editTextSearch_current);
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
        current_appoint = new ArrayList<>();
        data_appoint = new ArrayList<>();
       reference = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Appointment");
        reference_booking = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        reference_patient = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Chosen_Slots");
        reference_details = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Details");
        reference_doctor_appt = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Appointments");

        reference.child("waiting_approval").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    current_appoint = new ArrayList<>();
                    data_appoint = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            for (DataSnapshot snapshot3 : snapshot2.getChildren()) {
                                Appointment_details appoint_data = snapshot3.getValue(Appointment_details.class);
                                if(appoint_data.getEmail().equals(user.getEmail().replace(".",",")))
                                {
                                    current_appoint.add(appoint_data);
                                    data_appoint.add(snapshot3);
                                }

                            }
                        }
                    }
                    adapter = new Doctor_Appointment_Show_Adapter(current_appoint);
                    recyclerView.setAdapter(adapter);

                    ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            new AlertDialog.Builder(viewHolder.itemView.getContext())
                                    .setMessage("Do you want to mark this Appointment as Done? or Cancel this Appointment")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int position = viewHolder.getAdapterPosition();
                                            DataSnapshot data = data_appoint.get(position);
                                            Appointment_details appoint_class = current_appoint.get(position);
                                            //appointment approved by doctor
                                            reference.child("appointment_approved").child(appoint_class.getPemail()).child(appoint_class.getDate()).child(appoint_class.getTime()).setValue(appoint_class);
                                            reference.child("appointment_approved").child(appoint_class.getPemail()).child(appoint_class.getDate()).child(appoint_class.getTime()).child("flag").setValue(1);
                                            data.getRef().removeValue();
                                            Patient_Details details = new Patient_Details(appoint_class.getPemail().replace(".",","), appoint_class.getName());
                                            //patient email added
                                            String encoded_email = appoint_class.getEmail().replace(".", ",");
                                            reference_details.child(encoded_email).child(appoint_class.getPemail().replace(".",",")).setValue(details);
                                            reference_patient.child(encoded_email).child(appoint_class.getDate()).child(appoint_class.getTime()).child("flag").setValue(1);
                                            reference_patient.child(encoded_email).child(appoint_class.getDate()).child(appoint_class.getTime()).child("question").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String ques = " ";
                                                    if (snapshot.exists()) {
                                                        ques = snapshot.getValue(String.class);
                                                    }
                                                    Appointment_notif appointment_notif = new Appointment_notif("1", appoint_class.getDate(), appoint_class.getTime(), ques, appoint_class.getPhone(), appoint_class.getName(),appoint_class.getTransaction());
                                                    reference_doctor_appt.child(encoded_email).child(appoint_class.getDate()).child(appoint_class.getTime()).setValue(appointment_notif);
                                                    String check = appoint_class.getTime().split(" - ", 5)[0];
                                                    Booking_Appointments booking = new Booking_Appointments(1, appoint_class.getPhone());
                                                    reference_booking.child(encoded_email).child(appoint_class.getDate()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if (snapshot.exists()) {
                                                                String slot_val = "";
                                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                                    String item = dataSnapshot.getKey();
                                                                    int s = Integer.parseInt(item.split(" - ", 5)[0]);
                                                                    int e = Integer.parseInt(item.split(" - ", 5)[1]);
                                                                    int t = Integer.parseInt(appoint_class.getTime().split(":", 5)[0]);
                                                                    if (s <= t && t < e) {
                                                                        slot_val = item;
                                                                        break;
                                                                    }
                                                                }
                                                                reference_booking.child(encoded_email).child(appoint_class.getDate()).child(slot_val).child(check).child("email").setValue(appoint_class.getPemail());
                                                                senddocmail(appoint_class.getPemail().replace(",","."),"\nHello "+appoint_class.getName()+","+
                                                                        "\n Thanks for booking an appointment on V-Care."+
                                                                        "\nyour appointment is Approved by  doctor for time slot "+appoint_class.getTime()+" on "+appoint_class.getDate());
                                                            }
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
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int position = viewHolder.getAdapterPosition();
                                            DataSnapshot data = data_appoint.get(position);
                                            Appointment_details appoint_class = current_appoint.get(position);
                                            reference.child("appointment_approved").child(appoint_class.getPemail()).child(appoint_class.getDate()).child(appoint_class.getTime()).setValue(appoint_class);
                                            reference.child("appointment_approved").child(appoint_class.getPemail()).child(appoint_class.getDate()).child(appoint_class.getTime()).child("flag").setValue(2);
                                            data.getRef().removeValue();
                                            String encoded_email = appoint_class.getEmail().replace(".", ",");
                                            reference_patient.child(appoint_class.getPhone()).child(encoded_email).child(appoint_class.getDate()).child(appoint_class.getTime()).child("appointment").setValue(2);
                                            String check = appoint_class.getTime().split(" - ", 5)[0];
                                            Booking_Appointments booking = new Booking_Appointments(0, "null");
                                            reference_booking.child(encoded_email).child(appoint_class.getDate()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {
                                                        String slot_val = "";
                                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                            String item = dataSnapshot.getKey();
                                                            int s = Integer.parseInt(item.split(" - ", 5)[0]);
                                                            int e = Integer.parseInt(item.split(" - ", 5)[1]);
                                                            int t = Integer.parseInt(appoint_class.getTime().split(":", 5)[0]);
                                                            if (s <= t && t < e) {
                                                                slot_val = item;
                                                                break;
                                                            }
                                                        }
                                                        if (!(slot_val.equals(""))) {
                                                            reference_booking.child(encoded_email).child(appoint_class.getDate()).child(slot_val).child(check).setValue(booking);
                                                            String finalSlot_val = slot_val;
                                                            reference_booking.child(encoded_email).child(appoint_class.getDate()).child(slot_val).child("Count").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int count = snapshot.getValue(Integer.class);
                                                                    count = count - 1;
                                                                    reference_booking.child(encoded_email).child(appoint_class.getDate()).child(finalSlot_val).child("Count").setValue(count);
                                                                    senddocmail(appoint_class.getPemail().replace(",","."),"\nHello "+appoint_class.getName()+","+
                                                                            "\n Thanks for booking an appointment on V-Care."+
                                                                            "\nyour appointment is Declined by  doctor for time slot "+appoint_class.getTime()+" on "+appoint_class.getDate());

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
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
                        Toast.makeText(mcontext, "There are no Appointments!", Toast.LENGTH_SHORT).show();
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
        for (Appointment_details data : current_appoint) {
            //if the existing elements contains the search input
            if (data.getDate().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(data);
            }
        }
        adapter.filterList(filterdNames);
    }

}
