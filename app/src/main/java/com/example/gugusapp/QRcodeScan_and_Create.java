package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRcodeScan_and_Create extends AppCompatActivity {
    ImageButton btn_back;
    Animation scale_up, scale_down;

    EditText qrCodeEntry;
    ImageButton QRCODEgenerate, QRCODEScan;
    ImageView qrCodeView;


    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan_and__create);

        qrCodeEntry = findViewById(R.id.qrCodeEntry);
        QRCODEgenerate = findViewById(R.id.QRCODEgenerate);
        QRCODEScan = findViewById(R.id.QRCODEScan);
        qrCodeView = findViewById(R.id.qrCodeView);


        btn_back= findViewById(R.id.btn_back);

        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);
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

    public void QRCODEScan(View view) {
        Intent QRCodeScan = new Intent(QRcodeScan_and_Create.this, QRcodeScan.class);
        startActivity(QRCodeScan);
    }

    public void backhome(View view) {
        btn_back.startAnimation(scale_up);
        btn_back.startAnimation(scale_down);
        Intent BTN_Back = new Intent(QRcodeScan_and_Create.this, MainActivity.class);
        this.finish();
        startActivity(BTN_Back);
    }
}


