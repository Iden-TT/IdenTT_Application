package com.example.identt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class CodeScan extends AppCompatActivity {

    Bundle bundle;
    AppCompatButton back;
    CodeScannerView scannerView;
    CodeScanner mCodeScanner;
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
        setContentView(R.layout.activity_code_scanner);

        bundle = getIntent().getExtras();
        back = findViewById(R.id.BackQR);
        scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        if (checkPermission()) {
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String pres = result.getText().toString();
                            if(pres.charAt(pres.length() - 3) == 'm')
                            {
                                bundle.putString("TypeScan", "Form");
                            }
                            else if(pres.charAt(pres.length() - 8) == 'n')
                            {
                                bundle.putString("TypeScan", "Pan");
                                bundle.putString("Coming", "True");
                            }
                            else
                            {
                                bundle.putString("TypeScan", "Aadhar");
                                bundle.putString("Coming", "True");
                            }
                            startActivity(new Intent(getApplicationContext(), DocVerify.class).putExtras(bundle));
                            finish();
                        }
                    });
                }
            });

        } else {
            requestPermission();
        }

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Homepage.class).putExtras(bundle));
                finish();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Homepage.class).putExtras(bundle));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}