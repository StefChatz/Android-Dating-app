package com1032.cw1.sc01396.com1032_datingapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
/*
 * A map that handles all of the events that happen in the map.
 * @author Stefanos Chatzakis
 */
public class MapInit extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    private Location mLastKnownLocation;
    private CameraPosition mCameraPosition;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String KEY_CAMERA_POSITION = "camera_poition";
    private static final String KEY_LOCATION = "location";
    private GeofencingClient mGeofencingClient;
    private static final String TAG = MapInit.class.getSimpleName();

    /*
     * Markers that correspond to the user, as well as other predefined users.
     */

    private Marker userMarker;
    private Marker m1;
    private Marker m2;
    private Marker m3;
    private Marker m4;
    private Marker m5;
    private Marker m6;
    private Marker m7;
    private Marker m8;
    private Marker m9;
    private Marker m10;

    private LatLng latLng1 = new LatLng(51.243563, -0.587452);
    private LatLng latLng2 = new LatLng(51.246061, -0.589619);
    private LatLng latLng3 = new LatLng(51.249713, -0.589755);
    private LatLng latLng4 = new LatLng(51.247356, -0.596360);
    private LatLng latLng5 = new LatLng(51.236134, -0.592621);
    private LatLng latLng6 = new LatLng(51.239532, -0.594576);
    private LatLng latLng7 = new LatLng(51.242109, -0.584058);
    private LatLng latLng8 = new LatLng(51.240436, -0.578475);
    private LatLng latLng9 = new LatLng(51.245285, -0.578469);
    private LatLng latLng10 = new LatLng(51.238384, -0.584566);

    private Location loc1 = new Location("loc1");
    private Location loc2 = new Location("loc2");
    private Location loc3 = new Location("loc3");
    private Location loc4 = new Location("loc4");
    private Location loc5 = new Location("loc5");
    private Location loc6 = new Location("loc6");
    private Location loc7 = new Location("loc7");
    private Location loc8 = new Location("loc8");
    private Location loc9 = new Location("loc9");
    private Location loc10 = new Location("loc10");

    double distance1;
    double distance2;
    double distance3;
    double distance4;
    double distance5;
    double distance6;
    double distance7;
    double distance8;
    double distance9;
    double distance10;

    private LatLng myLocation;
    private Location loc0 = new Location("loc0");
    private HashMap<Integer, String> mHashMap = new HashMap<Integer, String>();
    private DatabaseReference mDatabase;

    /*
     * A default constructor that sets the location objects of the predefined users.
     */

    public MapInit() {

        loc1.setLatitude(latLng1.latitude);
        loc1.setLongitude(latLng1.longitude);

        loc2.setLatitude(latLng2.latitude);
        loc2.setLongitude(latLng2.longitude);

        loc3.setLatitude(latLng3.latitude);
        loc3.setLongitude(latLng3.longitude);

        loc4.setLatitude(latLng4.latitude);
        loc4.setLongitude(latLng4.longitude);

        loc5.setLatitude(latLng5.latitude);
        loc5.setLongitude(latLng5.longitude);

        loc6.setLatitude(latLng6.latitude);
        loc6.setLongitude(latLng6.longitude);

        loc7.setLatitude(latLng7.latitude);
        loc7.setLongitude(latLng7.longitude);

        loc8.setLatitude(latLng8.latitude);
        loc8.setLongitude(latLng8.longitude);

        loc9.setLatitude(latLng9.latitude);
        loc9.setLongitude(latLng9.longitude);

        loc10.setLatitude(latLng10.latitude);
        loc10.setLongitude(latLng10.longitude);
    }

    /*
     * The onCreate method of the class that sets the layouts as well as getting the
     * location of the user using gps.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_map);
        Button btn = findViewById(R.id.btn_look);
        btn.setOnClickListener(new View.OnClickListener() {
            /*
             * An onClick method that is initialized, when the button in the main activity is
             * pressed.
             * @return view.
             */

            @Override
            public void onClick(View view) {
                onLookPeople(view);
            }
        });
        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.ID);
        mf.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        try {
            Criteria locCriteria = new Criteria();
            locCriteria.setAccuracy(Criteria.ACCURACY_FINE);
            provider = locationManager.getBestProvider(locCriteria, false);
            provider = locationManager.GPS_PROVIDER;
            Location location = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }

    /*
     * Sets a custom Map Style.
     */

    public void setMapStyle() {
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    /*
     * A method that triggers before the map loads.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        googleMap.setOnMapLoadedCallback(this);
        this.setMapStyle();

    }

    /*
     * A method that includes the most functionality of this class.
     * It handles the user's location when it is changed as well as creating the markers for the
     * predefined users and putting them in a firebase Database table.
     * It also calculates if the user is within a km of the other users or not and depending, shows
     * or doesn't the users out of range.
     */

    @Override
    public void onMapLoaded() {
        // code to run when the map has loaded
        mMap.getUiSettings().setZoomControlsEnabled( true );

        try {
            m1 = mMap.addMarker(new MarkerOptions()
                    .position(latLng1).title("User_1"));
            mHashMap.put(1, m1.getTitle());
            mDatabase.child("markers").child("user1").setValue(m1);


            m2 = mMap.addMarker(new MarkerOptions()
                    .position(latLng2).title("User_2"));
            mHashMap.put(2, m2.getTitle());
            mDatabase.child("markers").child("user2").setValue(m2);


            m3 = mMap.addMarker(new MarkerOptions()
                    .position(latLng3).title("User_3"));
            mHashMap.put(3, m3.getTitle());
            mDatabase.child("markers").child("user3").setValue(m3);


            m4 = mMap.addMarker(new MarkerOptions()
                    .position(latLng4).title("User_4"));
            mHashMap.put(4, m4.getTitle());
            mDatabase.child("markers").child("user4").setValue(m4);


            m5 = mMap.addMarker(new MarkerOptions()
                    .position(latLng5).title("User_5"));
            mHashMap.put(5, m5.getTitle());
            mDatabase.child("markers").child("user5").setValue(m5);


            m6 = mMap.addMarker(new MarkerOptions()
                    .position(latLng6).title("User_6"));
            mHashMap.put(6, m6.getTitle());
            mDatabase.child("markers").child("user6").setValue(m6);


            m7 = mMap.addMarker(new MarkerOptions()
                    .position(latLng7).title("User_7"));
            mHashMap.put(7, m7.getTitle());
            mDatabase.child("markers").child("user7").setValue(m7);


            m8 = mMap.addMarker(new MarkerOptions()
                    .position(latLng8).title("User_8"));
            mHashMap.put(8, m8.getTitle());
            mDatabase.child("markers").child("user8").setValue(m8);


            m9 = mMap.addMarker(new MarkerOptions()
                    .position(latLng9).title("User_9"));
            mHashMap.put(9, m9.getTitle());
            mDatabase.child("markers").child("user9").setValue(m9);


            m10 = mMap.addMarker(new MarkerOptions()
                    .position(latLng10).title("User_10"));
            mHashMap.put(10, m10.getTitle());
            mDatabase.child("markers").child("user10").setValue(m10);

        }catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        // read user's current location, if possible
        //myLocation = getMyLocation();
        if (myLocation == null) {
            final LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            } else {
                //Permission is granted
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2048,
                    500, new LocationListener() {

            /*
             * A method that triggers when the user changes position.
             * @param location.
             */

                @Override
                public void onLocationChanged(Location location) {
                    myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    loc0.setLatitude(myLocation.latitude);
                    loc0.setLongitude(myLocation.longitude);

                    distance1 = loc0.distanceTo(loc1);
                    if(distance1>1000) {
                        mHashMap.remove(1);
                    }
                    distance2 = loc0.distanceTo(loc2);
                    if(distance2>1000) {
                        mHashMap.remove(2);
                    }
                    distance3 = loc0.distanceTo(loc3);
                    if(distance3>1000) {
                        mHashMap.remove(3);
                    }
                    distance4 = loc0.distanceTo(loc4);
                    if(distance4>1000) {
                        mHashMap.remove(4);
                    }
                    distance5 = loc0.distanceTo(loc5);
                    if(distance5>1000) {
                        mHashMap.remove(5);
                    }
                    distance6 = loc0.distanceTo(loc6);
                    if(distance6>1000) {
                        mHashMap.remove(6);
                    }
                    distance7 = loc0.distanceTo(loc7);
                    if(distance7>1000) {
                        mHashMap.remove(7);
                    }
                    distance8 = loc0.distanceTo(loc8);
                    if(distance8>1000) {
                        mHashMap.remove(8);
                    }
                    distance9 = loc0.distanceTo(loc9);
                    if(distance9>1000) {
                        mHashMap.remove(9);
                    }
                    distance10 = loc0.distanceTo(loc10);
                    if(distance10>1000) {
                        mHashMap.remove(10);
                    }
                    if(userMarker!=null) {
                            userMarker.remove();
                        }
                            userMarker = mMap.addMarker(new MarkerOptions()
                                    .position(myLocation)
                                    .title("ME!")
                                    .snippet("Having fun at lat: " +
                                            String.valueOf(myLocation.latitude) + ", lng: " +
                                            String.valueOf(myLocation.longitude) + "."));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

                            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                                // No explanation needed, we can request the permission.

                                ActivityCompat.requestPermissions(getParent(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            } else {
                                //Permission is granted
                            }


                }

                /*
                 * Imported method, not used.
                 * @return String, int, bundle.
                 */

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                /*
                 * Imported method, not used.
                 * @return String.
                 */

                @Override
                public void onProviderEnabled(String s) {

                }

                /*
                 * Imported method, not used.
                 * @return String.
                 */

                        @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
    }

    /*
     * Imported class, not used.
     * @param location.
     */

    @Override
    public void onLocationChanged(Location location) {

    }

    /*
     * Imported class, not used.
     * @return String, int, Bundle.
     */

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /*
     * Imported class, not used.
     * @return String
     */

    @Override
    public void onProviderEnabled(String provider) {

    }

    /*
     * Imported class, not used.
     * @param String.
     */

    @Override
    public void onProviderDisabled(String provider) {

    }

    /*
     * This method triggers when the button in the main activity is pressed.
     * @param view.
     */

    public void onLookPeople(View view) {
        Intent intent = new Intent(MapInit.this, SwipeActivity.class);
        intent.putExtra("map", mHashMap);
        MapInit.this.startActivity(intent);
    }

    /*
     * A getter that returns the HashMap containing the predefined user's markers.
     * @return HashMap.
     */

    public HashMap<Integer, String> getmHashMap() {
        return mHashMap;
    }
}