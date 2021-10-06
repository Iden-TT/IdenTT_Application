package com.example.identt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class LoginActivity extends AppCompatActivity {

    MaskedEditText phone;
    Button proceed;
    TextView signup;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    boolean pres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assign();

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isvalid(phone.getRawText().toString()))
                {
                    not_a_user(phone.getRawText());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if(!pres)
                            {
                                Toast.makeText(getApplicationContext(), "Phone Number is not registered! Please Sign Up!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Bundle bundle = new Bundle();
                                bundle.putString("TypeAuth", "Login");
                                bundle.putString("Phone", phone.getRawText());
                                startActivity(new Intent(getApplicationContext(), VerificationActivity.class).putExtras(bundle));
                                finish();
                            }
                        }
                    }, 5000);
                }
                else
                {
                    phone.setError("Phone number is not valid!");
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    private boolean isvalid(String s)
    {
        return (s.length() == 10);
    }

    private void assign()
    {
        phone = findViewById(R.id.phone);
        proceed = findViewById(R.id.proceed);
        signup = findViewById(R.id.signup_tv);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    private boolean not_a_user(String phone)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        pres = false;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        if(dataSnapshot1.getValue().equals(phone))
                        {
                            pres = true;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return !pres;
    }

}