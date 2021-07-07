package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    Button btnSpeichern;
    EditText etName, etIP;
    List<String> arrayname = new ArrayList<String>();
    List<String> arrayname1 = new ArrayList<String>();
    List<String> arrayIP = new ArrayList<String>();
    List<String> arrayIP1 = new ArrayList<String>();
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = findViewById(R.id.Name);
        etIP = findViewById(R.id.IP);
        btnSpeichern = findViewById(R.id.btn_idSpeichern);
        tv = findViewById(R.id.tv);

        btnSpeichern.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String name = etName.getText().toString();
        String IP = etIP.getText().toString();

        if (!name.equals("") && !IP.equals("")) {
            arrayname = PrefConfig.readListFromPref(this,"name");
            if (arrayname == null) {
                PrefConfig.writeListInPref(getApplicationContext(),arrayname1,"name");
                arrayname = PrefConfig.readListFromPref(this,"name");
            }
            arrayname.add(name);
            PrefConfig.writeListInPref(getApplicationContext(),arrayname,"name");



            arrayIP = PrefConfig.readListFromPref(this,"ip");
            if (arrayIP == null) {
                PrefConfig.writeListInPref(getApplicationContext(),arrayIP1,"ip");
                arrayIP = PrefConfig.readListFromPref(this,"ip");
            }
            arrayIP.add(IP);
            PrefConfig.writeListInPref(getApplicationContext(),arrayIP,"ip");

        }
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }
}
