package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

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
    }

    public void backhome(View view) {
        btn_back.startAnimation(scale_up);
        btn_back.startAnimation(scale_down);
        Intent BTN_Back = new Intent(DeviceInfo.this, MainActivity.class);
        this.finish();
        startActivity(BTN_Back);
    }
}