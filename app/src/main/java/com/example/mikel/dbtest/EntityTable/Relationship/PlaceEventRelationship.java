package com.example.mikel.dbtest.EntityTable.Relationship;

import com.example.mikel.dbtest.EntityTable.Place;

/**
 * This is the class for Event Person relationship table
 */

public class PlaceEventRelationship {
    // This class will only contain the
    private long rid;
    private long eventID;
    private long placeID;
    private String type;

    public PlaceEventRelationship(){

    }
    public PlaceEventRelationship(long rid, long placeID, long eventID, String type){
        this.rid = rid;
        this.eventID = eventID;
        this.placeID = placeID;
        this.type = type;
    }

    public void setRid(long rid){
        this.rid = rid;
    }
    public void setEventID(long eventID){
        this.eventID = eventID;
    }

    public void setPlaceID(long placeID){
        this.placeID = placeID;
    }

    public void setType(String type){
        this.type = type;
    }

    public long getRid(){
        return rid;
    }

    public long getEventID(){
        return eventID;
    }

    public long getPlaceID(){
        return placeID;
    }

    public String getType(){
        return type;
    }
}
