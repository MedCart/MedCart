package just.edu.cit.se.medcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class locations extends AppCompatActivity {

    RecyclerView recyclerView;
    public static lAdapter LA;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        LA=new lAdapter(this,search.Plist);
        recyclerView = findViewById(R.id.locations);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(LA);


        LA.notifyDataSetChanged();



    }
}
