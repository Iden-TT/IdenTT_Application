package com.example.identt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity {

    Button new_scan;
    TextView see_how, info1, info2, info3, info4;
    LinearLayout hidden;
    CardView cardView;
    ImageView aadhar, pan, form;
    boolean ishidden = true;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Bundle bundle;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onStart() {
        if(!checkPermission())
        {
            requestPermission();
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        bundle = getIntent().getExtras();

        if(bundle == null || bundle.getString("Phone") == null)
        {
            bundle = new Bundle();
            getphonenofromfirebase();
        }
        assign();

        bundle.putString("Coming", "False");

        see_how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ishidden)
                {
                    TransitionManager.beginDelayedTransition(cardView,
                            new AutoTransition());
                    ishidden = false;
                    hidden.setVisibility(View.VISIBLE);
                    see_how.setText("Hide");
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cardView,
                            new AutoTransition());
                    ishidden = true;
                    hidden.setVisibility(View.GONE);
                    see_how.setText("See how it works > ");
                }
            }
        });

        Bundle finalBundle = bundle;
        new_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalBundle.putString("TypeScan", "NewScan");
                startActivity(new Intent(getApplicationContext(), CodeScan.class).putExtras(finalBundle));
            }
        });

        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalBundle.putString("TypeScan", "Pan");
                startActivity(new Intent(getApplicationContext(), DocVerify.class).putExtras(finalBundle));
            }
        });

        aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalBundle.putString("TypeScan", "Aadhar");
                startActivity(new Intent(getApplicationContext(), DocVerify.class).putExtras(finalBundle));
            }
        });

        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalBundle.putString("TypeScan", "Form");
                startActivity(new Intent(getApplicationContext(), CodeScan.class).putExtras(finalBundle));
            }
        });

    }

    void assign()
    {
        new_scan = findViewById(R.id.new_scan);
        see_how = findViewById(R.id.see_how);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        hidden = findViewById(R.id.hidden_view);
        info1 = findViewById(R.id.dropdown_info1);
        info2 = findViewById(R.id.dropdown_info2);
        info3 = findViewById(R.id.dropdown_info3);
        info4 = findViewById(R.id.dropdown_info4);
        cardView = findViewById(R.id.base_cardview);
        form = findViewById(R.id.form);
        pan = findViewById(R.id.pan);
        aadhar = findViewById(R.id.aadhar);
    }

    void getphonenofromfirebase()
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    if(snapshot1.getKey().equals("Phone"))
                    {
                        bundle.putString("Phone", snapshot1.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

}