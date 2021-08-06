package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.MasterKey;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sharedpreferencesTEST extends AppCompatActivity {

    private Button btnAdd, btnDel;
    private TextView txtTotal;

    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedpreferences_test);

        btnAdd = findViewById(R.id.btn_add);
        btnDel = findViewById(R.id.btn_del);
        txtTotal = findViewById(R.id.txt_total);

        counter = PrefConfig_TEST.loadTotalFromPref(this);
        txtTotal.setText("Total :"+counter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                PrefConfig_TEST.saveTotalInPref(getApplicationContext(),counter);
                txtTotal.setText("Total :"+counter);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                PrefConfig_TEST.saveTotalInPref(getApplicationContext(),counter);
                txtTotal.setText("Total :"+counter);
            }
        });
    }




    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

    public void Menu_Item_Back(MenuItem item) {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

    public void resetPreferences(View view) {
        PrefConfig_TEST.removeDataFromPref(this);
        Intent refreshData = new Intent(this, sharedpreferencesTEST.class);
        this.finish();
        startActivity(refreshData);
    }
}