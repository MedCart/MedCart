package just.edu.cit.se.medcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class lAdapter extends RecyclerView.Adapter<lAdapter.CartViewHolder>{
    private Context mCtx;

    public lAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.pharmacies,null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        public CartViewHolder(View view) {
            super(view);
        }
    }
}
