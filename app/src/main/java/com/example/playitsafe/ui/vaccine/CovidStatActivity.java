package com.example.playitsafe.ui.vaccine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.playitsafe.PlaceSafe;
import com.example.playitsafe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class CovidStatActivity extends AppCompatActivity {

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_stat);
        textView = findViewById(R.id.covidText);
        getSupportActionBar().setTitle("US Covid Latest Stats");
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://corona-api.com/countries/US";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //data from https://corona-api.com/countries/US
                            String uspop = response.getJSONObject("data").getString("population");
                            String todaydeath = response.getJSONObject("data").getJSONObject("today").getString("deaths");
                            String todayconfirmed = response.getJSONObject("data").getJSONObject("today").getString("confirmed");
                            String totalcases = response.getJSONObject("data").getJSONObject("latest_data").getString("confirmed");
                            String totaldeaths = response.getJSONObject("data").getJSONObject("latest_data").getString("deaths");

                            String displayText = "US Population: "+uspop+"\n\nToday's Deaths: "+todaydeath+"\nToday's Confirmed Cases: "+todayconfirmed+"\n\nTotal Deaths: "+totaldeaths+"\nTotal Confirmed Cases: "+totalcases+"\n\n\nSource: https://corona-api.com/countries/US";
                            textView.setText(displayText);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            textView.setText(" Response: That didn't work!");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }

}