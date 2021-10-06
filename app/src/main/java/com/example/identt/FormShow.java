package com.example.identt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class FormShow extends AppCompatActivity {

    EditText name, fathername, mothername, dob, occupation, ccode, tino, pob, ccob, email, aadhar, pan;
    TextView aadhar_ev, pan_ev;
    MaskedEditText phone;
    RadioGroup gender, mar_status;
    RadioButton male, female, trans, married, unmarried, others;
    EditText resident_status;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_show);

        assign();

        bundle = getIntent().getExtras();

        name.setText(bundle.getString("FName"));
        fathername.setText(bundle.getString("FFName"));
        mothername.setText(bundle.getString("FMName"));
        dob.setText(bundle.getString("FDOB"));
        occupation.setText(bundle.getString("FOccupation"));
        ccode.setText(bundle.getString("Fccode"));
        tino.setText(bundle.getString("Ftino"));
        pob.setText(bundle.getString("Fpob"));
        phone.setText(bundle.getString("Fphone"));
        ccob.setText(bundle.getString("Fccob"));
        email.setText(bundle.getString("FEmail"));
        aadhar_ev.setText("e-verified");
        aadhar_ev.setTextColor(Color.parseColor("#48BF84"));
        pan_ev.setText("e-verified");
        pan_ev.setTextColor(Color.parseColor("#48BF84"));
        resident_status.setText(bundle.getString("FResident"));
        if(bundle.getString("FGender").equals("Male"))
        {
            male.setChecked(true);
        }
        else if(bundle.getString("FGender").equals("Female"))
        {
            female.setChecked(true);
        }
        else
        {
            trans.setChecked(true);
        }
        if(bundle.getString("FMar_status").equals("Married"))
        {
            married.setChecked(true);
        }
        else if(bundle.getString("FMar_status").equals("Unmarried"))
        {
            unmarried.setChecked(true);
        }
        else
        {
            others.setChecked(true);
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
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}