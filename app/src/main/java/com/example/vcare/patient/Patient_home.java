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

import com.example.vcare.R;
import com.example.vcare.admin.Admin_Dashboard;
import com.example.vcare.doctor.Doctors_Session_Mangement;
import com.example.vcare.predictor.PatientDashboard;
import com.example.vcare.register.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Patient_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawerLayout4;
    private Toolbar toolbar;
    private LinearLayout search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        drawerLayout4 = findViewById(R.id.pathomedraw);
        NavigationView navigationView1 = findViewById(R.id.patnav_view);
        Toolbar toolbar2= findViewById(R.id.toolbar);
        search=findViewById(R.id.home_search_view);
        setSupportActionBar(toolbar);

        navigationView1.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout4, toolbar2, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout4.addDrawerListener(toggle);
        toggle.syncState();
        navigationView1.setNavigationItemSelectedListener(Patient_home.this);
        navigationView1.setCheckedItem(R.id.nav_home);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_home.this,PatientDashboard.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, Patient_home.class));
                break;
            case R.id.doctors:
                Intent intent=new Intent(Patient_home.this,Available_Doctors.class);
                intent.putExtra("flag",0+"");
                startActivity(intent);
                break;
            case R.id.appointments:
                startActivity(new Intent(Patient_home.this, Patient_Appointments.class));
                break;
            case R.id.chats:
                //startActivity(new Intent(Patient.this, Patient_Chat_Display.class));
                break;
            case R.id.diseasepred:
                startActivity(new Intent(Patient_home.this, PatientDashboard.class));
                break;
            case R.id.logout:
                Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(Patient_home.this);
                doctors_session_mangement.removeSession();
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(Patient_home.this, Login.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.speciality_view_all:
                startActivity(new Intent(Patient_home.this,View_All_Speciality.class));
                break;

        }
        drawerLayout4.closeDrawer(GravityCompat.START);
        return true;
    }
}