package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceInfo extends AppCompatActivity {
    private Intent intent;
    private TextView modeSonnerie;
    private TextView infoManuf;
    private TextView infoProduit;
    private TextView infoId;
    private TextView infoNomHard;
    private AudioManager audioManager;
    private static String [] modeSonnerieTexte;
    private Handler monGestionnaire;
    private static int periode = 300; //période d'appel de la tache de fond en ms

    private Runnable tacheDeFond = new Runnable() {
        @Override
        public void run() {
            //CallBack
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            modeSonnerie.setText(modeSonnerieTexte[audioManager.getRingerMode()]);
            //Initialisation tempo avant le nouvel appel de la tache de fond
            monGestionnaire.postDelayed(this, periode);
        }
    };

    ImageButton btn_back;
    Animation scale_up, scale_down;
    LinearLayout layoutCpu,layoutNetwork,layoutSensor, IDK,layoutBattery,layoutmemory,layoutDisplay,layoutstorage,layoutMail,layoutTempMail;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 100;
    private Intent intent2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        //initialisation mode de sonnerie
        modeSonnerieTexte = new String[]{getString(R.string.sonSilence),getString(R.string.sonVibreur),getString(R.string.sonNormal)};

        //informarion générale invariante
        infoManuf = findViewById(R.id.infoManuf);
        infoManuf.setText(Build.MANUFACTURER);
        infoProduit = findViewById(R.id.infoProduit);
        infoProduit.setText(Build.PRODUCT);
        infoId = findViewById(R.id.infoId);
        infoId.setText(Build.ID);
        infoNomHard = findViewById(R.id.infoNomHard);
        infoNomHard.setText(Build.HARDWARE);
        //lancement du gestionnaire de tache de fond
        monGestionnaire = new Handler();
        monGestionnaire.postDelayed(tacheDeFond, periode);

        modeSonnerie = findViewById(R.id.modeSonnerie);

        btn_back= findViewById(R.id.btn_back);
        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);


        layoutCpu = findViewById(R.id.layoutCpu);
        layoutNetwork= findViewById(R.id.layoutNetwork);
        layoutSensor= findViewById(R.id.layoutSensor);
        IDK = findViewById(R.id.IDK);
        layoutBattery= findViewById(R.id.layoutBattery);
        layoutmemory= findViewById(R.id.layoutmemory);
        layoutDisplay= findViewById(R.id.layoutDisplay);
        layoutstorage= findViewById(R.id.layoutstorage);
        layoutMail= findViewById(R.id.layoutMail);
        layoutTempMail= findViewById(R.id.layoutTempMail);

        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);

    }

    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void run() {
                handler.postDelayed(runnable, delay);

                ImageView ImageViewBatteryLogo = (ImageView) findViewById(R.id.ImageViewBatteryLogo);
                Resources res = getResources();

                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, ifilter);

                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

                boolean charge = false;

                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                if (status == BatteryManager.BATTERY_STATUS_CHARGING){
                    charge=true;
                }

                if (charge){
                    ImageViewBatteryLogo.setImageDrawable(res.getDrawable(R.drawable.battery_c));
                } else if (level>39){
                    ImageViewBatteryLogo.setImageDrawable(res.getDrawable(R.drawable.battery_f));
                }else{
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


    public void backhome(View view) {
        btn_back.startAnimation(scale_up);
        btn_back.startAnimation(scale_down);
        Intent BTN_Back = new Intent(DeviceInfo.this, MainActivity.class);
        this.finish();
        startActivity(BTN_Back);
    }

    public void CpuView(View view) {
        layoutCpu.startAnimation(scale_up);
        layoutCpu.startAnimation(scale_down);
        Intent InfoDevice = new Intent(DeviceInfo.this, CpuInfo.class);
        this.finish();
        startActivity(InfoDevice);
    }

    public void NetworkInfo(View view) {
        layoutNetwork.startAnimation(scale_up);
        layoutNetwork.startAnimation(scale_down);
        Intent NetworkInfo = new Intent(DeviceInfo.this, NetworkInfo.class);
        this.finish();
        startActivity(NetworkInfo);
    }

    public void SensorView(View view) {
        layoutSensor.startAnimation(scale_up);
        layoutSensor.startAnimation(scale_down);
        Intent SensorView = new Intent(DeviceInfo.this, SensorsInfo.class);
        this.finish();
        startActivity(SensorView);
    }

    public void IDK(View view) {
        IDK.startAnimation(scale_up);
        IDK.startAnimation(scale_down);
    }

    public void BatteryView(View view) {
        layoutBattery.startAnimation(scale_up);
        layoutSensor.startAnimation(scale_down);
        Intent NetworkInfo = new Intent(DeviceInfo.this, BatteryInfo.class);
        this.finish();
        startActivity(NetworkInfo);
    }

    public void RAMView(View view) {
        layoutmemory.startAnimation(scale_up);
        layoutmemory.startAnimation(scale_down);
        Intent NetworkInfo = new Intent(DeviceInfo.this, MemoryInfo.class);
        this.finish();
        startActivity(NetworkInfo);
    }

    public void DisplayView(View view) {
        layoutDisplay.startAnimation(scale_up);
        layoutDisplay.startAnimation(scale_down);
        Intent DisplayView = new Intent(DeviceInfo.this, DisplayInfo.class);
        this.finish();
        startActivity(DisplayView);
    }

    public void StorageView(View view) {
        layoutstorage.startAnimation(scale_up);
        layoutstorage.startAnimation(scale_down);
        Intent StorageView = new Intent(DeviceInfo.this, StorageInfo.class);
        this.finish();
        startActivity(StorageView);
    }


    public void Menu_Item_Back(MenuItem item) {
        Intent Menu_Item_Back = new Intent(DeviceInfo.this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

}