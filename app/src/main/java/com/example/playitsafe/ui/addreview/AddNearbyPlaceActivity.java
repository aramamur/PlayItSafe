package com.example.playitsafe.ui.addreview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.playitsafe.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class AddNearbyPlaceActivity extends AppCompatActivity {

    TextView textfp;
    private static final String TAG = "Places Client Issue";

    private FusedLocationProviderClient fusedLocationClient;

   // private double latitude = 0;
   // private double longitude = 0;
    private String mapsquery = null;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnearbyplace);
        getSupportActionBar().setTitle("Find Nearby Place");
        String apiKey = getString(R.string.api_key);
        //initialize sdk
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        //PlacesClient placesClient = Places.createClient(this);

        textfp = (TextView) findViewById(R.id.text_findnearbyplace);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setCountry("US");
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.TYPES));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.

                String psname = place.getName();
                String psaddress = place.getAddress();
                List<Place.Type> pstype = place.getTypes();
                String pstypestring = null;
                if(pstype.contains(Place.Type.GROCERY_OR_SUPERMARKET))
                {
                    pstypestring = "Grocery Store";
                }
                else if(pstype.contains(Place.Type.RESTAURANT))
                {
                    pstypestring = "Restaurant";
                }
                else if(pstype.contains(Place.Type.STORE))
                {
                    pstypestring = "Store";
                }
                else if(pstype.contains(Place.Type.HOSPITAL))
                {
                    pstypestring = "Hospital";
                }
                else{
                    pstypestring = "Not A Valid Type";
                }
                String place_result = "Name: "+ psname + " Address: "+psaddress+" Type: "+ pstypestring;
                textfp.setText(place_result);
                //check if pstypestring is a valid type

                if(pstypestring.contains("Not A Valid Type") == false) {
                    Intent intent = new Intent(getBaseContext(), AddPlaceActivity.class);
                    intent.putExtra("PLACE_NAME", psname);
                    intent.putExtra("PLACE_ADDRESS", psaddress);
                    intent.putExtra("PLACE_TYPE", pstypestring);
                    startActivity(intent);
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                }

            }


            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
                textfp.setText(status.getStatusMessage());
                Log.i(TAG, "An error occurred: " + status);
            }
        });



    }


}