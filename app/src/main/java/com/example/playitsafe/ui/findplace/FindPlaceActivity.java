package com.example.playitsafe.ui.findplace;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playitsafe.R;
import com.example.playitsafe.PlaceSafe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FindPlaceActivity extends AppCompatActivity {


    //EditText textfindname;
    EditText textfindcity;
    Spinner spinner_place_type;
    Button buttonfind;

    private RecyclerView recyclerView;
    private ArrayList<PlaceSafe> placesafe = new ArrayList<>();
    private FindPlaceAdapter findPlaceAdapter;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findplace);


        buttonfind = (Button) findViewById(R.id.button_find);
       // textfindname = findViewById(R.id.text_findname);
        textfindcity = findViewById(R.id.text_findcity);
        spinner_place_type = (Spinner) findViewById(R.id.spinner_findplace);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.place_types_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_place_type.setAdapter(adapter);
        getSupportActionBar().setTitle("Find Place");

        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        findPlaceAdapter = new FindPlaceAdapter(this, placesafe);
        recyclerView.setAdapter(findPlaceAdapter);




        buttonfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stype = String.valueOf(spinner_place_type.getSelectedItem());
               // String sname = String.valueOf(textfindname.getText());
                String scity = String.valueOf(textfindcity.getText());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("places").whereEqualTo("type", stype).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    //String jsontxt = null;
                                    placesafe.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // jsontxt =  jsontxt + String.valueOf(document.getData());
                                         JSONObject jb = new JSONObject(document.getData());
                                        try {
                                            String psaddress = jb.getString("address");
                                            if(psaddress.contains(scity)) {
                                                String pscleaning = jb.getString("cleaning");
                                                String pscleanct = jb.getString("cleaningcarts");
                                                String psdistance = jb.getString("distancing");
                                                String pslimituse = jb.getString("limitingusers");
                                                String psmask = jb.getString("masks");
                                                String psname = jb.getString("name");
                                                String psplexi = jb.getString("plexiglass");
                                                String psrate = jb.getString("rating");
                                                String pstype = jb.getString("type");
                                                PlaceSafe ps = new PlaceSafe(psaddress, pscleaning, pscleanct, psdistance, pslimituse, psmask, psname, psplexi, psrate, pstype);
                                                placesafe.add(ps);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    findPlaceAdapter.setPlace(placesafe);

                                } else {

                                }
                            }
                        });
            }
        });


    }

}