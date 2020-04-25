package just.edu.cit.se.medcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Regist extends AppCompatActivity {

    EditText Rname,REmail,Rpassword,Rphone;
    ImageView Registerbtn;
    TextView Rlogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Rname=findViewById(R.id.RName);
        Rpassword=findViewById(R.id.RPassword);
        Rphone=findViewById(R.id.RphoneNumber);
        REmail=findViewById(R.id.REmail);
        Registerbtn=findViewById(R.id.regbtn);
        Rlogin=findViewById(R.id.Rlog);
        fStore=FirebaseFirestore.getInstance();

        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),search.class));
        }

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = REmail.getText().toString().trim();
                String password=Rpassword.getText().toString().trim();
                final String fullName=Rname.getText().toString();
                final String phone=Rphone.getText().toString();


                if(TextUtils.isEmpty(email)){
                    REmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Rpassword.setError("Password is required.");
                    return;
                }
                if(password.length()<6){
                    Rpassword.setError("password must be >= 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Regist.this,"User Created.",Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference =fStore.collection("Users").document(userID);
                            Map<String,Object> user=new HashMap<>();

                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                            startActivity(new Intent(getApplicationContext(),search.class));
                        }else{
                            Toast.makeText(Regist.this,"Error !." + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });

        Rlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn.class));
            }
        });
    }
}
