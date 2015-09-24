package com.example.andreluiz.epa;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //chave do maps AIzaSyBrIwlEAq46A-oPRLOWirulVnDLIfo_6rM

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
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
        LatLng minicurso3 = new LatLng(2.834377, -60.695445);
        LatLng minicurso5 = new LatLng(2.835402, -60.694514);
        LatLng ufrr = new LatLng(2.834083, -60.692882);
        LatLng palestra = new LatLng(2.833998, -60.691348);
        //mMap.addMarker(new MarkerOptions().position(ufrr).title("Universidade Federal de Roraima"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ufrr, 17));
        String text[] = {getString(R.string.pin_minicurso),getString(R.string.pin_palestra)};
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_azul_azul)).position(minicurso3).title(text[0]));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_azul_azul)).position(minicurso5).title(text[0]));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_verd_azul)).position(palestra).title(text[1]));
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.layout.out_in,R.layout.out_out);
        finish();
    }
}
