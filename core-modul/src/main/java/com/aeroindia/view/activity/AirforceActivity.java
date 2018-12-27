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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroindia.R;
import com.aeroindia.Utility.DirectionsJSONParser;
import com.aeroindia.custom.utility.AppUtilityCustom;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AirforceActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker noidaMarker;
    private String servicename;
    private int resId;
    AlertDialog levelDialog;
    private ImageView mapType,back_image;
    private TextView hallsTv,parkingTv,allTv,restaurantTv,hospitalTv,challetTv,gateTv,locTv,footer;
    KmlLayer upperlayer = null;
    private LatLng userLocation;
    private Context context;
    private PolylineOptions polys;
    private Polyline polylineFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airforce);
        getSupportActionBar().hide();
        context=this;
        mapType=(ImageView)findViewById(R.id.mapType);
        back_image=(ImageView)findViewById(R.id.back_image);
        hallsTv=(TextView) findViewById(R.id.hallsTv);
        allTv=(TextView) findViewById(R.id.allTv);
        parkingTv=(TextView) findViewById(R.id.parkingTv);
        restaurantTv=(TextView) findViewById(R.id.restaurantTv);
        challetTv=(TextView) findViewById(R.id.challetTv);
        hospitalTv=(TextView) findViewById(R.id.hospitalTv);
        gateTv=(TextView) findViewById(R.id.gateTv);
        locTv=(TextView) findViewById(R.id.locTv);
        footer=(TextView) findViewById(R.id.footer);
        mapType.setVisibility(View.VISIBLE);
        //allTv.setBackgroundColor(getResources().getColor(R.color.black));
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // markers=new Marker[100];
        if(getIntent()!=null)
        {
            servicename=getIntent().getStringExtra("servicename");
        }
        AppUtilityCustom.statusCheck(AirforceActivity.this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mMap == null) {
            final OnMapReadyCallback listener = this;
            mapFragment.getMapAsync(listener);

        }
        if(servicename!=null)
        {
            if(servicename.toLowerCase().contains("restaurant"))
            {
                resId=R.raw.airforcebanglorereastaurant;
            }
            else if(servicename.toLowerCase().contains("hall"))
            {
                resId=R.raw.airforcebanglorehalls;
            }
            else if(servicename.toLowerCase().contains("hospital"))
            {
                resId=R.raw.airforcebanglorehospital;
            }
            else if(servicename.toLowerCase().contains("parking"))
            {
                resId=R.raw.airforcebangloreparking;
            }
            else
            {
                resId=R.raw.airforcebanglore;

            }
        }
        else
        {
            resId=R.raw.airforcebanglore;

        }
        hallsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footer.setVisibility(View.GONE);
                if(polylineFinal!=null)
                polylineFinal.remove();

                //hallsTv.setBackgroundColor(getResources().getColor(R.color.black));
                parkingTv.setBackgroundColor(getResources().getColor(R.color.red));
                allTv.setBackgroundColor(getResources().getColor(R.color.allColor));
                restaurantTv.setBackgroundColor(getResources().getColor(R.color.blue));
                hospitalTv.setBackgroundColor(getResources().getColor(R.color.hospitalColor));
                challetTv.setBackgroundColor(getResources().getColor(R.color.chaletColor));
                gateTv.setBackgroundColor(getResources().getColor(R.color.gateColor));

                resId=R.raw.airforcebanglorehalls;
                if (mMap != null) {
                     upperlayer.removeLayerFromMap();
                    //  mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

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




                }
            }
        });
        gateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footer.setVisibility(View.GONE);

                // gateTv.setBackgroundColor(getResources().getColor(R.color.black));
                hallsTv.setBackgroundColor(getResources().getColor(R.color.hallColor));
                parkingTv.setBackgroundColor(getResources().getColor(R.color.red));
                allTv.setBackgroundColor(getResources().getColor(R.color.allColor));
                restaurantTv.setBackgroundColor(getResources().getColor(R.color.blue));
                hospitalTv.setBackgroundColor(getResources().getColor(R.color.hospitalColor));
                challetTv.setBackgroundColor(getResources().getColor(R.color.chaletColor));
                if(polylineFinal!=null)
                    polylineFinal.remove();
                resId=R.raw.airforcebangloregates;
                if (mMap != null) {
                     upperlayer.removeLayerFromMap();
                    //  mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

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




                }
            }
        });
        challetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footer.setVisibility(View.GONE);
                if(polylineFinal!=null)
                    polylineFinal.remove();
                //challetTv.setBackgroundColor(getResources().getColor(R.color.black));
                hallsTv.setBackgroundColor(getResources().getColor(R.color.hallColor));
                parkingTv.setBackgroundColor(getResources().getColor(R.color.red));
                allTv.setBackgroundColor(getResources().getColor(R.color.allColor));
                restaurantTv.setBackgroundColor(getResources().getColor(R.color.blue));
                hospitalTv.setBackgroundColor(getResources().getColor(R.color.hospitalColor));
                gateTv.setBackgroundColor(getResources().getColor(R.color.gateColor));

                resId=R.raw.airforcebanglorechalet;
                if (mMap != null) {
                     upperlayer.removeLayerFromMap();
                    //  mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

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




                }
            }
        });
        hospitalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footer.setVisibility(View.GONE);
                if(polylineFinal!=null)
                    polylineFinal.remove();
                //  hospitalTv.setBackgroundColor(getResources().getColor(R.color.black));
                hallsTv.setBackgroundColor(getResources().getColor(R.color.hallColor));
                parkingTv.setBackgroundColor(getResources().getColor(R.color.red));
                allTv.setBackgroundColor(getResources().getColor(R.color.allColor));
                restaurantTv.setBackgroundColor(getResources().getColor(R.color.blue));
                challetTv.setBackgroundColor(getResources().getColor(R.color.chaletColor));
                gateTv.setBackgroundColor(getResources().getColor(R.color.gateColor));

                resId=R.raw.airforcebanglorehospital;
                if (mMap != null) {
                     upperlayer.removeLayerFromMap();
                    //  mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

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




                }
            }
        });
        restaurantTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footer.setVisibility(View.GONE);
                if(polylineFinal!=null)
                    polylineFinal.remove();
                //restaurantTv.setBackgroundColor(getResources().getColor(R.color.black));
                hallsTv.setBackgroundColor(getResources().getColor(R.color.hallColor));
                parkingTv.setBackgroundColor(getResources().getColor(R.color.red));
                allTv.setBackgroundColor(getResources().getColor(R.color.allColor));
                hospitalTv.setBackgroundColor(getResources().getColor(R.color.hospitalColor));
                challetTv.setBackgroundColor(getResources().getColor(R.color.chaletColor));
                gateTv.setBackgroundColor(getResources().getColor(R.color.gateColor));

                resId=R.raw.airforcebanglorereastaurant;
                if (mMap != null) {
                     upperlayer.removeLayerFromMap();
                    //  mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

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




                }
            }
        });
        allTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footer.setVisibility(View.GONE);
                if(polylineFinal!=null)
                    polylineFinal.remove();
                //allTv.setBackgroundColor(getResources().getColor(R.color.black));
                parkingTv.setBackgroundColor(getResources().getColor(R.color.red));
                hallsTv.setBackgroundColor(getResources().getColor(R.color.hallColor));
                restaurantTv.setBackgroundColor(getResources().getColor(R.color.blue));
                hospitalTv.setBackgroundColor(getResources().getColor(R.color.hospitalColor));
                challetTv.setBackgroundColor(getResources().getColor(R.color.chaletColor));
                gateTv.setBackgroundColor(getResources().getColor(R.color.gateColor));

                resId=R.raw.airforcebanglore;
                if (mMap != null) {
                    upperlayer.removeLayerFromMap();
                    //  mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

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




                }
            }
        });
        parkingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footer.setVisibility(View.GONE);
                if(polylineFinal!=null)
                    polylineFinal.remove();
                //parkingTv.setBackgroundColor(getResources().getColor(R.color.black));
                hallsTv.setBackgroundColor(getResources().getColor(R.color.hallColor));
                allTv.setBackgroundColor(getResources().getColor(R.color.allColor));
                restaurantTv.setBackgroundColor(getResources().getColor(R.color.blue));
                hospitalTv.setBackgroundColor(getResources().getColor(R.color.hospitalColor));
                challetTv.setBackgroundColor(getResources().getColor(R.color.chaletColor));
                gateTv.setBackgroundColor(getResources().getColor(R.color.gateColor));

                resId=R.raw.airforcebangloreparking;
                if (mMap != null) {
                    upperlayer.removeLayerFromMap();
                    //  mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

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




                }
            }
        });
        locTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                footer.setVisibility(View.VISIBLE);
                resId = R.raw.airforcebanglore;
                if (mMap != null) {
                    upperlayer.removeLayerFromMap();
                    //  mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                    try {
                        upperlayer = new KmlLayer(mMap, resId, getApplicationContext());
                        upperlayer.addLayerToMap();

                        LatLng markerLoc = new LatLng(13.1358, 77.60243);

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(markerLoc);
                            builder.include(userLocation);

                        LatLngBounds bounds = builder.build();
//                        int padding = 0; // offset from edges of the map in pixels
//                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 40);

                        int padding = 300; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        mMap.moveCamera(cu);
                        mMap.animateCamera(cu);
//if(AppUtilityFunction.isNetworkAvailable(context))
//{
//    String url = getDirectionsUrl(markerLoc, userLocation);
//
//    DownloadTask downloadTask = new DownloadTask();
//
//    // Start downloading json data from Google Directions API
//    downloadTask.execute(url);
//}
//else {
     polys = new PolylineOptions().geodesic(true);
    polys.add(markerLoc).width(5).color(R.color.locColor);
    polys.add(userLocation).width(5).color(R.color.locColor);
                        polylineFinal = mMap.addPolyline(polys);
    double distance = distance(markerLoc.latitude, markerLoc.longitude, userLocation.latitude, userLocation.longitude);
    footer.setText("You are " + distance + " km. away from the show.");
//}
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

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

       // mMap.setMyLocationEnabled(true);
        IconGenerator iconFactory = new IconGenerator(this);

     /*   LatLng ofcPoint = new LatLng(28.5937, 77.1888);
         noidaMarker=mMap.addMarker(new
                MarkerOptions().position(ofcPoint));
        noidaMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Hall A")));

        LatLng stellarPoint = new LatLng(28.5939, 77.1887);
        final Marker hydMarker=mMap.addMarker(new
                MarkerOptions().position(stellarPoint));
        hydMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Hall B")));
        LatLng samsungPoint = new LatLng(28.5987, 77.1973);
        final Marker samsungMarker=mMap.addMarker(new
                MarkerOptions().position(samsungPoint));
        samsungMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Hall C")));


        LatLng basilPoint = new LatLng(28.5964, 77.1976);
        final Marker basilMarker=mMap.addMarker(new
                MarkerOptions().position(basilPoint));
        basilMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Restroom")));


        LatLng lastPoint = new LatLng(28.5966, 77.1958);
        final Marker lastMarker=mMap.addMarker(new
                MarkerOptions().position(lastPoint));
        lastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Parking")));

*/
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
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+"&key="+getResources().getString(R.string.google_maps_key);
Log.d("DIRECTION URL",url);
        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exc. while downloading", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";

            if(result.size()<1){
                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0){    // Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(getResources().getColor(R.color.locColor));
            }
            footer.setText("You are " + distance + " km. away from the show.");

           // footer.setText("Distance:"+distance + ", Duration:"+duration);

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

}

