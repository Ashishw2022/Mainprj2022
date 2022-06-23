package com.example.vcare.patient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vcare.R;
import com.example.vcare.doctor.Doctor_Images;
import com.example.vcare.doctor.Doctors;
import com.example.vcare.doctor.Doctors_Session_Mangement;
import com.example.vcare.doctor.Main_Specialisation;
import com.example.vcare.doctor.SliderAdapter;
import com.example.vcare.doctor.Slider_Data;
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
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Patient extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvname;
    String patemail;
    private DatabaseReference reference_user_details;
    private Toast backToast;
    private long backPressedTime;
    private DrawerLayout drawerLayout1;
    FirebaseAuth mauth;
    private CircleImageView mImageView;
    private TextView mName;
    private Doctor_Images doctor_images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        tvname=findViewById(R.id.doc_name);

        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(this);
        patemail = doctors_session_mangement.getDoctorSession()[0].replace(".",",");

        RecyclerView recyclerView_spec = (RecyclerView) findViewById(R.id.recycler_spec);
        ImageView all_doctors = (ImageView) findViewById(R.id.imageView_doc);
        SliderView sliderView = findViewById(R.id.slider);

        drawerLayout1 = findViewById(R.id.drawer_layout1);
        NavigationView navigationView1 = findViewById(R.id.nav_view1);
        Toolbar toolbar1 = findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar1);
        mName   = (TextView)navigationView1.getHeaderView(0).findViewById(R.id.lpat_name);
        mImageView = (CircleImageView) navigationView1.getHeaderView(0).findViewById(R.id.pat_img);;

        navigationView1.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar1, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout1.addDrawerListener(toggle);
        toggle.syncState();
        navigationView1.setNavigationItemSelectedListener(Patient.this);
        navigationView1.setCheckedItem(R.id.nav_home);

        reference_user_details = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User_Data");

//        reference_user_details.child(patemail).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                //Fetch values from you database child and set it to the specific view object.
//                mName.setText(dataSnapshot.child("name").getValue().toString());
////                String link =dataSnapshot.child("doc_pic").getValue().toString();
////                Picasso.with(getBaseContext()).load(link).into(mImageView);
//                doctor_images = dataSnapshot.child("doc_pic").getValue(Doctor_Images.class);
//                //sign_images = datasnapshot.child("sign_pic").getValue(Doctor_Images.class);
//                if(doctor_images != null) {
//                    Picasso.with(Patient.this).load(doctor_images.getUrl()).into(mImageView);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        Integer [] specialisation_pic={R.drawable.infectious,R.drawable.dermavenereolepro,R.drawable.skin,R.drawable.diabetes,
                                        R.drawable.thyroid,R.drawable.hormone, R.drawable.immunology, R.drawable.rheuma, R.drawable.neuro, R.drawable.ophtha, R.drawable.cardiac, R.drawable.cancer,
                                        R.drawable.gastro, R.drawable.ent};

        String[] specialisation_type={"Infectious Disease","Dermatology & Venereology","Leprology","Endocrinology & Diabetes","Thyroid","Hormone","Immunology","Rheumatology","Neurology","Ophthalmology","Cardiac Sciences","Cancer Care / Oncology","Gastroenterology, Hepatology & Endoscopy","Ear Nose Throat"};

        ArrayList<Main_Specialisation> main_specialisations = new ArrayList<>();

        for(int i=0;i<specialisation_pic.length;i++){
           Main_Specialisation specialisation=new Main_Specialisation(specialisation_pic[i],specialisation_type[i]);
           main_specialisations.add(specialisation);

        }

        //design horizontal layout
        LinearLayoutManager layoutManager_spec=new LinearLayoutManager(
                Patient.this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView_spec.setLayoutManager(layoutManager_spec);
        recyclerView_spec.setItemAnimator(new DefaultItemAnimator());

        Specialist_Adapter specialist_adapter = new Specialist_Adapter(main_specialisations, Patient.this);
        recyclerView_spec.setAdapter(specialist_adapter);

        all_doctors.setOnClickListener(v -> {
            Intent intent=new Intent(Patient.this,Available_Doctors.class);
            intent.putExtra("flag",0+"");
            startActivity(intent);
        });

        Integer [] sliderDataArrayList={R.drawable.image1,R.drawable.image2,R.drawable.image3};

        ArrayList<Slider_Data> slider_data = new ArrayList<>();

        for (Integer integer : sliderDataArrayList) {

            Slider_Data slider_data_arr = new Slider_Data(integer);
            slider_data.add(slider_data_arr);
        }

        SliderAdapter adapter = new SliderAdapter(this, slider_data);

        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        sliderView.setSliderAdapter(adapter);

        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();

    }



    @Override
    public void onBackPressed() {

        if(drawerLayout1.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout1.closeDrawer(GravityCompat.START);
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



    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, Patient_home.class));
                break;
            case R.id.doctors:
                Intent intent=new Intent(Patient.this,Available_Doctors.class);
                intent.putExtra("flag",0+"");
                startActivity(intent);
                break;
            case R.id.profile:
                startActivity(new Intent(Patient.this, Patient_viewprofile.class));
                break;
            case R.id.appointments:
                startActivity(new Intent(Patient.this, Patient_Appointments.class));
                break;
            case R.id.news:
                startActivity(new Intent(Patient.this, news.class));
                break;
            case R.id.diseasepred:
               startActivity(new Intent(Patient.this, PatientDashboard.class));
                break;
            case R.id.logout:
                Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(Patient.this);
                doctors_session_mangement.removeSession();
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(Patient.this, Login.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.speciality_view_all:
                startActivity(new Intent(Patient.this,View_All_Speciality.class));
                break;

        }
        drawerLayout1.closeDrawer(GravityCompat.START);
        return true;
    }
}
