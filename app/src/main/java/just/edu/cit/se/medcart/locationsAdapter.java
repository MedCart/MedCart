package just.edu.cit.se.medcart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class locationsAdapter extends RecyclerView.Adapter<locationsAdapter.CartViewHolder>{
    //attributes declaration
    private Context mCtx;
    private ArrayList<String> List;
    public  String ltd=new String();
    double latitude,longitude;
    public static LatLng location;
    int pos;
    //end of declaration

    class LID{ // inner class to hols the postion of the card
        String name;
        int position;

        public LID(String name, int position) {
            this.name = name;
            this.position = position;
        }
    } //end of inner class
    ArrayList <LID> l=new ArrayList<>();


    public locationsAdapter(Context mCtx, ArrayList<String> list) {//constructor
        this.mCtx = mCtx;
        List = list;
    }//end of constructor

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.pharmacies,null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        LID l2=new LID(List.get(position),position);
        l.add(l2);
        holder.name.setText(List.get(position));
    }

    @Override
    public int getItemCount() {
        return search.Plist.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder { //inner class viewholder
        TextView name;
        Button go;
        public CartViewHolder(View view) { //inner class constructor
            super(view);
            name = view.findViewById(R.id.loc);
            go = view.findViewById(R.id.GO);


            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ValueEventListener E = new ValueEventListener() { //to work with query results
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//to loop over the first query results
                                        ValueEventListener V = new ValueEventListener() { //to work with q results
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//to loop over the q results
                                                        ltd = snapshot.getValue().toString();
                                                        System.out.println(ltd);
                                                        String[] latlong = ltd.split(",");
                                                        latitude = Double.parseDouble(latlong[0]);
                                                        longitude = Double.parseDouble(latlong[1]);
                                                        location = new LatLng(latitude, longitude);
                                                    }//end of q for loop
                                                }//end of q if
                                            }//end of q onDataChanged

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }//end of onCanelled function
                                        }; //end of q listener
                                        String ID = snapshot.getKey();
                                        Query q = FirebaseDatabase.getInstance().getReference("Users").child(ID).limitToLast(1);
                                        System.out.println(ID);
                                        q.addValueEventListener(V);
                                    }// end of query results loop
                                }//end of query if
                            }//end of query onDataChanged

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };//end of query listener

                        for (int i = 0; i < l.size(); i++) {
                            if (l.get(i).name == name.getText().toString()) {
                                pos = i;
                                break;
                            }
                        }
                        Query query = FirebaseDatabase.getInstance().getReference("Users")
                                .orderByChild("Pharmacy")
                                .equalTo(l.get(pos).name);
                        query.addValueEventListener(E);


                        Intent intent = new Intent(mCtx, MapsActivity.class);
                        mCtx.startActivity(intent);
                    }
                    catch ( Exception e){

                    }//end of catch
                }//end of on click function
            });//end of go button
        } //end of inner class constructor
    }//end inner class
}//end of locationAdapter class
