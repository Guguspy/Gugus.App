package com.example.gugusapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryInfo extends AppCompatActivity {

    TextView TV_1, TV_2, batteryPercentage, tv_pourcentage;

    private double batteryCapacity;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;

    ImageButton btn_back;
    Animation scale_up, scale_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_info);

        //action bar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setTitle("Battery");
            //set back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            ((ActionBar) actionBar).setDisplayShowHomeEnabled(true);
        }

        //get app context
        android.content.Context context= getApplicationContext();
        //init new intent filter
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //register the broadcast receiver
        context.registerReceiver(mBroadCastReceiver, iFilter);

        //batteryPercentage = findViewById(R.id.battery_percentage);
        TV_1 = findViewById(R.id.textview1);
        TV_2 = findViewById(R.id.textview2);
        tv_pourcentage = findViewById(R.id.tv_pourcentage);
        mProgressBar = findViewById(R.id.pb);

        Object mPowerProfile = null;
        String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try{
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            batteryCapacity = (Double)Class.forName(POWER_PROFILE_CLASS).getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile,"battery.capacity");
        }catch (Exception e){
            e.printStackTrace();
        }

        btn_back= findViewById(R.id.btn_back);

        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);


    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed(); //go to previous activity
        return true;
    }

    private BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String charging_status = "",battery_condition="", power_source="Unplugged";
            //get battery percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            //get battery condition
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            if (health == BatteryManager.BATTERY_HEALTH_COLD){
                battery_condition="Cold";
            }
            if (health == BatteryManager.BATTERY_HEALTH_DEAD){
                battery_condition="Dead";
            }
            if (health == BatteryManager.BATTERY_HEALTH_GOOD){
                battery_condition="Good";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT){
                battery_condition="OverHeat";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
                battery_condition="Over Voltage";
            }
            if (health == BatteryManager.BATTERY_HEALTH_UNKNOWN){
                battery_condition="Unknown";
            }
            if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){
                battery_condition="Unspecified Failure";
            }
            //Get battery temparature in celcius
            int temperature_c = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0))/10;

            //get the battery powe source
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            if(chargePlug == BatteryManager.BATTERY_PLUGGED_USB){
                power_source="USB";
            }
            if(chargePlug == BatteryManager.BATTERY_PLUGGED_AC){
                power_source="AC";
            }
            if(chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS){
                power_source="WIRELESS";
            }

            //get the status of the battery i.e charging/discharging
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING){
                charging_status="Charging";
            }
            if (status == BatteryManager.BATTERY_STATUS_DISCHARGING){
                charging_status="Discharging";
            }
            if (status == BatteryManager.BATTERY_STATUS_FULL){
                charging_status="Full";
            }
            if (status == BatteryManager.BATTERY_STATUS_UNKNOWN){
                charging_status="Unknown";
            }
            if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING){
                charging_status="Not Charging";
            }

            //get battery tech
            String techBattery = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            //get battery voltage
            int voltageBattery = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

            //display the output of battery status

            /*batteryPercentage.setText("Battery Percentage : " + level + "%");*/
            TV_1.setText("Condition : \n" +
                    "Temperature : \n" +
                    "Power source  : \n" +
                    "Charging Status : \n" +
                    "Type : \n" +
                    "Voltage : \n" +
                    "Capacity : ");
            TV_2.setText(battery_condition+"\n"+
                    ""+temperature_c+""+(char)0x00B0+"\n"+
                    ""+power_source+"\n"+
                    ""+charging_status+"\n"+
                    ""+techBattery+"\n"+
                    ""+voltageBattery+"\n"+
                    ""+batteryCapacity +" mAh");

            int levels = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);

            float percentage = levels/(float)scale;

            //update
            mProgressStatus = (int)((percentage)*100);

            tv_pourcentage.setText(""+level+" %");

            //display the battery charged % in progress bar
            mProgressBar.setProgress(mProgressStatus);

        }
    };

    public void backhome(View view) {
        btn_back.startAnimation(scale_up);
        btn_back.startAnimation(scale_down);
        Intent BTN_Back = new Intent(BatteryInfo.this, MainActivity.class);
        this.finish();
        startActivity(BTN_Back);
    }
}