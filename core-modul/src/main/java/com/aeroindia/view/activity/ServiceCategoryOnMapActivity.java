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
import com.aeroindia.pojos.response.SubServicesResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

public class ServiceCategoryOnMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker noidaMarker;
private RelativeLayout backLayout;
TextView headerTV;
private String markers;
    private SubServicesResponse subServicesResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().hide();
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText(getResources().getString(R.string.allservices));
        if(getIntent()!=null)
        {
            markers=getIntent().getStringExtra("markers");
            subServicesResponse=SubServicesResponse.create(markers);
        }
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // markers=new Marker[100];
        AppUtilityCustom.statusCheck(ServiceCategoryOnMapActivity.this);
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
        LatLng point = null;
for (int i=0;i<subServicesResponse.subServices.size();i++) {
    if(subServicesResponse.subServices.get(i).getLatitude()!=null) {
        double latt=Double.parseDouble(subServicesResponse.subServices.get(i).getLatitude());
        double longg=Double.parseDouble(subServicesResponse.subServices.get(i).getLongitude());
         point = new LatLng(latt, longg);
        Marker marker = mMap.addMarker(new
                MarkerOptions().position(point));
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(subServicesResponse.subServices.get(i).getSubServceName())));
    }
}

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16F));
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
