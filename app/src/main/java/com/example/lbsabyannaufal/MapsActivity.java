package com.example.lbsabyannaufal;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener  {

    private GoogleMap mMap;
    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) { //Final Undo
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        markerList = new ArrayList<>();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        dbReference.child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String stringLatitude  = (String) (snapshot.child("latitude").getValue());
                    String stringLongitude = (String) (snapshot.child("longitude").getValue());
                    String place = (String) (snapshot.child("place").getValue());
                    String title = (String) (snapshot.child("title").getValue());
                    double latitude = Double.parseDouble(stringLatitude);
                    double longitude = Double.parseDouble(stringLongitude);


                    LatLng coordinate = new LatLng(latitude,longitude);
                    googleMap.addMarker(new MarkerOptions().position(coordinate).title(title));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
//       mMap.addMarker(new MarkerOptions().position(latLng).title("new Marker")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        Intent intent = new Intent(MapsActivity.this, AddActivity.class);
        intent.putExtra("latitude" , latLng.latitude);
        intent.putExtra("longitude" , latLng.longitude);

        startActivity(intent);
    }


}