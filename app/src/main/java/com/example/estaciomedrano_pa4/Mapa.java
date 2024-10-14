package com.example.estaciomedrano_pa4;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.estaciomedrano_pa4.databinding.ActivityMapaBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Mapa extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private ActivityMapaBinding binding;
    Button btnSatelite, btnHibrido, btnVintage;
    public LatLng ubicación;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //binding = ActivityMapaBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        ubicación = new LatLng(-13.52009021936155, -71.97525162810444);
        //Botones
        btnSatelite = findViewById(R.id.btnsatelite);
        btnHibrido = findViewById(R.id.btnHibrido);
        btnVintage = findViewById(R.id.btnVintage);

        btnSatelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMap != null){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });
        btnHibrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMap != null){
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });
        btnVintage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMap != null){
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }
        });
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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setOnMyLocationButtonClickListener(() -> {
                obtenerUbicacion();
                return false;
            });
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        try{
            boolean success  = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this,R.raw.estilo1)
            );
            if(!success){
                Log.e("MapsActivity","No se pudo aplicar el estilo del mapa.");
            }
        }catch (Resources.NotFoundException e){
            Log.e("MapsActivity","Archivo de estilo no encontrado.",e);
        }

        // Add a marker in Cusco and move the camera
        LatLng Ubi1 = new LatLng(-13.516994006035379, -71.98009095351105);
        mMap.addMarker(new MarkerOptions().position(Ubi1).title("Marker in Cusco"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Ubi1));
        CameraPosition cameraPosition = CameraPosition.builder().target(Ubi1).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onClick(View view) {
//        if (view.getId()==R.id.btnsatelite){
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//            mMap.addMarker(new MarkerOptions().position(ubicación).title("Qoricancha"));
//        }
//        if (view.getId()==R.id.btnHibrido){
//            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//            mMap.addMarker(new MarkerOptions().position(ubicación).title("Qoricancha"));
//        }
//        if (view.getId()==R.id.btnVintage){
//            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//            mMap.addMarker(new MarkerOptions().position(ubicación).title("Qoricancha"));
//        }

    }

    public void MoverseA(LatLng direccion){
        mMap.addMarker(new MarkerOptions().position(direccion).title("Qoricancha"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(direccion));
        CameraPosition cameraPosition = CameraPosition.builder().target(direccion).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void obtenerUbicacion() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            // Obtener la latitud y longitud
                            double latitud = location.getLatitude();
                            double longitud = location.getLongitude();

                            // Mostrar la ubicación en un Toast
                            String mensaje = "Ubicación actual: Latitud: " + latitud + ", Longitud: " + longitud;
                            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

                            // Mover la cámara a la ubicación actual
                            LatLng ubicacionActual = new LatLng(latitud, longitud);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 15));
                        } else {
                            // Si no se pudo obtener la ubicación, mostrar un mensaje
                            Toast.makeText(this, "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Solicitar permisos de ubicación si no están concedidos
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true); // Habilitar "Mi ubicación" una vez otorgado el permiso
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}