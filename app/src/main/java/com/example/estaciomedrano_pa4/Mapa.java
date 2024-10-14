package com.example.estaciomedrano_pa4;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.estaciomedrano_pa4.databinding.ActivityMapaBinding;

public class Mapa extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private ActivityMapaBinding binding;
    Button btnSatelite, btnHibrido, btnVintage;
    public LatLng ubicación;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ubicación = new LatLng(-13.520034947285891, -71.97523597327178);
        btnSatelite = findViewById(R.id.btnsatelite);
        btnHibrido = findViewById(R.id.btnHibrido);
        btnVintage = findViewById(R.id.btnVintage);

        btnSatelite.setOnClickListener(this);
        btnHibrido.setOnClickListener(this);
        btnVintage.setOnClickListener(this);

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-13.520034947285891, -71.97523597327178);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnsatelite){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mMap.addMarker(new MarkerOptions().position(ubicación).title("Qoricancha"));
        }
        if (view.getId()==R.id.btnHibrido.){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.addMarker(new MarkerOptions().position(ubicación).title("Qoricancha"));
        }
        if (view.getId()==R.id.btnVintage){
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mMap.addMarker(new MarkerOptions().position(ubicación).title("Qoricancha"));
        }

    }

}