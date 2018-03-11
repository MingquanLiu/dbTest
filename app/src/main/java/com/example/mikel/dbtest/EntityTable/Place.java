package com.example.mikel.dbtest.EntityTable;

/**
 * Class for SQL DB compensate the work of Personal Graph
 */

public class Place {

    private long pid;
    private String placeName;
    private String coordinate;
    private String streetAddress;
    private String placeType;

    // Empty Constructor
    public Place(){}
    public Place(long pid, String placeName, String coordinate, String streetAddress, String placeType){
        this.pid = pid;
        this.placeName = placeName;
        this.coordinate = coordinate;
        this.streetAddress = streetAddress;
        this.placeType = placeType;
    }

    // Getters
    public long getPid(){
        return pid;
    }
    public String getPlaceName(){
        return placeName;
    }
    public String getCoordinate(){
        return coordinate;
    }
    public String getStreetAddress(){
        return streetAddress;
    }
    public String getPlaceType(){
        return placeType;
    }
    // Update
    public void setPid(long pid){
        this.pid = pid;
    }
    public void setPlaceName(String placeName){
        this.placeName = placeName;
    }
    public void setCoordinate(String coordinate){
        this.coordinate = coordinate;
    }
    public void setStreetAddress(String streetAddress){
        this.streetAddress = streetAddress;
    }
    public void setPlaceType(String placeType){
        this.placeType = placeType;
    }
}
