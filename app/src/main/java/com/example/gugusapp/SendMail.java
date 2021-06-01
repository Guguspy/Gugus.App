package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.os.SystemClock.sleep;

public class SendMail extends AppCompatActivity {


    private EditText email, subject, message;
    private ImageButton button;

    boolean fast=false;
    int resultsend=0;

    ImageButton btn_back;
    Animation scale_up, scale_down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        email = findViewById(R.id.ET_Destinataire);
        subject = findViewById(R.id.ET_Subject);
        message = findViewById(R.id.ET_Message);
        button = findViewById(R.id.BTN_SendMail);

        btn_back= findViewById(R.id.btn_back);
        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);

    }


    private boolean sendEmail() {
        String mEmail = email.getText().toString();
        if(mEmail.isEmpty()){
            email.setError("Il vous faut saisir un Destinataire");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            email.setError("Adresse invalide !");
            return false;
        }

        String mSubject = subject.getText().toString();
        if(mSubject.isEmpty()){
            subject.setError("Il vous faut saisir un Sujet");
            return false;
        }

        String mMessage = message.getText().toString();
        if(mMessage.isEmpty()){
            message.setError("Il vous faut saisir un message !");
            return false;
        }

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mEmail, mSubject, mMessage);

        javaMailAPI.execute();
        if (verifenvoiemail.envoimail == "error"){
            email.getText().clear();
            subject.getText().clear();
            message.getText().clear();
            Toast.makeText(this, "Echec d'envoi, le message sera envoyé dès que vous serez connecté à internet !", Toast.LENGTH_LONG).show();
            return false;
        }else {
            email.getText().clear();
            subject.getText().clear();
            message.getText().clear();
            Toast.makeText(this, "Envoyé avec Succès !", Toast.LENGTH_LONG).show();
            resultsend=1;
            return true;
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

    private void retourback(){
        Intent BTN_Back = new Intent(SendMail.this, MainActivity.class);
        this.finish();
        startActivity(BTN_Back);
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
                sendEmail();
                if (resultsend==1){
                    retourback();
                }
            }
            else if (mobileConnected){
                if (fast){
                    sendEmailfast();
                }
                sendEmail();
                if (resultsend==1){
                    retourback();
                }
            }
            else if (vpnConnected){
                if (fast){
                    sendEmailfast();
                }
                Toast.makeText(this, "Attention vous êtes connectés avec un VPN, des erreurs peuvent être rencontrées.", Toast.LENGTH_LONG).show();
                sendEmail();
                if (resultsend==1){
                    retourback();
                }
            }
        }
        else{
            Toast.makeText(this, "Echec d'envoi, veuillez vous connecter à internet !", Toast.LENGTH_LONG).show();
        }
    }

    public void sendmail(View view) {
        fast=false;
        checkNetworkConnectionStatus();
    }

    public void backhome(View view) {
        btn_back.startAnimation(scale_up);
        btn_back.startAnimation(scale_down);
        Intent BTN_Back = new Intent(SendMail.this, MainActivity.class);
        this.finish();
        startActivity(BTN_Back);
    }

    public void sendmailfast(View view) {
        fast=true;
        checkNetworkConnectionStatus();
    }
}