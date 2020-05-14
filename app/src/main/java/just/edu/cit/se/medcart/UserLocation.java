package just.edu.cit.se.medcart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;



public class UserLocation extends FragmentActivity implements OnMapReadyCallback {
    //attributes declaration
    private static final  String TAG ="UserLocation";
    private static final  String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final  String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted=false;
    private static final  int LOCATION_PERMISSION_REQUEST_CODE =1234;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM=15f;
    public static LatLng UL ;
    private Double lat,lng;
    //end of declaration

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this,"map is ready",Toast.LENGTH_SHORT).show();
        mMap=googleMap;

        if(mLocationPermissionGranted)
        {
            getDeviceLoaction();
            mMap.setMyLocationEnabled(true);
        }
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);

        getLocationPermission();
    }


    private void getDeviceLoaction(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionGranted){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM);
                            lat=currentLocation.getLatitude();
                            lng=currentLocation.getLongitude();
                            UL=new LatLng(lat,lng);
                        }
                        else{
                            Log.d(TAG, "onComplete: current location is null;");
                            Toast.makeText(UserLocation.this,"unable to get current location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }catch(SecurityException e){}
    }//end of getdevicelocation

    private void moveCamera(LatLng latLng,float zoom)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }


    private void initMap()
    {
        SupportMapFragment mapfragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapfragment.getMapAsync(UserLocation.this);
    }


    private void getLocationPermission()
    {
        String[] permissions= {FINE_LOCATION, COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted=true;
                initMap();
            }

            else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }

        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted=false;

        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length>0 ){
                    for(int i=0;i<grantResults.length;i++)
                    {
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                        {
                            mLocationPermissionGranted=false;
                            return;
                        }
                    }
                    mLocationPermissionGranted=true;
                    initMap();
                }
            }
        }
    }
}//end of class