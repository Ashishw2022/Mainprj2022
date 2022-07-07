package com.example.vcare.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vcare.R;
import com.example.vcare.chat.Patient_Chat_Display;
import com.example.vcare.doctor.Doctor_Images;
import com.example.vcare.doctor.Session_Mangement;
import com.example.vcare.doctor.View_All_Speciality;
import com.example.vcare.news.news;
import com.example.vcare.predictor.PatientDashboard;
import com.example.vcare.register.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Patient_predico extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawerLayout4;
    private Toolbar toolbar;
    private LinearLayout search;
    private TextView mName;
    private CircleImageView mImageView;
    private DatabaseReference reference_user_details;
    private String patemail;
    private Doctor_Images doctor_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        drawerLayout4 = findViewById(R.id.pathomedraw);
        NavigationView navigationView1 = findViewById(R.id.patnav_view);
        Toolbar toolbar2= findViewById(R.id.toolbar);
        search=findViewById(R.id.home_search_view);
        setSupportActionBar(toolbar);
        mName   = (TextView)navigationView1.getHeaderView(0).findViewById(R.id.lpat_name);
        mImageView = (CircleImageView) navigationView1.getHeaderView(0).findViewById(R.id.pat_img);
        Session_Mangement _session_mangement = new Session_Mangement(this);
        patemail = _session_mangement.getDoctorSession()[0].replace(".",",");

        navigationView1.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout4, toolbar2, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout4.addDrawerListener(toggle);
        toggle.syncState();
        navigationView1.setNavigationItemSelectedListener(Patient_predico.this);
        navigationView1.setCheckedItem(R.id.nav_home);
        reference_user_details = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User_data");

        reference_user_details.child(patemail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Fetch values from you database child and set it to the specific view object.
                mName.setText(dataSnapshot.child("name").getValue().toString());
//                String link =dataSnapshot.child("doc_pic").getValue().toString();
//                Picasso.with(getBaseContext()).load(link).into(mImageView);
                doctor_images = dataSnapshot.child("doc_pic").getValue(Doctor_Images.class);
                //sign_images = datasnapshot.child("sign_pic").getValue(Doctor_Images.class);
                if(doctor_images != null) {
                    Picasso.with(Patient_predico.this).load(doctor_images.getUrl()).into(mImageView);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_predico.this,PatientDashboard.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, Patient.class));
                break;
            case R.id.doctors:
                Intent intent=new Intent(this,Available_Doctors.class);
                intent.putExtra("flag",0+"");
                startActivity(intent);
                break;
            case R.id.profile:
                startActivity(new Intent(this, Patient_viewprofile.class));
                break;
            case R.id.appointments:
                startActivity(new Intent(this, Patient_Appointments.class));
                break;
            case R.id.news:
                startActivity(new Intent(this, news.class));
                break;
            case R.id.chats:
                startActivity(new Intent(this, Patient_Chat_Display.class));
                break;
            case R.id.diseasepred:
                startActivity(new Intent(this, PatientDashboard.class));
                break;
            case R.id.predstatus:
                startActivity(new Intent(this, Patient_Prediction_Status_ListView.class));
                break;
            case R.id.logout:
                Session_Mangement _session_mangement = new Session_Mangement(this);
                _session_mangement.removeSession();
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(this, Login.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.speciality_view_all:
                startActivity(new Intent(this, View_All_Speciality.class));
                break;


        }
        drawerLayout4.closeDrawer(GravityCompat.START);
        return true;
    }
}