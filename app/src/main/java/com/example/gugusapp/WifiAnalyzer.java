package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class WifiAnalyzer extends AppCompatActivity {

    private WifiManager wifiManager;
    private ListView listView;
    private int size = 0;
    private List<ScanResult> results;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter adapter;

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

        listView = findViewById(R.id.wifiList);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        scanWifi();
    }
    private void scanWifi() {
        arrayList.clear();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = wifiManager.getScanResults();
            unregisterReceiver(this);

            for (ScanResult scanResult : results) {
                String WifiResultString ;

                int freq = scanResult.frequency;
                freq /=1000;

                WifiResultString = "[-------------------------------------------------------------------------]\n"
                        + "SSID : " + scanResult.SSID + "\n---  ---  ---  ---  ---  ---  ---  ---  ---\n"
                        + "capabilities : " + scanResult.capabilities+ "\n---  ---  ---  ---  ---  ---  ---  ---  ---\n"
                        + "operatorFriendlyName : " + scanResult.operatorFriendlyName + "\n---  ---  ---  ---  ---  ---  ---  ---  ---\n"
                        + "frequency : " + freq + "GHz\n---  ---  ---  ---  ---  ---  ---  ---  ---\n"
                        + "channelWidth : " + scanResult.channelWidth + "\n[-------------------------------------------------------------------------]\n";

                arrayList.add(WifiResultString);
                adapter.notifyDataSetChanged();
            }
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