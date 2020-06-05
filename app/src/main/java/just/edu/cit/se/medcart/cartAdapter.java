package just.edu.cit.se.medcart;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class cartAdapter extends RecyclerView.Adapter<cartAdapter.CartViewHolder> {
//attributes declaration
    private Context mCtx;
    private ArrayList<CartMeds> List;

    //inner claas to hold the position of the card
    class CID{
        String name;
        int position;

        public CID(String name, int position) {
            this.name = name;
            this.position = position;
        }
    }//end of CID class

    private ArrayList<CID> P=new ArrayList<>();
//end of declaration


    public cartAdapter(Context mCtx, ArrayList<CartMeds> list) { // class constructor
        this.mCtx = mCtx;
        List = list;
    } // end of constructor

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.cart_card,null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CID c;
        CartMeds medicine=Cart.list.get(position);
        holder.name.setText(medicine.name);
        holder.price.setText(medicine.price);
        holder.quantity.setText(Integer.toString(medicine.quantity));
        c=new CID(holder.name.getText().toString(),position);
        P.add(c);
    }//end of onBindViewerHolder function

    @Override
    public int getItemCount() {
        return Cart.list.size();
    }//end of getItemCount fucntion

    public class CartViewHolder extends RecyclerView.ViewHolder { // inner class for the viewHolder
        TextView name, price , quantity;
        Button Inc, dec, delete;

        public CartViewHolder(@NonNull final View itemView) { // constructor
            super(itemView);
            FirebaseAuth fAuth;
            final String userID;
            final FirebaseFirestore fStore;


            fStore = FirebaseFirestore.getInstance();
            fAuth = FirebaseAuth.getInstance(); // to get the user ID
            userID = fAuth.getCurrentUser().getUid();


            name = itemView.findViewById(R.id.textView);
            price = itemView.findViewById(R.id.textView2);
            quantity=itemView.findViewById(R.id.integer_number);

//buttons handlers
            Inc = itemView.findViewById(R.id.increase);
            Inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Map<String, Object> user = new HashMap<>();
                        int result = Integer.parseInt(quantity.getText().toString()) + 1;
                        user.put("quantity", result);
                        fStore.collection("Users").
                                document(userID).collection("cart").document(name.getText().toString()).update(user);
                        for (int i = 0; i < P.size(); i++) { // to loop all over the list elements
                            if (P.get(i).name == name.getText().toString()) { //to check in which position we are
                                ++Cart.list.get(P.get(i).position).quantity;
                                break;
                            }//end if
                        }//end of for loop
                        notifyDataSetChanged();

                    }//end of try statement
                    catch (Exception e){

                    }//end of catch
                }// end of onClick function
            }); // end of inc button listener

            dec = itemView.findViewById(R.id.decrease);
            dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (Integer.parseInt(quantity.getText().toString()) == 0) {
                            //to make sure that the quantity went get less than 0
                        }//end if
                        else {
                            Map<String, Object> user = new HashMap<>();
                            int result = Integer.parseInt(quantity.getText().toString()) - 1;
                            user.put("quantity", result);
                            fStore.collection("Users").
                                    document(userID).collection("cart").document(name.getText().toString()).update(user);
                            for (int i = 0; i < P.size(); i++)// to loop all over the list elements
                            {
                                if (P.get(i).name == name.getText().toString())//to check in which position we are
                                {
                                    --Cart.list.get(P.get(i).position).quantity;
                                    break;
                                }//end if
                            }//end of for
                            notifyDataSetChanged();
                        }//end else
                    }//end of try statement
                    catch (Exception e){

                    }//end of catch
                }//end of onClick function
            }); // end of dec button

            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        try {
                            fStore.collection("Users").document(userID).
                                    collection("cart").document(name.getText().toString()).delete();
                            for (int i = 0; i < P.size(); i++)// to loop all over the list elements
                            {
                                if (P.get(i).name == name.getText().toString())//to check in which position we are
                                {
                                    Cart.list.remove(P.get(i).position);
                                    search.Clist.remove(P.get(i).position);
                                    break;
                                }//end if
                            }//end of for loop
                            notifyDataSetChanged();
                        }//end of try statement
                        catch (Exception e) {

                        }//end of catch
                    } //end of onClick function

            });//end of delete button
        } // end of the constructor

        }//end of the inner class

    }// end of the adapter class

