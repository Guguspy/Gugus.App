package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int min = 1;
    int max = 2;
    TextView QuoteInfoView;

    LinearLayout layoutinfoappareil,layoutCpu,layoutNetwork,layoutSensor,layoutBattery,layoutmemory,layoutDisplay,layoutstorage,layoutMail,layoutTempMail,layoutDoge,layoutWait;
    Animation scale_up, scale_down;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 100;
    private Intent intent;

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

        layoutinfoappareil = findViewById(R.id.layoutinfoappareil);
        layoutCpu = findViewById(R.id.layoutCpu);
        layoutNetwork= findViewById(R.id.layoutNetwork);
        layoutSensor= findViewById(R.id.layoutSensor);
        layoutBattery= findViewById(R.id.layoutBattery);
        layoutmemory= findViewById(R.id.layoutmemory);
        layoutDisplay= findViewById(R.id.layoutDisplay);
        layoutstorage= findViewById(R.id.layoutstorage);
        layoutMail= findViewById(R.id.layoutMail);
        layoutTempMail= findViewById(R.id.layoutTempMail);
        layoutDoge= findViewById(R.id.layoutDoge);
        layoutWait= findViewById(R.id.layoutWait);

        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);



    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);

                ImageView ImageViewBatteryLogo = (ImageView) findViewById(R.id.ImageViewBatteryLogo);
                Resources res = getResources();

                if (checkBattery()>100){
                    ImageViewBatteryLogo.setImageDrawable(res.getDrawable(R.drawable.battery_c));
                } else if (checkBattery()>40){
                    ImageViewBatteryLogo.setImageDrawable(res.getDrawable(R.drawable.battery_f));
                }else if (checkBattery()<40){
                    ImageViewBatteryLogo.setImageDrawable(res.getDrawable(R.drawable.battery_e));
                }
            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }


    private int checkBattery() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        return level;
    }




    public void InfoDevice(View view) {
        layoutinfoappareil.startAnimation(scale_up);
        layoutinfoappareil.startAnimation(scale_down);
        Intent InfoDevice = new Intent(MainActivity.this, DeviceInfo.class);
        this.finish();
        startActivity(InfoDevice);
    }

    public void CpuView(View view) {
        layoutCpu.startAnimation(scale_up);
        layoutCpu.startAnimation(scale_down);
        Intent InfoDevice = new Intent(MainActivity.this, CpuInfo.class);
        this.finish();
        startActivity(InfoDevice);
    }

    public void NetworkInfo(View view) {
        layoutNetwork.startAnimation(scale_up);
        layoutNetwork.startAnimation(scale_down);
        Intent NetworkInfo = new Intent(MainActivity.this, NetworkInfo.class);
        this.finish();
        startActivity(NetworkInfo);
    }

    public void SensorView(View view) {
        layoutSensor.startAnimation(scale_up);
        layoutSensor.startAnimation(scale_down);
        Intent BTN_Back = new Intent(MainActivity.this, SensorsInfo.class);
        this.finish();
        startActivity(BTN_Back);
    }

    public void BatteryView(View view) {
        layoutBattery.startAnimation(scale_up);
        layoutSensor.startAnimation(scale_down);
        Intent NetworkInfo = new Intent(MainActivity.this, BatteryInfo.class);
        this.finish();
        startActivity(NetworkInfo);
    }

    public void RAMView(View view) {
        layoutmemory.startAnimation(scale_up);
        layoutmemory.startAnimation(scale_down);
        Intent NetworkInfo = new Intent(MainActivity.this, MemoryInfo.class);
        this.finish();
        startActivity(NetworkInfo);
    }

    public void DisplayView(View view) {
        layoutDisplay.startAnimation(scale_up);
        layoutDisplay.startAnimation(scale_down);
        Intent DisplayView = new Intent(MainActivity.this, DisplayInfo.class);
        this.finish();
        startActivity(DisplayView);
    }

    public void StorageView(View view) {
        layoutstorage.startAnimation(scale_up);
        layoutstorage.startAnimation(scale_down);
        Intent DisplayView = new Intent(MainActivity.this, StorageInfo.class);
        this.finish();
        startActivity(DisplayView);
    }

    public void SendMail_view(View view) {
        layoutMail.startAnimation(scale_up);
        layoutMail.startAnimation(scale_down);
        Intent BTN_Back = new Intent(MainActivity.this, SendMail.class);
        this.finish();
        startActivity(BTN_Back);
    }

    public void tempsmail_view(View view) {
        layoutTempMail.startAnimation(scale_up);
        layoutTempMail.startAnimation(scale_down);
        Intent BTN_Back = new Intent(MainActivity.this, tempsmail.class);
        this.finish();
        startActivity(BTN_Back);
    }

    public void Doge_view(View view) {
        layoutDoge.startAnimation(scale_up);
        layoutDoge.startAnimation(scale_down);
        Intent BTN_Back = new Intent(MainActivity.this, Doge_view.class);
        this.finish();
        startActivity(BTN_Back);
    }

    public void noideaview(View view) {
        layoutWait.startAnimation(scale_up);
        layoutWait.startAnimation(scale_down);
        Toast.makeText(this, "[En cours de developpement]", Toast.LENGTH_SHORT).show();
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