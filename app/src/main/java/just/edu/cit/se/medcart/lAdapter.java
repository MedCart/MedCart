package just.edu.cit.se.medcart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class lAdapter extends RecyclerView.Adapter<lAdapter.CartViewHolder>{
    private Context mCtx;
    private ArrayList<String> List;
    public  String ltd=new String();
    double latitude,longitude;
    public static LatLng location;

    class LID{
        String name;
        int position;

        public LID(String name, int position) {
            this.name = name;
            this.position = position;
        }
    }
    ArrayList <LID> l=new ArrayList<>();
    int pos;

    public lAdapter(Context mCtx, ArrayList<String> list) {
        this.mCtx = mCtx;
        List = list;
    }

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

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        Button go;
        public CartViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.loc);
            go = view.findViewById(R.id.GO);


            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ValueEventListener E=new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    ValueEventListener V=new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    ltd=snapshot.getValue().toString();
                                                    System.out.println(ltd);
                                                    String[] latlong =  ltd.split(",");
                                                    latitude = Double.parseDouble(latlong[0]);
                                                    longitude = Double.parseDouble(latlong[1]);
                                                    location = new LatLng(latitude, longitude);

                                                }}
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    };

                                    String soso = snapshot.getKey();

                                    Query q=FirebaseDatabase.getInstance().getReference("Users").child(soso).limitToLast(1);
                                    System.out.println(soso);
                                    q.addValueEventListener(V);



                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };

                    for(int i=0;i<l.size();i++){
                        if(l.get(i).name==name.getText().toString())
                        {pos=i;
                        break;
                        }}
                    Query query = FirebaseDatabase.getInstance().getReference("Users")
                            .orderByChild("Pharmacy")
                            .equalTo(l.get(pos).name);
                    query.addValueEventListener(E);


                    Intent intent = new Intent(mCtx,MapsActivity.class);
                    mCtx.startActivity(intent);
                }
            });
        }
    }
}
