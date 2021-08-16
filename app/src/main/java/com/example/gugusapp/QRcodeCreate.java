package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRcodeCreate extends AppCompatActivity {
    float x1,x2,y1,y2;

    ImageButton btn_back;
    Animation scale_up, scale_down;

    EditText qrCodeEntry;
    ImageButton QRCODEgenerate;
    ImageView qrCodeView;


    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_create);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewQrcode);
        bottomNavigationView.setSelectedItemId(R.id.generateqrcode);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.generateqrcode:
                        return true;
                    case R.id.scanqrcode:
                        startActivity(new Intent(getApplicationContext(), QRcodeScan.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        qrCodeEntry = findViewById(R.id.qrCodeEntry);
        QRCODEgenerate = findViewById(R.id.QRCODEgenerate);
        qrCodeView = findViewById(R.id.qrCodeView);


        btn_back= findViewById(R.id.btn_back);

        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1>x2){
                    Intent i = new Intent(this, QRcodeScan.class);
                    this.finish();
                    startActivity(i);
            }
            break;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.finish();
    }


    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }



    public void QRCODEgenerate(View view) {
        String data = qrCodeEntry.getText().toString();
        if(data.isEmpty()){
            qrCodeEntry.setError("La saisie ne peut pas être vide !");
        }else{
            qrgEncoder = new QRGEncoder(
                    data, null,
                    QRGContents.Type.TEXT,
                    1000 );
            qrgEncoder.setColorBlack(Color.BLACK);
            qrgEncoder.setColorWhite(Color.WHITE);
            try {
                bitmap = qrgEncoder.getBitmap();
                qrCodeView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void Menu_Item_QrCode_Back(MenuItem item) {
        Intent Menu_Item_QrCode_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_QrCode_Back);
    }

}


