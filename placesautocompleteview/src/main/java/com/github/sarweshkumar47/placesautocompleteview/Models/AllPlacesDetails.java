package com.github.sarweshkumar47.placesautocompleteview.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllPlacesDetails {

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("licence")
    private String license;

    @SerializedName("osm_type")
    private String osmType;

    @SerializedName("osm_id")
    private String osmId;

    @SerializedName("boundingbox")
    private ArrayList<String> geoLocation;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lon")
    private String lon;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("class")
    private String classType;

    @SerializedName("type")
    private String type;

    @SerializedName("importance")
    private String importance;

    @SerializedName("icon")
    private String icon;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getOsmType() {
        return osmType;
    }

    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }

    public String getOsmId() {
        return osmId;
    }

    public void setOsmId(String osmId) {
        this.osmId = osmId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public ArrayList<String> getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(ArrayList<String> geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
