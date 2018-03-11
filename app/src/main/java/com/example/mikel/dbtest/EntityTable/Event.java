package com.example.mikel.dbtest.EntityTable;

/**
 * Class for SQL DB compensate the work of Personal Graph
 */

public class Event {
    private long eid;
    private long calendarId; // The calendar id
    private String eventName;
    private String type;
    private long startTime;
    private long endTime;
    // Empty Constructor
    public Event(){}
    public Event(long eid, long calendarId, String eventName, String type, long startTime, long endTime){
        this.eid = eid;
        this.calendarId = calendarId;
        this.eventName = eventName;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public long getEid(){
        return eid;
    }
    public long getCalendarId(){
        return calendarId;
    }
    public String getEventName(){
        return eventName;
    }
    public String getType(){
        return type;
    }
    public long getStartTime(){
        return startTime;
    }
    public long getEndTime(){
        return endTime;
    }
    // Update
    public void setEid(long eid){
        this.eid = eid;
    }
    public void setCalendarId(long calendarId){
        this.calendarId = calendarId;
    }
    public void setEventName(String eventName){
        this.eventName = eventName;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setStartTime(long startTime){
        this.startTime = startTime;
    }
    public void setEndTime(long endTime){
        this.endTime = endTime;
    }

}
