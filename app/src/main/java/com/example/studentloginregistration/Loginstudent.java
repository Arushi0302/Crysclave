package com.example.studentloginregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Loginstudent extends AppCompatActivity {

    EditText emailID,password;
    Button signin;
    TextView tvsignup;
    FirebaseAuth mfirebaseauth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginstudent);

        mfirebaseauth=FirebaseAuth.getInstance();
           emailID=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        signin =findViewById(R.id.button);
        tvsignup=findViewById(R.id.textViewsignup);

        mAuthStateListner= new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseuser= mfirebaseauth.getCurrentUser();
                if(mfirebaseuser!= null)
                {
                    Toast.makeText(Loginstudent.this,"you are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Loginstudent.this,HomeActivity.class);
                    startActivity(i);

                }
                else{
                    Toast.makeText(Loginstudent.this,"plz log in",Toast.LENGTH_SHORT).show();

                }

            }
        };
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=  emailID.getText().toString();
                String pwd=password.getText().toString();
                if(email.isEmpty() || pwd.isEmpty()){
                    Toast.makeText(Loginstudent.this,"Fields are empty",Toast.LENGTH_LONG).show();

                }
               else if(!(email.trim().isEmpty() && pwd.trim().isEmpty())){
                    mfirebaseauth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(Loginstudent.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser users = mfirebaseauth.getCurrentUser();
                                        startActivity(new Intent(Loginstudent.this, HomeActivity.class));

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Loginstudent.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
                else{
                    Toast.makeText(Loginstudent.this,"error occured",Toast.LENGTH_LONG).show();
                }

            }
        });
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp=  new Intent(Loginstudent.this,MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseauth.addAuthStateListener(mAuthStateListner);
    }
}
