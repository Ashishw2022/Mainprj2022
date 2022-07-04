package com.example.vcare.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.admin.adapter.Admin_All_Doctor_Adapter;
import com.example.vcare.doctor.Doctors_Profile;
import com.example.vcare.doctor.Session_Mangement;
import com.example.vcare.register.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;




public class Doctor_admin_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rv;
    private TextView textView_appointments;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private DatabaseReference reference_doc,patient;
    private int flag;
    private String encodedemail;
    private Admin_All_Doctor_Adapter adapter ;
    private EditText search;
    private Toast backToast;
    private long backPressedTime;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private List<Doctors_Profile> listData;
    private List<String> emaildata;
    private int studentCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);

        search= findViewById(R.id.editTextSearch_appointment);
        Session_Mangement _session_mangement = new Session_Mangement(this);
        encodedemail= _session_mangement.getDoctorSession()[0].replace(".",",");
        rv=(RecyclerView)findViewById(R.id.recycler_doc);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        listData=new ArrayList<>();
        emaildata=new ArrayList<>();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        textView_appointments=findViewById(R.id.textView_appointments);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        final DatabaseReference nm= FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        nm.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                studentCounter = studentCounter + 1;

                //Convert counter to string
                String strCounter = String.valueOf(studentCounter);

                //Showing the user counter in the textview
                textView_appointments.setText("Available Doctors : " +strCounter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listData=new ArrayList<>();
                emaildata=new ArrayList<>();
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        String email=npsnapshot.getKey();
                        email = email.replace(",",".");
                        Doctors_Profile l=npsnapshot.getValue(Doctors_Profile.class);
                            listData.add(l);
                            emaildata.add(email);
                    }

                    adapter=new Admin_All_Doctor_Adapter(listData,emaildata, Doctor_admin_Activity.this);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, Admin_Dashboard.class));
                break;
            case R.id.appointment_doc:
                startActivity(new Intent(this, Admin_Available_Appointments.class));
                break;

            case R.id.Add_doc:
                startActivity(new Intent(Doctor_admin_Activity.this, Doctors_Add_Admin.class));
                break;
            case R.id.feedback_doc:
                startActivity(new Intent(Doctor_admin_Activity.this, Admin_FeedBack.class));
                break;
            case R.id.logout:
                Session_Mangement _session_mangement = new Session_Mangement(Doctor_admin_Activity.this);
                _session_mangement.removeSession();
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(Doctor_admin_Activity.this, Login.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void  filter(String text){

        ArrayList<Doctors_Profile> filterdNames = new ArrayList<>();
        for (Doctors_Profile doc_data: listData) {
            //if the existing elements contains the search input
            if (doc_data.getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(doc_data);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
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
