package uk.ac.abertay.cmp309_app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        OnSuccessListener<Location> {

    private static final int LOCATION_REQUEST = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Marker markerMyLocation;
    int cityID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this,this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(200);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult != null) {
                    if (mMap != null) {
                        double lat = locationResult.getLastLocation().getLatitude();
                        double lng = locationResult.getLastLocation().getLongitude();
                        LatLng myLatLng = new LatLng(lat, lng);
                        markerMyLocation.setPosition(myLatLng);
                    }
                }
            }
        };

        boolean checkResult = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (fusedLocationClient != null && checkResult) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(fusedLocationClient != null && locationCallback != null){
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng Dundee = new LatLng(56.4620,-2.9707);

        Intent cityIntent = getIntent();
        cityID = cityIntent.getIntExtra("cityID", -1);

        if(cityID == 0){
            mMap.moveCamera(CameraUpdateFactory.zoomTo(14));

            LatLng dundee = new LatLng(56.4620, -2.9701);
            LatLng mcManusArt = new LatLng(56.4625,-2.9712);
            LatLng vAndA = new LatLng(56.4575, -2.9670);
            LatLng dDan = new LatLng(56.4607, -2.9700);
            LatLng transportMuseum = new LatLng(56.4664, -2.9503);
            LatLng royaltyKinema = new LatLng(56.4677, -2.9519);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dundee));


            mMap.addMarker(new MarkerOptions()
                    .position(mcManusArt)
                    .title("McManus Art Gallery"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(new MarkerOptions()
                    .position(vAndA)
                    .title("V&A Art Museum"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            mMap.addMarker(new MarkerOptions()
                    .position(dDan)
                    .title("Desperate Dan Statue"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            mMap.addMarker(new MarkerOptions()
                    .position(transportMuseum)
                    .title("Dundee Transport Museum"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

            mMap.addMarker(new MarkerOptions()
                    .position(royaltyKinema)
                    .title("Royalty Cinema"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        else if(cityID == 1){
            mMap.moveCamera(CameraUpdateFactory.zoomTo(14));

            LatLng edinburgh = new LatLng(55.9533, -3.1883);
            LatLng royalMile = new LatLng(55.9507, -3.1844);
            LatLng ediVaults = new LatLng(55.9491, -3.1874);
            LatLng ediMuseum = new LatLng(55.9470, -3.1906);
            LatLng ediCastle = new LatLng(55.9486, -3.1999);
            LatLng camObscura = new LatLng(55.9490, -3.1957);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(edinburgh));

            mMap.addMarker(new MarkerOptions()
                    .position(royalMile)
                    .title("Royal Mile"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(new MarkerOptions()
                    .position(ediVaults)
                    .title("Edinburgh Vaults"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            mMap.addMarker(new MarkerOptions()
                    .position(ediMuseum)
                    .title("Edinburgh Museum"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            mMap.addMarker(new MarkerOptions()
                    .position(ediCastle)
                    .title("Edinburgh Castle"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

            mMap.addMarker(new MarkerOptions()
                    .position(camObscura)
                    .title("Camera Obscura"))
                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));


        } else{
            setContentView(R.layout.activity_main);
        }

        markerMyLocation = this.mMap.addMarker(new MarkerOptions()
                .position(Dundee)
                .title("Current Position")
                .icon(BitmapFromVector(getApplicationContext(),R.drawable.ic_location)));
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.getTitle().equals("Abertay University")){
            Intent abertayUniIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.abertay.ac.uk"));
            startActivity(abertayUniIntent);
        }
        return false;
    }

    @Override
    public void onSuccess(Location location) {

    }
}
