package com.example.gugusapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WifiList_Adapter extends RecyclerView.Adapter<WifiList_Adapter.WifiListHolder> {

    private Toast Toast1;

    Animation scale_up, scale_down;

    List<ListWifiModel> Wifi__List;

    private Context context;

    public WifiList_Adapter(List<ListWifiModel> Wifi_List, Context context) {
        Wifi__List = Wifi_List;
        this.context=context;

        scale_up = AnimationUtils.loadAnimation(context,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(context,R.anim.scale_down);
    }

    @NonNull
    @Override
    public WifiList_Adapter.WifiListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wifi_row_item, parent, false);
        WifiListHolder WifiListHolder = new WifiListHolder(view);
        return WifiListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WifiListHolder holder, int position) {
        holder.NameWifi.setText(Wifi__List.get(position).NameWifi);
        holder.Ghz_Wifi.setText(Wifi__List.get(position).Ghz_Wifi);
        holder.Channel_Wifi.setText(Wifi__List.get(position).Channel_Wifi);
        holder.BW_Wifi.setText(Wifi__List.get(position).BW_Wifi);
        holder.WifiAnalyzerIMG.setImageResource(Wifi__List.get(position).WifiAnalyzerIMG);


        holder.RelativeLayoutWifiAnalyzer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Toast1 = Toast.makeText(context, "test", Toast.LENGTH_SHORT);
                Toast1.show();*/

                holder.WifiAnalyzerIMG.startAnimation(scale_up);
                holder.WifiAnalyzerIMG.startAnimation(scale_down);
            }
        });

    }

    @Override
    public int getItemCount() {
        try {
            return Wifi__List.size();
        }catch (Exception e){
            System.out.println("Failed : "+ e);
            return 0;
        }
    }


    public class WifiListHolder extends RecyclerView.ViewHolder {

        TextView NameWifi, Ghz_Wifi, Channel_Wifi, BW_Wifi;
        RelativeLayout RelativeLayoutWifiAnalyzer;
        ImageView WifiAnalyzerIMG;

        public WifiListHolder(@NonNull View itemView) {
            super(itemView);
            NameWifi = itemView.findViewById(R.id.NameWifi);
            Ghz_Wifi = itemView.findViewById(R.id.Ghz_Wifi);
            Channel_Wifi = itemView.findViewById(R.id.Channel_Wifi);
            BW_Wifi = itemView.findViewById(R.id.BW_Wifi);
            WifiAnalyzerIMG = itemView.findViewById(R.id.WifiAnalyzerIMG);
            RelativeLayoutWifiAnalyzer = itemView.findViewById(R.id.RelativeLayoutWifiAnalyzer);
        }

    }

}
