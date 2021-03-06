package just.edu.cit.se.medcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class search extends AppCompatActivity {

//attribute declaration
    private static final String TAG = "SearchActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    public static ArrayList<CartMeds> Clist ;
    public static ArrayList<String> Plist;
    ImageView searchIV ,VcartIV,AddCart;
    EditText MedET ;
    TextView name,price,dosage,usage ,Dtag,Utag,Ptag;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    CartMeds mcart;
    Cart c;
    ImageView logut;
//end of attributes declaration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//class attributes initialization
        c=new Cart();
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        searchIV=findViewById(R.id.s);
        AddCart=findViewById(R.id.addCart);
        VcartIV=findViewById(R.id.viewCart);
        MedET=findViewById(R.id.etse);
        Clist = new ArrayList<>();
        Plist=new ArrayList<>();
        logut=findViewById(R.id.logout);
        usage=findViewById(R.id.usage);
        dosage=findViewById(R.id.dosage);
        Dtag=findViewById(R.id.Dtag);
        Ptag=findViewById(R.id.Ptag);
        Utag=findViewById(R.id.Utag);
        fStore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
//end of class attributes initialization


        if(fAuth.getCurrentUser() != null) { //to check if the logout button is needed or no and to get the user saved cart
            userID = fAuth.getCurrentUser().getUid();
            logut.setVisibility(View.VISIBLE);

            fStore.collection("Users").document(userID).collection("cart").get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> LD = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot D : LD) { //to loop over the records and add them to cart list
                                CartMeds m=D.toObject(CartMeds.class);
                                Clist.add(m);
                            }//end of for
                        }//end inner if
                    }
                });// end of successListener
        }// end outer if



        VcartIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fAuth.getCurrentUser() == null) {//to check if the user is logged in
                    Toast.makeText(search.this,"you should be logged in to have a cart!!",Toast.LENGTH_SHORT).show();
                }//end if
                else {
                Intent secondActivity =new Intent(getApplicationContext(), Cart.class);
                startActivity(secondActivity);
                }//end else
            }
        });//end of listener


        AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //on click function
                try{  // try statement
                if(fAuth.getCurrentUser() != null) { //outer if
                    if ( mcart ==null || mcart.name.matches("")) { //inner if
                        Toast.makeText(search.this, "No Medicine to add!", Toast.LENGTH_SHORT).show();
                    }//end of inner if
                    else if (inCart()) { //inner else if
                        Toast.makeText(search.this, "This Medicine is already added!", Toast.LENGTH_SHORT).show();}//end of else if
                     else {//inner else
                        Map<String, Object> user = new HashMap<>();
                         user.put("name", mcart.name);
                         user.put("price", mcart.price);
                         user.put("quantity", 1);
                         fStore.collection("Users").document(userID).collection("cart").document(mcart.name).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 Toast.makeText(search.this, "Medicine added.", Toast.LENGTH_SHORT).show();
                                 Clist.add(mcart);
                             }
                         });
                    }//end of inner else
                }//end of outer if

                else {
                    Toast.makeText(search.this, "you should be logged in to have a cart!!", Toast.LENGTH_SHORT).show();
                }//end of outer else
                } // end of try
                catch (Exception e){
                    Toast.makeText(search.this,e.toString(),Toast.LENGTH_SHORT).show();
                }//end of catch statement
            }//end of on click function
        });




       searchIV.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {// on click function
             try { //try statement
                     String MedName = String.valueOf(MedET.getText());
                     if(MedName.matches("")){
                         Toast.makeText(search.this,"no medicine to search for",Toast.LENGTH_SHORT).show();
                         return;
                     }//end if

                     Query query = FirebaseDatabase.getInstance().getReference("database").child("0").child("data")
                             .orderByChild("name")
                             .equalTo(MedName.toLowerCase());
                     query.addListenerForSingleValueEvent(valueEventListener);
                     Query query1 = FirebaseDatabase.getInstance().getReference("Pharmacies").orderByChild(MedName.toLowerCase()).equalTo(MedName.toLowerCase());
                     query1.addValueEventListener(valueEventListener1);


                } //end of try statement

             catch (Exception e) { //catch statement

             }//end of catch statement

         }//end of onclick function

       }); // end of listener




    } // end of onCreate function

    public boolean inCart() // function to check if the medicine is already on the cart or no
    {
        for(int i=0;i<Clist.size();i++)
        { //for to loop all over the cart list
            System.out.println(Clist.get(i).name);
            if(Clist.get(i).name.equals(String.valueOf(MedET.getText()).toLowerCase()))
            { // if statement to check for matching medicine name
                return true;
            }// end of if statement
        }//end of for
        return false;
    }// end of inCart function

    ValueEventListener valueEventListener = new ValueEventListener() { // to work on the search query
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            try {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //for to loop over the record from firebase
                        MedicineInfo medicine = snapshot.getValue(MedicineInfo.class);
                        mcart = new CartMeds();
                        mcart.name = medicine.name;
                        Dtag.setVisibility(View.VISIBLE);
                        Ptag.setVisibility(View.VISIBLE);
                        Utag.setVisibility(View.VISIBLE);
                        mcart.price = medicine.price;
                        name.setText(medicine.name);
                        usage.setText(medicine.usage);
                        dosage.setText(medicine.getDosage());
                        price.setText(medicine.price);
                    } // end of for loop

                } // end of if statement
                else {
                    Toast.makeText(search.this, "Medicine is not exit!", Toast.LENGTH_SHORT).show();
                }// end of else
            }// try statement
            catch(Exception e){
                Toast.makeText(search.this, e.toString(), Toast.LENGTH_SHORT).show();
            }// end of catch
        }// end of onDataChange function

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }; // end of valueEventListener


    ValueEventListener valueEventListener1=new ValueEventListener() { // to work on the pharmacies query
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            try {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // to loop over the records
                        String S = snapshot.getKey();
                        if(Plist.contains(S))
                            continue;
                        else
                        Plist.add(S); // to add the pharmacies to list
                    }//end of for loop
                }//end of if statement
                else {
                }// end of else
            }//end of try statement
            catch (Exception e){
                Toast.makeText(search.this, e.toString(), Toast.LENGTH_SHORT).show();
            }//end of catch
        }// end of onDataChange

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }; // end of valueEventListener1




    public void logout(View view) { //function to do logout functionality
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }// end of logout function
}//end of search class

