package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class wecker extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    Socket client;
    String message, IP, time = null, weekday = null;
    Button btnMo, btnDi, btnMi, btnDo, btnFr, btnSa, btnSo, btnBestätigen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecker);

        btnMo = findViewById(R.id.btn_idMo);
        btnDi = findViewById(R.id.btn_idDi);
        btnMi = findViewById(R.id.btn_idMi);
        btnDo = findViewById(R.id.btn_idDo);
        btnFr = findViewById(R.id.btn_idFr);
        btnSa = findViewById(R.id.btn_idSa);
        btnSo = findViewById(R.id.btn_idSo);
        btnBestätigen = findViewById(R.id.btn_idBestätigenwecker);

        btnMo.setOnClickListener(this);
        btnDi.setOnClickListener(this);
        btnMi.setOnClickListener(this);
        btnDo.setOnClickListener(this);
        btnFr.setOnClickListener(this);
        btnSa.setOnClickListener(this);
        btnSo.setOnClickListener(this);
        btnBestätigen.setOnClickListener(this);

        Button TimeChangerOpen = findViewById(R.id.openTimePicker);
        TimeChangerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        login();

    }

    void login() {

        SharedPreferences sharedPref = getSharedPreferences("IP", Context.MODE_PRIVATE);

        IP = sharedPref.getString("IP", "0");

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tv = findViewById(R.id.tvTest);

        String hours = Integer.toString(hourOfDay);
        String minutes = Integer.toString(minute);

        if (hours.length() == 1)
        {
            hours = "0" + hours;
        }
        if (minutes.length() == 1)
        {
            minutes = "0" + minutes;
        }

        time = hours + ":" + minutes;
        tv.setText(time);
        time = "2" + time;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_idMo:
                weekday = "0";
                break;
            case R.id.btn_idDi:
                weekday = "1";
                break;
            case R.id.btn_idMi:
                weekday = "2";
                break;
            case R.id.btn_idDo:
                weekday = "3";
                break;
            case R.id.btn_idFr:
                weekday = "4";
                break;
            case R.id.btn_idSa:
                weekday = "5";
                break;
            case R.id.btn_idSo:
                weekday = "6";
                break;
            case R.id.btn_idBestätigenwecker:
                if (weekday != null && time != null) {
                    message = time + weekday;
                    new LongOperation().execute("");
                }
                break;
        }
    }

    private class LongOperation extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {

                client = new Socket(IP, 1234);  //connect to server
                DataOutputStream printwriter = new DataOutputStream(client.getOutputStream());
                byte[] bytes = message.getBytes("UTF-8");
                printwriter.write(bytes);  //write the message to output stream

                printwriter.flush();
                printwriter.close();
                client.close();   //closing the connection

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Executed";
        }
    }
}
