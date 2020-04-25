package just.edu.cit.se.medcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth=FirebaseAuth.getInstance();


        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),search.class));
        }
    }


    public void re(View view) {
        Intent  secondActivity =new Intent(getApplicationContext(),Regist.class);
        startActivity(secondActivity);
    }

    public void login(View view) {
        Intent  secondActivity =new Intent(getApplicationContext(),LogIn.class);
        startActivity(secondActivity);
    }

    public void skip(View view)
    {
        Intent  secondActivity =new Intent(getApplicationContext(),search.class);
        startActivity(secondActivity);
    }
}
