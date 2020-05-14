package just.edu.cit.se.medcart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    //attributes declaration
    private static final  String TAG ="MapsActivity";
    private static final  String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final  String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted=false;
    private static final  int LOCATION_PERMISSION_REQUEST_CODE =1234;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM=15f;
    //end of declaration



    @Override
    public void onMapReady(GoogleMap googleMap) { //overloaded function that will work when the map is ready
        Toast.makeText(this,"map is ready",Toast.LENGTH_SHORT).show();
        mMap=googleMap;

        if(mLocationPermissionGranted)
        {

            getDeviceLoaction();
            mMap.setMyLocationEnabled(true);
        }//end if
    }//end of onMapReady



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getLocationPermission();
    } //end of onCreate


    private void getDeviceLoaction(){ //function to get the location of the device
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
                           MarkerOptions MO=new MarkerOptions();
                           MO.position(locationsAdapter.location);
                           mMap.addMarker(MO);
                           moveCamera(locationsAdapter.location,DEFAULT_ZOOM);
                       }//end inner if

                       else{
                           Log.d(TAG, "onComplete: current location is null;");
                           Toast.makeText(MapsActivity.this,"unable to get current location",Toast.LENGTH_SHORT).show();
                       }//end else
                   }
                });//end of completeListener
             }//end if

        }//end of try statement
        catch(SecurityException e){

        }//end of catch
    }//end of getdevicelocation


    private void moveCamera(LatLng latLng,float zoom) //function that will move the the view of the maps to the given location
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }//end of moveCamera


    private void initMap() //function that will initialize the map
    {
        SupportMapFragment mapfragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapfragment.getMapAsync(MapsActivity.this);
    }//end of initMap function


    private void getLocationPermission() //function that will get permission
    {
        String[] permissions= {FINE_LOCATION, COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted=true;
                initMap();
            }//inner if end
            else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);

            }//inner else end

        }//outer if end
        else
            {
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }//outer else end
    }//end of getLocationPermission

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

                        }//end of inner if
                    }//end of for loop
                    mLocationPermissionGranted=true;
                    initMap();

                }//end outer if
            }//end case
        }//switch end

    }//end of onRe... function
}//end of class

