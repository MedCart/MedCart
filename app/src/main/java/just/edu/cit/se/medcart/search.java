package just.edu.cit.se.medcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class search extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;


    public static ArrayList<MCart> Clist ;
    ImageView searchIV ,VcartIV,AddCart;
    EditText MedET ;
    TextView name,price,dosage,usage ;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    MCart Mcart;
    Cart c;
    ImageView logut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);




        c=new Cart();
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        searchIV=findViewById(R.id.s);
        AddCart=findViewById(R.id.addCart);
        VcartIV=findViewById(R.id.viewCart);
        MedET=findViewById(R.id.etse);
        Clist = new ArrayList<MCart>();
        logut=findViewById(R.id.logout);
        usage=findViewById(R.id.usage);
        dosage=findViewById(R.id.dosage);



        fStore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        if(fAuth.getCurrentUser() != null) {
            logut.setVisibility(View.VISIBLE);

        fStore.collection("Users").document(userID).collection("cart").get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> LD = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot D : LD) {
                                MCart m=D.toObject(MCart.class);
                                Clist.add(m);
                            }
                        }
                    }
                });
        }



        VcartIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivity =new Intent(getApplicationContext(), Cart.class);
                startActivity(secondActivity);


            }
        });


        AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fAuth.getCurrentUser() != null) {
                    if (Mcart.name == "") {
                        Toast.makeText(search.this, "No Medicine to add!", Toast.LENGTH_SHORT).show();
                    } else if (inCart()) {
                        Toast.makeText(search.this, "This Medicine is already added!", Toast.LENGTH_SHORT).show();}
                     else {
                        Map<String, Object> user = new HashMap<>();
                         user.put("name", Mcart.name);
                         user.put("price", Mcart.price);
                         user.put("quantity", 1);
                         fStore.collection("Users").document(userID).collection("cart").document(Mcart.name).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 Toast.makeText(search.this, "Medicine added.", Toast.LENGTH_SHORT).show();
                                 Clist.add(Mcart);
                             }
                         });
                    }
                }

                else{Toast.makeText(search.this,"you should be logged in to have a cart!!",Toast.LENGTH_SHORT).show();

                }
            }
        });



       searchIV.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String MedName= String.valueOf(MedET.getText());
             Query query = FirebaseDatabase.getInstance().getReference("database").child("0").child("data")
                      .orderByChild("name")
                      .equalTo(MedName);
             query.addListenerForSingleValueEvent(valueEventListener);
         }
       });


        if (isServiceOK()) { //to check if the maps service is okay then it will initialize it

            inint();
        }

    }

    public boolean inCart()
    {
        for(int i=0;i<Clist.size();i++)
        {
            if(Clist.get(i).name.equals(name.getText().toString()) )
            {
                return true;
            }
        }
        return false;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Mcart =new MCart();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Medicine medicine = snapshot.getValue(Medicine.class);
                    Mcart.name=medicine.name;
                    Mcart.price=medicine.price;
                    name.setText(medicine.name);
                    usage.setText(medicine.usage);
                    dosage.setText(medicine.getDosage());
                    price.setText(medicine.price);
                }

            }
            else {Toast.makeText(search.this,"Medicine is not exit!",Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    public void map(View view) {
       Intent secondActivity =new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(secondActivity);


    }


    public void inint(){
        @SuppressLint("WrongViewCast") ImageView btnMap=(ImageView) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search.this,MapsActivity.class);
                startActivity(intent);
            }
        }
        );
    }



    public boolean isServiceOK(){
        Log.d(TAG, "isServiceOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(search.this);
        if(available == ConnectionResult.SUCCESS)
        {
            Log.d(TAG, "isServiceOK: google play services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServiceOK: as error occured but we can fix it");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(search.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();

        }
        else {Toast.makeText(this,"you can't make app request",Toast.LENGTH_SHORT).show();
        }
        return false;
        }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}

