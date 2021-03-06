package com.example.gugusapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkInfo extends AppCompatActivity {

    private static final String TAG = "";
    //Views
    ImageView mConStatusIv;
    TextView mConStatusTv;
    Button mConStatusBtn;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 250;

    TextView varText;

    int wifiOn = 1;

    int requestgps = 0;

    int MacReveal = 0;

    String macAdress2 ="No value";
    String macAdress ="";

    ImageButton btn_back;
    Animation scale_up, scale_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_info);

        //link view with xml
        mConStatusIv=findViewById(R.id.conStatusIv);
        mConStatusTv=findViewById(R.id.conStatusTv);

        varText=(TextView) findViewById(R.id.TV_WifiInfo);

        if (ContextCompat.checkSelfPermission(NetworkInfo.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(NetworkInfo.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(NetworkInfo.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(NetworkInfo.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        btn_back= findViewById(R.id.btn_back);
        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);
    }


    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, DeviceInfo.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(NetworkInfo.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Accord??e", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Permission Refus??e", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (wifiOn==1){
                buildAlertMessageNoGps();
            }
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        requestgps=1;
        builder.setMessage("La localisation semble d??sactiv??e, voulez vous l'activer pour afficher le SSID ?")
                .setCancelable(false)
                .setPositiveButton("Activer", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        requestgps = 0;
                    }
                })
                .setNegativeButton("Passer", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                checkNetworkConnectionStatus();
                if (requestgps==0){
                    statusCheck();
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

    public String getEmoji(int uni){
        return new String(Character.toChars(uni));
    }


    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    private void checkNetworkConnectionStatus() {
        boolean wifiConnected;
        boolean mobileConnected;
        boolean vpnConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if(activeInfo != null && activeInfo.isConnected()){
            int unicode=0x1F604;
            String emoji= getEmoji(unicode);
            String text=emoji;
            wifiConnected = activeInfo.getType()==ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType()==ConnectivityManager.TYPE_MOBILE;
            vpnConnected = activeInfo.getType()==ConnectivityManager.TYPE_VPN;
            if(vpnConnected){
                mConStatusIv.setImageResource(R.drawable.ic_vpn_x128);
                mConStatusTv.setText("Connect?? en VPN "+text);
                wifiOn=1;
            }
            else if (mobileConnected){
                ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);

                NetworkCapabilities nc = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    nc = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                }
                String downSpeed = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    downSpeed = String.valueOf(nc.getLinkDownstreamBandwidthKbps()/1024);
                }
                String upSpeed = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    upSpeed = String.valueOf(nc.getLinkUpstreamBandwidthKbps()/1024);
                }

                mConStatusIv.setImageResource(R.drawable.ic_action_data);
                mConStatusTv.setText("Connect?? en donn??es mobile "+text+"\nDown : "+downSpeed+" Kb/s | Up : "+upSpeed+" Kb/s");
                wifiOn = 0;
            }
            else if(wifiConnected){
                mConStatusIv.setImageResource(R.drawable.ic_baseline_wifi_24);
                mConStatusTv.setText("Connect?? en Wifi "+text);
                wifiOn = 1;
            }

        }
        else{

            int unicode=0x1F622;
            String emoji= getEmoji(unicode);
            String text=emoji;

            mConStatusIv.setImageResource(R.drawable.ic_action_no);
            mConStatusTv.setText("Pas de connexion Internet "+text);
            wifiOn = 5;
        }

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = (WifiInfo) wifiManager.getConnectionInfo();

        String mobileIp = getMobileIPAddress();

        int ip = wifiInfo.getIpAddress();
        String ipAdress = Formatter.formatIpAddress(ip);

        macAdress = getMacAddr();

        String SSID = wifiInfo.getSSID();

        if (wifiOn == 1){
            wifiOn=1;
            MacReveal = 1;
            if (MacReveal == 1){
                ImageButton BTN_WifiOn = (ImageButton) findViewById(R.id.BTN_WifiOn);
                BTN_WifiOn.setVisibility(View.INVISIBLE);
                TextView TV_WifiOn = (TextView) findViewById(R.id.TV_WifiOn);
                String textoff = "";
                TV_WifiOn.setText(textoff);
                TV_WifiOn.setVisibility(View.INVISIBLE);
            }
        }else if(wifiOn == 0){
            ipAdress= mobileIp;
            if (MacReveal == 0){
                macAdress= "Turn on wifi";
                ImageButton BTN_WifiOn = (ImageButton) findViewById(R.id.BTN_WifiOn);
                BTN_WifiOn.setVisibility(View.VISIBLE);
                TextView TV_WifiOn = (TextView) findViewById(R.id.TV_WifiOn);
                String textonwifi = "Click to Turn on Wifi";
                TV_WifiOn.setText(textonwifi);
                TV_WifiOn.setVisibility(View.VISIBLE);
            }else{
                ImageButton BTN_WifiOn = (ImageButton) findViewById(R.id.BTN_WifiOn);
                BTN_WifiOn.setVisibility(View.INVISIBLE);
                TextView TV_WifiOn = (TextView) findViewById(R.id.TV_WifiOn);
                String textoff = "";
                TV_WifiOn.setText(textoff);
                TV_WifiOn.setVisibility(View.INVISIBLE);
            }
            SSID = "Donn??es mobiles activ??es !";
        }else if(wifiOn == 5){
            ipAdress="No Ip";
            SSID = "Wifi d??sactiv?? !";
            if (MacReveal == 1){
                ImageButton BTN_WifiOn = (ImageButton) findViewById(R.id.BTN_WifiOn);
                BTN_WifiOn.setVisibility(View.INVISIBLE);
                TextView TV_WifiOn = (TextView) findViewById(R.id.TV_WifiOn);
                String textoff = "";
                TV_WifiOn.setText(textoff);
                TV_WifiOn.setVisibility(View.INVISIBLE);
            }
        }

        macAdress = getMacAddr();
        if (macAdress.contains(":")){
            macAdress2 = macAdress;
            String info = "IP : " + ipAdress+
                    "\nMAC : " + macAdress+
                    "\nSSID : " + SSID;
            varText=(TextView) findViewById(R.id.TV_WifiInfo);
            varText.setText(info);
        }else{
            macAdress=macAdress2;
            String info = "IP : " + ipAdress+
                    "\nMAC : " + macAdress+
                    "\nSSID : " + SSID;
            varText=(TextView) findViewById(R.id.TV_WifiInfo);
            varText.setText(info);
        }

    }


    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif: all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b: macBytes) {
                    //res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {}
        return "Try to turn on Wifi";
    }


    public void WifiOn(View view) {
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }


    public void Menu_Item_Back(MenuItem item) {
        Intent Menu_Item_Back = new Intent(this, DeviceInfo.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }
}