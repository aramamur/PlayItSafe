package com.example.playitsafe.ui.vaccine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.playitsafe.PlaceSafe;
import com.example.playitsafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

public class VaccineStatsActivity extends AppCompatActivity {

    TextView vac_result;
    TextView vac_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_stats);
        vac_result = findViewById(R.id.vaccineReviewResult);
        vac_review = findViewById(R.id.txtReview);
        Bundle extras = getIntent().getExtras();
        String vac_name = extras.getString("VAC_NAME");
        String vac_age = extras.getString("VAC_AGE");
        String vac_race = extras.getString("VAC_RACE");
        String vac_gender  = extras.getString("VAC_GENDER");

        String vacResult = "Reviews for: "+vac_name+", "+vac_age+", "+vac_race+", "+vac_gender;

        vac_result.setText(vacResult);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("vaccine").whereEqualTo("vacname", vac_name).whereEqualTo("age_range", vac_age).whereEqualTo("race", vac_race).whereEqualTo("gender", vac_gender).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //String jsontxt = null;
                            String vac_review_result = null;
                            boolean first_time = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // jsontxt =  jsontxt + String.valueOf(document.getData());
                                JSONObject jb = new JSONObject(document.getData());
                                try {

                                        String vaccomment= jb.getString("comments");
                                        String vaccovid = jb.getString("covid_after");
                                        String vacsideeffect = jb.getString("side_effects");
                                        if(first_time == false) {
                                            vac_review_result = " Covid After Vaccine: "+vaccovid+" Side Effect: "+vacsideeffect+" Comment: "+vaccomment+"\n\n";
                                            first_time = true;
                                        }
                                        else{
                                            vac_review_result = vac_review_result+" Covid After Vaccine: "+vaccovid+" Side Effect: "+vacsideeffect+" Comment: "+vaccomment+"\n\n";
                                        }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            vac_review.setText(vac_review_result);

                        } else {

                        }
                    }
                });

        //Here the latest stats on displayed depending on what the user selects
    }
}