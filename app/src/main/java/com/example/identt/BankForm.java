package com.example.identt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class BankForm extends AppCompatActivity {

    Bundle bundle;
    EditText name, fathername, mothername, dob, occupation, ccode, tino, pob, ccob, email, aadhar, pan;
    TextView aadhar_ev, pan_ev;
    MaskedEditText phone;
    RadioGroup gender, mar_status;
    RadioButton male, female, trans, married, unmarried, others;
    Spinner resident_status;
    ImageView drop_spinner;
    Button submit;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String link_1, link_2;

    String name_regex = "([A-Z][a-z]{2,} )([A-Z][a-z]{2,} )?([A-Z][a-z]{2,})";
    String email_regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_form);

        bundle = getIntent().getExtras();

        assign();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isvalid())
                {
                    put_in_bundle();
                    push_in_firebase();
                    startActivity(new Intent(getApplicationContext(), Account_opened.class).putExtras(bundle));
                    finish();
                }
            }
        });

        aadhar_ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("TypeScan", "NA");
                startActivity(new Intent(getApplicationContext(), AadharRetrieval.class).putExtras(bundle));
                aadhar_ev.setText("e-verified");
                aadhar_ev.setTextColor(Color.parseColor("#48BF84"));
                aadhar.setText("1111 1111 1111");
            }
        });

        pan_ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("TypeScan", "NA");
                startActivity(new Intent(getApplicationContext(), PanRetrieval.class).putExtras(bundle));
                pan_ev.setText("e-verified");
                pan_ev.setTextColor(Color.parseColor("#48BF84"));
                pan.setText("111 111 1111");
            }
        });
    }

    void push_in_firebase()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("forms");
        Map<String, String> user = new HashMap<>();
        user.put("Name", bundle.getString("FName"));
        user.put("Father Name", bundle.getString("FFName"));
        user.put("Mother Name", bundle.getString("FMName"));
        user.put("DOB", bundle.getString("FDOB"));
        user.put("Occupation", bundle.getString("FOccupation"));
        user.put("Country code", bundle.getString("Fccode"));
        user.put("Tax Iden Number", bundle.getString("Ftino"));
        user.put("Place of Birth", bundle.getString("Fpob"));
        user.put("Phone", bundle.getString("Fphone"));
        user.put("Country Code of Birth", bundle.getString("Fccob"));
        user.put("Email", bundle.getString("FEmail"));
        user.put("Residential Address", bundle.getString("FResident"));
        user.put("Gender", bundle.getString("FGender"));
        user.put("Maritial Status", bundle.getString("FMar_status"));
        user.put("Pending", "True");
        user.put("User ID", firebaseAuth.getUid());
        user.put("Aadhar Card", link_1);
        user.put("Pan Card", link_2);
        user.put("Requested", "Bank Form");
        databaseReference.push().setValue(user);
    }

    void put_in_bundle()
    {
        bundle.putString("FName", name.getText().toString());
        bundle.putString("FFName", fathername.getText().toString());
        bundle.putString("FMName", mothername.getText().toString());
        bundle.putString("FDOB", dob.getText().toString());
        bundle.putString("FOccupation", occupation.getText().toString());
        bundle.putString("Fccode", ccode.getText().toString());
        bundle.putString("Ftino", tino.getText().toString());
        bundle.putString("Fpob", pob.getText().toString());
        bundle.putString("Fphone", phone.getRawText());
        bundle.putString("Fccob", ccob.getText().toString());
        bundle.putString("FEmail", email.getText().toString());
        bundle.putString("FResident", resident_status.getSelectedItem().toString());
        if(male.isChecked())
        {
            bundle.putString("FGender", "Male");
        }
        else if(female.isChecked())
        {
            bundle.putString("FGender", "Female");
        }
        else
        {
            bundle.putString("FGender", "Transgender");
        }
        if(married.isChecked())
        {
            bundle.putString("FMar_status", "Married");
        }
        else if(unmarried.isChecked())
        {
            bundle.putString("FMar_status", "Unmarried");
        }
        else
        {
            bundle.putString("FMar_status", "Others");
        }
    }

    void assign()
    {
        name = findViewById(R.id.yourname);
        fathername = findViewById(R.id.fathername);
        mothername = findViewById(R.id.mothername);
        dob = findViewById(R.id.dob);
        occupation = findViewById(R.id.occupation);
        ccode = findViewById(R.id.cc);
        tino = findViewById(R.id.tax_iden_no);
        pob = findViewById(R.id.placeofbirth);
        phone = findViewById(R.id.phone);
        ccob = findViewById(R.id.ccbirth);
        email = findViewById(R.id.email);
        aadhar = findViewById(R.id.aadhar);
        pan = findViewById(R.id.pan);
        resident_status = findViewById(R.id.res_spinner);
        drop_spinner = findViewById(R.id.dropdown_spinner);
        submit = findViewById(R.id.proceed);
        gender = findViewById(R.id.Gender_rg);
        mar_status = findViewById(R.id.maritial_rg);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        trans = findViewById(R.id.Transgender);
        married = findViewById(R.id.married);
        unmarried = findViewById(R.id.unmarried);
        others = findViewById(R.id.Others);
        aadhar_ev = findViewById(R.id.everifyaadhar);
        pan_ev = findViewById(R.id.everifypan);
        link_1 = "https://firebasestorage.googleapis.com/v0/b/identt-9b64f.appspot.com/o/8208208208_aadhar.jpg?alt=media&token=1e628abe-4816-4edc-9ff5-cee36ace8379";
        link_2 = "https://firebasestorage.googleapis.com/v0/b/identt-9b64f.appspot.com/o/8208208208_pan.jpg?alt=media&token=4be811dd-59cc-4fb6-8c47-58803d0c058f";
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Resident and Ordinarily Resident (ROR)");
        arrayList.add("Resident but Not Ordinarily Resident (RNOR)");
        arrayList.add("Non-Resident (NR)");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resident_status.setAdapter(arrayAdapter);
    }

    private boolean isvalid()
    {
        if(!Pattern.compile(name_regex).matcher(name.getText().toString()).matches())
        {
            name.setError("Please enter a valid name!");
            return false;
        }
        else if(!Pattern.compile(name_regex).matcher(fathername.getText().toString()).matches())
        {
            fathername.setError("Please enter a valid name!");
            return false;
        }
        else if(!Pattern.compile(name_regex).matcher(mothername.getText().toString()).matches())
        {
            mothername.setError("Please enter a valid name!");
            return false;
        }
        else if(dob.getText().toString().equals(""))
        {
            dob.setError("The field is empty!");
            return false;
        }
        else if(!male.isChecked() && !female.isChecked() && !trans.isChecked())
        {
            Toast.makeText(getApplicationContext(), "Please select a gender!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!married.isChecked() && !unmarried.isChecked() && !others.isChecked())
        {
            Toast.makeText(getApplicationContext(), "Please select a marital status!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(resident_status.getSelectedItem() == null)
        {
            Toast.makeText(getApplicationContext(), "Please select your residential status!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(occupation.getText().equals(""))
        {
            occupation.setError("Please enter your occupation!");
            return false;
        }
        else if(ccode.getText().equals(""))
        {
            ccode.setError("Please enter your country code!");
            return false;
        }
        else if(tino.getText().equals(""))
        {
            tino.setError("Please enter your tax identification number!");
            return false;
        }
        else if(pob.getText().equals(""))
        {
            pob.setError("Please enter your Place of Birth!");
            return false;
        }
        else if(ccob.getText().equals(""))
        {
            ccob.setError("Please enter your country code of birth!");
            return false;
        }
        else if(phone.getRawText().length() != 10)
        {
            phone.setError("Please enter your phone number!");
            return false;
        }
        else if(!Pattern.compile(email_regex).matcher(email.getText().toString()).matches())
        {
            email.setError("Please enter a valid name!");
            return false;
        }
        else if(!aadhar_ev.getText().toString().equals("e-verified"))
        {
            Toast.makeText(getApplicationContext(), "Please e-verify your Aadhar!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!pan_ev.getText().toString().equals("e-verified"))
        {
            Toast.makeText(getApplicationContext(), "Please e-verify your Pan!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(bundle == null) bundle = new Bundle();
        startActivity(new Intent(getApplicationContext(), Homepage.class).putExtras(bundle));
        finish();
    }
}