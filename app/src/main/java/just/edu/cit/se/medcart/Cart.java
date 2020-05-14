package just.edu.cit.se.medcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;


public class Cart extends AppCompatActivity {

    //attribute declaration
    public static ArrayList<MCart> list;
    RecyclerView recyclerView;
    ImageView order;
    public static cartAdapter CA;
    public static FirebaseFirestore fStore;
    public static FirebaseAuth fAuth;
    public static String userID;
    TextView name;
    //end of attributes declaration


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_meds);

//class attributes initialization
        recyclerView = findViewById(R.id.cartRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        CA = new cartAdapter(this,list);
        recyclerView.setAdapter(CA);
        fStore=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        userID=fAuth.getCurrentUser().getUid();
        name=findViewById(R.id.name);
        order=findViewById(R.id.order);
//end of class attributes initialization


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivity =new Intent(getApplicationContext(), order.class);
                startActivity(secondActivity);
            }
        });//end of order button click


        fStore.collection("Users").document(userID).collection("cart").get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {//to make sure that there are snapshots
                    List<DocumentSnapshot> LD = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot D : LD) {//to loop all over the records and add them to the list
                        MCart m = D.toObject(MCart.class);
                        list.add(m);
                    }//end of for loop
                    CA.notifyDataSetChanged();
                }//end of if statement
            }
        });
    }//end of onCreate function
}//end of cart class
