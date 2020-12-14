package com.example.playitsafe.ui.vaccine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.playitsafe.R;
import com.example.playitsafe.Vaccine;
import com.example.playitsafe.ui.addreview.AddPlaceActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class VaccineReviewActivity extends AppCompatActivity {
    Spinner spinner_vaccine;
    Spinner spinner_race;
    Spinner spinner_gender;
    Spinner spinner_age;
    CheckBox check_covid;
    Button buttonvaccine;
    Button buttonfindvaccine;
    EditText text_comment;
    EditText text_sideeffect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_review);
        getSupportActionBar().setTitle("Review Vaccine");

        check_covid = findViewById(R.id.checkCovid);
        buttonfindvaccine = findViewById(R.id.buttonFindVaccine);
        buttonvaccine = findViewById(R.id.buttonVaccine);
        text_comment = findViewById(R.id.txtComments);
        text_sideeffect = findViewById(R.id.txtSideEffects);
        //Here people enter their covid vaccine data
        //this will update the vaccine stats data as well

        spinner_vaccine = (Spinner) findViewById(R.id.spinner_vaccine);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vaccine_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_vaccine.setAdapter(adapter);


        spinner_race = (Spinner) findViewById(R.id.spinner_race);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterr = ArrayAdapter.createFromResource(this,
                R.array.race_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_race.setAdapter(adapterr);

        spinner_gender = (Spinner) findViewById(R.id.spinner_gender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterg = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_gender.setAdapter(adapterg);

        spinner_age = (Spinner) findViewById(R.id.spinner_age);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adaptera = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_age.setAdapter(adaptera);

        //button clicked logic
        buttonvaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String has_covid = "false";

                if(check_covid.isChecked() == true){
                    has_covid = "true";
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String svaccine = String.valueOf(spinner_vaccine.getSelectedItem());
                String srace = String.valueOf(spinner_race.getSelectedItem());
                String sgender = String.valueOf(spinner_gender.getSelectedItem());
                String sage = String.valueOf(spinner_age.getSelectedItem());
                String side_effects = text_sideeffect.getText().toString();
                String comments = text_comment.getText().toString();;
                Vaccine vaccine = new Vaccine(svaccine, sgender, srace, sage, side_effects, has_covid, comments);
                CollectionReference dbPlaces = db.collection("vaccine");
                dbPlaces.add(vaccine);

            }
        });

        buttonfindvaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), VaccineStatsActivity.class);
                String svaccine = String.valueOf(spinner_vaccine.getSelectedItem());
                String srace = String.valueOf(spinner_race.getSelectedItem());
                String sgender = String.valueOf(spinner_gender.getSelectedItem());
                String sage = String.valueOf(spinner_age.getSelectedItem());
                intent.putExtra("VAC_NAME", svaccine);
                intent.putExtra("VAC_RACE", srace);
                intent.putExtra("VAC_GENDER", sgender);
                intent.putExtra("VAC_AGE", sage);
                startActivity(intent);
            }
        });

    }
}