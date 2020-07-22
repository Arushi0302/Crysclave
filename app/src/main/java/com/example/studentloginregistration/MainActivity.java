package com.example.studentloginregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    EditText emailID, password,name,regno,phno;
    Button signup;
    TextView tvsignin;
    FirebaseAuth mfirebaseauth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirebaseauth = FirebaseAuth.getInstance();

        emailID = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        name=findViewById(R.id.editText3);
        regno=findViewById(R.id.editText4);
        phno=findViewById(R.id.editText5);
        signup = findViewById(R.id.button);
        tvsignin = findViewById(R.id.textViewsignin);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailID.getText().toString();
                final String pwd = password.getText().toString();
                final String Name = name.getText().toString();
                final String Regno =regno.getText().toString();
                final String phone = phno.getText().toString();
                if (email.trim().isEmpty() || pwd.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
                } else {

                    mfirebaseauth.createUserWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser users = mfirebaseauth.getCurrentUser();
                                        Toast.makeText(MainActivity.this, "SUCCESS",
                                                Toast.LENGTH_SHORT).show();
                                        assert users != null;
                                        String userid = users.getUid();

                                        ref = FirebaseDatabase.getInstance().getReference("Users").child(userid).child("General Info");

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", userid);
                                        hashMap.put("EmailID", email);
                                        hashMap.put("Name", Name);
                                        hashMap.put("Reg no", Regno);
                                        hashMap.put("Phone Number", phone);
                                        hashMap.put("Password", pwd);

                                        ref.setValue(hashMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Intent MainIntent = new Intent(MainActivity.this, HomeActivity.class);
                                                            MainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(MainIntent);
                                                            Toast.makeText(MainActivity.this, "You are Successfully Registered.",
                                                                    Toast.LENGTH_LONG).show();
                                                            finish();
                                                        }
                                                    }
                                                });
                                   //     startActivity(new Intent(MainActivity.this, HomeActivity.class));

                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    /*mfirebaseauth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                Log.d("email*****************",email);

                            } else {
                                Toast.makeText(MainActivity.this, "Unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        }
                    });*/

                }
            }
        });
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Loginstudent.class);
                startActivity(i);
            }
        });
    }
}
