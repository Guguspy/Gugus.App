package com.example.gugusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WakeOnLanAdd extends AppCompatActivity {
    Animation scale_up, scale_down;
    float x1,x2,y1,y2;

    RadioButton radioButton;
    RadioGroup RadioGroupChooseType;

    EditText NamePost, IPAdress, MacAdress;
    ImageButton WakeOnLanButtonAdd;

    List<ProfileWolModel> ProfilWOL_List;

    private static final String IPV4_REGEX =
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_on_lan_add);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavViewWakeOnLan);
        bottomNavigationView.setSelectedItemId(R.id.WakeOnLanAdd);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.WakeOnLanAdd:
                        return true;
                    case R.id.WakeOnLanProfil:
                        startActivity(new Intent(getApplicationContext(), WakeOnLan.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        NamePost = findViewById(R.id.NamePost);
        IPAdress = findViewById(R.id.IPAdress);
        MacAdress = findViewById(R.id.MacAdress);

        WakeOnLanButtonAdd = findViewById(R.id.WakeOnLanButtonAdd);

        IPAdress.addTextChangedListener(new TextWatcher() {
            int count=0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                //Check if the text is 6 characters length, and if the user is writing.
                //If that condition is true then add a "-" to the end of the text and set the cursor to the last position
                //Otherwise if the text is 6 characters length, and if the user is deleting.
                //By removing the "-" you're actually removing the las digit as well, then the the cursor is set to the last position

                if (count <= IPAdress.getText().toString().length() && s.length()==3){

                    String numberOnProgress = IPAdress.getText().toString() + ".";
                    IPAdress.setText(numberOnProgress);
                    int lastPosition = IPAdress.getText().length();
                    IPAdress.setSelection(lastPosition);

                }else if (count >= IPAdress.getText().toString().length() && s.length()==3){
                    String numberOnProgress = IPAdress.getText().toString().substring(0,IPAdress.getText().toString().length()-1);
                    IPAdress.setText(numberOnProgress);
                    int pos = IPAdress.getText().length();
                    IPAdress.setSelection(pos);
                }else if (count <= IPAdress.getText().toString().length() && s.length()==7){

                    String numberOnProgress = IPAdress.getText().toString() + ".";
                    IPAdress.setText(numberOnProgress);
                    int lastPosition = IPAdress.getText().length();
                    IPAdress.setSelection(lastPosition);

                }else if (count >= IPAdress.getText().toString().length() && s.length()==7){
                    String numberOnProgress = IPAdress.getText().toString().substring(0,IPAdress.getText().toString().length()-1);
                    IPAdress.setText(numberOnProgress);
                    int pos = IPAdress.getText().length();
                    IPAdress.setSelection(pos);
                }else if (count <= IPAdress.getText().toString().length() && s.length()==9){

                    String numberOnProgress = IPAdress.getText().toString() + ".";
                    IPAdress.setText(numberOnProgress);
                    int lastPosition = IPAdress.getText().length();
                    IPAdress.setSelection(lastPosition);

                }else if (count >= IPAdress.getText().toString().length() && s.length()==9){
                    String numberOnProgress = IPAdress.getText().toString().substring(0,IPAdress.getText().toString().length()-1);
                    IPAdress.setText(numberOnProgress);
                    int pos = IPAdress.getText().length();
                    IPAdress.setSelection(pos);
                }
                //Update the count value at the end
                count = IPAdress.getText().toString().length();
            }
        });

        MacAdress.addTextChangedListener(new TextWatcher() {
            int count=0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                //Check if the text is 6 characters length, and if the user is writing.
                //If that condition is true then add a "-" to the end of the text and set the cursor to the last position
                //Otherwise if the text is 6 characters length, and if the user is deleting.
                //By removing the "-" you're actually removing the las digit as well, then the the cursor is set to the last position

                if (count <= MacAdress.getText().toString().length() && s.length()==2){

                    String numberOnProgress = MacAdress.getText().toString() + ":";
                    MacAdress.setText(numberOnProgress);
                    int lastPosition = MacAdress.getText().length();
                    MacAdress.setSelection(lastPosition);

                }else if (count >= MacAdress.getText().toString().length() && s.length()==2){
                    String numberOnProgress = MacAdress.getText().toString().substring(0,MacAdress.getText().toString().length()-1);
                    MacAdress.setText(numberOnProgress);
                    int pos = MacAdress.getText().length();
                    MacAdress.setSelection(pos);
                }else if (count <= MacAdress.getText().toString().length() && s.length()==5){

                    String numberOnProgress = MacAdress.getText().toString() + ":";
                    MacAdress.setText(numberOnProgress);
                    int lastPosition = MacAdress.getText().length();
                    MacAdress.setSelection(lastPosition);

                }else if (count >= MacAdress.getText().toString().length() && s.length()==5){
                    String numberOnProgress = MacAdress.getText().toString().substring(0,MacAdress.getText().toString().length()-1);
                    MacAdress.setText(numberOnProgress);
                    int pos = MacAdress.getText().length();
                    MacAdress.setSelection(pos);
                }else if (count <= MacAdress.getText().toString().length() && s.length()==8){

                    String numberOnProgress = MacAdress.getText().toString() + ":";
                    MacAdress.setText(numberOnProgress);
                    int lastPosition = MacAdress.getText().length();
                    MacAdress.setSelection(lastPosition);

                }else if (count >= MacAdress.getText().toString().length() && s.length()==8){
                    String numberOnProgress = MacAdress.getText().toString().substring(0,MacAdress.getText().toString().length()-1);
                    MacAdress.setText(numberOnProgress);
                    int pos = MacAdress.getText().length();
                    MacAdress.setSelection(pos);
                }else if (count <= MacAdress.getText().toString().length() && s.length()==11){

                    String numberOnProgress = MacAdress.getText().toString() + ":";
                    MacAdress.setText(numberOnProgress);
                    int lastPosition = MacAdress.getText().length();
                    MacAdress.setSelection(lastPosition);

                }else if (count >= MacAdress.getText().toString().length() && s.length()==11){
                    String numberOnProgress = MacAdress.getText().toString().substring(0,MacAdress.getText().toString().length()-1);
                    MacAdress.setText(numberOnProgress);
                    int pos = MacAdress.getText().length();
                    MacAdress.setSelection(pos);
                }else if (count <= MacAdress.getText().toString().length() && s.length()==14){

                    String numberOnProgress = MacAdress.getText().toString() + ":";
                    MacAdress.setText(numberOnProgress);
                    int lastPosition = MacAdress.getText().length();
                    MacAdress.setSelection(lastPosition);

                }else if (count >= MacAdress.getText().toString().length() && s.length()==14){
                    String numberOnProgress = MacAdress.getText().toString().substring(0,MacAdress.getText().toString().length()-1);
                    MacAdress.setText(numberOnProgress);
                    int pos = MacAdress.getText().length();
                    MacAdress.setSelection(pos);
                }
                //Update the count value at the end
                count = MacAdress.getText().toString().length();
            }
        });

        RadioGroupChooseType = findViewById(R.id.RadioGroupChooseType);

        scale_up = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        loadDataWolProfil();
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
                if(x1<x2){
                    Intent i = new Intent(this, WakeOnLan.class);
                    this.finish();
                    startActivity(i);
                }
                break;
        }
        return false;

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

    private void saveDataWolProfil() {
        SharedPreferences sharedPreferences = getSharedPreferences("WOLPROFIL", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ProfilWOL_List);
        editor.putString("PROFIL", json);
        editor.apply();
    }



    @Override
    public void onBackPressed() {
        Intent Menu_Item_Back = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.finish();
    }


    public void Menu_Item_Back_WakeOnLan(MenuItem item) {
        Intent Menu_Item_Back_WakeOnLan = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(Menu_Item_Back_WakeOnLan);
    }

    boolean verifyIP(CharSequence args)
    {
        if (args == null) return false;

        Matcher matcher = IPv4_PATTERN.matcher(args);
        return matcher.matches();
    }

    public void NewProfilCheck(View view) {
        WakeOnLanButtonAdd.startAnimation(scale_up);
        WakeOnLanButtonAdd.startAnimation(scale_down);

        String ip = IPAdress.getText().toString();
        String mac = MacAdress.getText().toString();
        String Name = NamePost.getText().toString();

        int radioChoice = RadioGroupChooseType.getCheckedRadioButtonId();

        radioButton = findViewById(radioChoice);

        String Type = String.valueOf(radioButton.getText());


        if(Name.isEmpty()){
            NamePost.setError("Name cannot be empty");
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
        }else if (Name.length()<3){
            NamePost.setError("Please enter more than 3 characters");
            Toast.makeText(this, "Please enter more than 3 characters", Toast.LENGTH_SHORT).show();
        }else{
            if (verifyIP(ip)){
                if (mac.length()==17){
                    LayoutInflater inflater = getLayoutInflater();
                    View customToastLayout = inflater.inflate(R.layout.custom_toast_main_activity, findViewById(R.id.root_layout));

                    String messageToast = "Name : "+Name+"\nIP : "+ip+"\nMAC : "+mac+"\nType : "+Type;

                    System.out.println(messageToast);

                    TextView txtMessage = customToastLayout.findViewById(R.id.txt_message);
                    txtMessage.setText(messageToast);
                    txtMessage.setTextColor(Color.GREEN);

                    ImageView imageView = customToastLayout.findViewById(R.id.icon);
                    if(Type.equals("Computer")) {
                        imageView.setImageResource(R.drawable.type_monitor);
                        ProfilWOL_List.add(new ProfileWolModel(R.drawable.type_monitor, Name, ip, mac, Type, "NotFav"));
                    }else if(Type.equals("Laptop")){
                        imageView.setImageResource(R.drawable.type_laptop);
                        ProfilWOL_List.add(new ProfileWolModel(R.drawable.type_laptop, Name, ip, mac, Type, "NotFav"));
                    }

                    Toast mToast = new Toast(getApplicationContext());
                    mToast.setDuration(Toast.LENGTH_SHORT);
                    mToast.setView(customToastLayout);

                    mToast.show();

                    saveDataWolProfil();

                    /*ProfilWOL_List.add(new ProfileWolModel(image, Name, ip, mac, (String) radioButton.getText()));*/
                }else{
                    MacAdress.setError("Check MAC address");
                    Toast.makeText(this, "Check MAC address", Toast.LENGTH_SHORT).show();
                }
            }else{
                IPAdress.setError("Check IP address");
                Toast.makeText(this, "Check IP address", Toast.LENGTH_SHORT).show();
            }
        }

    }
}