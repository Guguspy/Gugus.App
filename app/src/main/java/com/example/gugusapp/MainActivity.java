package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    int min = 1;
    int max = 2;
    TextView QuoteInfoView;

    LinearLayout layoutcryptoCurrency, layoutqrCode, layoutinfoappareil,layoutWakeOnLan, layoutWait2,layoutMail, layoutWifiAnalyzer, LayoutSharedPref;
    Animation scale_up, scale_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();


        QuoteInfoView = (TextView) findViewById(R.id.QuoteInfoView);

        int quote1 = R.string.quote1;
        int quote2 = R.string.quote2;
        int quote3 = R.string.quote3;
        int quote4 = R.string.quote4;
        int quote5 = R.string.quote5;


        int value = random.nextInt(max + min) + min;
        if (value==1){
            QuoteInfoView.setText(quote1);
        }else if (value==2){
            QuoteInfoView.setText(quote2);
        }else if (value==3){
            QuoteInfoView.setText(quote3);
        }else if (value==4){
            QuoteInfoView.setText(quote4);
        }else if (value==5){
            QuoteInfoView.setText(quote5);
        }


        layoutcryptoCurrency = findViewById(R.id.layoutcryptoCurrency);
        layoutqrCode = findViewById(R.id.layoutqrCode);
        layoutinfoappareil = findViewById(R.id.layoutinfoappareil);
        layoutMail= findViewById(R.id.layoutMail);
        layoutWifiAnalyzer= findViewById(R.id.layoutWifiAnalyzer);
        LayoutSharedPref= findViewById(R.id.LayoutSharedPref);
        layoutWakeOnLan = findViewById(R.id.layoutWakeOnLan);
        layoutWait2= findViewById(R.id.layoutWait2);

        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);

    }

    @Override
    public void onBackPressed() {


        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            //super.onBackPressed();
            //MainActivity.this.finish();
            finish();
            System.exit(0);
            //super.onDestroy();
            return;
        }else{
            //backToast = Toast.makeText(this, "Back again to exit", Toast.LENGTH_SHORT);
            LayoutInflater inflater = getLayoutInflater();
            View customToastLayout = inflater.inflate(R.layout.custom_toast_main_activity, findViewById(R.id.root_layout));

            TextView txtMessage = customToastLayout.findViewById(R.id.txt_message);
            txtMessage.setText("Press Back again to leave");

            Toast mToast = new Toast(getApplicationContext());
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(customToastLayout);

            backToast = mToast ;

            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void cryptoCurrency(View view) {
        layoutcryptoCurrency.startAnimation(scale_up);
        layoutcryptoCurrency.startAnimation(scale_down);
        Intent cryptoCurrency = new Intent(MainActivity.this, CryptoTracker.class);
        this.finish();
        startActivity(cryptoCurrency);
    }

    public void qrCode(View view) {
        layoutqrCode.startAnimation(scale_up);
        layoutqrCode.startAnimation(scale_down);
        Intent QrCode = new Intent(MainActivity.this, QRcodeCreate.class);
        this.finish();
        startActivity(QrCode);
    }

    public void InfoDevice(View view) {
        layoutinfoappareil.startAnimation(scale_up);
        layoutinfoappareil.startAnimation(scale_down);
        Intent InfoDevice = new Intent(MainActivity.this, DeviceInfo.class);
        this.finish();
        startActivity(InfoDevice);
    }

    public void SendMail_view(View view) {
        layoutMail.startAnimation(scale_up);
        layoutMail.startAnimation(scale_down);
        Intent SendMail = new Intent(MainActivity.this, SendMail.class);
        this.finish();
        startActivity(SendMail);
    }

    public void WakeOnLan(View view) {
        layoutWakeOnLan.startAnimation(scale_up);
        layoutWakeOnLan.startAnimation(scale_down);
        Intent WakeOnLan = new Intent(MainActivity.this, WakeOnLan.class);
        this.finish();
        startActivity(WakeOnLan);
    }

    public void WifiAnalyzer(View view) {
        layoutWifiAnalyzer.startAnimation(scale_up);
        layoutWifiAnalyzer.startAnimation(scale_down);
        Intent WifiAnalyzer = new Intent(MainActivity.this, WifiAnalyzer.class);
        this.finish();
        startActivity(WifiAnalyzer);
    }

    public void SharedPref(View view) {
        LayoutSharedPref.startAnimation(scale_up);
        LayoutSharedPref.startAnimation(scale_down);
        Intent SharedPref = new Intent(MainActivity.this, sharedpreferencesTEST.class);
        this.finish();
        startActivity(SharedPref);
    }
    public void noideaview3(View view) {
        layoutWait2.startAnimation(scale_up);
        layoutWait2.startAnimation(scale_down);
        Toast.makeText(this, "[Recherche Idée]", Toast.LENGTH_SHORT).show();
    }




    public void Menu_Item_Home(MenuItem item) {

    }

    public void Menu_Item_Fav(MenuItem item) {
        Toast.makeText(this, "[En cours de developpement]", Toast.LENGTH_SHORT).show();
    }


    public void Menu_Item_Setting(MenuItem item) {
        Toast.makeText(this, "[En cours de developpement]", Toast.LENGTH_SHORT).show();
    }
}