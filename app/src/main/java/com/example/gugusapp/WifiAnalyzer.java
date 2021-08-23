package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class WifiAnalyzer extends AppCompatActivity {

    private Toast backToast;

    private WifiManager wifiManager;
    private List<ScanResult> results;
    WifiList_Adapter adapter;


    RecyclerView recyclerView;
    List<ListWifiModel> Wifi_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_analyzer);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewWifiAnalyzer);
        bottomNavigationView.setSelectedItemId(R.id.WifiShow);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.WifiShow:
                        return true;
                    case R.id.WifiRefresh:
                        scanWifi();
                        return true;
                    case R.id.WifiSettings:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        Wifi_List = new ArrayList<>();


        recyclerView=findViewById(R.id.ListWifiDectected);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }


        adapter = new WifiList_Adapter(Wifi_List, this);
        recyclerView.setAdapter(adapter);

        scanWifi();
    }
    private void scanWifi() {
        Wifi_List = new ArrayList<>();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        LayoutInflater inflater = getLayoutInflater();
        View customToastLayout = inflater.inflate(R.layout.custom_toast_main_activity, findViewById(R.id.root_layout));

        ImageView imageView = customToastLayout.findViewById(R.id.icon);

        imageView.setImageResource(R.drawable.wifianalyzer);

        TextView txtMessage = customToastLayout.findViewById(R.id.txt_message);

        txtMessage.setText("Scanning WiFi ...");

        Toast mToast = new Toast(getApplicationContext());
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(customToastLayout);

        backToast = mToast ;

        backToast.show();
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = wifiManager.getScanResults();
            unregisterReceiver(this);

            for (ScanResult scanResult : results) {
                /*String WifiResultString ;*/

                String SSIDNAME;

                int freq = scanResult.frequency;
                freq /=1000;

                if (scanResult.SSID.isEmpty()){
                    Wifi_List.add(new ListWifiModel(R.drawable.wifiiconhidden,
                            "SSID : Hidden SSID",
                            "Signal : "+String.valueOf(freq)+" Ghz",
                            "Channel : "+String.valueOf(scanResult.channelWidth),
                            "Encryption : "+scanResult.capabilities));
                }else{
                    Wifi_List.add(new ListWifiModel(R.drawable.wifiicon,
                            "SSID : "+scanResult.SSID,
                            "Signal : "+String.valueOf(freq)+" Ghz",
                            "Channel : "+String.valueOf(scanResult.channelWidth),
                            "Encryption : "+scanResult.capabilities));
                }

            }

            adapter = new WifiList_Adapter(Wifi_List, context);
            recyclerView.setAdapter(adapter);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewWifiAnalyzer);
                    bottomNavigationView.setSelectedItemId(R.id.WifiShow);
                }
            }, 2000);
        };
    };

    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.finish();
    }


    public void Menu_Item_Back_WifiAnalyzer(MenuItem item) {
        Intent Menu_Item_Back_WifiAnalyzer = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back_WifiAnalyzer);
    }
}