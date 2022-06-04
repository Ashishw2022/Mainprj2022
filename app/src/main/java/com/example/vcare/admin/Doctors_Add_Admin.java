package com.example.vcare.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vcare.R;
import com.example.vcare.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Doctors_Add_Admin extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextView registerText,registerUser;
    private EditText editTextFullName,editTextEmail,editTextPassword;
    private ProgressBar progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add_admin);

        progressBar=(ProgressBar) findViewById(R.id.progressbar_doc_add);
        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.registerUser);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                registerUser();

            }
        });


    }


    private void registerUser() {
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(fullName.isEmpty())
        {
            editTextFullName.setError("Full Name is a required field !");
            progressBar.setVisibility(View.INVISIBLE);
            editTextFullName.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            editTextEmail.setError("Email is a required field !");
            progressBar.setVisibility(View.INVISIBLE);
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please provide Valid Email !");
            progressBar.setVisibility(View.INVISIBLE);
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            editTextPassword.setError("Password is a required field !");
            progressBar.setVisibility(View.INVISIBLE);
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            editTextPassword.setError("Minimum length of password should be 6 characters !");
            progressBar.setVisibility(View.INVISIBLE);
            editTextPassword.requestFocus();
            return;
        }


        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Users user = new Users(fullName,email,"online", "Doctor","1","");
                            String encodedemail = EncodeString(email);
                            FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
                                    .child(encodedemail)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {    progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Doctors_Add_Admin.this,"Doctor has been registered successfully !",Toast.LENGTH_LONG).show();
//                                       mAuth.signOut();
//                                        startActivity(new Intent(Doctors_Add_Admin.this,DoctorsLogin.class));
                                        startActivity(getSendEmailIntent(Doctors_Add_Admin.this,email,"Doctor Added","your name is added as doctor"));
                                    }
                                    else
                                    {   progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Doctors_Add_Admin.this,"Failed to Register. Doctor already exists !",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else
                        {   progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Doctors_Add_Admin.this,"Failed to Register. Doctor already exists !",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public Intent getSendEmailIntent(Context context, String email, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        try {

            // Explicitly only use Gmail to send
            emailIntent.setClassName("com.google.android.gm",
                    "com.google.android.gm.ComposeActivityGmail");

            emailIntent.setType("text/html");

            // Add the recipients
            if (email != null)
                emailIntent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { email });

            if (subject != null)
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                        subject);

            if (body != null)
                emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));

            // Add the attachment by specifying a reference to our custom
            // ContentProvider
            // and the specific file of interest
            // emailIntent.putExtra(
            // Intent.EXTRA_STREAM,
            // Uri.parse("content://" + CachedFileProvider.AUTHORITY + "/"
            // + fileName));

            return emailIntent;
            //          myContext.startActivity(emailIntent);
        } catch (Exception e) {
            emailIntent.setType("text/html");

            // Add the recipients
            if (email != null)
                emailIntent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { email });

            if (subject != null)
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                        subject);

            if (body != null)
                emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));

            //          myContext.startActivity(Intent.createChooser(emailIntent,
            //                  "Share Via"));
            return emailIntent;
        }
    }
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }
}