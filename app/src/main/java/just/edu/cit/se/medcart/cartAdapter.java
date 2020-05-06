package just.edu.cit.se.medcart;


import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class cartAdapter extends RecyclerView.Adapter<cartAdapter.CartViewHolder> {

    private Context mCtx;
    private ArrayList<MCart> List;

    class CID{
        String name;
        int position;

        public CID(String name, int position) {
            this.name = name;
            this.position = position;
        }
    }

    private ArrayList<CID> P=new ArrayList<>();



    public cartAdapter(Context mCtx, ArrayList<MCart> list) {
        this.mCtx = mCtx;
        List = list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.cart_card,null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CID c;
        MCart medicine=Cart.list.get(position);
        holder.name.setText(medicine.name);
        holder.price.setText(medicine.price);
        holder.quantity.setText(Integer.toString(medicine.quantity));
        c=new CID(holder.name.getText().toString(),position);
        P.add(c);

    }

    @Override
    public int getItemCount() {
        return Cart.list.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, price , quantity;
        Button Inc, dec, delete;

        public CartViewHolder(@NonNull final View itemView) {
            super(itemView);
            FirebaseAuth fAuth;
            final String userID;
            final FirebaseFirestore fStore;


            fStore = FirebaseFirestore.getInstance();
            fAuth = FirebaseAuth.getInstance();
            userID = fAuth.getCurrentUser().getUid();


            name = itemView.findViewById(R.id.textView);
            price = itemView.findViewById(R.id.textView2);
            quantity=itemView.findViewById(R.id.integer_number);

            Inc = itemView.findViewById(R.id.increase);
            Inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,Object> user=new HashMap<>();
                    int result=Integer.parseInt(quantity.getText().toString())+1 ;
                    user.put("quantity",result);
                    fStore.collection("Users").
                            document(userID).collection("cart").document(name.getText().toString()).update(user);
                    for(int i=0;i<P.size();i++)
                    {
                        if(P.get(i).name==name.getText().toString())
                        {
                            ++Cart.list.get(P.get(i).position).quantity;
                           break;
                        }
                    }
                    notifyDataSetChanged();


                }
            });

            dec = itemView.findViewById(R.id.decrease);
            dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(quantity.getText().toString())==0)
                    {

                    }
                    else{
                    Map<String,Object> user=new HashMap<>();
                    int result=Integer.parseInt(quantity.getText().toString())-1 ;
                    user.put("quantity",result);
                    fStore.collection("Users").
                            document(userID).collection("cart").document(name.getText().toString()).update(user);
                        for(int i=0;i<P.size();i++)
                        {
                            if(P.get(i).name==name.getText().toString())
                            {
                                --Cart.list.get(P.get(i).position).quantity;
                                break;
                            }
                        }
                        notifyDataSetChanged();
                   }
                }
            });

            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            fStore.collection("Users").document(userID).
                                    collection("cart").document(name.getText().toString()).delete();
                    for(int i=0;i<P.size();i++)
                    {
                        if(P.get(i).name==name.getText().toString())
                        {
                            Cart.list.remove(P.get(i).position);
                            search.Clist.remove(P.get(i).position);
                            break;
                        }
                    }
                    notifyDataSetChanged();

                    }

            });
        }

        }

    }

