package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.os.SystemClock.sleep;

public class SendMailFast extends AppCompatActivity {


    private EditText email, subject, message;
    private ImageButton button;

    boolean fast=false;
    int resultsend=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail_fast);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewSendMail);
        bottomNavigationView.setSelectedItemId(R.id.SendMailFast);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.SendMailFast:
                        return true;
                    case R.id.SendMail:
                        startActivity(new Intent(getApplicationContext(), SendMail.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings_IDMail:
                        startActivity(new Intent(getApplicationContext(), SendMail_Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

    private void checkNetworkConnectionStatus() {

        boolean wifiConnected;
        boolean mobileConnected;
        boolean vpnConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if(activeInfo != null && activeInfo.isConnected()){
            wifiConnected = activeInfo.getType()==ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType()==ConnectivityManager.TYPE_MOBILE;
            vpnConnected = activeInfo.getType()==ConnectivityManager.TYPE_VPN;
            if(wifiConnected){
                if (fast){
                    sendEmailfast();
                }
                if (resultsend==1){
                }
            }
            else if (mobileConnected){
                if (fast){
                    sendEmailfast();
                }
                if (resultsend==1){
                }
            }
            else if (vpnConnected){
                if (fast){
                    sendEmailfast();
                }
                Toast.makeText(this, "Attention vous êtes connectés avec un VPN, des erreurs peuvent être rencontrées.", Toast.LENGTH_LONG).show();
                if (resultsend==1){
                }
            }
        }
        else{
            Toast.makeText(this, "Echec d'envoi, veuillez vous connecter à internet !", Toast.LENGTH_LONG).show();
        }
    }

    private void sendEmailfast() {
        String mEmail = "OTP.Python@gmail.com";
        String mSubject = "MOVE";
        String mMessage = "COME FAST !";

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mEmail, mSubject, mMessage);

        javaMailAPI.execute();
        if (verifenvoiemail.envoimail == "error"){
            email.getText().clear();
            subject.getText().clear();
            message.getText().clear();
            Toast.makeText(this, "Echec d'envoi, le message sera envoyé dès que vous serez connecté à internet !", Toast.LENGTH_LONG).show();
            sleep(3000);
        }else {
            email.getText().clear();
            subject.getText().clear();
            message.getText().clear();
            Toast.makeText(this, "Envoyé avec Succès !", Toast.LENGTH_LONG).show();
            sleep(3000);
        }

    }

    public void sendmailfast(View view) {
        fast=true;
        checkNetworkConnectionStatus();
    }

    public void Menu_Item_SendMail_Back(MenuItem item) {
        Intent Menu_Item_SendMail_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_SendMail_Back);
    }
}