package just.edu.cit.se.medcart;


import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class orderInfo extends AppCompatActivity {
    //declare attributes
    ImageView location,confirm;
    EditText number;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    private static final String TAG = "order";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    //end of declaration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

//initialize attribute
        location=findViewById(R.id.location);
        confirm=findViewById(R.id.confrim);
        number=findViewById(R.id.Onumber);
        fStore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
//end of initialization


        if(isServicesOK()){
            init();
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Map<String, Object> user = new HashMap<>();
                    user.put("Location", UserLocation.UL);
                    user.put("Number", number.getText().toString());
                    user.put("Medicines", "All the Cart");
                    fStore.collection("Users").document(userID).collection("order").document("information").set(user).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(orderInfo.this, "your order is confirmed we will contact you", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(orderInfo.this, search.class);
                                    startActivity(intent);

                                }
                            });//end of success listener
                }//end of try statement
                catch (Exception e){

                }//end of catch
            }//end of onClick function
        });//end of confirm button
    }//end of onCreate function

    private void init(){
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(orderInfo.this, UserLocation.class);
                startActivity(intent);
            }
        });//end of location button
    }//end of init function

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");
        try {
            int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(orderInfo.this);

            if (available == ConnectionResult.SUCCESS) {
                //everything is fine and the user can make map requests
                Log.d(TAG, "isServicesOK: Google Play Services is working");
                return true;
            } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
                //an error occured but we can resolve it
                Log.d(TAG, "isServicesOK: an error occured but we can fix it");
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(orderInfo.this, available, ERROR_DIALOG_REQUEST);
                dialog.show();
            } else {
                Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
            }
            return false;
        }//end of try statement
        catch (Exception e) {
            return false;
        }//end of catch
    }//end of is Service ok function
}
