package com.aeroindia.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppUtilityCustom;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

public class StallLocationOnMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker noidaMarker;
private RelativeLayout backLayout;
TextView headerTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().hide();
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText("Location");
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // markers=new Marker[100];
        AppUtilityCustom.statusCheck(StallLocationOnMapActivity.this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mMap == null) {
            final OnMapReadyCallback listener = this;
            mapFragment.getMapAsync(listener);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        mMap.setMyLocationEnabled(true);

        IconGenerator iconFactory = new IconGenerator(this);

        LatLng ofcPoint = new LatLng(28.5937, 77.1888);
         noidaMarker=mMap.addMarker(new
                MarkerOptions().position(ofcPoint));
        noidaMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Stall1")));

      /*  LatLng stellarPoint = new LatLng(28.5939, 77.1887);
        final Marker hydMarker=mMap.addMarker(new
                MarkerOptions().position(stellarPoint));
        hydMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.
                makeIcon("Eau de Monsoon")));*/

//        LatLng samsungPoint = new LatLng(28.5987, 77.1973);
//        final Marker samsungMarker=mMap.addMarker(new
//                MarkerOptions().position(samsungPoint));
//        samsungMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Restaurant 3")));
//
//
//        LatLng basilPoint = new LatLng(28.5964, 77.1976);
//        final Marker basilMarker=mMap.addMarker(new
//                MarkerOptions().position(basilPoint));
//        basilMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Restaurant 4")));
//
//
//        LatLng lastPoint = new LatLng(28.5966, 77.1958);
//        final Marker lastMarker=mMap.addMarker(new
//                MarkerOptions().position(lastPoint));
//        lastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Restaurant 5")));
//

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ofcPoint, 20F));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMarkerClickListener(this);

      /*  new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Marker markers[]={noidaMarker,hydMarker};
                //Move & animate the camera to show all markers
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int padding = 100; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.moveCamera(cu);
                mMap.animateCamera(cu);
            }
            }, 500);*/

        }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        if (marker.equals(noidaMarker))
//        {
            LatLng latLng= marker.getPosition();
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr="+mMap.getMyLocation().getLatitude()+","+mMap.getMyLocation().getLongitude()+"&daddr="+latLng.latitude+","+latLng.longitude));
            startActivity(intent);
       // }

//        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latLng.latitude, latLng.longitude);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        startActivity(intent);
        return false;
    }
}
