package com.example.identt;

import androidx.appcompat.app.AppCompatActivity;

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

public class PanRetrieval extends AppCompatActivity {

    Bundle bundle;
    TextView name, mobile_no;
    TextView ret_home;
    ImageView back;
    Button view_pan;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_retrieval);

        bundle = getIntent().getExtras();

        ret_home = findViewById(R.id.HomeReq);
        back = findViewById(R.id.backVer);

        name = findViewById(R.id.name_info);
        mobile_no = findViewById(R.id.mobile_info);
        view_pan = findViewById(R.id.view_pan);

        name.setText(bundle.getString("Name"));
        mobile_no.setText(bundle.getString("Phone"));

        if(bundle.getString("Coming").equals("True"))
        {
            push_in_firebase();
        }

        if(bundle.getString("TypeScan").equals("NA"))
        {
            //Coming from Bank
            ret_home.setText("Return to Form");
            ret_home.setTextColor(Color.parseColor("#FC5D73"));
        }

        view_pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("ViewType", "Pan");
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
        user.put("Requested", "Pan Card");
        user.put("Pending", "True");
        user.put("User ID", FirebaseAuth.getInstance().getUid());
        databaseReference.push().setValue(user);
    }

}