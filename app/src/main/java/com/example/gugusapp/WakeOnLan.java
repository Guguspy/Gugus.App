package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WakeOnLan extends AppCompatActivity {
    float x1,x2,y1,y2;
    private Toast backToast;

    RecyclerView recyclerView;
    List<ProfileWolModel> ProfilWOL_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_on_lan);

        loadDataWolProfil();

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewWakeOnLan);
        bottomNavigationView.setSelectedItemId(R.id.WakeOnLanProfil);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.WakeOnLanProfil:
                        return true;
                    case R.id.WakeOnLanAdd:
                        saveDataWolProfil();
                        startActivity(new Intent(getApplicationContext(), WakeOnLanAdd.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerView=findViewById(R.id.ListProfileWOL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        ProfileWOL_Adapter adapter = new ProfileWOL_Adapter(ProfilWOL_List, this);
        recyclerView.setAdapter(adapter);
    }

    private void saveDataWolProfil() {
        SharedPreferences sharedPreferences = getSharedPreferences("WOLPROFIL", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ProfilWOL_List);
        editor.putString("PROFIL", json);
        editor.apply();
    }

    private void loadDataWolProfil() {
        SharedPreferences sharedPreferences = getSharedPreferences("WOLPROFIL", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("PROFIL", null);
        Type type = new TypeToken<ArrayList<ProfileWolModel>>(){}.getType();
        ProfilWOL_List = gson.fromJson(json, type);

        if(ProfilWOL_List == null){
            ProfilWOL_List = new ArrayList<>();
        }
    }


    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1>x2){
                    Intent i = new Intent(this, WakeOnLanAdd.class);
                    saveDataWolProfil();
                    this.finish();
                    startActivity(i);
                }
                break;
        }
        return false;
    }



    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        saveDataWolProfil();
        this.finish();
        startActivity(Menu_Item_Back);
    }

    @Override
    public void onPause() {
        saveDataWolProfil();
        super.onPause();
        this.finish();
    }


    public void Menu_Item_Back_WakeOnLan(MenuItem item) {
        Intent Menu_Item_Back_WakeOnLan = new Intent(this, MainActivity.class);
        saveDataWolProfil();
        this.finish();
        startActivity(Menu_Item_Back_WakeOnLan);
    }

}