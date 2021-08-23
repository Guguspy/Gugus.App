package com.example.gugusapp;

public class ListWifiModel {

    int WifiAnalyzerIMG;
    String NameWifi;
    String Ghz_Wifi;
    String Channel_Wifi;
    String BW_Wifi;

    public ListWifiModel(int WifiAnalyzerIMG, String NameWifi, String Ghz_Wifi, String Channel_Wifi, String BW_Wifi) {
        this.WifiAnalyzerIMG = WifiAnalyzerIMG;
        this.NameWifi = NameWifi;
        this.Ghz_Wifi = Ghz_Wifi;
        this.Channel_Wifi = Channel_Wifi;
        this.BW_Wifi = BW_Wifi;
    }

    public String getWifiAnalyzerIMG() {
        return String.valueOf(WifiAnalyzerIMG);
    }

    public void setWifiAnalyzerIMG(int WifiAnalyzerIMG) {
        WifiAnalyzerIMG = WifiAnalyzerIMG;
    }


    public String getNameWifi() {
        return NameWifi;
    }

    public void setNameWifi(String NameWifi) {
        NameWifi = NameWifi;
    }


    public String getMhz_Wifi() {
        return Ghz_Wifi;
    }

    public void setMhz_Wifi(String Mhz_Wifi) {
        this.Ghz_Wifi = Mhz_Wifi;
    }


    public String getChannel_Wifi() {
        return Channel_Wifi;
    }

    public void setChannel_Wifi(String Channel_Wifi) {
        this.Channel_Wifi = Channel_Wifi;
    }


    public String getBW_Wifi() {
        return BW_Wifi;
    }

    public void setBW_Wifi(String BW_Wifi) {
        this.BW_Wifi = BW_Wifi;
    }

}
