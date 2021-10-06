package com.example.identt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DocShow extends AppCompatActivity {

    ImageView img;
    Bundle bundle;
    String link_1, link_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_show);

        img = findViewById(R.id.pres_doc);

        link_1 = "https://firebasestorage.googleapis.com/v0/b/identt-9b64f.appspot.com/o/8208208208_aadhar.jpg?alt=media&token=1e628abe-4816-4edc-9ff5-cee36ace8379";
        link_2 = "https://firebasestorage.googleapis.com/v0/b/identt-9b64f.appspot.com/o/8208208208_pan.jpg?alt=media&token=4be811dd-59cc-4fb6-8c47-58803d0c058f";

        bundle = getIntent().getExtras();

        if(bundle.getString("ViewType").equals("Aadhar"))
        {
            Picasso.get().load(link_1).into(img);
        }
        else
        {
            Picasso.get().load(link_2).into(img);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}