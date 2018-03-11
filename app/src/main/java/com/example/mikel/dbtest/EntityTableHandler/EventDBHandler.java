package com.example.mikel.dbtest.EntityTableHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mikel.dbtest.EntityTable.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * The SQL DB handler for event object
 */

public class EventDBHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "EventDBHandler";
    // Database Name
    private static final String DATABASE_NAME = "EventsManager";
    // Events table name
    private static final String TABLE_EVENTS = "events";

    // Events Table Columns names
    private static final String KEY_EID = "eid";
    private static final String KEY_CALENDAR_ID = "calendar_id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_EVENT_NAME = "event_name";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";

    //Constructor
    public EventDBHandler(Context context){
        super( context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_EID + " INTEGER PRIMARY KEY," + KEY_CALENDAR_ID + " INTEGER,"
                + KEY_EVENT_NAME + " TEXT,"+ KEY_TYPE + " TEXT,"
                + KEY_START_TIME + " INTEGER,"+ KEY_END_TIME + " INTEGER"
                + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV ) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        // Create tables again
        onCreate(db);
    }

    // Add Event
    public void addEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_EID,event.getEid());
        values.put(KEY_CALENDAR_ID,event.getCalendarId());
        values.put(KEY_EVENT_NAME,event.getEventName());
        values.put(KEY_TYPE,event.getType());
        values.put(KEY_START_TIME,event.getStartTime());
        values.put(KEY_END_TIME,event.getEndTime());
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    public Event getEventByEId(long eid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_EID,
                        KEY_CALENDAR_ID, KEY_EVENT_NAME, KEY_TYPE, KEY_START_TIME, KEY_END_TIME }, KEY_EID + "=?",
                new String[] { String.valueOf(eid) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Event event = new Event(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), cursor.getString(2),cursor.getString(3),
                Long.parseLong(cursor.getString(4)), Long.parseLong(cursor.getString(5)));
        cursor.close();
        db.close();
        // return Event
        return event;
    }
    // Get Event by Calendar ID
    public Event getEventByCalendarId(long calendarId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_EID,
                        KEY_CALENDAR_ID, KEY_EVENT_NAME, KEY_TYPE, KEY_START_TIME, KEY_END_TIME }, KEY_CALENDAR_ID + "=?",
                new String[] { String.valueOf(calendarId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Event event = new Event(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), cursor.getString(2),cursor.getString(3),
                Long.parseLong(cursor.getString(4)), Long.parseLong(cursor.getString(5)));
        cursor.close();
        db.close();
        // return CalendarEvent
        return event;
    }

    // Getting All Events
    public List<Event> getAllEvents() {
        List<Event> EventList = new ArrayList<Event>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setEid(Long.parseLong(cursor.getString(0)));
                event.setCalendarId(Long.parseLong(cursor.getString(1)));
                event.setEventName(cursor.getString(2));
                event.setType(cursor.getString(3));
                event.setStartTime(Long.parseLong(cursor.getString(4)));
                event.setEndTime(Long.parseLong(cursor.getString(5)));
                // Adding Event to list
                EventList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return Event list
        return EventList;
    }

    // Updating single Event
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CALENDAR_ID, event.getCalendarId());
        values.put(KEY_EVENT_NAME, event.getEventName());
        values.put(KEY_TYPE, event.getType());
        values.put(KEY_START_TIME, event.getStartTime());
        values.put(KEY_END_TIME, event.getEndTime());
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(event.getEid()) });
    }

    // Updating event's name by its event id
    public int updateEventNameByEId(String eventName, long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, eventName);
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
    }

    // Updating event's type by its event id
    public int updateTypeByEId(String type, long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, type);
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
    }

    // Updating event's start time by its event id
    public int updateStartTimeByEId(long startTime, long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_START_TIME, startTime);
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
    }
    // Updating event's end time by its event id
    public int updateEndTimeByEId(long endTime, long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_END_TIME, endTime);
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
    }

    // Deleting single Event
    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_EID + " = ?",
                new String[] { String.valueOf(event.getEid()) });
        db.close();
    }
    // Deleting single Event
    public void deleteEventByEId(long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
        db.close();
    }


    // Getting CalendarEvents Count
    public int getEventsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }
}
