package com.example.vcare.doctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.appointments.Appointment_notif;
import com.example.vcare.appointments.Appointments_Adapter;
import com.example.vcare.appointments.Retrieve_Appointments;
import com.example.vcare.chat.Doctor_Chat_Display;
import com.example.vcare.news.news;
import com.example.vcare.register.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctors extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button add_doc, choose_slots;
    private View navView;
    private RecyclerView rv;
    private Appointments_Adapter adapter ;
    private FirebaseUser user;
    private ArrayList<Retrieve_Appointments> appointments;
    private ArrayList<Appointment_notif> appointment_notifs;
    private ProgressBar progressBar;
    private DatabaseReference reference_doc,patient;
    private int flag;
    private String encodedemail;
    private EditText search;
    private Toast backToast;
    private long backPressedTime;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DatabaseReference reference, reference_doctor;
    private ArrayList<Appointment_notif> current_appt;
    private String email;
    private Date d1, d2;
    private TextView mName;
    private CircleImageView mImageView;
    private Doctor_Images doctor_images;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        search= findViewById(R.id.editTextSearch_appointment);
        add_doc=findViewById(R.id.add_doctor);
        appointment_notifs=new ArrayList<>();
        appointments=new ArrayList<>();

        Session_Mangement _session_mangement = new Session_Mangement(this);
        email = _session_mangement.getDoctorSession()[0].replace(".",",");
        //navView = navigationView.inflateHeaderView(R.layout.header_doctor);
       // NavProfileImage = (CircleImageView)findViewById(R.id.doc_img);
        //mName = (TextView)navView.findViewById(R.id.ldoc_name);

        rv=(RecyclerView)findViewById(R.id.recycler_available_appointments);
        final DatabaseReference nm= FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        patient= FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Chosen_Slots");
        reference_doctor = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        reference_doctor.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    startActivity(new Intent(Doctors.this, Doctors_Update_Profile.class));
                    Toast.makeText(Doctors.this, "Please Update your Profile First", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//                 }if (dataSnapshot.hasChild("doc_pic")) {
//                    doctor_images = dataSnapshot.child("doc_pic").getValue(Doctor_Images.class);
//                    //String image = dataSnapshot.child("doc_pic").getValue().toString();
//                    Picasso.with(Doctors.this).load(doctor_images.getUrl()).into(NavProfileImage);
//
//
//                }else {
//                    Toast.makeText(Doctors.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();






        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mName   = (TextView)navigationView.getHeaderView(0).findViewById(R.id.ldoc_name);
        mImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.doc_img);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        current_appt = new ArrayList<>();
        reference = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Appointments");
        String[] monthName = {"Jan", "Feb",
                "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov",
                "Dec"};
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        String month = monthName[cal.get(Calendar.MONTH)];
        int year = cal.get(Calendar.YEAR);
        String value = day + " " + month + " " + year;
        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            d1 = sdformat.parse(day+"-"+month+"-"+year);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        reference_doctor.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Fetch values from you database child and set it to the specific view object.
                mName.setText(dataSnapshot.child("name").getValue().toString().toUpperCase());
//                String link =dataSnapshot.child("doc_pic").getValue().toString();
//                Picasso.with(getBaseContext()).load(link).into(mImageView);
                doctor_images = dataSnapshot.child("doc_pic").getValue(Doctor_Images.class);
                //sign_images = datasnapshot.child("sign_pic").getValue(Doctor_Images.class);
                if(doctor_images != null) {
                    Picasso.with(Doctors.this).load(doctor_images.getUrl()).into(mImageView);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    current_appt = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String value_2 = dataSnapshot.getKey();
                        value_2 = value_2.replace(" ", "-");
                        try {
                            d2 = sdformat.parse(value_2);
                            if (d1.compareTo(d2) <= 0) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Appointment_notif appointment_notif = dataSnapshot1.getValue(Appointment_notif.class);
                                    if(appointment_notif.getAppointment_text().equals("1")) {
                                        current_appt.add(appointment_notif);
                                    }
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                    adapter = new Appointments_Adapter(current_appt, Doctors.this);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
    }


    private void filter(String text) {
        ArrayList<Appointment_notif> filterdNames = new ArrayList<>();
        int counter=0;
        for(Appointment_notif obj: current_appt){
            counter=counter+1;
            if(obj.getDate().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(obj);
            }
        }
        adapter.filterList(filterdNames);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, Doctors.class));
                break;
            case R.id.profile:
                startActivity(new Intent(this, Doctors_View_Profile.class));
                break;
            case R.id.slots:
                startActivity(new Intent(this, Doctor_ChooseSlots.class));
                break;
            case R.id.appointment:
                startActivity(new Intent(this, Doctor_appointment.class));
                break;
            case R.id.predico:
                startActivity(new Intent(this, Predico_detailview.class));
                break;
            case R.id.chats:
                startActivity(new Intent(Doctors.this, Doctor_Chat_Display.class));
                break;
            case R.id.news:
                startActivity(new Intent(Doctors.this, news.class));
                break;
            case R.id.logout:
                Session_Mangement _session_mangement = new Session_Mangement(Doctors.this);
                _session_mangement.removeSession();
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(Doctors.this, Login.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                 break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            if(backPressedTime+2000>System.currentTimeMillis())
            {
                finishAffinity();
                backToast.cancel();
                super.onBackPressed();
                return;
            }
            else
            {
                backToast = Toast.makeText(getBaseContext(),"Press Back again to exit",Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }

}
