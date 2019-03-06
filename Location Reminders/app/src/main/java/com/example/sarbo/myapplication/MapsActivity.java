package com.example.sarbo.myapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    // Google client to interact with Google API

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    String location;
    int x=0;


    static double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        // First we need to check availability of play services
        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
        }

        Intent intent = getIntent();
         x = intent.getIntExtra(Constants.ID,0);
        //String tagStringForUpdateAlarm = intent.getStringExtra(Constants.UPDATEALARM);




        Button sd = (Button) findViewById(R.id.Button_Add);
        sd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /* TODO Auto-generated method stub */

                if(x == 1){
                Intent intent = new Intent(MapsActivity.this,CreateAlram.class);
                intent.putExtra(Constants.LOC_NAME,location);
                intent.putExtra("lat",latitude);
                intent.putExtra("lon",longitude);
                startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MapsActivity.this,UpdateAlarm.class);
                    intent.putExtra(Constants.LOC_NAME,location);

                    startActivity(intent);}
                }


        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        checkPlayServices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }


    }








    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }



    @Override
    public void onConnected(Bundle bundle) {
        //displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
    }



    public void onSearch(View view){

        EditText Location_tf =(EditText)findViewById(R.id.edt_location);
         location = Location_tf.getText().toString();
        List<Address> addressList=null;
        if (location.length()==0){
            Location_tf.setError("nothing is entered!!!");
        }
        else if (location !=null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList= geocoder.getFromLocationName(location,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        displayLocation();
    }


    public void changeType(View view)
    {
        if (mMap.getMapType()== GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        }
        else
        { mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);}
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }



    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);

    }


    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
             latitude = mLastLocation.getLatitude();
             longitude = mLastLocation.getLongitude();

            Toast.makeText(getApplicationContext(),"Your're at Latitude = "+ latitude +" Longitude = "+longitude,Toast.LENGTH_LONG).show();

           // lblLocation.setText(latitude + ", " + longitude);

        } else {
                Toast.makeText(getApplicationContext(),"Couldn't get location make sure that your mobile GPS is ON ",Toast.LENGTH_LONG).show();
           // lblLocation.setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    public double gettingLatitude(){
        return latitude;
    }
    public  double gettingLongitude(){
        return longitude;
    }


    @Override
    public void onLocationChanged(Location location) {

        //assign the new location
        mLastLocation =location;
        Toast.makeText(getApplicationContext(),"Location Changed:",Toast.LENGTH_SHORT).show();
        //displaaying the new location on UI
        displayLocation();


    }

    // Creating location request object
    protected void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    //Starting the Location updates

    protected void startLocationUpdates(){
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);

    }
    //Stoping location Updates
    protected  void stopLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    // Method to toggle periodic location updates
 /*   private void togglePeriodicLocationUpdates(){
        if (!mRequestingLocationUpdates){
            //Changing the button text
            startLocation.setText("stop update");

            mRequestingLocationUpdates = true;
            startLocationUpdates();
            Log.d(TAG, "Periodic location updates started: ");
        }else {
            startLocation.setText("start update");
            mRequestingLocationUpdates = false;
            stopLocationUpdates();
            Log.d(TAG, "periodic location updates stop: ");
        }
    }*/
}
