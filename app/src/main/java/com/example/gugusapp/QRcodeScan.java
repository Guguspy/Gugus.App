package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
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

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView resultData;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);

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
                        String Resultt = result.getText();
                        resultData.setText(result.getText());
                        resultData.setMovementMethod(new ScrollingMovementMethod());
                    }
                });
            }
        });

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
                Toast.makeText(QRcodeScan.this, "L'accès à la caméra est refusé", Toast.LENGTH_LONG).show();
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
}