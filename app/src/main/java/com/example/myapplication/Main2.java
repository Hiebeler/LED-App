package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import yuku.ambilwarna.AmbilWarnaDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import static com.example.myapplication.R.id.id_rot2;

public class Main2 extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private Socket client;
    private String message = "000x0000001.0";
    int animation;

    List<String> colors1 = Arrays.asList("000xFF0000", "000xFF5500", "000xFF7700", "000xFFB300", "000xFFFF00", "000x00FF00", "000x6FFF00", "000x00FF73", "000x009358", "000x003A1A", "000x0000FF", "000x0090FF", "000x5100FF", "000xA600FF", "000xFF00EE", "000xFF2200", "000xFF00FF", "000x00FFFF");
    List<String> colors;

    Button btnRot1, btnRot2, btnRot3, btnRot4, btnRot5, btngruen1, btngruen2, btngruen3, btngruen4,
            btngruen5, btnblau1, btnblau2, btnblau3, btnblau4, btnblau5, btnlogin, btnStop, btnAnimationen;

    List<Integer> buttonids = Arrays.asList(R.id.id_rot1, R.id.id_rot2, R.id.id_rot3, R.id.id_rot4, R.id.id_rot5,R.id.id_gruen1, R.id.id_gruen2, R.id.id_gruen3, R.id.id_gruen4, R.id.id_gruen5, R.id.id_blau1, R.id.id_blau2, R.id.id_blau3, R.id.id_blau4, R.id.id_blau5);

    List<Button> buttons;

    int i = 0, DefaultColor;
    SeekBar Dimmen;
    Boolean firstTime = true;
    String IP, color19 = "19";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        animation = getIntent().getIntExtra("animationen", 0);


        btnRot1 = findViewById(R.id.id_rot1);
        btnRot2 = findViewById(id_rot2);
        btnRot3 = findViewById(R.id.id_rot3);
        btnRot4 = findViewById(R.id.id_rot4);
        btnRot5 = findViewById(R.id.id_rot5);
        btngruen1 = findViewById(R.id.id_gruen1);
        btngruen2 = findViewById(R.id.id_gruen2);
        btngruen3 = findViewById(R.id.id_gruen3);
        btngruen4 = findViewById(R.id.id_gruen4);
        btngruen5 = findViewById(R.id.id_gruen5);
        btnblau1 = findViewById(R.id.id_blau1);
        btnblau2 = findViewById(R.id.id_blau2);
        btnblau3 = findViewById(R.id.id_blau3);
        btnblau4 = findViewById(R.id.id_blau4);
        btnblau5 = findViewById(R.id.id_blau5);
        btnlogin = findViewById(R.id.login);
        btnStop = findViewById(R.id.id_stop);
        btnAnimationen = findViewById(R.id.id_animations);

        List<Button> buttonnames = Arrays.asList(btnRot1, btnRot2, btnRot3, btnRot4, btnRot5, btngruen1, btngruen2, btngruen3, btngruen4,
                btngruen5, btnblau1, btnblau2, btnblau3, btnblau4, btnblau5);

        for (int i = 0; i < buttonnames.size(); i++) {
            buttonnames.get(i).setOnClickListener(this);
            buttonnames.get(i).setOnLongClickListener(this);
        }

        btnlogin.setOnClickListener(this);
        btnlogin.setOnLongClickListener(this);
        btnStop.setOnLongClickListener(this);
        btnStop.setOnClickListener(this);
        btnAnimationen.setOnClickListener(this);
        btnAnimationen.setOnLongClickListener(this);

        if (animation == 0) {
            btnAnimationen.setText("Animationen");
        }
        else if (animation == 19) {
            btnAnimationen.setText("Save");
        }
        else {
            btnAnimationen.setText("Normal");
        }


        colors = PrefConfig.readListFromPref(this, "colors");

        if (colors == null) {
            PrefConfig.writeListInPref(getApplicationContext(), colors1, "colors");
            colors = PrefConfig.readListFromPref(this, "colors");
        }

        buttons = Arrays.asList(btnRot1, btnRot2, btnRot3, btnRot4, btnRot5, btngruen1, btngruen2, btngruen3, btngruen4,
                btngruen5, btnblau1, btnblau2, btnblau3, btnblau4, btnblau5);

        for (int i = 0; i < buttons.size(); i++) {
            String ce = colors.get(i);
            ce = ce.replace("000x", "#");
            int c = Color.parseColor(ce);
            buttons.get(i).setBackgroundColor(c);
        }


        DefaultColor = ContextCompat.getColor(this, R.color.gruen1);


        Dimmen = findViewById(R.id.seekBar);


        Dimmen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int intDimmen = progress;
                float dimmen = (float) intDimmen / 100;
                String s = String.valueOf(dimmen);
                if (!firstTime) {
                    message = message.substring(0, message.length() - s.length());
                }
                firstTime = false;
                message += s;

                new LongOperation().execute("");
                //tvHelligkeit.setText("Helligkeit: " + intDimmen + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        registration();


    }

    void registration() {
        List<String> namen;

        namen = PrefConfig.readListFromPref(this,"name");
        if (namen == null) {
            List<String> arrayname =  Arrays.asList("Emanuels Zimmer", "Daniels Zimmer", "Maries Zimmer", "Papas Zimmer");
            PrefConfig.writeListInPref(getApplicationContext(),arrayname,"name");

            List<String> IPlist =  Arrays.asList("192.168.1.77", "192.168.1.33", "192.168.1.204", "192.168.1.172");
            PrefConfig.writeListInPref(getApplicationContext(),IPlist,"ip");

            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }
        else {
            login();
        }
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

        btnlogin.setText(name);


        if (IP == "0") {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }
    }


    @Override
    public void onClick(View v) {
        firstTime = true;

        for (int i = 0; i < buttonids.size(); i++) {
            if (v.getId() == buttonids.get(i)) {
                message = colors.get(i);
            }
        }


        switch (v.getId()) {
            case R.id.login:
                    Intent login = new Intent(this, Login.class);
                    startActivity(login);
                break;
            case R.id.id_stop:
                    message = "000x0000000";
                    new LongOperation().execute("");
                break;
            case R.id.id_animations:
                    if (animation == 0) {
                        Intent animation = new Intent(this, animationen.class);
                        startActivity(animation);
                    }
                    else if (animation == 19) {
                        new LongOperation().execute("");
                    }
                    else {
                        animation = 0;
                        btnAnimationen.setText("Animationen");
                    }
                break;
        }

        if (animation == 19) {
            color19 += message;
        }

        if (i < 1 && animation != 19) {
            message += "1.0";
        }


        if (animation != 0 && animation != 19) {
            message = message.substring(2);
            StringBuilder sb = new StringBuilder(message);
            String stranimation = String.valueOf(animation);
            sb.insert(0, stranimation);
            message = sb.toString();
        }

        if (animation != 19) {
            new LongOperation().execute("");
        }
    }

    @Override
    public boolean onLongClick(View v) {
        for (int i = 0; i < buttonids.size(); i++) {
            if (v.getId() == buttonids.get(i)) {
                openColorPicker(buttons.get(i), i);
            }
        }

        return true;
    }

        public void openColorPicker ( final Button button, final int intColor){
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, DefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {

                }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    DefaultColor = color;
                    String stcolor = Integer.toHexString(color);//int to hexString
                    stcolor = stcolor.substring(2);//first to characters cut off, are just transparents
                    message = "000x" + stcolor;

                    button.setBackgroundColor(color);
                    colors.set(intColor, message);
                    PrefConfig.writeListInPref(getApplicationContext(), colors, "colors");

                }
            });
            colorPicker.show();
        }



    private class LongOperation extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                try {
                    byte[] bytes;
                    client = new Socket(IP, 1234);  //connect to server
                    DataOutputStream printwriter = new DataOutputStream(client.getOutputStream());
                    if (animation != 19) {
                        bytes = message.getBytes("UTF-8");
                    } else {
                        bytes = color19.getBytes("UTF-8");
                        animation = 0;
                        btnAnimationen.setText("Animationen");
                    }
                    printwriter.write(bytes);  //write the message to output stream

                    printwriter.flush();
                    printwriter.close();
                    client.close();   //closing the connection


                    if (i < 1) {
                        message = message.substring(0, message.length() - 3);
                    }
                    i++;

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "Executed";
            }
        }
    }
