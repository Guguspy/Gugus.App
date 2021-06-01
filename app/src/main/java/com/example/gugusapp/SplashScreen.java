package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreen extends AppCompatActivity {

    private final int timesplash = 2000;

    GifImageView GifPenguin;
    TextView TV_Version, TV_Letter_G, TV_Letter_U,TV_Letter_G2,TV_Letter_U2,TV_Letter_S,TV_Letter_point,TV_Letter_A,TV_Letter_P,TV_Letter_P2;
    Animation top,bottom,left,right;

    /*Handler handler = new Handler();
    Runnable runnable;
    int delay = 295;
    int progressbar = 0;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GifPenguin = findViewById(R.id.gifImageView);
        TV_Version = findViewById(R.id.TV_Version);
        TV_Letter_G = findViewById(R.id.TV_Letter_G);
        TV_Letter_U = findViewById(R.id.TV_Letter_U);
        TV_Letter_G2 = findViewById(R.id.TV_Letter_G2);
        TV_Letter_U2 = findViewById(R.id.TV_Letter_U2);
        TV_Letter_S = findViewById(R.id.TV_Letter_S);
        TV_Letter_point = findViewById(R.id.TV_Letter_point);
        TV_Letter_A = findViewById(R.id.TV_Letter_A);
        TV_Letter_P = findViewById(R.id.TV_Letter_P);
        TV_Letter_P2 = findViewById(R.id.TV_Letter_P2);

        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        left = AnimationUtils.loadAnimation(this, R.anim.left);
        right = AnimationUtils.loadAnimation(this, R.anim.right);

        GifPenguin.setAnimation(top);
        TV_Version.setAnimation(bottom);

        TV_Letter_G.setAnimation(left);
        TV_Letter_U.setAnimation(left);
        TV_Letter_G2.setAnimation(left);
        TV_Letter_U2.setAnimation(left);
        TV_Letter_S.setAnimation(left);
        TV_Letter_point.setAnimation(left);

        TV_Letter_A.setAnimation(right);
        TV_Letter_P.setAnimation(right);
        TV_Letter_P2.setAnimation(right);




        //rediriger vers la page principale "MainActivity" après 3 secondes.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, timesplash);
    }

        /*@Override
        protected void onResume() {
            handler.postDelayed(runnable = new Runnable() {
                public void run() {
                    handler.postDelayed(runnable, delay);
                    ProgressBar pb_splash2 = (ProgressBar) findViewById(R.id.pb_splash);
                    progressbar=progressbar+10;
                    pb_splash2.setProgress(progressbar);

                    TextView tv = (TextView) findViewById(R.id.TVpb_splash);
                    tv.setText("Chargement : "+progressbar+" %");

                }
            }, delay);
            super.onResume();
        }
        @Override
        protected void onPause() {
            super.onPause();
            handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
        }*/

}