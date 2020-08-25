package com.example.lbsabyannaufal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    private TextView viewLatitude, viewLongitude;
    private EditText addTitle, addPlace;
    private Location location;
    private Bundle extras;

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbReference = FirebaseDatabase.getInstance().getReference();

        addPlace = findViewById(R.id.add_place);
        addTitle = findViewById(R.id.add_title);
        viewLatitude = findViewById(R.id.text_latitude);
        viewLongitude = findViewById(R.id.text_longitude);
        extras = getIntent().getExtras();
        location = new Location();
    }

    public void btnSave(View view){
        saveLocation();
    }

    private void saveLocation(){
        String place = addPlace.getText().toString().trim();
        String title = addTitle.getText().toString().trim();

        boolean isEmptyFields = false;

        if(extras != null){
            viewLatitude.setText(String.valueOf(extras.getDouble("latitude")));
            viewLongitude.setText(String.valueOf(extras.getDouble("longitude")));
        }

        if(TextUtils.isEmpty(place)){
            isEmptyFields = true;
            addPlace.setError("Field can't be empty!");
        }
        if(TextUtils.isEmpty(title)){
            isEmptyFields =true;
            addTitle.setError("Field can't be empty!");
        }

        if(!isEmptyFields){
            Toast.makeText(AddActivity.this,"Saving data...", Toast.LENGTH_SHORT).show();

            DatabaseReference dbLocation = dbReference.child("Wisata");

            String id = dbLocation.push().getKey();
            location.setId(id);
            location.setPlace(place);
            location.setTitle(title);
            location.setLatitude(viewLatitude.getText().toString());
            location.setLongitude(viewLongitude.getText().toString());

            //Insert to Firebase
            dbLocation.child(id).setValue(location);
            finish();
        }
    }
}