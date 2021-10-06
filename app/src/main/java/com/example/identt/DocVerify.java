package com.example.identt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class DocVerify extends AppCompatActivity {

    TextView verify_phone;
    Button proceed;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    Bundle bundle;
    OtpTextView otpView;

    String type = "", phone_no;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_verify);

        bundle = getIntent().getExtras();

        assign();

        type = bundle.getString("TypeScan");

        phone_no = "+91" + bundle.getString("Phone");

        verify_phone.setText(phone_no);

        sendVerificationCode(phone_no);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otpView.getOTP().trim().length() == 6)
                {
                    verifyCode(otpView.getOTP().toString().trim());
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Full OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void assign()
    {
        proceed = findViewById(R.id.proceed);
        firebaseAuth = FirebaseAuth.getInstance();
        otpView = findViewById(R.id.otp_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        verify_phone = findViewById(R.id.tv_verify_phone);
    }

    void sendVerificationCode(String phone)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otpView.setOTP(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(type.equals("Form"))
                            {
                                startActivity(new Intent(getApplicationContext(), BankForm.class).putExtras(bundle));
                                finish();
                            }
                            else if(type.equals("Pan"))
                            {
                                startActivity(new Intent(getApplicationContext(), PanRetrieval.class).putExtras(bundle));
                                finish();
                            }
                            else if(type.equals("Aadhar"))
                            {
                                startActivity(new Intent(getApplicationContext(), AadharRetrieval.class).putExtras(bundle));
                                finish();
                            }
                            else
                            {
                                if(type.equals("Form"))
                                {
                                    startActivity(new Intent(getApplicationContext(), BankForm.class).putExtras(bundle));
                                }
                                else if(type.equals("Aadhar"))
                                {
                                    startActivity(new Intent(getApplicationContext(), AadharRetrieval.class).putExtras(bundle));
                                }
                                else
                                {
                                    startActivity(new Intent(getApplicationContext(), PanRetrieval.class).putExtras(bundle));
                                }
                                finish();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Homepage.class).putExtras(bundle));
        finish();
    }

}