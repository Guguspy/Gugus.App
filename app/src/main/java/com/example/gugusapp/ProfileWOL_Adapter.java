package com.example.gugusapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.SparseIntArray;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileWOL_Adapter extends RecyclerView.Adapter<ProfileWOL_Adapter.ProfileWOLHolder> {

    private Toast Toast1;

    Animation scale_up, scale_down;

    List<ProfileWolModel> Profil_WOL_List;

    private Context context;

    public ProfileWOL_Adapter(List<ProfileWolModel> ProfilWOL_List, Context context) {
        Profil_WOL_List = ProfilWOL_List;
        this.context=context;

        scale_up = AnimationUtils.loadAnimation(context,R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(context,R.anim.scale_down);
    }

    @NonNull
    @Override
    public ProfileWOL_Adapter.ProfileWOLHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wol_row_item, parent, false);
        ProfileWOLHolder profileWOLHolder = new ProfileWOLHolder(view);
        return profileWOLHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileWOLHolder holder, int position) {
        holder.ProfilTypeImg.setImageResource(Profil_WOL_List.get(position).ImageType);
        holder.NameProfil_WOL.setText(Profil_WOL_List.get(position).NameProfil);
        holder.IPProfil_WOL.setText("IP : " + Profil_WOL_List.get(position).IPProfil);
        holder.MACProfil_WOL.setText("MAC : " + Profil_WOL_List.get(position).MACProfil);
        String Fav= Profil_WOL_List.get(position).FavProfil;
        if (Fav.equals("Fav")){
            holder.FavProfil.setBackgroundResource(R.drawable.favorite_yes_x64);
        }else{
            holder.FavProfil.setBackgroundResource(R.drawable.favorite_x64);
        }

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

                try{
                    Toast1.cancel();
                }catch (Exception e){
                    System.out.println(e);
                }
                Toast1 = Toast.makeText(context, messageToast, Toast.LENGTH_SHORT);
                Toast1.show();

                holder.ProfilTypeImg.startAnimation(scale_up);
                holder.ProfilTypeImg.startAnimation(scale_down);
            }
        });

        holder.DelProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Fav.equals("Fav")){
                    holder.DelProfil.startAnimation(scale_up);
                    holder.DelProfil.startAnimation(scale_down);
                    try{
                        Toast1.cancel();
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    Toast1 = Toast.makeText(context, "Impossible de retirer car en Fav", Toast.LENGTH_SHORT);
                    Toast1.show();
                }else{
                    try{
                        Toast1.cancel();
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    Toast1 = Toast.makeText(context, "Le "+Profil_WOL_List.get(position).TypeProfil.toLowerCase()+" \""+Profil_WOL_List.get(position).NameProfil+"\" a été supprimé", Toast.LENGTH_SHORT);
                    Toast1.show();
                    Profil_WOL_List.remove(position);
                    notifyDataSetChanged();
                }


            }
        });


        holder.FavProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Fav.equals("Fav")){
                    try{
                        Toast1.cancel();
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    Toast1 = Toast.makeText(context, "Retiré des Favoris", Toast.LENGTH_SHORT);
                    Toast1.show();
                    Profil_WOL_List.set(position, new ProfileWolModel(Profil_WOL_List.get(position).ImageType,
                            Profil_WOL_List.get(position).NameProfil,
                            Profil_WOL_List.get(position).IPProfil,
                            Profil_WOL_List.get(position).MACProfil,
                            Profil_WOL_List.get(position).TypeProfil,
                            "NotFav"));
                    notifyDataSetChanged();
                }else if (Fav.equals("NotFav")){
                    try{
                        Toast1.cancel();
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    Toast1 = Toast.makeText(context, "Ajouté aux Favoris", Toast.LENGTH_SHORT);
                    Toast1.show();
                    Profil_WOL_List.set(position, new ProfileWolModel(Profil_WOL_List.get(position).ImageType,
                            Profil_WOL_List.get(position).NameProfil,
                            Profil_WOL_List.get(position).IPProfil,
                            Profil_WOL_List.get(position).MACProfil,
                            Profil_WOL_List.get(position).TypeProfil,
                            "Fav"));
                    notifyDataSetChanged();
                }

                holder.FavProfil.startAnimation(scale_up);
                holder.FavProfil.startAnimation(scale_down);
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
        ImageButton DelProfil, FavProfil;
        CardView WOLCardview;

        public ProfileWOLHolder(@NonNull View itemView) {
            super(itemView);
            NameProfil_WOL = itemView.findViewById(R.id.NameProfil_WOL);
            IPProfil_WOL = itemView.findViewById(R.id.IPProfil_WOL);
            MACProfil_WOL = itemView.findViewById(R.id.MACProfil_WOL);
            ProfilTypeImg = itemView.findViewById(R.id.ProfilTypeImg);
            RelativeLayoutWOLProfil = itemView.findViewById(R.id.RelativeLayoutWOLProfil);
            DelProfil = itemView.findViewById(R.id.DelProfil);
            FavProfil = itemView.findViewById(R.id.FavProfil);
            WOLCardview = itemView.findViewById(R.id.WOLCardview);
        }

    }

}
