package com.example.gugusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class Doge_view extends AppCompatActivity {

    WebView wv;
    ImageButton btn_back;
    Animation scale_up, scale_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doge_view);

        wv = (WebView) findViewById(R.id.WebView);
        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBlockNetworkLoads (false);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        if (18 < Build.VERSION.SDK_INT ){
            //18 = JellyBean MR2, KITKAT=19
            wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        wv.loadUrl("https://dogeland.io/");
        btn_back= findViewById(R.id.btn_back);
        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }

    public void backhome(View view) {
        btn_back.startAnimation(scale_up);
        btn_back.startAnimation(scale_down);
        Intent BTN_Back = new Intent(Doge_view.this, MainActivity.class);
        this.finish();
        startActivity(BTN_Back);
    }
}