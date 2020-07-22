package com.example.studentloginregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button login,logout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        editText=(EditText)findViewById(R.id.editText);
        textView=(TextView)findViewById(R.id.textView5);
        login=(Button)findViewById(R.id.button);
        logout=(Button)findViewById(R.id.logout);

       // textView.setText(editText.getText().toString());


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain= new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intToMain);
            }
        });
    }
}
