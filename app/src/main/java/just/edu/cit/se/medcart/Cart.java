package just.edu.cit.se.medcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.Q;

public class Cart extends AppCompatActivity {

    public static ArrayList<MCart> list;
    RecyclerView recyclerView;
    ImageView order;
    public static cartAdapter CA;
    public static FirebaseFirestore fStore;
    public static FirebaseAuth fAuth;
    public static String userID;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_meds);

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



        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivity =new Intent(getApplicationContext(), order.class);
                startActivity(secondActivity);
            }
        });

        fStore.collection("Users").document(userID).collection("cart").get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> LD = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot D : LD) {
                        MCart m = D.toObject(MCart.class);
                        list.add(m);
                    }
                    CA.notifyDataSetChanged();
                }
            }
        });}


}
