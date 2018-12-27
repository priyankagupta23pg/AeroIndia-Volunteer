package com.aeroindia.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppUtilityCustom;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class HallRedirectionActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker noidaMarker;
    private String hallname;
    private int resId;
    AlertDialog levelDialog;
    private ImageView mapType;
    KmlLayer upperlayer = null;
    private LatLng userLocation;
    private Context context;
    private PolylineOptions polys;
    private Polyline polylineFinal;
    private RelativeLayout backLayout;
    private TextView headerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hallredirection);
        getSupportActionBar().hide();
        context=this;
        mapType=(ImageView)findViewById(R.id.mapType);
        backLayout=(RelativeLayout) findViewById(R.id.backLayout);
        headerTV=(TextView) findViewById(R.id.headerTV);

        mapType.setVisibility(View.VISIBLE);
        //allTv.setBackgroundColor(getResources().getColor(R.color.black));
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // markers=new Marker[100];
        if(getIntent()!=null)
        {
            hallname=getIntent().getStringExtra("hallname");
        }
        headerTV.setText(hallname);
        AppUtilityCustom.statusCheck(HallRedirectionActivity.this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mMap == null) {
            final OnMapReadyCallback listener = this;
            mapFragment.getMapAsync(listener);

        }
        if(hallname!=null)
        {
            if(hallname.toLowerCase().equalsIgnoreCase("hall a"))
            {
                resId=R.raw.airforcebanglorehall_a;
            }
            else if(hallname.toLowerCase().equalsIgnoreCase("hall b"))
            {
                resId=R.raw.airforcebanglorehalls_b;
            }
            else if(hallname.toLowerCase().equalsIgnoreCase("hall ab"))
            {
                resId=R.raw.airforcebanglorehall_ab;
            }
            else if(hallname.toLowerCase().equalsIgnoreCase("hall c"))
            {
                resId=R.raw.airforcebanglorehalls_c;
            }
            else if(hallname.toLowerCase().equalsIgnoreCase("hall d"))
            {
                resId=R.raw.airforcebanglorehall_d;
            } else if(hallname.toLowerCase().equalsIgnoreCase("hall e"))
            {
                resId=R.raw.airforcebanglorehalls_e;
            }
        }


// Strings to Show In Dialog with Radio Buttons
        final CharSequence[] items = {" Default "," Satellite ","Terrain"};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Map type");
        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch(item)
                {
                    case 0:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);

                        // Your code when first option seletced
                        break;
                    case 1:
                        // Your code when 2nd  option seletced
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                        break;
                    case 2:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                        // Your code when 3rd option seletced
                        break;


                }
                levelDialog.dismiss();
            }
        });
        levelDialog = builder.create();

        mapType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelDialog.show();

            }
        });
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


        if (mMap != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            try {
                upperlayer = new KmlLayer(mMap, resId, getApplicationContext());
                upperlayer.addLayerToMap();

     LatLng markerLoc = new LatLng(13.1358, 77.60243);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLoc, 14.9F));

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // Network Provider
            LocationManager service = (LocationManager)
                    getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = service.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                 userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                Log.d("curr loc",location.getLatitude()+"---------"+location.getLongitude());
//                final Marker lastMarker = mMap.addMarker(new
//                        MarkerOptions().position(userLocation));
              //  lastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Current Location")));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14F));
                mMap.addMarker(new MarkerOptions()
                        .position(userLocation)
                        .title("Current location"));
            }


       /*     Location myLocation = mMap.getMyLocation();
            Log.d("myLocation",myLocation+"");

            if(myLocation!=null) {
                LatLng myLatLong = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
               Log.d("LatLong",myLatLong+"");
                final Marker lastMarker = mMap.addMarker(new
                        MarkerOptions().position(myLatLong));
                lastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Current")));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLong, 20F));
            }*/


            /*Location myLocation = mMap.getMyLocation();
            if(myLocation!=null) {
                LatLng myLatLong = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                final Marker lastMarker = mMap.addMarker(new
                        MarkerOptions().position(myLatLong));
                lastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("")));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLong, 14F));
            }
        }

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
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        if (marker.equals(noidaMarker))
//        {
        LatLng latLng = marker.getPosition();
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + mMap.getMyLocation().getLatitude() + "," + mMap.getMyLocation().getLongitude() + "&daddr=" + latLng.latitude + "," + latLng.longitude));
        startActivity(intent);
        // }

//        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latLng.latitude, latLng.longitude);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        startActivity(intent);
        return false;
    }



}

