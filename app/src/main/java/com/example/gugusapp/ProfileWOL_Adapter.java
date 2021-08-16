package com.example.gugusapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileWOL_Adapter extends RecyclerView.Adapter<ProfileWOL_Adapter.ProfileWOLHolder> {

    List<ProfileWolModel> Profil_WOL_List;

    private Context context;

    public ProfileWOL_Adapter(List<ProfileWolModel> ProfilWOL_List, Context context) {

        Profil_WOL_List = ProfilWOL_List;
        this.context=context;
    }

    @NonNull
    @Override
    public ProfileWOL_Adapter.ProfileWOLHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wol_row_item, parent, false);
        ProfileWOLHolder profileWOLHolder = new ProfileWOLHolder(view);
        return profileWOLHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileWOL_Adapter.ProfileWOLHolder holder, int position) {

        holder.ProfilTypeImg.setImageResource(Profil_WOL_List.get(position).ImageType);
        holder.NameProfil_WOL.setText(Profil_WOL_List.get(position).NameProfil);
        holder.IPProfil_WOL.setText("IP : " + Profil_WOL_List.get(position).IPProfil);
        holder.MACProfil_WOL.setText("MAC : " + Profil_WOL_List.get(position).MACProfil);

        holder.RelativeLayoutWOLProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NamePost = Profil_WOL_List.get(position).NameProfil;
                String ip = Profil_WOL_List.get(position).IPProfil;
                String mac = Profil_WOL_List.get(position).MACProfil;

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            WakeOnLanSetting.main(new String[]{ip, mac});
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println(e);
                        }
                    }
                });
                thread.start();


                String messageToast = "Envoyé à "+NamePost
                        +"\nIP : "+ip
                        +"\nMAC : "+mac;
                Toast.makeText(context, messageToast, Toast.LENGTH_SHORT).show();
            }
        });

        holder.DelProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profil_WOL_List.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return Profil_WOL_List.size();
    }


    public class ProfileWOLHolder extends RecyclerView.ViewHolder {

        TextView NameProfil_WOL, IPProfil_WOL, MACProfil_WOL;
        ImageView ProfilTypeImg;
        RelativeLayout RelativeLayoutWOLProfil;
        ImageButton DelProfil;

        public ProfileWOLHolder(@NonNull View itemView) {
            super(itemView);
            NameProfil_WOL = itemView.findViewById(R.id.NameProfil_WOL);
            IPProfil_WOL = itemView.findViewById(R.id.IPProfil_WOL);
            MACProfil_WOL = itemView.findViewById(R.id.MACProfil_WOL);
            ProfilTypeImg = itemView.findViewById(R.id.ProfilTypeImg);
            RelativeLayoutWOLProfil = itemView.findViewById(R.id.RelativeLayoutWOLProfil);
            DelProfil = itemView.findViewById(R.id.DelProfil);
        }

    }
}
