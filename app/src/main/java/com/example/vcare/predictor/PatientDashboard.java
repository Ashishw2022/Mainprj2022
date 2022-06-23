package com.example.vcare.predictor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;

import com.example.vcare.R;
import com.example.vcare.patient.Patient_Booking_Appointments;
import com.example.vcare.patient.Patient_Session_Management;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatientDashboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText ename, eage;
    String patemail;
    String user_name;
    TextView vali;
    Spinner spin1;
    RadioButton r1,r2,r3,r4,r5;
    RadioGroup radio_gender;
    private DatabaseReference reference_user_details;
    Interpreter interpreter;
    List<String> symList = new ArrayList<String>();
    int i = 0;
    String[] selected={};
    String[] all_symptoms = {"select a symptom", "itching", "skin_rash", "nodal_skin_eruptions", "continuous_sneezing", "shivering",
            "chills", "joint_pain", "stomach_pain", "acidity", "ulcers_on_tongue", "muscle_wasting vomiting",
            "burning_micturition", "spotting_ urination", "fatigue", "weight_gain", "anxiety", "cold_hands_and_feets",
            "mood_swings", "weight_loss", "restlessness", "lethargy", "patches_in_throat", "irregular_sugar_level", "cough",
            "high_fever", "sunken_eyes", "breathlessness", "sweating ", "dehydration", "indigestion", "headache",
            "yellowish_skin", "dark_urine", "nausea", "loss_of_appetite", "pain_behind_the_eyes", "back_pain",
            "constipation", "abdominal_pain", "diarrhoea",
            "mild_fever", "yellow_urine", "yellowing_of_eyes", "acute_liver_failure",
            "fluid_overload", "swelling_of_stomach", "swelled_lymph_nodes", "malaise", "blurred_and_distorted_vision",
            "phlegm", "throat_irritation", "redness_of_eyes", "sinus_pressure", "runny_nose", "congestion", "chest_pain",
            "weakness_in_limbs", "fast_heart_rate", "pain_during_bowel_movements", "pain_in_anal_region", "bloody_stool",
            "irritation_in_anus", "neck_pain", "dizziness", "cramps", "bruising", "obesity", "swollen_legs", "swollen_blood_vessels",
            "puffy_face_and_eyes", "enlarged_thyroid", "brittle_nails", "swollen_extremeties", "excessive_hunger", "extra_marital_contacts",
            "drying_and_tingling_lips", "slurred_speech", "knee_pain", "hip_joint_pain", "muscle_weakness", "stiff_neck", "swelling_joints",
            "movement_stiffness", "spinning_movements", "loss_of_balance", "unsteadiness", "weakness_of_one_body_side",
            "loss_of_smell", "bladder_discomfort", "foul_smell_of urine", "continuous_feel_of_urine", "passage_of_gases",
            "internal_itching", "toxic_look_(typhos)", "depression", "irritability", "muscle_pain", "altered_sensorium",
            "red_spots_over_body", "belly_pain", "abnormal_menstruation", "dischromic _patches", "watering_from_eyes",
            "increased_appetite", "polyuria", "family_history", "mucoid_sputum", "rusty_sputum", "lack_of_concentration",
            "visual_disturbances", "receiving_blood_transfusion", "receiving_unsterile_injections", "coma", "stomach_bleeding",
            "distention_of_abdomen", "history_of_alcohol_consumption", "fluid_overload blood_in_sputum", "prominent_veins_on_calf",
            "palpitations", "painful_walking", "pus_filled_pimples", "blackheads", "scurring", "skin_peeling", "silver_like_dusting",
            "small_dents_in_nails", "inflammatory_nails", "blister", "red_sore_around_nose", "yellow_crust_ooze"};
    String[] all_symptoms1 = {"select a symptom", "itching", "skin_rash", "nodal_skin_eruptions", "continuous_sneezing", "shivering",
            "chills", "joint_pain", "stomach_pain", "acidity", "ulcers_on_tongue", "muscle_wasting vomiting",
            "burning_micturition", "spotting_ urination", "fatigue", "weight_gain", "anxiety", "cold_hands_and_feets",
            "mood_swings", "weight_loss", "restlessness", "lethargy", "patches_in_throat", "irregular_sugar_level", "cough",
            "high_fever", "sunken_eyes", "breathlessness", "sweating ", "dehydration", "indigestion", "headache",
            "yellowish_skin", "dark_urine", "nausea", "loss_of_appetite", "pain_behind_the_eyes", "back_pain",
            "constipation", "abdominal_pain", "diarrhoea"};
    String[] all_symptoms2 = {"select a symptom","mild_fever", "yellow_urine", "yellowing_of_eyes", "acute_liver_failure",
            "fluid_overload", "swelling_of_stomach", "swelled_lymph_nodes", "malaise", "blurred_and_distorted_vision",
            "phlegm", "throat_irritation", "redness_of_eyes", "sinus_pressure", "runny_nose", "congestion", "chest_pain",
            "weakness_in_limbs", "fast_heart_rate", "pain_during_bowel_movements", "pain_in_anal_region", "bloody_stool",
            "irritation_in_anus", "neck_pain", "dizziness", "cramps", "bruising", "obesity", "swollen_legs", "swollen_blood_vessels",
            "puffy_face_and_eyes", "enlarged_thyroid", "brittle_nails", "swollen_extremeties", "excessive_hunger", "extra_marital_contacts",
            "drying_and_tingling_lips", "slurred_speech", "knee_pain", "hip_joint_pain", "muscle_weakness", "stiff_neck", "swelling_joints",
            "movement_stiffness", "spinning_movements", "loss_of_balance", "unsteadiness", "weakness_of_one_body_side"};
    String[] all_symptoms3 ={"select a symptom","loss_of_smell", "bladder_discomfort", "foul_smell_of urine", "continuous_feel_of_urine", "passage_of_gases",
            "internal_itching", "toxic_look_(typhos)", "depression", "irritability", "muscle_pain", "altered_sensorium",
            "red_spots_over_body", "belly_pain", "abnormal_menstruation", "dischromic _patches", "watering_from_eyes",
            "increased_appetite", "polyuria", "family_history", "mucoid_sputum", "rusty_sputum", "lack_of_concentration",
            "visual_disturbances", "receiving_blood_transfusion", "receiving_unsterile_injections", "coma", "stomach_bleeding",
            "distention_of_abdomen", "history_of_alcohol_consumption", "fluid_overload blood_in_sputum", "prominent_veins_on_calf",
            "palpitations", "painful_walking", "pus_filled_pimples", "blackheads", "scurring", "skin_peeling", "silver_like_dusting",
            "small_dents_in_nails", "inflammatory_nails", "blister", "red_sore_around_nose", "yellow_crust_ooze"};

    float[] input = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    String[] predict_Diseases = {"Fungal infection", "Allergy", "GERD", "Chronic cholestasis", "Drug Reaction",
            "Peptic ulcer disease", "AIDS", "Diabetes ", "Gastroenteritis", "Bronchial Asthma",
            "Hypertension ", "Migraine", "Cervical spondylosis", "Paralysis (brain hemorrhage)",
            "Jaundice", "Malaria", "Chicken pox", "Dengue", "Typhoid", "hepatitis A", "Hepatitis B",
            "Hepatitis C", "Hepatitis D", "Hepatitis E", "Alcoholic hepatitis", "Tuberculosis",
            "Common Cold", "Pneumonia", "Dimorphic hemmorhoids(piles)", "Heart attack",
            "Varicose veins", "Hypothyroidism", "Hyperthyroidism", "Hypoglycemia",
            "Osteoarthristis", "Arthritis", "(vertigo) Paroymsal  Positional Vertigo",
            "Acne", "Urinary tract infection", "Psoriasis", "Impetigo"};

    String[] disease_category = {"Dermatology & Venereology","Dermatology & Venereology","Gastroenterology, Hepatology & Endoscopy","Gastroenterology, Hepatology & Endoscopy",
            "Dermatology & Venereology","Gastroenterology, Hepatology & Endoscopy","Infectious Disease","Endocrinology & Diabetes","Gastroenterology, Hepatology & Endoscopy",
            "Immunology","Hormone", "Ear Nose Throat","Gastroenterology, Hepatology & Endoscopy","Dermatology & Venereology","Dermatology & Venereology","Gastroenterology, Hepatology & Endoscopy","Gastroenterology, Hepatology & Endoscopy",
            "Dermatology & Venereology","Gastroenterology, Hepatology & Endoscopy","Infectious Disease","Endocrinology & Diabetes","Gastroenterology, Hepatology & Endoscopy",
            "Immunology","Hormone", "Ear Nose Throat","Gastroenterology, Hepatology & Endoscopy","Dermatology & Venereology","Dermatology & Venereology","Gastroenterology, Hepatology & Endoscopy","Gastroenterology, Hepatology & Endoscopy",
            "Dermatology & Venereology","Gastroenterology, Hepatology & Endoscopy","Infectious Disease","Endocrinology & Diabetes","Gastroenterology, Hepatology & Endoscopy",
            "Immunology","Hormone", "Ear Nose Throat","Gastroenterology, Hepatology & Endoscopy"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        ename = findViewById(R.id.edit_name);
        eage = findViewById(R.id.edit_age);
        radio_gender=findViewById(R.id.radio_gender);
        vali=findViewById(R.id.vali);
        user_name = ename.getText().toString();
        String user_age = eage.getText().toString();

        reference_user_details = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User_data");

        Patient_Session_Management session = new Patient_Session_Management(PatientDashboard.this);
        patemail = session.getSession();

        reference_user_details.child(patemail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ename.setText(snapshot.child("name").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //if (TextUtils.isEmpty(user_name)) {
        //    ename.setError("Enter your Name");
        //} else if (TextUtils.isEmpty(user_age)) {
        //    eage.setError("Enter your age");
        //}

        try {
            interpreter = new Interpreter(loadModelFile(), null);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        spin1 = (Spinner) findViewById(R.id.spinner1);
        //spin1.setOnItemSelectedListener(this);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        //spin2.setOnItemSelectedListener(this);
        Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
        //spin3.setOnItemSelectedListener(this);
        Spinner spin4 = (Spinner) findViewById(R.id.spinner4);
        //spin4.setOnItemSelectedListener(this);
        selected=all_symptoms;

        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, all_symptoms1);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(aa1);
        System.out.println(spin1);
        spin1.setOnItemSelectedListener(this);

        Log.d("main", "Main function 1");
        ArrayAdapter aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, all_symptoms2);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(aa2);
        spin2.setOnItemSelectedListener(this);

        Log.d("main", "Main function 2");

        ArrayAdapter aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, all_symptoms3);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(aa3);
        spin3.setOnItemSelectedListener(this);
        Log.d("main", "Main function 3");

        ArrayAdapter aa4 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, all_symptoms2);
        aa4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin4.setAdapter(aa4);
        spin4.setOnItemSelectedListener(this);
        Log.d("main", "Main function 4");
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), all_symptoms[position], Toast.LENGTH_LONG).show();
        String sym = arg0.getItemAtPosition(position).toString();

        input[position] = 1;
        if(!sym.equals("select a symptom")){
            symList.add(sym);
        }


       //
        Log.d("onItemSelected", "Item selected ");
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd("MPtflite.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long length = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, length);
    }

    public float[][] doInference(float[][] val) {
        Log.d("doInference", "doINference method called");
        float[][] output = new float[1][41];
        interpreter.run(val, output);
        return output;
    }
    private static int findMax(float[][] matrix) {
        Log.d("findMax", "max method called");
        float maxNum = matrix[0][0];
        int k = 0;
        for (int j = 0; j < matrix[0].length; j++) {
            if (maxNum < matrix[0][j]) {
                maxNum = matrix[0][j];
                k = j;
            }
        }
        Log.d("findMax", "k value is" + k);
        return k;
    }


    public void predict(View view) {
        String lname = ename.getText().toString().trim();
        String age = eage.getText().toString().trim();
        if (lname.isEmpty()) {
            ename.setError("Name is a required field !");
            ename.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            eage.setError("Age is a required field !");
            eage.requestFocus();
            return;
        }
        if(radio_gender.getCheckedRadioButtonId()==-1)
        {
            vali.setText("Please enter your gender");
            return;
        }
        if(radio_gender.getCheckedRadioButtonId()!=-1)

        {
            vali.setText("");
        }
//        if (spin1.getSelectedItem().toString().trim().equals("select a symptom")) {
//            Toast.makeText(PatientDashboard.this, "please select atleast 2 symptoms", Toast.LENGTH_SHORT).show();
//        }
        Log.d("predict", "Predict method called");
        float[][] finalinput = {input};
        Log.d("predict", "finalinput" + Arrays.deepToString(finalinput));

        float[][] out = doInference(finalinput);

        //out=[[41 values]]
        int element = findMax(out);

        //create a text view to display
        Log.d("predict", "k value returned,element" + predict_Diseases[element]);

        Toast.makeText(getApplicationContext(), predict_Diseases[element], Toast.LENGTH_LONG).show();
        for (i = 0; i <= 131; i++) {
            input[i] = 0;
        }
        Intent intent1 = new Intent(this, Loading.class);
        String dis = predict_Diseases[element];
        intent1.putExtra("Disease_name", dis);
        intent1.putExtra("pname",lname);
        intent1.putExtra("disease_category", disease_category[element]);
        intent1.putExtra("Symptoms", String.valueOf(symList));
        startActivity(intent1);
    }
}