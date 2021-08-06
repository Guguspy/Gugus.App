package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SendMail_Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail__settings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewSendMail);
        bottomNavigationView.setSelectedItemId(R.id.settings_IDMail);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settings_IDMail:
                        return true;
                    case R.id.SendMail:
                        startActivity(new Intent(getApplicationContext(), SendMail.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.SendMailFast:
                        startActivity(new Intent(getApplicationContext(), SendMailFast.class));
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

    public void Menu_Item_SendMail_Back(MenuItem item) {
        Intent Menu_Item_SendMail_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_SendMail_Back);
    }
}