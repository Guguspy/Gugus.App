package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;


public class speedtest extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedtest);


    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                checkNetworkConnectionStatus();

            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }

    private void checkNetworkConnectionStatus() {

        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if(activeInfo != null && activeInfo.isConnected()){
            wifiConnected = activeInfo.getType()==ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType()==ConnectivityManager.TYPE_MOBILE;
            if(wifiConnected){
                /*Context context = null;
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                int linkSpeed = wifiManager.getConnectionInfo().getRssi();
                String level = String.valueOf(WifiManager.calculateSignalLevel(linkSpeed, 5));

                String sppeedd ="> : "+level+ " Mb/s\n";

                TextView sp = (TextView) findViewById(R.id.TV_Speed);
                sp.setText(sppeedd);*/

                String sppeedd ="Wifi not supported for the moment";

                TextView sp = (TextView) findViewById(R.id.TV_Speed);
                sp.setText(sppeedd);
            }
            else if (mobileConnected){
                ConnectivityManager cm = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
                NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
                String downSpeed = String.valueOf(nc.getLinkDownstreamBandwidthKbps()/800);
                String upSpeed = String.valueOf(nc.getLinkUpstreamBandwidthKbps()/800);

                String sppeedd ="Down : "+downSpeed+ " Mb/s\n" +
                        "Up : "+upSpeed+ " Mb/s";

                TextView sp = (TextView) findViewById(R.id.TV_Speed);
                sp.setText(sppeedd);
            }

        }
        else{
            String sppeedd ="Down : 0 Kbps\n" +
                    "Up : 0 Kbps";

            TextView sp = (TextView) findViewById(R.id.TV_Speed);
            sp.setText(sppeedd);
        }
    }

    private void checkspeed(){
        try{
            ConnectivityManager cm = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
            NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
            String downSpeed = String.valueOf(nc.getLinkDownstreamBandwidthKbps()/800);
            String upSpeed = String.valueOf(nc.getLinkUpstreamBandwidthKbps()/800);

            String sppeedd ="Down : "+downSpeed+ " Mb/s\n" +
                    "Up : "+upSpeed+ " Mb/s";

            TextView sp = (TextView) findViewById(R.id.TV_Speed);
            sp.setText(sppeedd);
        }catch (Exception e){
            String sppeedd ="Down : 0 Kbps\n" +
                    "Up : 0 Kbps";

            TextView sp = (TextView) findViewById(R.id.TV_Speed);
            sp.setText(sppeedd);
        }
    }



    public void backhome(View view) {
        Intent BTN_Back = new Intent(speedtest.this, MainActivity.class);
        this.finish();
        startActivity(BTN_Back);
    }
}