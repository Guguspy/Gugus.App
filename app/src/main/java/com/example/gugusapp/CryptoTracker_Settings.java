package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CryptoTracker_Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_tracker__settings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewCryptoTracker);
        bottomNavigationView.setSelectedItemId(R.id.settings_cryptotracker);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settings_cryptotracker:
                        return true;
                    case R.id.cryptotracker:
                        startActivity(new Intent(getApplicationContext(), CryptoTracker.class));
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

    @Override
    public void onPause() {
        super.onPause();
        this.finish();
    }

    public void Menu_Item_QrCode_Back(MenuItem item) {
        Intent Menu_Item_QrCode_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_QrCode_Back);
    }
}