package com.example.playitsafe.ui.addreview;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.playitsafe.PlaceSafe;
import com.example.playitsafe.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddPlaceActivity extends AppCompatActivity  {


    TextView displayText;
    CheckBox check_mask;
    CheckBox check_clean;
    CheckBox check_cleancart;
    CheckBox check_limituser;
    CheckBox check_distance;
    CheckBox check_plexiglass;
    Button buttonplace;
    String snameextra = null;
    String stypeextra = null;
    String saddressextra = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplace);
        Bundle extras = getIntent().getExtras();


        buttonplace = findViewById(R.id.button_place);



        check_mask = findViewById(R.id.check_masks);
        check_clean = findViewById(R.id.check_clean);
        check_cleancart = findViewById(R.id.check_cleancarts);
        check_limituser = findViewById(R.id.check_limitusers);
        check_distance = findViewById(R.id.check_distance);
        check_plexiglass = findViewById(R.id.check_plexiglass);
        displayText = findViewById(R.id.display_text);

        getSupportActionBar().setTitle("Add Review of Place");





        if(extras != null){
           snameextra = extras.getString("PLACE_NAME");
           stypeextra = extras.getString("PLACE_TYPE");
           saddressextra = extras.getString("PLACE_ADDRESS");
           String extraresult = "Name: "+snameextra+" Address: "+saddressextra+" Type: "+stypeextra;
           displayText.setText(extraresult);

        }
        else{
            displayText.setText("Result");
        }
        //if store exists then add review to the store collection
        //otherwise add the store and the review

        buttonplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();



                int rating_calc = 0;
                String rating_store = "Poor";
                String sclean = "false";
                String scleancart = "false";
                String sdistance = "false";
                String slimituser = "false";
                String smask = "false";
                String splexiglass = "false";


                if(check_clean.isChecked() == true){
                    rating_calc++;
                    sclean = "true";
                }
                if(check_cleancart.isChecked() == true){
                    rating_calc++;
                    scleancart = "true";
                }
                if(check_distance.isChecked() == true){
                    rating_calc++;
                    sdistance = "true";
                }
                if(check_limituser.isChecked() == true){
                    rating_calc++;
                    slimituser = "true";
                }
                if(check_mask.isChecked() == true){
                    rating_calc++;
                    smask = "true";
                }
                if(check_plexiglass.isChecked() == true){
                    rating_calc++;
                    splexiglass = "true";
                }
                if(rating_calc>=5)
                {
                    rating_store = "Excellent";
                }
                else if(rating_calc>=3)
                {
                    rating_store = "Good";
                }
                else if(rating_calc>=2)
                {
                    rating_store = "Average";
                }



                PlaceSafe placeSafe = new PlaceSafe(saddressextra, sclean, scleancart, sdistance, slimituser, smask, snameextra, splexiglass, rating_store,  stypeextra);


                CollectionReference dbPlaces = db.collection("places");
                displayText.setText("PlaceSafe added: "+snameextra);
               dbPlaces.add(placeSafe);
            }
        });
    }





}