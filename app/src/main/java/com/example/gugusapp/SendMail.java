package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.os.SystemClock.sleep;

public class SendMail extends AppCompatActivity {


    private EditText email, subject, message;
    private ImageButton button;
    int resultsend=0;

    ImageButton btn_back;
    Animation scale_up, scale_down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewSendMail);
        bottomNavigationView.setSelectedItemId(R.id.SendMail);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.SendMail:
                        return true;
                    case R.id.SendMailFast:
                        startActivity(new Intent(getApplicationContext(), SendMailFast.class));
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

        email = findViewById(R.id.ET_Destinataire);
        subject = findViewById(R.id.ET_Subject);
        message = findViewById(R.id.ET_Message);
        button = findViewById(R.id.BTN_SendMail);

        btn_back= findViewById(R.id.btn_back);
        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);

    }

    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
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
            Toast.makeText(this, "Echec d'envoi, le message sera envoy?? d??s que vous serez connect?? ?? internet !", Toast.LENGTH_LONG).show();
            return false;
        }else {
            email.getText().clear();
            subject.getText().clear();
            message.getText().clear();
            Toast.makeText(this, "Envoy?? avec Succ??s !", Toast.LENGTH_LONG).show();
            resultsend=1;
            return true;
        }
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
                sendEmail();
            }
            else if (mobileConnected){
                sendEmail();
            }
            else if (vpnConnected){
                Toast.makeText(this, "Attention vous ??tes connect??s avec un VPN, des erreurs peuvent ??tre rencontr??es.", Toast.LENGTH_LONG).show();
                sendEmail();
            }
        }
        else{
            Toast.makeText(this, "Echec d'envoi, veuillez vous connecter ?? internet !", Toast.LENGTH_LONG).show();
        }
    }

    public void sendmail(View view) {
        checkNetworkConnectionStatus();
    }

    public void Menu_Item_SendMail_Back(MenuItem item) {
        Intent Menu_Item_SendMail_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_SendMail_Back);
    }

}