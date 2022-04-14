package com.example.capstone.adapter;

public class ListViewItem {

    private String POIStr;
    private String AddressStr;
    private double POIlat;
    private double POIlon;

    public String getPOIStr() {
        return this.POIStr;
    }

    public void setPOIStr(String title) {
        POIStr = title;
    }

    public String getAddressStr() {
        return this.AddressStr;
    }

    public void setAddressStr(String desc) {
        AddressStr = desc;
    }

    public double getLat() {
        return this.POIlat;
    }

    public void setLat(double lat) {
        POIlat = lat;
    }

    public double getLon() {
        return this.POIlon;
    }

    public void setLon(double lon) {
        POIlon = lon;
    }
}
