package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Text;

import java.util.Scanner;

public class QRcodeScan extends AppCompatActivity {
    float x1,x2,y1,y2;

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView resultData;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewQrcode);
        bottomNavigationView.setSelectedItemId(R.id.scanqrcode);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.generateqrcode:
                        startActivity(new Intent(getApplicationContext(), QRcodeCreate.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scanqrcode:
                        return true;
                }
                return false;
            }
        });

        scannerView = findViewById(R.id.scannerview);
        codeScanner = new CodeScanner(this, scannerView);
        resultData = findViewById(R.id.TV_resultQRCode);

        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultData.setText(result.getText());
                        resultData.setMovementMethod(new ScrollingMovementMethod());
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestCameraAuthorizations();
    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();
        super.onPause();
        this.finish();
    }


    public void CloseonPermissionDenied(){
        Intent Back = new Intent(this, QRcodeCreate.class);
        this.finish();
        startActivity(Back);
    }

    public void ScanView(View view) {
        requestCameraAuthorizations();
    }


    private void requestCameraAuthorizations(){
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

                //backToast = Toast.makeText(this, "Back again to exit", Toast.LENGTH_SHORT);
                LayoutInflater inflater = getLayoutInflater();
                View customToastLayout = inflater.inflate(R.layout.custom_toast_qrcode_scan_error, findViewById(R.id.root_layout));

                TextView txtMessage = customToastLayout.findViewById(R.id.txt_message);
                txtMessage.setText("L'accès à la caméra est refusé");

                Toast mToast = new Toast(getApplicationContext());
                mToast.setDuration(Toast.LENGTH_SHORT);
                mToast.setView(customToastLayout);
                mToast.show();

                CloseonPermissionDenied();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    public void resultQRCode(View view) {
        String data = resultData.getText().toString();
        myClip = ClipData.newPlainText("text", data);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getApplicationContext(), "Texte copié !",
                Toast.LENGTH_LONG).show();
    }


    public void Menu_Item_QrCode_Back(MenuItem item) {
        Intent Menu_Item_QrCode_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_QrCode_Back);
    }


}
