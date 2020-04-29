package just.edu.cit.se.medcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class lAdapter extends RecyclerView.Adapter<lAdapter.CartViewHolder>{
    private Context mCtx;
    private ArrayList<String> List;

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
        holder.name.setText("celine");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public CartViewHolder(View view) {
            super(view);
            name = itemView.findViewById(R.id.loc);
        }
    }
}
