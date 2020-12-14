package com.example.playitsafe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.playitsafe.ui.addreview.AddPlaceActivity;
import com.example.playitsafe.ui.addreview.AddNearbyPlaceActivity;
import com.example.playitsafe.ui.findplace.FindPlaceActivity;
import com.example.playitsafe.ui.getpermissions.GetPermissionsActivity;
import com.example.playitsafe.ui.vaccine.CovidStatActivity;
import com.example.playitsafe.ui.vaccine.VaccineReviewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;



import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText userid;
    EditText userpswd;
    TextView userresult;
    Button registerbutton;
    Button loginbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userid = findViewById(R.id.txtusername);
        userpswd = findViewById(R.id.txtPwd);
        userresult = findViewById(R.id.userResult);
        registerbutton = findViewById(R.id.buttoncreate);
        loginbutton = findViewById(R.id.buttonlogin);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "For any questions or concerns contact aramamur@umich.edu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useriddb = userid.getText().toString();
                String userpswdb = userpswd.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                User useradd = new User(useriddb, userpswdb);
                CollectionReference dbUser = db.collection("users");
                dbUser.add(useradd);
                userresult.setText("User Created.");
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useriddb = userid.getText().toString();
                String userpswdb = userpswd.getText().toString();


                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").whereEqualTo("uname", useriddb).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    //String jsontxt = null;

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // jsontxt =  jsontxt + String.valueOf(document.getData());
                                        JSONObject jb = new JSONObject(document.getData());


                                            try {
                                                String uspwdcheck = jb.getString("password");
                                                if (uspwdcheck.equals(userpswdb)) {
                                                    userresult.setText("User Login Successful.");
                                                }
                                                else{
                                                    userresult.setText("User Login Unsuccessful. Try again or create new login.");
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                    }
                                } else {
                                    Log.d("user query", "Error getting documents: ", task.getException());

                                }
                            }
                        });


            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_getpermissions){
            Intent intent = new Intent(this, GetPermissionsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_addnearbyplace){
            Intent intent = new Intent(this, AddNearbyPlaceActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_findplace) {
            Intent intent = new Intent(this, FindPlaceActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_covidstat) {
            Intent intent = new Intent(this, CovidStatActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_vaccinereview){
            Intent intent = new Intent(this, VaccineReviewActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}