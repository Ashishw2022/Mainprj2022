package com.example.vcare.patient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vcare.R;
import com.example.vcare.appointments.Booking_Appointments;
import com.example.vcare.doctor.Appointment_details;
import com.example.vcare.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Patient_Appointment_Status extends AppCompatActivity {

    private String check,date_val,fees,phone,email,chosen_time,question_data,pname,slot_val,fees_val,bookemail_id;
    private TextView name;
    private EditText tid;
    private DatabaseReference reference_user, reference_doctor, reference_booking, reference_patient, reference_details, reference_doctor_appt, reference_appointment;
    private Button pay_app;
    private TextView timeslot;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_apppointment_status);
        firebaseAuth=FirebaseAuth.getInstance();
        reference_doctor = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        reference_doctor_appt = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Appointments");
        reference_booking = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        reference_patient = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Chosen_Slots");
        reference_details = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Details");
        reference_appointment = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Appointment");
        Patient_Session_Management session = new Patient_Session_Management(Patient_Appointment_Status.this);
        bookemail_id = session.getSession();
        pname = getIntent().getSerializableExtra("pname").toString();
        email = getIntent().getSerializableExtra("email").toString();
        chosen_time = getIntent().getSerializableExtra("chosen_time").toString();
        question_data = getIntent().getSerializableExtra("question").toString();
        slot_val = getIntent().getSerializableExtra("slot_val").toString();
        date_val = getIntent().getSerializableExtra("date").toString();
        fees_val = getIntent().getSerializableExtra("fees").toString();
        check = getIntent().getSerializableExtra("check").toString();
        name = (TextView) findViewById(R.id.name);
        timeslot = (TextView) findViewById(R.id.timeslot);
        name.setText(pname);
        timeslot.setText(chosen_time );
        firebaseUser=firebaseAuth.getCurrentUser();
        Users obj=new Users();


        String finalSlot_val = slot_val;

        reference_booking.child(email).child(date_val).child(slot_val).child("Count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int count = snapshot.getValue(Integer.class);
                    count = count + 1;
                    Booking_Appointments booking_appointments = new Booking_Appointments(1, bookemail_id);
                    reference_booking.child(email).child(date_val).child(slot_val).child(check).setValue(booking_appointments);
                    reference_booking.child(email).child(date_val).child(finalSlot_val).child("Count").setValue(count);
                    Patient_Chosen_Slot_Class patient = new Patient_Chosen_Slot_Class(chosen_time, 0, question_data, pname, 0,"");
                    reference_patient.child(email).child(date_val).child(chosen_time).setValue(patient);
                    reference_doctor.child(email).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String dname = snapshot.getValue(String.class);
                                Appointment_details payment = new Appointment_details("transactionid", dname, email.replace(".",","),firebaseUser.getEmail().replace(".",","),"", pname, 0, date_val, chosen_time, 0);
                                reference_appointment.child("waiting_approval").child(firebaseUser.getEmail().replace(".",",")).child(date_val).child(chosen_time).setValue(payment);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else{
                    Toast.makeText(Patient_Appointment_Status.this, "The Doctor is not available! Select other slot with the same transaction ID!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Patient_Appointment_Status.this, Patient_Booking_Appointments.class);
                    intent.putExtra("Email ID", email);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return;
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//                reference_doctor.child(email).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()){
//                            String dname = snapshot.getValue(String.class);
//                            Admin_Payment_Class payment = new Admin_Payment_Class(transactionid, dname, email, bookemail_id, pname, 0, date_val, chosen_time, 0);
//                            reference_payment.child("waiting_approval").child(bookemail_id).child(date_val).child(chosen_time).setValue(payment);
//                            reference_booking.child(email).child(date_val).child(finalSlot_val).child("Count").addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    int count = snapshot.getValue(Integer.class);
//                                    count = count+1;
//                                    reference_booking.child(email).child(date_val).child(finalSlot_val).child("Count").setValue(count);
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//                            Toast.makeText(Patient_Payment_Appt.this, "Payment Done! Please Wait for Confirmation!", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(Patient_Payment_Appt.this, Patient.class));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Booking_Appointments booking_appointments = new Booking_Appointments(0, "null");
        reference_booking.child(email).child(date_val).child(slot_val).child(check).setValue(booking_appointments);
        startActivity(new Intent(Patient_Appointment_Status.this, Patient.class));
    }
}
