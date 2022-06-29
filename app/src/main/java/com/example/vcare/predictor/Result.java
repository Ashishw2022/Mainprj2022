package com.example.vcare.predictor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vcare.R;
import com.example.vcare.patient.Patient;
import com.example.vcare.patient.Patient_Appointment_Status;
import com.example.vcare.patient.Patient_Session_Management;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Result extends AppCompatActivity {
    private DatabaseReference reference_prediction,reference_user_details;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private String patemail,ename;
    Button needdoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String desc = "";

        setContentView(R.layout.activity_result);
        Patient_Session_Management session = new Patient_Session_Management(Result.this);
        patemail = session.getSession();


        Intent intent1=getIntent();
        String str=intent1.getStringExtra("Disease_name");
        String pname=intent1.getStringExtra("name");

        String symList = intent1.getStringExtra("Symptoms");
        String category = intent1.getStringExtra("disease_category");

        needdoc=findViewById(R.id.needdoc);
        needdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Result.this, Doctor_recommendation.class);
                intent1.putExtra("disease_category",category);
                startActivity(intent1);
            }
        });

        Log.d("main", "Main function 2"+str);
        //TextView tv1 =  findViewById(R.id.predicted_disease_name);
        //tv1.setText(str);
        reference_prediction = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("disease_prediction");

        if(str.equals("Fungal infection")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc = "Fungal infections are common throughout much of the natural world. In humans, fungal infections occur when an invading fungus takes over an area of the body and is too much for the immune system to handle.  \n" +
                    "The symptoms of a fungal infection will depend on the type, but common symptoms include the following:skin changes, including red and possibly cracking or peeling skin,itching.\n" +
                    "\n" +
                    "Diagnosis, treatment, and prevention\n" +
                    "\n" +
                    "Not all itchy feet are the result of an athlete's foot. Doctors usually diagnose the infection by scraping scaling skin off of a person and inspecting it under a microscope for evidence of any fungus.\n" +
                    "\n" +
                    "There are a few different fungi that can cause athlete’s foot. The infection may behave differently depending on the specific fungus that is infecting the skin.\n" +
                    "\n" +
                    "Prevention methods include allowing the feet plenty of air to breathe and keeping them clean and dry. It is a good idea to wear sandals in public showers or locker rooms";
            tv2.setText(desc);
        }
        if(str.equals("Pneumonia")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Symptoms\n" +
                    "Symptoms may include:\n" +
                    "•\tCough producing mucus\n" +
                    "•\tFever\n" +
                    "•\tSweating\n" +
                    "•\tFast breathing\n" +
                    "•\tShortness of breath\n" +
                    "•\tChills, teeth grinding\n" +
                    "•\tChest pain while coughing\n" +
                    "•\tFast heartbeat\n" +
                    "•\tFeeling very tired or very weak\n" +
                    "•\tNausea and vomiting\n" +
                    "•\tDiarrhoea\n" +
                    "•\tLoss of appetite\n" +
                    "•\tBody pain\n" +
                    "Severely affected patients my cough up blood or show cyanosis (have a blue colour around the mouth due to lack of oxygen)\n" +
                    "\n" +
                    "Nutrition\n" +
                    "Foods to eat:\n" +
                    "•\tIncrease fluid intake; broth based soups provide relief from symptoms\n" +
                    "•\tTake plenty of fruits and vegetables\n" +
                    "•\tConsume lean proteins such as chicken\n" +
                    "\n" +
                    "Foods to avoid:\n" +
                    "•\tRefined starch and sugar; pasteurized milk and dairy products\n" +
                    "\n" +
                    "Medication\n" +
                    "Antibiotics: To treat bacterial infection. \n" +
                    "Levofloxacin . Azithromycin\n" +
                    "\n" +
                    "\n" +
                    "Antivirals: To treat viral infection. \n" +
                    "Oseltamivir . Zanamivir\n" +
                    "\n" +
                    "\n" +
                    "Antifungals: If the infection is caused by fungi. \n" +
                    "Fluconazole . Itraconazole\n" +
                    "\n" +
                    "\n" +
                    "Analgesics: To ease symptoms of pain. \n" +
                    "Acetaminophen . Ibuprofen\n" +
                    "\n" +
                    "\n" +
                    "Cough suppressants: To relieve cough. \n" +
                    "Dextromethorphan . Codeine\n";
            tv2.setText(desc);
        }
        if(str.equals("Common Cold")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc = "Symptoms\n" +
                    "Symptoms occur 1-3 days after viral infection. Symptoms include:\n" +
                    "•\tNose stuffiness\n" +
                    "•\tRunny nose\n" +
                    "•\tSore throat\n" +
                    "•\tCough\n" +
                    "•\tCongestion\n" +
                    "•\tMild headache\n" +
                    "•\tSneezing\n" +
                    "•\tMalaise\n" +
                    "•\tFever\n" +
                    "\n" +
                    "Nutrition\n" +
                    "Foods to eat:\n" +
                    "•\tChicken soup\n" +
                    "•\tVitamin C rich foods such as oranges\n" +
                    "•\tAntioxidant containing foods such as kale, broccoli\n" +
                    "\n" +
                    "Foods to avoid:\n" +
                    "•\tAlcohol and caffeine\n" +
                    "Medication\n" +
                    "Analgesics: It relieves symptoms \n" +
                    "Acetaminophen\n" +
                    "\n" +
                    "\n" +
                    "Antihistamines: It reduces allergic reactions \n" +
                    "Brompheniramine . Cetirizine\n" +
                    "\n";
            tv2.setText(desc);
        }
        if(str.equals("Tuberculosis")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Symptoms\n" +
                    "Early infection causes no symptoms as the body's immune system protects spread, and in many cases, it remains inactive - this stage is referred to as Latent TB and shows no symptoms. Early diagnosis and treatment is necessary to control the spread of the infection.\n" +
                    "Active TB is a stage where immediate medications and care is required, the common symptoms include:\n" +
                    "•\tPersistent cough (which lasts for more than 2 - 3 weeks)\n" +
                    "•\tCough with blood in sputum\n" +
                    "•\tFever for more than 2 -3 weeks\n" +
                    "•\tSudden weight loss\n" +
                    "•\tNight sweats\n" +
                    "•\tLoss of appetite\n" +
                    "\n" +
                    "\n" +
                    "•\tMedication\n" +
                    "•\tAntibiotics: To fight the infection or kill the bacteria. \n" +
                    "•\tIsoniazid . Rifampicin . Ethambutol . Bedaquiline\n";
            tv2.setText(desc);
        }
        if(str.equals("Alcoholic hepatitis")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Symptoms\n" +
                    "The most common symptom is jaundice, characterised by yellowing of the skin and eyes.\n" +
                    "Other symptoms are\n" +
                    "•\tNausea and vomiting\n" +
                    "•\tLow-grade fever\n" +
                    "•\tWeakness and fatigue\n" +
                    "•\tAbdominal fullness and tenderness\n" +
                    "•\tLoss of appetite\n" +
                    "•\tMore severe symptoms include -\n" +
                    "•\tMental confusion\n" +
                    "•\tKidney and liver failure\n" +
                    "\n" +
                    "Nutrition\n" +
                    "Foods to eat:\n" +
                    "•\tFoods rich in vitamin B - whole grains such as brown rice\n" +
                    "•\tFoods rich in protein, e.g., fish and chicken\n" +
                    "\n" +
                    "Foods to avoid:\n" +
                    "•\tFoods with saturated fats like butter, sour cream\n" +
                    "•\tSugary foods like sweets and sodas\n" +
                    "•\tSalty foods, e.g., fast foods\n" +
                    "\n" +
                    "Medication\n" +
                    "Corticosteroids: To reduce inflammation. \n" +
                    "Prednisolone\n" +
                    "\n" +
                    "\n" +
                    "Anti-inflammatory drugs: To reduce inflammation. \n" +
                    "Pentoxifylline\n" +
                    "\n" +
                    "\n" +
                    "Dietary supplements: Minerals, multivitamins and parenteral protein is recommended to prevent or treat protein-energy malnutrition. \n" +
                    "Folate . Thiamine\n";
            tv2.setText(desc);
        }
        if(str.equals("Hepatitis E")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Symptoms\n" +
                    "You might not have any. If you do have symptoms, they may start anywhere from 2 to 6 weeks after your infection. They may include:\n" +
                    "•\tMild fever\n" +
                    "•\tFeeling very tired\n" +
                    "•\tLess hunger\n" +
                    "•\tFeeling sick to your stomach\n" +
                    "•\tThrowing up\n" +
                    "•\tBelly pain\n" +
                    "•\tDark pee\n" +
                    "•\tLight-colored poop\n" +
                    "•\tSkin rash or itching\n" +
                    "•\tJoint pain\n" +
                    "•\tYellowish skin or eyes\n" +
                    "\n" +
                    "Treatment\n" +
                    "In most cases, hepatitis E goes away on its own in about 4-6 weeks. These steps can help ease your symptoms:\n" +
                    "•\tRest\n" +
                    "•\tEat healthy foods\n" +
                    "•\tDrink lots of water\n" +
                    "•\tAvoid alcohol\n" +
                    "Check with your doctor before you take any medicine that may damage your liver, such as acetaminophen.\n" +
                    "If you’re pregnant, your doctor may keep you under watch in the hospital. If your condition is serious, you may get medicine to fight the infection.\n" +
                    "\n" + "Prevention\n" +
                    "No vaccine can prevent the hepatitis E virus. It’s most common in less-developed countries in Asia, the Middle East, Africa, and Central America. You can lower your chances of getting the virus if you:\n" +
                    "•\tDon’t drink water or use ice that you don’t know is clean.\n" +
                    "•\tDon’t eat undercooked pork, deer meat, or raw shellfish.\n" +
                    "Wash your hands with soap and water after you use the bathroom, change a diaper, and before you prepare or eat food.\n";
            tv2.setText(desc);
        }
        if(str.equals("Hepatitis D")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Hepatitis D doesn’t always cause symptomsTrusted Source. When symptoms do occur, they often include:\n" +
                    "•\tyellowing of the skin and eyes, which is called jaundice\n" +
                    "•\tjoint pain\n" +
                    "•\tabdominal pain\n" +
                    "•\tvomiting\n" +
                    "•\tloss of appetite\n" +
                    "•\tdark urine\n" +
                    "•\tfatigue\n" +
                    "The symptoms of hepatitis B and hepatitis D are similar, so it can be difficult to determine which disease is causing your symptoms. In some cases, hepatitis D can make the symptoms of hepatitis B worse. It can also cause symptoms in people who have hepatitis B but who never had symptoms.\n" +
                    "\n" +
                    "How is hepatitis D treated?\n" +
                    "There are no known treatments for acute or chronic hepatitis D. Unlike other forms of hepatitis, currentTrusted Source antiviral medications don’t seem to be very effective in treating HDV.\n" +
                    "You may be given large doses of a medication called interferon for up to 12 months. Interferon is a type of protein that may stop the virus from spreading and lead to remission from the disease. However, even after treatment, people with hepatitis D can still test positive for the virus. This means that it’s still important to use precautionary measures to prevent transmission. You should also remain proactive by watching for recurring symptoms.\n" +
                    "If you have cirrhosis or another type of liver damage, you may need a liver transplant. A liver transplant is a major surgical operation that involves removing the damaged liver and replacing it with a healthy liver from a donor. In cases where a liver transplant is needed, approximately 70 percent of people live 5 years or longer after the operation.\n";
            tv2.setText(desc);
        }
        if(str.equals("Hepatitis C")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Symptoms\n" +
                    "During the acute phase (first 6 months after infection) most persons show no symptoms or might experience a mild illness.\n" +
                    "During the chronic phase the symptoms include:\n" +
                    "•\tJaundice (yellow discolouration of the skin and eyes)\n" +
                    "•\tEasy bruising and bleeding\n" +
                    "•\tDark-coloured urine, light-coloured stools\n" +
                    "•\tFatigue\n" +
                    "•\tAbdominal pain\n" +
                    "•\tLoss of appetite\n" +
                    "•\tNausea\n" +
                    "•\tDiarrhoea\n" +
                    "•\tFever\n" +
                    "•\tAscites (accumulation of fluid and swelling of the abdominal cavity)\n" +
                    "•\tStar-shaped vein pattern developing on the swollen belly\n" +
                    "•\tItching\n" +
                    "\n" +
                    "Medication\n" +
                    "Antiviral: Drugs which are used for treating viral infections and hepatitis C virus. \n" +
                    "Ribavirin\n" +
                    "\n" +
                    "\n" +
                    "Direct-acting antivirals: Drug to target specific genotypes and stop virus multiplication. Depending on the genotype, they are used alone or in combination. \n" +
                    "Simeprevir . Daclatasvir . Elbasvir . Grazoprevir . Glecaprevir . Pibrentasvir . Ledipasvir . Sofosbuvir . Ombitasvir . Paritaprevir . Ritonavir . Dasabuvir\n" +
                    "\n" +
                    "\n" +
                    "Vaccine: There is no vaccination for hepatitis C. Vaccination for hepatitis A and B will be recommended to avoid any further complications caused by these viruses. \n" +
                    "Hepatitis A vaccine . Hepatitis B vaccine\n";
            tv2.setText(desc);
        }
        if(str.equals("Hepatitis B")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Symptoms\n" +
                    "Common symptoms are:\n" +
                    "•\tPain in the area of liver or abdominal pain\n" +
                    "•\tDark urine\n" +
                    "•\tPale coloured stools\n" +
                    "•\tFever\n" +
                    "•\tItching all over the body\n" +
                    "•\tJoint pain\n" +
                    "•\tNausea and vomiting\n" +
                    "•\tLoss of appetite\n" +
                    "•\tExtreme fatigue and weakness\n" +
                    "•\tYellow colouring of the skin and the whites of the eyes (jaundice)\n" +
                    "\n" +
                    "Nutrition\n" +
                    "Foods to eat:\n" +
                    "•\tEat healthy and nutritious diet\n" +
                    "•\tInclude fresh fruits and vegetables in your diet\n" +
                    "•\tDrink plenty of fluids to prevent dehydration\n" +
                    "\n" +
                    "Foods to avoid:\n" +
                    "•\tAlcohol\n" +
                    "•\tSaturated fats\n" +
                    "\n" +
                    "Medication\n" +
                    "Antivirals: Cannot clear the infection but can stop the replication of the virus and minimise liver damage. \n" +
                    "Lamivudine . Adefovir . Entecavir\n" +
                    "\n" +
                    "\n" +
                    "Immunomodulators: Reduces the viral load by preventing replication of viruses. Response differs with genetic variations of the virus. \n" +
                    "Interferon alfa-2b\n" ;
            tv2.setText(desc);
        }
        if(str.equals("hepatitis A")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Symptoms\n" +
                    "•\tSymptoms of hepatitis A virus infection are noted a few weeks after the virus enters the body\n" +
                    "•\tNot all people with infection develop symptoms\n" +
                    "•\tNotable symptoms include\n" +
                    "•\tFatigue\n" +
                    "•\tNausea and vomiting\n" +
                    "•\tAbdominal pain or discomfort, particularly at the location of liver\n" +
                    "•\tClay-colour stools\n" +
                    "•\tLoss of appetite\n" +
                    "•\tLow-grade fever\n" +
                    "•\tDark urine\n" +
                    "•\tJoint pain\n" +
                    "•\tYellowing of eyes and skin\n" +
                    "•\tMedication\n" +
                    "•\tVaccine: Hepatitis A vaccine or hepatitis A immune globulin may be recommended to prevent infection after being exposed to the virus. \n" +
                    "•\tHepatitis A vaccine\n";
            tv2.setText(desc);
        }
        if(str.equals("Typhoid")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Symptoms\n" +
                    "In some cases, complications such as dehydration, delirium, intestinal bleeding, intestinal perforation, haemorrhage, meningitis etc; can occur after around three weeks.\n" +
                    "•\tLow to high fever\n" +
                    "•\tHeadache and body pain\n" +
                    "•\tLoss of appetite and weight loss\n" +
                    "•\tDry cough\n" +
                    "•\tSweating\n" +
                    "•\tAbdominal pain\n" +
                    "•\tSwelling in abdomen\n" +
                    "•\tDiarrhoea or constipation\n" +
                    "•\tItching or rashes\n" +
                    "•\tMedication\n" +
                    "•\tAntibiotics: Reduces the growth and kills bacteria. \n" +
                    "•\tCiprofloxacin . Ceftriaxone\n";
            tv2.setText(desc);
        }
        if(str.equals("Dengue")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Symptoms\n" +
                    "Commonly observed symptoms are as follows:\n" +
                    "•\tSudden onset of very high fever\n" +
                    "•\tSevere headache usually at the back of eyes\n" +
                    "•\tVomiting\n" +
                    "•\tMuscles and joints becomes painful\n" +
                    "•\tDevelopment of skin rashes\n" +
                    "•\tFatigue\n" +
                    "•\tSkin rash\n" +
                    "•\tMild bleeding\n" +
                    "\n" +
                    "Treatments\n" +
                    "Treatment of dengue fever is mainly supportive and depends on the stage of infection\n" +
                    "Medical procedures: Blood transfusion\n" +
                    "Therapies: Intravenous therapy\n" +
                    "\n" +
                    "Causes\n" +
                    "•\tDengue fever is caused by a virus called dengue virus which is transmitted by a mosquito species called Aedes especially by A. aegypti\n" +
                    "•\tDengue virus has different types and once infected by one type, prolonged immunity is observed to the particular type and a short lived immunity to the other types\n" +
                    "•\tUsually it is spread by a female Aedes mosquito which normally bites an infected person in the morning to take a blood meal and spreads the virus anytime of the day. In 2- 10 days, the mosquito becomes infected and virus spreads to other tissues and is released into saliva. Once the mosquito bites an uninfected person, the person becomes infected. The mosquito is usually infected for lifetime\n" +
                    "•\tIt also spreads through blood or organ donation from an infected person\n";
            tv2.setText(desc);
        }
        if(str.equals("Chicken pox")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Symptoms\n" +
                    "Symptoms include\n" +
                    "•\tFever\n" +
                    "•\tLoss of appetite\n" +
                    "•\tHeadache\n" +
                    "•\tTiredness and a general feeling of being unwell\n" +
                    "•\tSkin rash may appear in 3 phases\n" +
                    "•\tRaised pink or red bumps\n" +
                    "•\tFluid filled blister bumps\n" +
                    "•\tCrusts and scabs that cover the broken blisters\n" +
                    "\n" +
                    "•\tMedication\n" +
                    "•\tAntipyretics: Prescribed to reduce fever. \n" +
                    "•\tAcetaminophen\n" +
                    "•\t\n" +
                    "\n" +
                    "•\tAntivirals: To control viral replication. \n" +
                    "•\tAcyclovir\n" +
                    "•\t\n" +
                    "\n" +
                    "•\tAntihistamines: They are prescribed to reduce itching. \n" +
                    "•\tDiphenhydramine\n";
            tv2.setText(desc);
        }
        if(str.equals("Malaria")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Malaria is a disease having symptoms of recurrent fever with chill and headache. After onset of fever it subsides after sometimes and again reoccurs. In severe cases it can progress to coma or even death. It is caused by parasites known as Plasmodium. It commences with the bite of female Anopheles mosquitoes which carries this parasite.\n" +
                    "The treatment chosen will depend upon whether the patient has vivax malaria or falciparum malaria as diagnosed by the blood test, age of the patient, the pregnancy status of the female patient and location of the patient.\n" +
                    "Individual Preventions\n" +
                    "•\tUse an intact mosquito net while sleeping.\n" +
                    "•\tUse mosquito repellent creams, liquids, coils and mats. \n" +
                    "•\tUse Indoor Residual Spray (IRS) with insecticides. \n" +
                    "•\tUse Aerosol space spray during day time.\n";
            tv2.setText(desc);
        }
        if(str.equals("Jaundice")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc ="Common causes\n" +
                    "Jaundice is not always related to an underlying condition. It may be caused by:\n" +
                    "•\tLarge bruises leading to high amounts of unconjugated bilirubin\n" +
                    "•\tFasting for long periods\n" +
                    "•\tMedications\n" +
                    "•\tExcess alcohol intake\n" +
                    "\n" +
                    "Self-treatment: Self- care steps that may be helpful in some less- serious cases:\n" +
                    "•\tAdequate hydration\n" +
                    "•\tTaking adequate rest\n" +
                    "•\tAbstinence from alcohol\n" +
                    "\n" +
                    "See a doctor immediately if you notice:\n" +
                    "•\tYellow eyes\n" +
                    "•\tYellow urine\n";
            tv2.setText(desc);
        }
        if(str.equals("Paralysis (brain hemorrhage)")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
             desc = "Sleep paralysis is a state, during waking up or falling asleep, in which a person is aware but unable to move or speak. During an episode, one may hallucinate (hear, feel, or see things that are not there), which often results in fear.[1] Episodes generally last less than a couple of minutes. It may occur as a single episode or be recurrent. \n" +
                    "The condition may occur in those who are otherwise healthy or those with narcolepsy, or it may run in families as a result of specific genetic changes. The condition can be triggered by sleep deprivation, psychological stress, or abnormal sleep cycles. The underlying mechanism is believed to involve a dysfunction in REM sleep.] Diagnosis is based on a person's description.[2] Other conditions that can present similarly include narcolepsy, atonic seizure, and hypokalemic periodic paralysis.\n" +
                    "\n" +
                    "Symptoms:\n" +
                    "\n" +
                    "The main symptom of sleep paralysis is being unable to move or speak during awakening. \n" +
                    "Imagined sounds such as humming, hissing, static, zapping and buzzing noises are reported during sleep paralysis. Other sounds such as voices, whispers and roars are also experienced. It has also been known that one may feel pressure on their chest during an episode.  These symptoms are usually accompanied by intense emotions such as fearand panic. People also have sensations of being dragged out of bed or of flying, numbness, and feelings of electric tingles or vibrations running through their body.\n" +
                    "\n" +
                    "Treatment:\n" +
                    "\n" +
                    "Though no large trials have taken place which focus on the treatment of sleep paralysis, several drugs have promise in case studies. Two trials of GHB for people with narcolepsy demonstrated reductions in sleep paralysis episodes. \n" +
                    "Pimavanserin has been proposed as a possible candidate for future studies in treating sleep paralysis.\n";
            tv2.setText(desc);
        }
        if(str.equals("Cervical spondylosis")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Cervical spondylosis:\n" +
                    "Spondylosis is the degeneration of the vertebral column from any cause. In the more narrow sense it refers to spinal osteoarthritis, the age-related wear and tear of the spinal column, which is the most common cause of spondylosis. The degenerative process in osteoarthritis chiefly affects the vertebral bodies, the neural foramina and the facet joints (facet syndrome). If severe, it may cause pressure on the spinal cord or nerve roots with subsequent sensory or motor disturbances, such as pain, paresthesia, imbalance, and muscle weakness in the limbs.\n" +
                    "Symptoms:\n" +
                    "A rare but severe complication of this disease is vertebrobasilar insufficiency. This is a result of the vertebral artery becoming occluded as it passes up in the transverse foramen. The spinal joints become stiff in cervical spondylosis. Thus the chondrocytes which maintain the disc become deprived of nutrition and die. Secondary osteophytes may cause stenosis for spinal nerves, manifesting as radiculopathy.\n" +
                    "\n" + "Cause:\n" +
                    "Spondylosis is caused from years of constant abnormal pressure, caused by joint subluxation, stress induced by sports, acute and/or repetitive trauma, or poor posture, being placed on the vertebrae and the discs between them. The abnormal stress causes the body to form new bone in order to compensate for the new weight distribution. This abnormal weight bearing from bone displacement will cause spondylosis to occur. Poor postures and loss of the normal spinal curves can lead to spondylosis as well. Spondylosis can affect a person at any age; however, older people are more susceptible.\n";
            tv2.setText(desc);
        }
        if(str.equals("Migraine")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="A migraine is a medical condition which usually causes a pounding, throbbing headache on one side of the head. The pain may be very bad and hurt so much that a person may have a hard time doing anything. While most people who have migraines get a headache, not everyone does. There are different kinds of migraines, and some do not cause a headache but do have other symptoms.\n" +
                    "Symptoms:\n" +
                    "Most migraines cause a headache and nausea and might make the person dizzy or very sensitive to bright lights or loud noises. Some people have \"auras\" before a migraine starts, which means their ability to see becomes different. They may see funny patterns, have blurry vision, or may not be able to see at all. Other senses can change before or during a migraine, and the person may sense funny smells or tastes. Migraines can last a long time. Migraines usually last between four and 72 hours.\n" +
                    "\n" + "Diagnosis:\n" +
                    "There are no specific tests to diagnose migraine but a doctor may use different tests to rule out other causes for a person's symptoms. The diagnosis of migraine is a clinical diagnosis which means it is based upon a person's medical history that a person reports to the doctor.\n";
            tv2.setText(desc);
        }
        if(str.equals("Hypertension ")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Hypertension (HTN or HT), also known as high blood pressure (HBP), is a long-term medical condition in which the blood pressure in the arteries is persistently elevated. High blood pressure typically does not cause symptoms. Long-term high blood pressure, however, is a major risk factor for coronary artery disease, stroke, heart failure, atrial fibrillation, peripheral arterial disease, vision loss, chronic kidney disease, and dementia.\n" +
                    "Symptoms:\n" +
                    "Hypertension is rarely accompanied by symptoms, and its identification is usually through screening, or when seeking healthcare for an unrelated problem. Some people with high blood pressure report headaches (particularly at the back of the head and in the morning), as well as lightheadedness, vertigo, tinnitus (buzzing or hissing in the ears), altered vision or fainting episodes.[20] These symptoms, however, might be related to associated anxiety rather than the high blood pressure itself.\n" +
                    "\n" + "Medication:\n" +
                    "\n" +
                    "Several classes of medications, collectively referred to as antihypertensive medications, are available for treating hypertension.\n" +
                    "First-line medications for hypertension include thiazide-diuretics, calcium channel blockers, angiotensin converting enzyme inhibitors (ACE inhibitors), and angiotensin receptor blockers (ARBs). These medications may be used alone or in combination (ACE inhibitors and ARBs are not recommended for use in combination); the latter option may serve to minimize counter-regulatory mechanisms that act to restore blood pressure values to pre-treatment levels. Most people require more than one medication to control their hypertension. Medications for blood pressure control should be implemented by a stepped care approach when target levels are not reached. \n";
            tv2.setText(desc);
        }
        if(str.equals("Bronchial Asthma")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Asthma is a long-term inflammatory disease of the airways of the lungs. It is characterized by variable and recurring symptoms, reversible airflow obstruction, and easily triggered bronchospasms. Symptoms include episodes of wheezing,coughing, chest tightness, and shortness of breath. These may occur a few times a day or a few times per week. Depending on the person, asthma symptoms may become worse at night or with exercise.\n" +
                    "Symptoms:\n" +
                    "Asthma is characterized by recurrent episodes of wheezing, shortness of breath, chest tightness, and coughing. Sputummay be produced from the lung by coughing but is often hard to bring up. During recovery from an asthma attack (exacerbation), it may appear pus-like due to high levels of white blood cells called eosinophils. Symptoms are usually worse at night and in the early morning or in response to exercise or cold air. Some people with asthma rarely experience symptoms, usually in response to triggers, whereas others may react frequently and readily and experience persistent symptoms.\n" +
                    "\n" + "Prevention:\n" +
                    "The evidence for the effectiveness of measures to prevent the development of asthma is weak. The World Health Organization recommends decreasing risk factors such as tobacco smoke, air pollution, chemical irritants including perfume, and the number of lower respiratory infections. Other efforts that show promise include: limiting smoke exposure in utero, breastfeeding, and increased exposure to daycare or large families, but none are well supported enough to be recommended for this indication.\n" +
                    "\n" + "Medication:\n" +
                    "Medications used to treat asthma are divided into two general classes: quick-relief medications used to treat acute symptoms; and long-term control medications used to prevent further exacerbation. Antibiotics are generally not needed for sudden worsening of symptoms or for treating asthma at any time.\n";
            tv2.setText(desc);
        }
        if(str.equals("Gastroenteritis")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Gastroenteritis, also known as infectious diarrhea and gastro, is inflammation of the gastrointestinal tract—the stomachand small intestine. Symptoms may include diarrhea, vomiting and abdominal pain. Fever, lack of energy and dehydrationmay also occur. This typically lasts less than two weeks. It is not related to influenza, though it has erroneously been called the \"stomach flu\"\n" +
                    "Symptoms:\n" +
                    "Gastroenteritis usually involves both diarrhea and vomiting. Sometimes, only one or the other is present. This may be accompanied by abdominal cramps. Signs and symptoms usually begin 12–72 hours after contracting the infectious agent. If due to a virus, the condition usually resolves within one week. Some viral infections also involve fever, fatigue, headache and muscle pain. If the stool is bloody, the cause is less likely to be viral and more likely to be bacterial. Some bacterial infections cause severe abdominal pain and may persist for several weeks.\n" +
                    "\n" +"Precautions:\n" +
                    "A supply of easily accessible uncontaminated water and good sanitation practices are important for reducing rates of infection and clinically significant gastroenteritis. Personal measures (such as hand washing with soap) have been found to decrease rates of gastroenteritis in both the developing and developed world by as much as 30%. Alcohol-based gels may also be effective. Food or drink that is thought to be contaminated should be avoided. Breastfeeding is important, especially in places with poor hygiene, as is improvement of hygiene generally. Breast milk reduces both the frequency of infections and their duration.\n";
            tv2.setText(desc);
        }
        if(str.equals("Diabetes ")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Diabetes mellitus (DM), commonly known as diabetes, is a group of metabolic disorders characterized by a high blood sugar level over a prolonged period of time. Symptoms often include frequent urination, increased thirst, and increased appetite. If left untreated, diabetes can cause many complications. Acute complications can include diabetic ketoacidosis, hyperosmolar hyperglycemic state, or death. Serious long-term complications include cardiovascular disease, stroke, chronic kidney disease, foot ulcers, damage to the nerves, damage to the eyes and cognitive impairment\n" +
                    "Symptoms:\n" +
                    "The classic symptoms of untreated diabetes are unintended weight loss, polyuria (increased urination), polydipsia(increased thirst), and polyphagia (increased hunger). Symptoms may develop rapidly (weeks or months) in type 1 diabetes, while they usually develop much more slowly and may be subtle or absent in type 2 diabetes.\n" +
                    "\n" +"Medication:\n" +
                    "Most medications used to treat diabetes act by lowering blood sugar levels through different mechanisms. There is broad consensus that when people with diabetes maintain tight glucose control – keeping the glucose levels in their blood within normal ranges – they experience fewer complications, such as kidney problems or eye problems. There is however debate as to whether this is appropriate and cost effective for people later in life in whom the risk of hypoglycemia may be more significant.\n";
            tv2.setText(desc);
        }
        if(str.equals("AIDS")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Human immunodeficiency virus infection and acquired immune deficiency syndrome (HIV/AIDS) is a spectrum of conditions caused by infection with the human immunodeficiency virus (HIV). Following initial infection a person may not notice any symptoms, or may experience a brief period of influenza-like illness. Typically, this is followed by a prolonged period with no symptoms. If the infection progresses, it interferes more with the immune system, increasing the risk of developing common infections such as tuberculosis, as well as other opportunistic infections, and tumors which are otherwise rare in people who have normal immune function. These late symptoms of infection are referred to as acquired immunodeficiency syndrome (AIDS). This stage is often also associated with unintended weight loss.\n" +
                    "There is currently no cure, nor an effective HIV vaccine. Treatment consists of highly active antiretroviral therapy (HAART) which slows progression of the disease. As of 2010more than 6.6 million people were receiving this in low- and middle-income countries Treatment also includes preventive and active treatment of opportunistic infections. As of March 2020, two persons have been successfully cleared of HIV. Rapid initiation of anti-retroviral therapy within one week of diagnosis appear to improve treatment outcomes in low and medium-income settings\n";
            tv2.setText(desc);
        }
        if(str.equals("Peptic ulcer disease")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Peptic ulcer disease (PUD) is a break in the inner lining of the stomach, the first part of the small intestine, or sometimes the lower esophagus. An ulcer in the stomach is called a gastric ulcer, while one in the first part of the intestines is aduodenal ulcer. The most common symptoms of a duodenal ulcer are waking at night with upper abdominal pain and upper abdominal pain that improves with eating. With a gastric ulcer, the pain may worsen with eating. The pain is often described as a burning or dull ache. Other symptoms include belching, vomiting, weight loss, or poor appetite. About a third of older people have no symptoms. Complications may include bleeding, perforation, and blockage of the stomach.[2]Bleeding occurs in as many as 15% of cases.\n" +
                    "Symptoms:\n" +
                    "\n" +
                    "Signs and symptoms of a peptic ulcer can include one or more of the following:\n" +
                    "•\tabdominal pain, classically epigastric, strongly correlated with mealtimes. In case of duodenal ulcers, the pain appears about three hours after taking a meal and wakes the person from sleep;\n" +
                    "•\tbloating and abdominal fullness;\n" +
                    "•\twaterbrash (a rush of saliva after an episode of regurgitation to dilute the acid in esophagus, although this is more associated with gastroesophageal reflux disease);\n" +
                    "•\tnausea and copious vomiting;\n" +
                    "•\tloss of appetite and weight loss, in gastric ulcer;\n";
            tv2.setText(desc);
        }
        if(str.equals("Drug Reaction")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "An adverse drug reaction (ADR) is an injury caused by taking medication. ADRs may occur following a single dose or prolonged administration of a drug or result from the combination of two or more drugs. The meaning of this term differs from the term \"side effect\" because side effects can be beneficial as well as detrimental. The study of ADRs is the concern of the field known as pharmacovigilance. An adverse drug event (ADE) refers to any injury occurring at the time a drug is used, whether or not it is identified as a cause of the injury. An ADR is a special type of ADE in which a causative relationship can be shown. ADRs are only one type of medication-related harm, as harm can also be caused by omitting to take indicated medications.\n" +
                    "\n" +"Causes:\n" +
                    "The U.S Food and Drug Administration defines a serious adverse event as one when the patient outcome is one of the following: \n" +
                    "•\tDeath\n" +
                    "•\tLife-threatening\n" +
                    "•\tHospitalization (initial or prolonged)\n" +
                    "•\tDisability - significant, persistent, or permanent change, impairment, damage or disruption in the patient's body function/structure, physical activities or quality of life.\n" +
                    "•\tCongenital abnormality\n" +
                    "•\tRequires intervention to prevent permanent impairment or damage\n";
            tv2.setText(desc);
        }
        if(str.equals("Chronic cholestasis")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Cholestasis is a condition where bile cannot flow from the liver to the duodenum. The two basic distinctions are an obstructive type of cholestasis where there is a mechanical blockage in the duct system that can occur from a gallstone ormalignancy, and metabolic types of cholestasis which are disturbances in bile formation that can occur because of genetic defects or acquired as a side effect of many medications.\n" +
                    "•\tItchiness (pruritus). Pruritus is the primary symptom of cholestasis and is thought to be due to interactions of serum bile acids with opioidergic nerves. In fact, the opioid antagonist naltrexone is used to treat pruritus due to cholestasis.\n" +
                    "•\tJaundice. Jaundice is an uncommon occurrence in intrahepatic (metabolic) cholestasis, but is common in obstructive cholestasis.\n" +
                    "•\tPale stool. This symptom implies obstructive cholestasis.\n" +
                    "•\tDark urine\n" +
                    "\n" +
                    "Possible causes:\n" +
                    "•\tpregnancy\n" +
                    "•\tandrogens\n" +
                    "•\tbirth control pills\n" +
                    "•\tantibiotics (such as TMP/SMX)\n" +
                    "•\tabdominal mass (e.g. cancer)\n" +
                    "•\tbiliary atresia and other pediatric liver diseases\n" +
                    "•\tbiliary trauma\n" +
                    "•\tcongenital anomalies of the biliary tract\n";
            tv2.setText(desc);
        }
        if(str.equals("GERD")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "GERD\n" +
                    "Gastroesophageal reflux disease (GERD), also known as acid reflux, is a long-term condition in which stomach contents rise up into the esophagus, resulting in either symptoms or complications. Symptoms include the taste of acid in the back of the mouth, heartburn, bad breath, chest pain, regurgitation, breathing problems, and wearing away of the teeth.Complications include esophagitis, esophageal stricture, and Barrett's esophagus.\n" +
                    "\n" +"Symptoms:\n" +
                    "The most common symptoms of GERD in adults are an acidic taste in the mouth, regurgitation, and heartburn. Less common symptoms include pain with swallowing/sore throat, increased salivation (also known as water brash), nausea, chest pain, and coughing.\n" +
                    "Acid reflux is due to poor closure of the lower esophageal sphincter, which is at the junction between the stomach and the esophagus. Factors that can contribute to GERD:\n" +
                    "Hiatal hernia, which increases the likelihood of GERD due to mechanical and motility factors. \n" +
                    "Obesity: increasing body mass index is associated with more severe GERD.] In a large series of 2,000 patients with symptomatic reflux disease, it has been shown that 13% of changes in esophageal acid exposure is attributable to changes in body mass index.\n" +
                    "\n" +
                    "Treatment:\n" +
                    "The primary medications used for GERD are proton-pump inhibitors, H2 receptor blockers and antacids with or without alginic acid. The use of acid suppression therapy is a common response to GERD symptoms and many people get more of this kind of treatment than their case merits. The overuse of acid suppression is a problem because of the side effects and costs\n";
            tv2.setText(desc);
        }

        if(str.equals("Impetigo")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Impetigo:\n" +
                    "\n" +
                    "Impetigo is a common and highly contagious skin infection that mainly affects infants and children. Impetigo usually appears as red sores on the face, especially around a child's nose and mouth, and on hands and feet. The sores burst and develop honey-coloured crusts. Impetigo most commonly occurs in children ages 2 to 5.\n" +
                    "\n" +
                    "Causes:\n" +
                    "You're exposed to the bacteria that cause impetigo when you come into contact with the sores of someone who's infected or with items they've touched — such as clothing, bed linen, towels and even toys.\n" +
                    "\n" +
                    "Prevention:\n" +
                    "Keeping skin clean is the best way to keep it healthy. It's important to wash cuts, scrapes, insect bites and other wounds right away.\n" +
                    "To help prevent impetigo from spreading to others:\n" +
                    "Gently wash the affected areas with mild soap and running water and then cover lightly with gauze.\n" +
                    "Wash an infected person's clothes, linens and towels every day and don't share them with anyone else in your family.\n" +
                    "Wear gloves when applying antibiotic ointment and wash your hands thoroughly afterward.\n" +
                    "Cut an infected child's nails short to prevent damage from scratching.\n" +
                    "Wash hands frequently.\n" +
                    "Keep your child home until your doctor says he or she isn't contagious.\n" +
                    "\n" +
                    "Diagnosis\n" +
                    "Doctors usually diagnose impetigo by looking at the distinctive sores. Lab tests generally aren't necessary.\n" +
                    "If the sores don't clear, even with antibiotic treatment, your doctor may take a sample of the liquid produced by a sore and test it to see what types of antibiotics might work best on it. Some types of the bacteria that cause impetigo have become resistant to certain antibiotic drugs.\n" +
                    "\n" +
                    "Treatment\n" +
                    "Impetigo typically is treated with an antibiotic ointment or cream that you apply directly to the sores. You may need to first soak the affected area in warm water or use wet compresses to help remove the scabs so the antibiotic can penetrate the skin.\n" +
                    "If you have more than just a few impetigo sores, your doctor might recommend antibiotic drugs that can be taken by mouth. Be sure to finish the entire course of medication even if the sores are healed. This helps prevent the infection from recurring and makes antibiotic resistance less likely.\n" +
                    "\n" +
                    "\n";
            tv2.setText(desc);
        }
        if(str.equals("Psoriasis")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Psoriasis:\n" +
                    "\n" +
                    "Psoriasis is a skin disorder that causes skin cells to multiply up to 10 times faster than normal. This makes the skin build up into bumpy red patches covered with white scales. They can grow anywhere, but most appear on the scalp, elbows, knees, and lower back.\n" +
                    "\n" +
                    "Causes:\n" +
                    "No one knows the exact cause of psoriasis, but experts believe that it’s a combination of things. Something wrong with the immune system causes inflammation, triggering new skin cells to form too quickly. Normally, skin cells are replaced every 10 to 30 days. With psoriasis, new cells grow every 3 to 4 days. The build-up of old cells being replaced by new ones creates those silver scales.\n" +
                    "Psoriasis tends to run in families, but it may be skip generations. For instance, a grandfather and his grandson may be affected, but not the child's mother.\n" +
                    "Things that can trigger an outbreak of psoriasis include: Cuts, scrapes, or surgery, Emotional stress, Strep infections, Medications, including, Blood pressure medications (like beta-blockers), Hydroxychloroquine, antimalarial medication\n" +
                    "\n" +
                    "Diagnosis:\n" +
                    "Physical exam. It’s usually easy for your doctor to diagnose psoriasis, especially if you have plaques on areas such as your: scalp, ears, elbows, knees, belly button, nails\n" +
                    "Your doctor will give you a full physical exam and ask if people in your family have psoriasis.\n" +
                    "\n" +
                    "Treatment:\n" +
                    "Luckily, there are many treatments. Some slow the growth of new skin cells, and others relieve itching and dry skin. Your doctor will select a treatment plan that is right for you based on the size of your rash, where it is on your body, your age, your overall health, and other things. \n" +
                    "Common treatments include: steroid creams, moisturizers for dry skin, coal tar (a common treatment for scalp psoriasis available in lotions, creams, foams, shampoos, and bath solutions), cream or ointment (a strong kind ordered by your doctor. Vitamin D in foods and pills has no effect.), retinoid creams\n";
            tv2.setText(desc);
        }
        if(str.equals("Urinary tract infection")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Urinary tract infection:\n" +
                    "\n" +
                    "A urinary tract infection (UTI) is an infection in any part of your urinary system — your kidneys, ureters, bladder and urethra. Most infections involve the lower urinary tract — the bladder and the urethra.\n" +
                    "\n" +
                    "Causes\n" +
                    "Urinary tract infections typically occur when bacteria enter the urinary tract through the urethra and begin to multiply in the bladder. \n" +
                    "The most common UTIs occur mainly in women and affect the bladder and urethra.\n" +
                    "Infection of the bladder (cystitis): This type of UTI is usually caused by Escherichia coli, a type of bacteria commonly found in the gastrointestinal tract. However, sometimes other bacteria are responsible.\n" +
                    "Infection of the urethra (urethritis): This type of UTI can occur when GI bacteria spread from the anus to the urethra. Also, because the female urethra is close to the vagina, sexually transmitted \n" +
                    "infections, such as herpes, gonorrhoea, chlamydia and mycoplasma, can cause urethritis.\n" +
                    "\n" +
                    "Prevention\n" +
                    "You can take these steps to reduce your risk of urinary tract infections:\n" +
                    "Drink plenty of liquids, especially water. Drinking water helps dilute your urine and ensures that you'll urinate more frequently — allowing bacteria to be flushed from your urinary tract before an infection can begin.\n" +
                    "Drink cranberry juice. Although studies are not conclusive that cranberry juice prevents UTIs, it is likely not harmful.\n" +
                    "Wipe from front to back. Doing so after urinating and after a bowel movement helps prevent bacteria in the anal region from spreading to the vagina and urethra.\n" +
                    "Empty your bladder soon after intercourse. Also, drink a full glass of water to help flush bacteria.\n" +
                    "\n" +
                    "Treatment\n" +
                    "Antibiotics usually are the first line treatment for urinary tract infections. Which drugs are prescribed and for how long depend on your health condition and the type of bacteria found in your urine.\n" +
                    "Drugs commonly recommended for simple UTIs include: Trimethoprim/ sulfamethoxazole (Bactrim, Septra, others), Fosfomycin (Monurol), Nitrofurantoin (Macrodantin, Macrobid), Cephalexin (Keflex), Ceftriaxone\n" +
                    "The group of antibiotic medicines known as fluoroquinolones — such as ciprofloxacin (Cipro), levofloxacin and others — isn't commonly recommended for simple UTIs, as the risks of these medicines generally outweigh the benefits for treating uncomplicated UTIs. In some cases, such as a complicated UTI or kidney infection, your doctor might prescribe a fluoroquinolone medicine if there are no other treatment options.\n";
            tv2.setText(desc);
        }
        if(str.equals("Acne")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Acne:\n" +
                    "\n" +
                    "Acne is a skin condition that occurs when your hair follicles become plugged with oil and dead skin cells. It causes whiteheads, blackheads or pimples. Acne is most common among teenagers, though it affects people of all ages.\n" +
                    "\n" +
                    "Causes:\n" +
                    "Four main factors cause acne: excess oil (sebum) production, hair follicles clogged by oil and dead skin cells, bacteria, inflammation.\n" +
                    "Acne typically appears on your face, forehead, chest, upper back and shoulders because these areas of skin have the most oil (sebaceous) glands. Hair follicles are connected to oil glands.\n" +
                    "Certain things may trigger or worsen acne: hormonal changes, certain medications, diet, stress\n" +
                    "\n" +
                    "Treatment:\n" +
                    "Acne medications work by reducing oil production and swelling or by treating bacterial infection. With most prescription acne drugs, you may not see results for four to eight weeks. It can take many months or years for your acne to clear up completely.\n" +
                    "The treatment regimen your doctor recommends depends on your age, the type and severity of your acne, and what you are willing to commit to. For example, you may need to wash and apply medications to the affected skin twice a day for several weeks. Topical medications and drugs you take by mouth (oral medication) are often used in combination. Treatment options for pregnant women are limited due to the risk of side effects.\n";
            tv2.setText(desc);
        }
        if(str.equals("(vertigo) Paroxysmal  Positional Vertigo")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Benign paroxysmal positional vertigo (BPPV):\n" +
                    "\n" +
                    "Benign paroxysmal positional vertigo (BPPV) is one of the most common causes of vertigo — the sudden sensation that you're spinning or that the inside of your head is spinning.\n" +
                    "\n" +
                    "Causes\n" +
                    "Often, there's no known cause for BPPV. This is called idiopathic BPPV.\n" +
                    "When there is a known cause, BPPV is often associated with a minor to severe blow to your head. Less common causes of BPPV include disorders that damage your inner ear or, rarely, damage that occurs during ear surgery or long periods positioned on your back, such as in a dentist chair. BPPV also has been associated with migraines.\n" +
                    "\n" +
                    "When to see a doctor:\n" +
                    "Generally, see your doctor if you experience any recurrent, sudden, severe, or prolonged and unexplained dizziness or vertigo.\n" +
                    "\n" +
                    "Diagnosis\n" +
                    "Your doctor may do a series of tests to determine the cause of your dizziness. During a physical exam, your doctor will likely look for:\n" +
                    "Signs and symptoms of dizziness that are prompted by eye or head movements and then decrease in less than one minute\n" +
                    "Dizziness with specific eye movements that occur when you lie on your back with your head turned to one side and tipped slightly over the edge of the examination bed\n" +
                    "Involuntary movements of your eyes from side to side\n" +
                    "Inability to control your eye movements\n" +
                    "If your doctor can't find the cause of your signs and symptoms, he or she may order additional testing, such as:\n" +
                    "Electronystagmography (ENG) or videonystagmography (VNG): The purpose of these tests is to detect abnormal eye movement. \n" +
                    "Magnetic resonance imaging (MRI): This test uses a magnetic field and radio waves to create cross-sectional images of your head and body. \n" +
                    "\n" +
                    "Treatment\n" +
                    "Benign paroxysmal positional vertigo may go away on its own within a few weeks or months. But, to help relieve BPPV sooner, your doctor, audiologist or physical therapist may treat you with a series of movements known as the canalith repositioning procedure.\n" +
                    "Canalith repositioning:\n" +
                    "The goal is to move particles from the fluid-filled semicircular canals of your inner ear into a tiny baglike open area (vestibule) that houses one of the otolith organs in your ear, where these particles don't cause trouble and are more easily resorbed.\n" +
                    "Each position is held for about 30 seconds after any symptoms or abnormal eye movements stop. This procedure usually works after one or two treatments.Your doctor will likely teach you how to perform the procedure on yourself so that you can do it at home if needed.\n";
            tv2.setText(desc);
        }
        if(str.equals("Arthritis")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Arthritis:\n" +
                    "Arthritis is the swelling and tenderness of one or more of your joints. The main symptoms of arthritis are joint pain and stiffness, which typically worsen with age. The most common types of arthritis are osteoarthritis and rheumatoid arthritis.\n" +
                    "\n" +
                    "Causes:\n" +
                    "Cartilage is a firm but flexible connective tissue in your joints. It protects the joints by absorbing the pressure and shock created when you move and put stress on them. A reduction in the normal amount of this cartilage tissue cause some forms of arthritis.\n" +
                    "A common form of arthritis, RA, is an autoimmune disorder. It occurs when your body’s immune system attacks the tissues of the body. These attacks affect the synovium, a soft tissue in your joints that produces a fluid that nourishes the cartilage and lubricates the joints.\n" +
                    "\n" +
                    "Diagnosis:\n" +
                    "Seeing your primary care physician is a good first step if you’re unsure who to see for an arthritis diagnosis. They will perform a physical exam to check for fluid around the joints, warm or red joints, and limited range of motion in the joints. Your doctor can refer you to a specialist if needed.\n" +
                    "If you’re experiencing severe symptoms, you may choose to schedule an appointment with a rheumatologist first. This may lead to a faster diagnosis and treatment.\n" +
                    "\n" +
                    "Treatment:\n" +
                    "The main goal of treatment is to reduce the amount of pain you’re experiencing and prevent additional damage to the joints. Some people find heating pads and ice packs to be soothing. Others use mobility assistance devices, like canes or walkers, to help take pressure off sore joints.\n" +
                    "\n" +
                    "Medication:\n" +
                    "A number of different types of medication treat arthritis:\n" +
                    "Analgesics, such as hydrocodone (Vicodin) or acetaminophen (Tylenol), are effective for pain management, but don’t help decrease inflammation.\n" +
                    "Nonsteroidal anti-inflammatory drugs (NSAIDs), such as ibuprofen (Advil) and salicylates, help control pain and inflammation. Salicylates can thin the blood, so they should be used very cautiously with additional blood thinning medications.\n" +
                    "Menthol or capsaicin creams block the transmission of pain signals from your joints.\n" +
                    "\n" +
                    "Surgery:\n" +
                    "Surgery to replace your joint with an artificial one may be an option. This form of surgery is most commonly performed to replace hips and knees.\n" +
                    "If your arthritis is most severe in your fingers or wrists, your doctor may perform a joint fusion. In this procedure, the ends of your bones are locked together until they heal and become one.\n" +
                    "\n" +
                    "Physical therapy:\n" +
                    "Physical therapy involving exercises that help strengthen the muscles around the affected joint is a core component of arthritis treatment.\n";
            tv2.setText(desc);
        }
        if(str.equals("Osteoarthristis")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Osteoarthristis:\n" +
                    "\n" +
                    "Osteoarthritis occurs when the protective cartilage that cushions the ends of your bones wears down over time. Although osteoarthritis can damage any joint, the disorder most commonly affects joints in your hands, knees, hips and spine.\n" +
                    "\n" +
                    "Causes:\n" +
                    "Osteoarthritis occurs when the cartilage that cushions the ends of bones in your joints gradually deteriorates. Cartilage is a firm, slippery tissue that enables nearly frictionless joint motion. Eventually, if the cartilage wears down completely, bone will rub on bone.\n" +
                    "Osteoarthritis has often been referred to as a \"wear and tear\" disease. But besides the breakdown of cartilage, osteoarthritis affects the entire joint. It causes changes in the bone and deterioration of the connective tissues that hold the joint together and attach muscle to bone. It also causes inflammation of the joint lining.\n" +
                    "\n" +
                    "When to see a doctor:\n" +
                    "If you have joint pain or stiffness that doesn't go away, make an appointment with your doctor.\n" +
                    "\n" +
                    "Treatment:\n" +
                    "Osteoarthritis can't be reversed, but treatments can reduce pain and help you move better.\n" +
                    "\n" +
                    "Medications:\n" +
                    "Acetaminophen. Acetaminophen (Tylenol, others) has been shown to help some people with osteoarthritis who have mild to moderate pain. Taking more than the recommended dose of acetaminophen can cause liver damage.\n" +
                    "Nonsteroidal anti-inflammatory drugs (NSAIDs).  NSAIDs, such as ibuprofen (Advil, Motrin IB, others) and naproxen sodium (Aleve, others), taken at the recommended doses, typically relieve osteoarthritis pain. \n" +
                    "\n" +
                    "Therapy:\n" +
                    "Physical therapy. A physical therapist can show you exercises to strengthen the muscles around your joint, increase your flexibility and reduce pain. Regular gentle exercise that you do on your own, such as swimming or walking, can be equally effective.\n" +
                    "Occupational therapy. An occupational therapist can help you discover ways to do everyday tasks without putting extra stress on your already painful joint. \n" +
                    "\n" +
                    "Surgical and other procedures:\n" +
                    "If conservative treatments don't help, you may want to consider procedures such as:\n" +
                    "Cortisone injections. Injections of corticosteroid medications may relieve pain in your joint. During this procedure your doctor numbs the area around your joint, then places a needle into the space within your joint and injects medication. \n" +
                    "Lubrication injections. Injections of hyaluronic acid may offer pain relief by providing some cushioning in your knee, though some research suggests these injections offer no more relief than a placebo. \n" +
                    "Joint replacement. In joint replacement surgery (arthroplasty), your surgeon removes your damaged joint surfaces and replaces them with plastic and metal parts. Surgical risks include infections and blood clots.\n";
            tv2.setText(desc);
        }
        if(str.equals("Hypoglycemia")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Hypoglycemia:\n" +
                    "\n" +
                    "Hypoglycemia is a condition caused by low blood glucose (blood sugar) levels. Glucose is the main way your body gets energy. The condition is most common in people with diabetes who have issues with medicine, food, or exercise. But sometimes people who don't have diabetes can also get low blood glucose.\n" +
                    "\n" +
                    "Causes:\n" +
                    "Hypoglycemia is either reactive or non-reactive. Each type has different causes:\n" +
                    "Reactive hypoglycemia: Reactive hypoglycemia occurs within a few hours after a meal. An overproduction of insulin causes reactive hypoglycemia. Having reactive hypoglycemia may mean that you’re at risk for developing diabetes.\n" +
                    "Non-reactive hypoglycemia: Non-reactive hypoglycemia isn’t necessarily related to meals and may be due to an underlying disease. Causes of non-reactive, or fasting hypoglycemia can include: some medications, like those used in adults and children with kidney failure, excess amounts of alcohol, which can stop your liver from producing glucose and pregnancy.\n" +
                    "\n" +
                    "When to see a doctor:\n" +
                    "Seek a doctor's help immediately if:\n" +
                    "You have what might be hypoglycemia symptoms and you don't have diabetes.\n" +
                    "You have diabetes and hypoglycemia isn't responding to treatment, such as drinking juice or regular soft drinks, eating candy, or taking glucose tablets.\n" +
                    "\n" +
                    "\n" +
                    "Treatment\n" +
                    "Immediate treatment\n" +
                    "If you have symptoms of hypoglycemia, do the following:\n" +
                    "Eat or drink 15 to 20 grams of fast-acting carbohydrates: These are sugary foods without protein or fat that are easily converted to sugar in the body. Try glucose tablets or gel, fruit juice, regular — not diet — soft drinks, honey, and sugary candy.\n" +
                    "Recheck blood sugar levels 15 minutes after treatment: If blood sugar levels are still under 70 mg/dL (3.9 mmol/L), eat or drink another 15 to 20 grams of fast-acting carbohydrate, and recheck the blood sugar level again in 15 minutes. Repeat these steps until the blood sugar is above 70 mg/dL (3.9 mmol/L).\n" +
                    "Have a snack or meal: Once your blood sugar is normal, eating a snack or meal can help stabilize it and replenish your body's glycogen stores.\n";
            tv2.setText(desc);
        }
        if(str.equals("Hyperthyroidism")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Hyperthyroidism:\n" +
                    "Hyperthyroidism (overactive thyroid) occurs when your thyroid gland produces too much of the hormone thyroxine. Hyperthyroidism can accelerate your body's metabolism, causing unintentional weight loss and a rapid or irregular heartbeat.\n" +
                    "\n" +
                    "Causes:\n" +
                    "Normally, your thyroid releases the right amount of hormones, but sometimes it produces too much T4. This may occur for a number of reasons, including:\n" +
                    "Graves' disease: Graves' disease is an autoimmune disorder in which antibodies produced by your immune system stimulate your thyroid to produce too much T4. It's the most common cause of hyperthyroidism.\n" +
                    "Thyroiditis: Sometimes your thyroid gland can become inflamed after pregnancy, due to an autoimmune condition or for unknown reasons. The inflammation can cause excess thyroid hormone stored in the gland to leak into your bloodstream. \n" +
                    "\n" +
                    "When to see a doctor\n" +
                    "If you experience unexplained weight loss, a rapid heartbeat, unusual sweating, swelling at the base of your neck or other signs and symptoms associated with hyperthyroidism, see your doctor. It's important to completely describe the changes you've observed, because many signs and symptoms of hyperthyroidism may be associated with a number of other conditions.\n" +
                    "\n" +
                    "Treatment:\n" +
                    "Several treatments for hyperthyroidism exist. The best approach for you depends on your age, physical condition, the underlying cause of the hyperthyroidism, personal preference and the severity of your disorder. Possible treatments include:\n" +
                    "Radioactive iodine: Taken by mouth, radioactive iodine is absorbed by your thyroid gland, where it causes the gland to shrink. Symptoms usually subside within several months. Excess radioactive iodine disappears from the body in weeks to months.\n" +
                    "Anti-thyroid medications: These medications gradually reduce symptoms of hyperthyroidism by preventing your thyroid gland from producing excess amounts of hormones. \n" +
                    "Surgery (thyroidectomy): In a thyroidectomy, your doctor removes most of your thyroid gland. Risks of this surgery include damage to your vocal cords and parathyroid glands — four tiny glands situated on the back of your thyroid gland that help control the level of calcium in your blood.\n";
            tv2.setText(desc);
        }
        if(str.equals("Hypothyroidism")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc="Hypothyroidism:\n" +
                    "Causes:\n" +
                    "When your thyroid doesn't produce enough hormones, the balance of chemical reactions in your body can be upset. There can be a number of causes, including autoimmune disease, hyperthyroidism treatments, radiation therapy, thyroid surgery and certain medications. Hypothyroidism results when the thyroid gland fails to produce enough hormones. \n" +
                    "Hypothyroidism may be due to a number of factors, including:\n" +
                    "Autoimmune disease, over-response to hyperthyroidism treatment, thyroid surgery, radiation therapy, medications\n" +
                    "Less often, hypothyroidism may result from one of the following:\n" +
                    "Congenital disease, pituitary disorder, pregnancy, iodine deficiency \n" +
                    "\n" +
                    "When to see a doctor:\n" +
                    "See your doctor if you're feeling tired for no reason or have any of the other signs or symptoms of hypothyroidism, such as dry skin, a pale, puffy face, constipation or a hoarse voice.\n" +
                    "If you're receiving hormone therapy for hypothyroidism, schedule follow-up visits as often as your doctor recommends. Initially, it's important to make sure you're receiving the correct dose of medicine. And over time, the dose you need may change.\n" +
                    "\n" +
                    "Treatment\n" +
                    "Standard treatment for hypothyroidism involves daily use of the synthetic thyroid hormone levothyroxine (Levo-T, Synthroid, others). This oral medication restores adequate hormone levels, reversing the signs and symptoms of hypothyroidism.\n" +
                    "You'll likely start to feel better soon after you start treatment. The medication gradually lowers cholesterol levels elevated by the disease and may reverse any weight gain. Treatment with levothyroxine will likely be lifelong, but because the dosage you need may change, your doctor is likely to check your TSH level every year.\n";
            tv2.setText(desc);
        }
        if(str.equals("Varicose veins")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Varicose veins:\n" +
                    "Varicose veins are twisted, enlarged veins. Any superficial vein may become varicosed, but the veins most commonly affected are those in your legs. That's because standing and walking upright increases the pressure in the veins of your lower body. For many people, varicose veins and spider veins — a common, mild variation of varicose veins — are simply a cosmetic concern. For other people, varicose veins can cause aching pain and discomfort. Sometimes varicose veins lead to more-serious problems.\n" +
                    "\n" +
                    "Causes:\n" +
                    "Weak or damaged valves can lead to varicose veins. Arteries carry blood from your heart to the rest of your tissues, and veins return blood from the rest of your body to your heart, so the blood can be recirculated. To return blood to your heart, the veins in your legs must work against gravity.\n" +
                    "Muscle contractions in your lower legs act as pumps, and elastic vein walls help blood return to your heart. Tiny valves in your veins open as blood flows toward your heart then close to stop blood from flowing backward. If these valves are weak or damaged, blood can flow backward and pool in the vein, causing the veins to stretch or twist.\n" +
                    "\n" +
                    "When to see a doctor:\n" +
                    "Self-care — such as exercise, elevating your legs or wearing compression stockings — can help you ease the pain of varicose veins and may prevent them from getting worse. But if you're concerned about how your veins look and feel and self-care measures haven't stopped your condition from getting worse, see your doctor.\n" +
                    "\n" +
                    "Complications of varicose veins, although rare, can include:\n" +
                    "Ulcers: Painful ulcers may form on the skin near varicose veins, particularly near the ankles.\n" +
                    "Blood clots: Occasionally, veins deep within the legs become enlarged. In such cases, the affected leg may become painful and swell. \n" +
                    "Bleeding: Occasionally, veins very close to the skin may burst. This usually causes only minor bleeding. But any bleeding requires medical attention.\n" +
                    "\n" +
                    "Prevention:\n" +
                    "There's no way to completely prevent varicose veins. But improving your circulation and muscle tone may reduce your risk of developing varicose veins or getting additional ones. The same measures you can take to treat the discomfort from varicose veins at home can help prevent varicose veins, including:\n" +
                    "Exercising, watching your weight, eating a high-fibre, low-salt diet, avoiding high heels and tight hosiery, elevating your legs, changing your sitting or standing position regularly.\n";
            tv2.setText(desc);
        }
        if(str.equals("Heart attack")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Heart attack:\n" +
                    "Causes:\n" +
                    "A heart attack occurs when the flow of blood to the heart is blocked. The blockage is most often a build-up of fat, cholesterol and other substances, which form a plaque in the arteries that feed the heart (coronary arteries). Sometimes, a plaque can rupture and form a clot that blocks blood flow. The interrupted blood flow can damage or destroy part of the heart muscle.\n" +
                    "\n" +
                    "Risk Factors You Can Control:\n" +
                    "The major risk factors for a heart attack that you can control include:\n" +
                    "Smoking, High blood pressure, High blood cholesterol, Overweight and obesity, An unhealthy diet (for example, a diet high in saturated fat, trans fat, cholesterol, and sodium), Lack of routine physical activity, High blood sugar due to insulin resistance or diabetes.\n" +
                    "\n" +
                    "Most common symptoms:\n" +
                    "The most common warning symptoms of a heart attack for both men and women are:\n" +
                    "Chest pain or discomfort: Most heart attacks involve discomfort in the centre or left side of the chest. The discomfort usually lasts for more than a few minutes or goes away and comes back. It can feel like pressure, squeezing, fullness, or pain. It also can feel like heartburn or indigestion. The feeling can be mild or severe.\n" +
                    "Upper body discomfort: You may feel pain or discomfort in one or both arms, the back, shoulders, neck, jaw, or upper part of the stomach (above the belly button).\n" +
                    "Shortness of breath: This may be your only symptom, or it may occur before or along with chest pain or discomfort. It can occur when you are resting or doing a little bit of physical activity.\n" +
                    "\n" +
                    "Diagnosis:\n" +
                    "Ideally, your doctor should screen you during regular physical exams for risk factors that can lead to a heart attack. If you're in an emergency setting for symptoms of a heart attack, you'll be asked about your symptoms and have your blood pressure, pulse and temperature checked. You'll be connected to a heart monitor and have tests to see if you're having a heart attack.\n" +
                    "Tests to diagnose a heart attack include: Electrocardiogram (ECG), Chest X-ray, Echocardiogram, Coronary catheterization (angiogram), Cardiac CT or MRI. \n" +
                    "\n";
            tv2.setText(desc);
        }

        if(str.equals("Dimorphic hemmorhoids(piles)")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Dimorphic hemorrhoids(piles):\n" +
                    "Hemorrhoids (HEM-uh-roids), also called piles, are swollen veins in your anus and lower rectum, similar to varicose veins. Hemorrhoids can develop inside the rectum (internal hemorrhoids) or under the skin around the anus (external hemorrhoids).\n" +
                    "\n" +
                    "Causes:\n" +
                    "The veins around your anus tend to stretch under pressure and may bulge or swell. Hemorrhoids can develop from increased pressure in the lower rectum due to:\n" +
                    "Straining during bowel movements, sitting for long periods of time on the toilet, having chronic diarrhoea or constipation, being obese, being pregnant, having anal intercourse, eating a low-fibre diet, regular heavy lifting.\n" +
                    "\n" +
                    "Prevention:\n" +
                    "The best way to prevent hemorrhoids is to keep your stools soft, so they pass easily. To prevent hemorrhoids and reduce symptoms of hemorrhoids, follow these tips:\n" +
                    "Eat high-fibre foods: Eat more fruits, vegetables and whole grains. Doing so softens the stool and increases its bulk, which will help you avoid the straining that can cause hemorrhoids. \n" +
                    "Drink plenty of fluids: Drink six to eight glasses of water and other liquids (not alcohol) each day to help keep stools soft.\n" +
                    "Don't strain: Straining and holding your breath when trying to pass a stool creates greater pressure in the veins in the lower rectum.\n" +
                    "Go as soon as you feel the urge: If you wait to pass a bowel movement and the urge goes away, your stool could dry out and be harder to pass.\n" +
                    "\n" +
                    "Diagnosis:\n" +
                    "Your doctor might be able to see external hemorrhoids. Diagnosing internal hemorrhoids might include examination of your anal canal and rectum.\n" +
                    "Digital examination. Your doctor inserts a gloved, lubricated finger into your rectum. He or she feels for anything unusual, such as growths.\n" +
                    "Visual inspection. Because internal hemorrhoids are often too soft to be felt during a rectal exam, your doctor might examine the lower portion of your colon and rectum with an anoscope, proctoscope or sigmoidoscope.\n" +
                    "\n" +
                    "Treatment:\n" +
                    "If your hemorrhoids produce only mild discomfort, your doctor might suggest over-the-counter creams, ointments, suppositories or pads. These products contain ingredients such as witch hazel, or hydrocortisone and lidocaine, which can temporarily relieve pain and itching.\n" +
                    "Don't use an over-the-counter steroid cream for more than a week unless directed by your doctor because it can thin your skin.\n";
            tv2.setText(desc);
        }
        if(str.equals("Allergy")) {
            TextView tv1 =  findViewById(R.id.predicted_disease_name);
            tv1.setText(str);

            TextView tv2 =  findViewById(R.id.desc);
           desc= "Allergies, also known as allergic diseases, are a number of conditions caused by hypersensitivity of the immune system to typically harmless substances in the environment." +
                    " These diseases include hay fever, food allergies, atopic dermatitis,allergic asthma, and anaphylaxis." +
                    " Symptoms may include red eyes, an itchy rash, sneezing, a runny nose, shortness of breath, or swelling." +
                    " Food intolerances and food poisoning are separate conditions.\n" +
                    "Many allergens such as dust or pollen are airborne particles. In these cases, symptoms arise in areas in contact with air, such as eyes, nose, and lungs. For instance, allergic rhinitis, also known as hay fever, causes irritation of the nose, sneezing, itching, and redness of the eyes." +
                    " Inhaled allergens can also lead to increased production of mucus in the lungs, shortness of breath, coughing, and wheezing\n" +
                    "Risk factors for allergy can be placed in two general categories, namely host and environmental factors." +
                    " Host factors include heredity, sex, race, and age, with heredity being by far the most significant. However, there have been recent increases in the incidence of allergic disorders that cannot be explained by genetic factors alone. " +
                    "Four major environmental candidates are alterations in exposure to infectious diseases during early childhood, environmental pollution, allergen levels, and dietary changes.\n" +
                    "A drug allergy is an allergy to a drug, most commonly a medication, and is a form of adverse drug reaction. Medical attention should be sought immediately if an allergic reaction is suspected.\n" +
                    "An allergic reaction will not occur on the first exposure to a substance. The first exposure allows the body to create antibodies and memory lymphocyte cells for the antigen. However, drugs often contain many different substances, including dyes, which could cause allergic reactions.\n";
            tv2.setText(desc);
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference_prediction = FirebaseDatabase.getInstance("https://vcare-healthapp-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("disease_prediction");



        reference_prediction.child("pending").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        //    for (DataSnapshot snapshot3 : snapshot2.getChildren()) {

                            DiseasePrediction dp  = snapshot2.getValue(DiseasePrediction.class)  ;

                    int total= dp.getPid();
                    if(total>1){
                        DiseasePrediction details= new   DiseasePrediction(total+1,symList,str, 0,"",pname,user.getEmail(),category) ;
                        int finalVal = total + 1;//
                        // reference_prediction.child("pending").child(patemail).child("token").setValue(finalVal);
                        reference_prediction.child("pending").child(patemail).child(String.valueOf(new Date())).setValue(details);

                    }}}
                   /* D   iseasePrediction details = snapshot.getValue(DiseasePrediction.class);

                    reference_prediction.child("approved").child(patemail).setValue(details);
                    DiseasePrediction details1= new   DiseasePrediction(symList,str, 0,"",pname,user.getEmail(),category) ;

                    reference_prediction.child("pending").child(patemail).setValue(details1);

                }else{*/


               }else {
                    DiseasePrediction details = new DiseasePrediction(1000,symList, str, 0, "", pname, user.getEmail(), category);

                  //  reference_prediction.child("pending").child(patemail).child("token").setValue(1000);
                    reference_prediction.child("pending").child(patemail).child(String.valueOf(new Date())).setValue(details);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        }

        );

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //Booking_Appointments booking_appointments = new Booking_Appointments(0, "null");
        //reference_booking.child(email).child(date_val).child(slot_val).child(check).setValue(booking_appointments);
        startActivity(new Intent(Result.this, Patient.class));
    }
}

