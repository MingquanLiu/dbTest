package com.example.mikel.dbtest.EntityTable.Relationship;

/**
 * This is the class for Event Person relationship table
 */

public class PersonPlaceRelationship {
    // This class will only contain the
    private long rid;
    private long personID;
    private long placeID;
    private String type;

    public PersonPlaceRelationship(){
    }
    public PersonPlaceRelationship(long rid, long personID, long placeID, String type){
        this.rid = rid;
        this.personID = personID;
        this.placeID = placeID;
        this.type = type;
    }
    public void setRid(long rid){
        this.rid = rid;
    }

    public void setPersonID(long personID){
        this.personID = personID;
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

    public long getPersonID(){
        return  personID;
    }

    public long getPlaceID(){
        return placeID;
    }

    public String getType(){
        return type;
    }
}
