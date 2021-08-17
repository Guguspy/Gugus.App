package com.example.gugusapp;

import android.widget.ImageView;

public class ProfileWolModel {

    int ImageType;
    String NameProfil;
    String IPProfil;
    String MACProfil;
    String TypeProfil;
    String FavProfil;

    public ProfileWolModel(int imageType, String nameProfil, String IPProfil, String MACProfil, String TypeProfil, String favProfil) {
        this.ImageType = imageType;
        this.NameProfil = nameProfil;
        this.IPProfil = IPProfil;
        this.MACProfil = MACProfil;
        this.TypeProfil = TypeProfil;
        this.FavProfil = favProfil;
    }

    public int getImageType() {
        return ImageType;
    }

    public void setImageType(int imageType) { ImageType = imageType; }

    public String getNameProfil() {
        return NameProfil;
    }

    public void setNameProfil(String nameProfil) {
        NameProfil = nameProfil;
    }

    public String getIPProfil() {
        return IPProfil;
    }

    public void setIPProfil(String IPProfil) {
        this.IPProfil = IPProfil;
    }

    public String getMACProfil() {
        return MACProfil;
    }

    public void setMACProfil(String MACProfil) {
        this.MACProfil = MACProfil;
    }

    public String getTypeProfil() {
        return TypeProfil;
    }

    public void setTypeProfil(String TypeProfil) {
        this.TypeProfil = TypeProfil;
    }

    public String getFavProfil() {
        return FavProfil;
    }

    public void setFavProfil(String favProfil) {
        FavProfil = favProfil;
    }
}
