package com.example.mikel.dbtest.EntityTable.Relationship;

/**
 * This is the class for Event Person relationship table
 */

public class EventPersonRelationship {
    // This class will only contain the
    private long rid;
    private long personID;
    private long eventID;
    private String type;

    public EventPersonRelationship(){
    }

    public EventPersonRelationship(long rid, long eventID, long personID, String type){
        this.rid = rid;
        this.personID = personID;
        this.eventID = eventID;
        this.type = type;
    }

    public void setRid(long rid){
        this.rid = rid;
    }

    public void setPersonID(long personID){
        this.personID = personID;
    }

    public void setEventID(long eventID){
        this.eventID = eventID;
    }

    public void setType(String type){
        this.type = type;
    }

    public long getRid(){ return rid; }

    public long getPersonID(){
        return  personID;
    }

    public long getEventID(){
        return eventID;
    }

    public String getType(){
        return type;
    }

}
