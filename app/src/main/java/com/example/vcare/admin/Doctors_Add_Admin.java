package com.example.vcare.admin;

import android.content.Context;
import android.content.Intent;
import android.media.tv.TvInputService;
import android.os.Bundle;
import android.os.StrictMode;
import android.service.textservice.SpellCheckerService;
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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class Doctors_Add_Admin extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextView sendmail,registerUser;
    private EditText editTextFullName,editTextEmail,editTextPassword;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add_admin);

        progressBar = (ProgressBar) findViewById(R.id.progressbar_doc_add);
        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.registerUser);
        //sendmail = (Button) findViewById(R.id.sendmail);

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

                if (fullName.isEmpty()) {
                    editTextFullName.setError("Full Name is a required field !");
                    progressBar.setVisibility(View.INVISIBLE);
                    editTextFullName.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    editTextEmail.setError("Email is a required field !");
                    progressBar.setVisibility(View.INVISIBLE);
                    editTextEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Please provide Valid Email !");
                    progressBar.setVisibility(View.INVISIBLE);
                    editTextEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password is a required field !");
                    progressBar.setVisibility(View.INVISIBLE);
                    editTextPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editTextPassword.setError("Minimum length of password should be 6 characters !");
                    progressBar.setVisibility(View.INVISIBLE);
                    editTextPassword.requestFocus();
                    return;
                }


                Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Users user = new Users(fullName, email, "online", "Doctor", "1", "");
                                    String encodedemail = EncodeString(email);
                                    FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
                                            .child(encodedemail)
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        senddocmail();
                                                        progressBar.setVisibility(View.INVISIBLE);

                                                        Toast.makeText(Doctors_Add_Admin.this, "Doctor has been registered successfully !", Toast.LENGTH_LONG).show();
//                                       mAuth.signOut();
//                                        startActivity(new Intent(Doctors_Add_Admin.this,DoctorsLogin.class));
                                                        //startActivity((Doctors_Add_Admin.this,"Doctor Added","your name is added as doctor"))


                                                    } else {
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        Toast.makeText(Doctors_Add_Admin.this, "Failed to Register. Doctor already exists !", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });


                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Doctors_Add_Admin.this, "Failed to Register. Doctor already exists !", Toast.LENGTH_LONG).show();
                                }

                            }

                        });

            }

    private void senddocmail() {
        try {
            final String frommail = "ashishvwprj@gmail.com";
            final String fpass = "gqezrowmplvieddv";
            String subject = "New Doctor account credentials";

            String name=editTextFullName.getText().toString().trim();
            String mail = editTextEmail.getText().toString().trim();
            String pass = editTextPassword.getText().toString().trim();

            String msg="\n Welcome DR."+name+"\nYour Account credintials are: \n" +"    Email: "+mail+"\n   Password: "+pass;

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
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(frommail,fpass);
                        }
                    });
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(frommail));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(editTextEmail.getText().toString()));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                        Toast.makeText(Doctors_Add_Admin.this, "Mail sent", Toast.LENGTH_SHORT).show();
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



    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }
}