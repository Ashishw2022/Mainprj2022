package com.example.vcare.register;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vcare.R;
import com.example.vcare.admin.Admin_Dashboard;
import com.example.vcare.doctor.Doctor_Email_Id;
//import com.example.vcare.doctor.Doctors;
import com.example.vcare.doctor.Doctors;
import com.example.vcare.doctor.Doctors_Session_Mangement;
import com.example.vcare.model.Patient_email_id;
import com.example.vcare.patient.Patient;
import com.example.vcare.patient.Patient_Session_Management;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {


    private TextView register, forgotPassword,newuser;
    private EditText editTextEmailMain, editTextPasswordMain;

    private Button signIn;
    private int flag = 0;
    private FirebaseAuth myAuth;
    private String encoded_email;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        signIn = (Button) findViewById(R.id.loginButton);
        signIn.setOnClickListener(this);

        editTextEmailMain = (EditText) findViewById(R.id.emailMain);
        editTextPasswordMain = (EditText) findViewById(R.id.passwordMain);
        progressBar = (ProgressBar) findViewById(R.id.progressbarlogin);

        myAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView) findViewById(R.id.forgotPasswordText);
        forgotPassword.setOnClickListener(this);
        newuser = (TextView) findViewById(R.id.newuser);
        newuser.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        checkDoctorSession();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.loginButton:
                userLogin();
                break;
            case R.id.forgotPasswordText:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
            case R.id.newuser:
                startActivity(new Intent(this, Signup.class));
                break;
        }
    }

    private void userLogin() {
        String emailMain = editTextEmailMain.getText().toString().trim();
        String passwordMain = editTextPasswordMain.getText().toString().trim();
        encoded_email = EncodeString(emailMain);

        if (emailMain.isEmpty()) {
            editTextEmailMain.setError("Email is a required field !");
            editTextEmailMain.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailMain).matches()) {
            editTextEmailMain.setError("Please provide Valid Email !");
            editTextEmailMain.requestFocus();
            return;
        }
        if (passwordMain.isEmpty()) {
            editTextPasswordMain.setError("Password is a required field !");
            editTextPasswordMain.requestFocus();
            return;
        }
        if (passwordMain.length() < 6) {
            editTextPasswordMain.setError("Minimum length of password should be 6 characters !");
            editTextPasswordMain.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        myAuth.signInWithEmailAndPassword(emailMain, passwordMain).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        databaseReference.child(encoded_email).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String status = dataSnapshot.child("u_active").getValue(String.class);
                                    if(user!=null) {

                                        if(status.equals("1"))
                                    {

                                        String usertype = dataSnapshot.child("user_type").getValue(String.class);
                                        if (usertype.equals("Doctor")) {
                                            flag=0;
                                            /*if (flag == 0) {
                                                showChangePasswordDialog();
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }*/
                                            Doctor_Email_Id doctor_email_id = new Doctor_Email_Id(emailMain, "Doctor");
                                            Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(Login.this);
                                            doctors_session_mangement.saveDoctorSession(doctor_email_id);

                                            startActivity(new Intent(Login.this, Doctors.class));
                                            progressBar.setVisibility(View.INVISIBLE);
                                        } else if (usertype.equals("user")) {

                                            Doctor_Email_Id doctor_email_id = new Doctor_Email_Id(emailMain, "user");
                                            Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(Login.this);
                                            doctors_session_mangement.saveDoctorSession(doctor_email_id);
                                            Patient_email_id patient = new Patient_email_id(encoded_email);
                                            Patient_Session_Management session_management = new Patient_Session_Management(Login.this);
                                            session_management.saveSession(patient);
                                            startActivity(new Intent(Login.this, Patient.class));
                                            progressBar.setVisibility(View.INVISIBLE);
                                        } else {

                                            Doctor_Email_Id doctor_email_id = new Doctor_Email_Id(emailMain, "Admin");
                                            Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(Login.this);
                                            doctors_session_mangement.saveDoctorSession(doctor_email_id);
                                            startActivity(new Intent(Login.this, Admin_Dashboard.class));
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(Login.this, " Account Disabled  ", Toast.LENGTH_SHORT).show();

                                    }}
                                }
//
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        progressBar.setVisibility(View.INVISIBLE);

                     }else if (flag == 0) {
                        databaseReference.child(encoded_email).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String usertype = snapshot.child("user_type").getValue(String.class);
                                if(usertype.equalsIgnoreCase("Doctor")) {
                                    showChangePasswordDialog();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }else{
                                    user.sendEmailVerification();
                                    flag =3;
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }


                    else if(flag==2)
                    {
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                    else
                     {
                        user.sendEmailVerification();
                        progressBar.setVisibility(View.INVISIBLE);
//                        startActivity(new Intent(DoctorsLogin.this, ForgotPassword.class));
                        Toast.makeText(Login.this, "Check your Email to verify your account", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Failed to Login ! Please check your credentials", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void showChangePasswordDialog() {
        View view = LayoutInflater.from(Login.this).inflate(R.layout.update_password, null);
        final EditText passwordold = view.findViewById(R.id.passwordEt);
        final EditText passwordnew = view.findViewById(R.id.newpasswordEt);
        Button updatepass = view.findViewById(R.id.updatePasswordBtn);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = passwordold.getText().toString().trim();
                String newpass = passwordnew.getText().toString().trim();
                if (TextUtils.isEmpty(oldpass)) {
                    Toast.makeText(Login.this, "Enter Your Current Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newpass.length() < 6) {
                    Toast.makeText(Login.this, "Password Length must have atleast 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
                flag = 1;
                updatePassword(oldpass, newpass);
            }
        });
    }

    private void updatePassword(String oldpass, String newpass) {
        FirebaseUser user = myAuth.getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldpass);
        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        user.updatePassword(newpass)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Login.this, "Password Updated!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void checkDoctorSession() {

        Doctors_Session_Mangement doctors_session_mangement=new Doctors_Session_Mangement(Login.this);
        String isDoctorLoggedin[] =doctors_session_mangement.getDoctorSession();
        if(!isDoctorLoggedin[0].equals("-1")){
            moveToDoctorActivity();
        }

    }

    private void moveToDoctorActivity() {
        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(Login.this);
        String type[] = doctors_session_mangement.getDoctorSession();


        if(type[1].equals("Doctor")){
            Intent intent = new Intent(Login.this, Doctors.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (type[1].equals("Admin")){
            Intent intent = new Intent(Login.this, Admin_Dashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (type[1].equals("user")){
            Intent intent = new Intent(Login.this, Patient.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

}