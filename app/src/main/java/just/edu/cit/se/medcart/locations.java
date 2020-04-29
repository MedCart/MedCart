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
    public static ArrayList<String> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        list = new ArrayList<>();
        LA=new lAdapter(this,list);
        recyclerView = findViewById(R.id.locations);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(LA);



        list.add("celo");

        LA.notifyDataSetChanged();



    }
}
