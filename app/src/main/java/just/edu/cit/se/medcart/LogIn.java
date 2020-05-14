package just.edu.cit.se.medcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LogIn extends AppCompatActivity {
//attributes declaration
    EditText LEmail,Lpassword;
    ImageView loginbtn;
    TextView LReg;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    //end of declaration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//attributes initialization
        LEmail=findViewById(R.id.LEmail);
        Lpassword =findViewById(R.id.LPassword);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        loginbtn=findViewById(R.id.loginbtn);
        LReg=findViewById(R.id.LReg);
//end of initialization

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email = LEmail.getText().toString().trim();
                    String password = Lpassword.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        LEmail.setError("Email is required.");
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Lpassword.setError("Password is required.");
                        return;
                    }
                    if (password.length() < 6) {
                        Lpassword.setError("password must be >= 6 characters");
                        return;
                    }


                    progressBar.setVisibility(View.VISIBLE);


                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LogIn.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), search.class));
                            }//end if
                            else {
                                Toast.makeText(LogIn.this, "Error !." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }//end else
                        }
                    });
                }//end try statement
                catch (Exception e) {
                    Toast.makeText(LogIn.this, e.toString(), Toast.LENGTH_SHORT).show();
                }//catch end
            }//end of onClick function
        });//end of login button

        LReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Regist.class));
            }//end of onClick function

        });//end of LReg button
    } // end of onClick function


}//end of login class
