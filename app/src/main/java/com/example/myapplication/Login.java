package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btnRegistration;
    LinearLayout linearLayout;

    List<String> arrayname = new ArrayList<String>();
    List<String> arrayIP = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linearLayout = findViewById(R.id.id_linearLayout);

        btnRegistration = findViewById(R.id.id_registration);
        btnRegistration.setOnClickListener(this);

        arrayname = PrefConfig.readListFromPref(this, "name");
        arrayIP = PrefConfig.readListFromPref(this, "ip");

        final SharedPreferences.Editor prefs = getSharedPreferences("IP", Context.MODE_PRIVATE).edit();

        for (int i = 0; i < arrayname.size(); i++) {

            final int z = i;
            Button btn = new Button(this);
            btn.setId(i);
            btn.setText(arrayname.get(i));
            btn.setBackgroundTintList(ColorStateList.valueOf(getApplicationContext().getResources().getColor(R.color.grau)));
            btn.setTextColor(Color.rgb(255,255,255));
            linearLayout.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    prefs.putInt("id",z);
                    prefs.apply();
                    start();
                }
            });

        }


    }

    @Override
    public void onClick(View v) {

        Intent registration = new Intent(this, Registration.class);
        startActivity(registration);


    }

    public void start() {
        Intent submit = new Intent(this, Main2.class);
        startActivity(submit);
    }
}
