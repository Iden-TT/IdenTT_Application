package com.example.identt;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, aadhar;
    MaskedEditText phone;
    Button proceed;
    TextView login;
    boolean already;

    String name_regex = "([A-Z][a-z]{2,} )([A-Z][a-z]{2,} )?([A-Z][a-z]{2,})";
    String email_regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        assign();

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean pres = isvalid();
                if(pres)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("TypeAuth", "Signup");
                    bundle.putString("Phone", phone.getRawText());
                    bundle.putString("Name", name.getText().toString());
                    bundle.putString("Email", email.getText().toString());
                    bundle.putString("Aadhar", aadhar.getText().toString());
                    startActivity(new Intent(getApplicationContext(), VerificationActivity.class).putExtras(bundle));
                    finish();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    void assign()
    {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        aadhar = findViewById(R.id.aadhar);
        proceed = findViewById(R.id.proceed);
        login = findViewById(R.id.login_tv);
        phone = findViewById(R.id.phone);
    }

    boolean isvalid()
    {
        String str_name = name.getText().toString();
        String str_email = email.getText().toString().trim();
        String str_aadhar = aadhar.getText().toString().trim();
        String str_phone = phone.getRawText().toString();
        Pattern pname = Pattern.compile(name_regex);
        Pattern pemail = Pattern.compile(email_regex);
        if(!pname.matcher(str_name).matches())
        {
            name.setError("Not a valid name!");
            return false;
        }
        else if(str_phone.length() != 10)
        {
            phone.setError("Not a valid phone number!");
            return false;
        }
        else if(!pemail.matcher(str_email).matches())
        {
            email.setError("Not a valid email address!");
            return false;
        }
        else if(str_aadhar.length() != 12 && str_aadhar.length() != 0)
        {
            aadhar.setError("Aadhar should be 12 digits long!");
            return false;
        }
        else
        {
            return true;
        }
    }

}