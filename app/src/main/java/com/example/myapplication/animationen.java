package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class animationen extends AppCompatActivity implements View.OnClickListener{

    Button btnFadeEasy, btnFade, btnColorSnake, btnSparkle, btnColorWipe, btnSnow, btnRandom, btnBlitzen, btnBlitzenRandom, btnFadeSnake, btnOwnColors, btnLogin, btnWecker, btnReturn;
    String message, IP;
    Socket client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animationen);

        btnFadeEasy = (Button) findViewById(R.id.id_btnfadeEasy);
        btnFade = findViewById(R.id.id_btnFade);
        btnColorSnake = findViewById(R.id.id_btnColorSnake);
        btnSparkle = findViewById(R.id.id_btnSparkle);
        btnColorWipe = findViewById(R.id.id_btnColorWipe);
        btnSnow = findViewById(R.id.btn_idSnow);
        btnRandom = findViewById(R.id.btn_idRandom);
        btnBlitzen = findViewById(R.id.btn_idBlitzen);
        btnBlitzenRandom = findViewById(R.id.btn_idBlitzenRandom);
        btnFadeSnake = findViewById(R.id.id_btnfadesnake);
        btnOwnColors = findViewById(R.id.btn_idowncolors);
        btnWecker = findViewById(R.id.btn_idWecker);
        btnLogin = findViewById(R.id.login);
        btnReturn = findViewById(R.id.btn_idreturn);


        btnFadeEasy.setOnClickListener(this);
        btnFade.setOnClickListener(this);
        btnColorSnake.setOnClickListener(this);
        btnSparkle.setOnClickListener(this);
        btnColorWipe.setOnClickListener(this);
        btnSnow.setOnClickListener(this);
        btnRandom.setOnClickListener(this);
        btnBlitzen.setOnClickListener(this);
        btnBlitzenRandom.setOnClickListener(this);
        btnFadeSnake.setOnClickListener(this);
        btnOwnColors.setOnClickListener(this);
        btnWecker.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnReturn.setOnClickListener(this);

        login();

    }

    void login() {

        SharedPreferences sharedPref = getSharedPreferences("IP", Context.MODE_PRIVATE);

        int id = sharedPref.getInt("id", 0);

        List<String> ip_list;
        ip_list = PrefConfig.readListFromPref(this,"ip");
        IP = ip_list.get(id);

        List<String> name_list;
        name_list = PrefConfig.readListFromPref(this,"name");
        String name = name_list.get(id);

        btnLogin.setText(name);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.id_btnfadeEasy:
                message = "10";
                break;
            case R.id.id_btnFade:
                message = "11";
                break;
            case R.id.id_btnColorSnake:
                message = "12";
                break;
            case R.id.id_btnSparkle:
                message = "0";
                Intent color = new Intent(this, Main2.class);
                color.putExtra("animationen", 13);
                startActivity(color);
                break;
            case R.id.id_btnColorWipe:
                message = "0";
                Intent color1 = new Intent(this, Main2.class);
                color1.putExtra("animationen", 14);
                startActivity(color1);
                break;
            case R.id.btn_idSnow:
                message = "15";
                break;
            case R.id.btn_idRandom:
                message = "16";
                break;
            case R.id.btn_idBlitzen:
                message = "0";
                Intent color2 = new Intent(this, Main2.class);
                color2.putExtra("animationen", 17);
                startActivity(color2);
                break;
            case R.id.btn_idBlitzenRandom:
                message = "179";
                break;
            case R.id.id_btnfadesnake:
                message = "18";
                break;
            case R.id.btn_idowncolors:
                message = "0";
                Intent color3 = new Intent(this, Main2.class);
                color3.putExtra("animationen", 19);
                startActivity(color3);
                break;
            case R.id.btn_idWecker:
                message = "0";
                Intent wecker = new Intent(this, wecker.class);
                startActivity(wecker);
                break;
            case R.id.login:
                message = "0";
                Intent login = new Intent(this, Login.class);
                startActivity(login);
                break;
            case R.id.btn_idreturn:
                message = "0";
                Intent IntentReturn = new Intent(this, Main2.class);
                startActivity(IntentReturn);
                break;
        }

        new LongOperation().execute("");
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

