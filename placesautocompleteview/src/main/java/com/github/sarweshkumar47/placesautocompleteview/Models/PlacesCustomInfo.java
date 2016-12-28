package com.github.sarweshkumar47.placesautocompleteview.Models;

import java.util.ArrayList;

public class PlacesCustomInfo {

    private String placeName;

    private ArrayList<Double> geoBoundingBox;

    private double latitude;

    private double longitude;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public ArrayList<Double> getGeoBoundingBox() {
        return geoBoundingBox;
    }

    public void setGeoBoundingBox(ArrayList<Double> geoBoundingBox) {
        this.geoBoundingBox = geoBoundingBox;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
