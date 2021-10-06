package com.example.identt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AadharRetrieval extends AppCompatActivity {

    Bundle bundle;
    TextView ret_home;
    ImageView back;
    TextView name, mobile_no;
    Button view_aadhar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_retrieval);

        bundle = getIntent().getExtras();

        ret_home = findViewById(R.id.HomeReq);
        back = findViewById(R.id.backVer);
        name = findViewById(R.id.name_info);
        mobile_no = findViewById(R.id.mobile_info);
        view_aadhar = findViewById(R.id.view_aadhar);

        name.setText(bundle.getString("Name"));
        mobile_no.setText(bundle.getString("Phone"));

        if(bundle.getString("TypeScan").equals("NA"))
        {
            //Coming from Bank
            ret_home.setText("Return to Form");
            ret_home.setTextColor(Color.parseColor("#FC5D73"));
        }

        if(bundle.getString("Coming").equals("True"))
        {
            push_in_firebase();
        }

        view_aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("ViewType", "Aadhar");
                startActivity(new Intent(getApplicationContext(), DocShow.class).putExtras(bundle));
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ret_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    void push_in_firebase()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("forms");
        Map<String, String> user = new HashMap<>();
        user.put("Name", bundle.getString("Name"));
        user.put("Phone", bundle.getString("Phone"));
        user.put("Requested", "Aadhar Card");
        user.put("Pending", "True");
        user.put("User ID", FirebaseAuth.getInstance().getUid());
        databaseReference.push().setValue(user);
    }
}