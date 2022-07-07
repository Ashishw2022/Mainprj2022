package com.example.vcare.patient;

import static com.airbnb.lottie.L.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vcare.R;
import com.example.vcare.appointments.Booking_Appointments;
import com.example.vcare.doctor.Doctor_Images;
import com.example.vcare.model.Users;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.hdodenhof.circleimageview.CircleImageView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Patient_Booking_Appointments extends AppCompatActivity implements PaymentResultListener {
    private String phone,email,date_val,chosen_time="",question_data,fees,bookemail_id,name,finalSlot_val,check,slot_val,pname;
    private TextView doctor_name, speciality;
    private HorizontalCalendar horizontalCalendar;
    private DatabaseReference reference_user, reference_doctor,reference_user_details, reference_booking, reference_patient, reference_details, reference_doctor_appt;
    private ArrayList<String> dates;
    private CircleImageView circle_image;
    private Doctor_Images doctor_images;
    private TextInputLayout time_layout;
    private AutoCompleteTextView time_view;
    private ArrayAdapter<String> time_adapter;
    private EditText question,patient_name, tid;
    private Button book_app;
    FirebaseUser firebaseUser;
    private Set<String> set_timeSlot;
    private TextView paymentLink;
    private boolean isresumed = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_book_appointment);
        circle_image = (CircleImageView) findViewById(R.id.profile_image);
        doctor_name = (TextView) findViewById(R.id.doctor_name);
        speciality = (TextView) findViewById(R.id.doctor_speciality);
        patient_name = (EditText) findViewById(R.id.name_patient_input);

        //tid = (EditText) findViewById(R.id.paymentsinput);
        reference_doctor = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        reference_doctor_appt = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Appointments");
        reference_user_details = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User_data");

        Checkout.preload(getApplicationContext());


        dates = new ArrayList<>();
        set_timeSlot=new HashSet<String>();
        time_layout = (TextInputLayout) findViewById(R.id.timeLayout);
        time_view = (AutoCompleteTextView) findViewById(R.id.timeview);
        question = (EditText) findViewById(R.id.questioninput);
        book_app = (Button) findViewById(R.id.book_button);
        //paymentLink = findViewById(R.id.linkPayment);
        email = getIntent().getSerializableExtra("Email ID").toString();
        email = email.replace(".", ",");
        Patient_Session_Management session = new Patient_Session_Management(Patient_Booking_Appointments.this);
        bookemail_id = session.getSession();
        isresumed = true;
        reference_user_details.child(bookemail_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    patient_name.setText(snapshot.child("name").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference_booking = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        reference_patient = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Chosen_Slots");
        reference_details = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Details");

        reference_doctor.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    doctor_name.setText(datasnapshot.child("name").getValue(String.class));
                    speciality.setText(datasnapshot.child("desc").getValue(String.class));
                    doctor_images = datasnapshot.child("doc_pic").getValue(Doctor_Images.class);
                    fees = datasnapshot.child("fees").getValue(String.class);
                    if (doctor_images != null) {
                        Picasso.with(Patient_Booking_Appointments.this).load(doctor_images.getUrl()).into(circle_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH,0);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        String[] monthName = {"Jan", "Feb",
                "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov",
                "Dec"};
        date_val = DateFormat.format("dd MMM yyyy", startDate).toString();
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.date_View)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        reference_booking.child(email).child(date_val).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dates = new ArrayList<>();
                chosen_time = "";
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (!(dataSnapshot.getKey().equals("Count"))) {
                            int start = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[0]);
                            int end = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[1]);
                            int start_time = start;
                            int slots = (end - start) * 2;
                            String date = "";
                            String check = "";
                            for (int i = 0; i < slots; i++) {
                                if (i % 2 == 0) {
                                    date = start + ":00 - " + start + ":30";
                                    check = start + ":00";
                                } else {
                                    date = start + ":30";
                                    check = date;
                                    start += 1;
                                    date = date + " - " + start + ":00";
                                }
                                String finalDate = date;
                                String complete_slot = start_time + " - " + end;
                                set_timeSlot.add(complete_slot);
                                reference_booking.child(email).child(date_val).child(complete_slot).child(check).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            Booking_Appointments booking = snapshot.getValue(Booking_Appointments.class);
                                            int val = booking.getValue();
                                            if (val == 0) {
                                                dates.add(finalDate);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }
                }
                else{
                    if(dates.size() == 0 && isIsresumed()) {
                        Toast.makeText(Patient_Booking_Appointments.this, "No Slots Available!", Toast.LENGTH_SHORT).show();
                    }
                }

                time_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_gender, dates);
                time_view.setAdapter(time_adapter);
                time_view.setThreshold(1);
                time_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        chosen_time = time_adapter.getItem(position);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                date_val = DateFormat.format("dd MMM yyyy", date).toString();
                chosen_time = "";
                time_view.setText("");
                reference_booking.child(email).child(date_val).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dates = new ArrayList<>();
                        set_timeSlot=new HashSet<String>();
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (!(dataSnapshot.getKey().equals("Count"))) {
                                    int start = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[0]);
                                    int end = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[1]);
                                    int start_time = start;
                                    int slots = (end - start) * 2;
                                    String date = "";
                                    String check = "";
                                    String complete_slot = start_time + " - " + end;
                                    set_timeSlot.add(complete_slot);
                                    for (int i = 0; i < slots; i++) {
                                        if (i % 2 == 0) {
                                            date = start + ":00 - " + start + ":30";
                                            check = start + ":00";
                                        } else {
                                            date = start + ":30";
                                            check = date;
                                            start += 1;
                                            date = date + " - " + start + ":00";
                                        }
                                        String finalDate = date;
                                        reference_booking.child(email).child(date_val).child(complete_slot).child(check).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    Booking_Appointments booking = snapshot.getValue(Booking_Appointments.class);
                                                    int val = booking.getValue();
                                                    if (val == 0) {
                                                        dates.add(finalDate);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }
                        }
                        else{
                            if(dates.size() == 0 && isIsresumed()) {
                                Toast.makeText(Patient_Booking_Appointments.this, "No Slots Available!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        time_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_gender, dates );
                        time_view.setAdapter(time_adapter);
                        time_view.setThreshold(1);
                        time_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                chosen_time = time_adapter.getItem(position);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        book_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chosen_time.equals("")){
                    Toast.makeText(Patient_Booking_Appointments.this, "Please Select the Time Slot!", Toast.LENGTH_SHORT).show();
                    return;
                }
                question_data = question.getText().toString().trim();

                 pname = patient_name.getText().toString().trim();

                if(pname.isEmpty()){
                    patient_name.setError("Patient's Name is a required field");
                    patient_name.requestFocus();
                    return;
                }

                check = chosen_time.split(" - ", 5)[0];
                Booking_Appointments booking_appointments = new Booking_Appointments(1, bookemail_id);
                 slot_val="";
                for(String item: set_timeSlot){
                    int s = Integer.parseInt(item.split(" - ",5)[0]);
                    int e = Integer.parseInt(item.split(" - ",5)[1]);
                    int t = Integer.parseInt(chosen_time.split(":",5)[0]);

                    if(s <= t && t < e){
                        slot_val=item;
                        break;
                    }
                }

                Booking_Appointments booking = new Booking_Appointments(1, bookemail_id);
                  finalSlot_val = slot_val;



//                Appointment_notif appointment_notif = new Appointment_notif("", date_val, chosen_time, question_data, bookemail_id, pname);
//                reference_doctor_appt.child(email).child(date_val).child(chosen_time).setValue(appointment_notif);
                startpayment();

            }
        });
    }
    @SuppressLint("RestrictedApi")
    private void startpayment()
    {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_120xIdwi4pRz5t");

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            int i=Integer.parseInt(fees);
            int tot=i*100;
            String mail=bookemail_id.replace(",",".");

            options.put("name", "V-Care");
            options.put("description", "Reference No. #1001");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
        //    options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", tot);//pass amount in currency subunits
            options.put("prefill.email","ashi@gmail.com");
           // options.put("prefill.contact","9988776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        isresumed = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        isresumed = false;
    }

    public boolean isIsresumed(){
        return isresumed;
    }

    @Override
    public void onPaymentSuccess(String s) {

        reference_booking.child(email).child(date_val).child(slot_val).child(check).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Booking_Appointments booking = snapshot.getValue(Booking_Appointments.class);

                    if (booking.getValue() == 1) {
                        Toast.makeText(Patient_Booking_Appointments.this, "The Slot is already Booked. Please Choose other Slot!", Toast.LENGTH_SHORT).show();
                        return;

                    } else {

                        Booking_Appointments booking_appointments = new Booking_Appointments(1, "null");
                        reference_booking.child(email).child(date_val).child(finalSlot_val).child(check).setValue(booking_appointments);
                        Toast.makeText(Patient_Booking_Appointments.this, "The Slot is Booked.", Toast.LENGTH_SHORT).show();
                        sendmail(bookemail_id.replace(",","."),"\nHello "+pname+
                                ",\n Thanks for booking an appointment on V-Care."+
                                "\nyour appointment is Booked on "+date_val+"|"+chosen_time+" and payment is confirmed please wait for doctor confirmation ");
                        Intent intent = new Intent(Patient_Booking_Appointments.this, Patient_Appointment_Status.class);
                        intent.putExtra("pname", pname);
                        intent.putExtra("email", email);
                        intent.putExtra("docname", String.valueOf(doctor_name));
                        intent.putExtra("speci", String.valueOf(speciality));
                        intent.putExtra("chosen_time", chosen_time);
                        intent.putExtra("question", question_data);
                        intent.putExtra("slot_val", finalSlot_val);
                        intent.putExtra("date", date_val);
                        intent.putExtra("fees", fees);
                        intent.putExtra("check", check);
                        intent.putExtra("paymentstatus", 1);

                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(Patient_Booking_Appointments.this, "The Doctor is not available! Select other slot!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Patient_Booking_Appointments.this, "Payment not done! ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void sendmail(String rvmai,String msg) {
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

    @Override
    public void onPaymentError(int i, String s) {

    }
}