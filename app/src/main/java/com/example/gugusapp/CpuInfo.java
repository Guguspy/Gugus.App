package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class CpuInfo extends AppCompatActivity {

    ImageButton btn_back;
    Animation scale_up, scale_down;

    TextView textView ;
    ProcessBuilder processBuilder;
    String Holder = "";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream inputStream;
    Process process ;
    byte[] byteArry ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_info);

        btn_back= findViewById(R.id.btn_back);

        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        textView = (TextView)findViewById(R.id.textView);

        byteArry = new byte[1024];

        try{
            processBuilder = new ProcessBuilder(DATA);

            process = processBuilder.start();

            inputStream = process.getInputStream();

            while(inputStream.read(byteArry) != -1){

                Holder = Holder + new String(byteArry);
            }

            inputStream.close();

        } catch(IOException ex){

            ex.printStackTrace();
        }

        textView.setText(Holder);

    }


    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, DeviceInfo.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

    public void Menu_Item_Back(MenuItem item) {
        Intent Menu_Item_Back = new Intent(this, DeviceInfo.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }
}