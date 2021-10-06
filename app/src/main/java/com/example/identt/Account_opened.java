package com.example.identt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Account_opened extends AppCompatActivity {

    Bundle bundle;
    ImageView back;
    Button proceed;
    TextView home, aadhar_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_opened);

        bundle = getIntent().getExtras();
        back = findViewById(R.id.backVer);
        home = findViewById(R.id.HomeReq);
        aadhar_info = findViewById(R.id.AdhaarInfo);
        proceed = findViewById(R.id.view_form);
        aadhar_info.setText("1111 1111 1111 - " + bundle.getString("FName"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BankForm.class).putExtras(bundle));
                finish();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FormShow.class).putExtras(bundle));
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Homepage.class).putExtras(bundle));
                finish();
            }
        });

    }
}