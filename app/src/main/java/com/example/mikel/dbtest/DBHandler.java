package com.example.mikel.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mikel.dbtest.EntityTable.Event;
import com.example.mikel.dbtest.EntityTable.Person;
import com.example.mikel.dbtest.EntityTable.Place;
import com.example.mikel.dbtest.EntityTable.Relationship.EventPersonRelationship;
import com.example.mikel.dbtest.EntityTable.Relationship.PersonPlaceRelationship;
import com.example.mikel.dbtest.EntityTable.Relationship.PlaceEventRelationship;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the DB for person and event and person event relationship
 *
 */

public class DBHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBHandler";
    // Database Name
    private static final String DATABASE_NAME = "PG";
    // Persons table name
    private static final String TABLE_PERSONS = "persons";

    // Persons Table Columns names
    private static final String KEY_PID = "pid";
    private static final String KEY_CONTACT_ID = "contact_id";
    private static final String KEY_PERSON_NAME = "person_name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_EMAIL_ADDRESS = "email_address";
    // Events table name
    private static final String TABLE_EVENTS = "events";
    // Events Table Columns names
    private static final String KEY_EID = "eid";
    private static final String KEY_CALENDAR_ID = "calendar_id";
    private static final String KEY_EVENT_TYPE = "event_type";
    private static final String KEY_EVENT_NAME = "event_name";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";

    // Persons table name
    public static final String TABLE_PLACES = "places";

    // Persons Table Columns names
    public static final String KEY_PLID = "plid";
    public static final String KEY_PLACE_NAME = "place_name";
    public static final String KEY_COORDINATE = "coordinate";
    public static final String KEY_STREET_ADDRESS = "street_address";
    public static final String KEY_PLACE_TYPE = "place_type";


    // Events Person table name
    private static final String TABLE_EVENT_PERSON = "event_person";
    // EventPerson Relationship Table Columns names
    private static final String KEY_EP_RID = "ep_rid";
    private static final String KEY_EP_EVENT_ID = "ep_event_id";
    private static final String KEY_EP_PERSON_ID = "ep_person_id";
    private static final String KEY_EVENT_PERSON_RELATIONSHIP_TYPE = "event_person_relationship_type";

    // Person Place table name
    private static final String TABLE_PERSON_PLACE = "person_place";
    // PersonPlace Relationship Table Columns names
    private static final String KEY_PP_RID = "pp_rid";
    private static final String KEY_PP_PLACE_ID = "pp_place_id";
    private static final String KEY_PP_PERSON_ID = "pp_person_id";
    private static final String KEY_PERSON_PLACE_RELATIONSHIP_TYPE = "person_place_relationship_type";

    // Place Event table name
    private static final String TABLE_PLACE_EVENT = "place_event";
    // PlaceEvent Relationship Table Columns names
    private static final String KEY_PE_RID = "pe_rid";
    private static final String KEY_PE_EVENT_ID = "pe_event_id";
    private static final String KEY_PE_PLACE_ID = "pe_place_id";
    private static final String KEY_PLACE_EVENT_RELATIONSHIP_TYPE = "place_event_relationship_type";

    //Constructor
    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_PERSONS_TABLE = "CREATE TABLE " + TABLE_PERSONS + "("
                + KEY_PID + " INTEGER PRIMARY KEY," + KEY_CONTACT_ID + " INTEGER,"
                + KEY_PERSON_NAME + " TEXT,"+ KEY_PHONE_NUMBER + " TEXT,"
                + KEY_EMAIL_ADDRESS + " TEXT"
                + ")";

        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_EID + " INTEGER PRIMARY KEY," + KEY_CALENDAR_ID + " INTEGER,"
                + KEY_EVENT_NAME + " TEXT,"+ KEY_EVENT_TYPE + " TEXT,"
                + KEY_START_TIME + " INTEGER,"+ KEY_END_TIME + " INTEGER"
                + ")";
        String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
                + KEY_PLID + " INTEGER PRIMARY KEY," + KEY_PLACE_NAME + " TEXT,"
                + KEY_COORDINATE + " TEXT,"+ KEY_STREET_ADDRESS + " TEXT,"
                + KEY_PLACE_TYPE + " TEXT"
                + ")";

        String CREATE_EVENT_PERSON_TABLE = "CREATE TABLE " + TABLE_EVENT_PERSON + "("
                + KEY_EP_RID + " INTEGER PRIMARY KEY, " +  KEY_EP_EVENT_ID+ " INTEGER,"
                //REFERENCES "+ PersonDBHandler.TABLE_PERSONS+"("+ KEY_PID + "), "
                + KEY_EP_PERSON_ID + " INTEGER, "+ KEY_EVENT_PERSON_RELATIONSHIP_TYPE + " TEXT, "
                + "FOREIGN KEY ("+ KEY_EP_PERSON_ID
                + ") REFERENCES "+ TABLE_PERSONS+"("+KEY_PID + "), "
                + "FOREIGN KEY ("+ KEY_EP_EVENT_ID + ") REFERENCES "+TABLE_EVENTS
                +"("+KEY_EID + ")"
                + ");";

        String CREATE_PERSON_PLACE_TABLE = "CREATE TABLE " + TABLE_PERSON_PLACE + "("
                + KEY_PP_RID + " INTEGER PRIMARY KEY, " + KEY_PP_PERSON_ID + " INTEGER,"
                //REFERENCES "+ PersonDBHandler.TABLE_PERSONS+"("+ KEY_PID + "), "
                + KEY_PP_PLACE_ID + " INTEGER, "+ KEY_PERSON_PLACE_RELATIONSHIP_TYPE + " TEXT, "
                + "FOREIGN KEY ("+ KEY_PP_PERSON_ID
                + ") REFERENCES "+ TABLE_PERSONS+"("+KEY_PID + "), "
                + "FOREIGN KEY ("+ KEY_PP_PLACE_ID + ") REFERENCES "+ TABLE_PLACES
                +"("+KEY_PLID + ")"
                + ");";
        String CREATE_PLACE_EVENT_TABLE = "CREATE TABLE " + TABLE_PLACE_EVENT + "("
                + KEY_PE_RID + " INTEGER PRIMARY KEY, " + KEY_PE_PLACE_ID + " INTEGER,"
                //REFERENCES "+ PersonDBHandler.TABLE_PERSONS+"("+ KEY_PID + "), "
                + KEY_PE_EVENT_ID + " INTEGER, "+ KEY_PLACE_EVENT_RELATIONSHIP_TYPE + " TEXT, "
                + "FOREIGN KEY ("+ KEY_PE_PLACE_ID
                + ") REFERENCES "+ TABLE_PLACES+"("+KEY_PLID + "), "
                + "FOREIGN KEY ("+ KEY_PE_EVENT_ID + ") REFERENCES "+ TABLE_EVENTS
                +"("+KEY_EID + ")"
                + ");";
        db.execSQL(CREATE_PLACES_TABLE);
        db.execSQL(CREATE_PERSONS_TABLE);
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_EVENT_PERSON_TABLE);
        db.execSQL(CREATE_PERSON_PLACE_TABLE);
        db.execSQL(CREATE_PLACE_EVENT_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV ) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        // Create tables again
        onCreate(db);
    }


    // Add Person
    public void addPerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_PID,person.getPid());
        values.put(KEY_CONTACT_ID,person.getContactId());
        values.put(KEY_PERSON_NAME,person.getPersonName());
        values.put(KEY_PHONE_NUMBER,person.getPhoneNumber());
        values.put(KEY_EMAIL_ADDRESS,person.getEmailAddress());;
        db.insert(TABLE_PERSONS, null, values);
        db.close();
    }

    public Person getPersonByPId(long pid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSONS, new String[] { KEY_PID,
                        KEY_CONTACT_ID, KEY_PERSON_NAME, KEY_PHONE_NUMBER, KEY_EMAIL_ADDRESS }, KEY_PID + "=?",
                new String[] { String.valueOf(pid) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Person person = new Person(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), cursor.getString(2),cursor.getString(3),
                cursor.getString(4));
        cursor.close();
        db.close();
        // return Person
        return person;
    }

    // Get Person by Contact ID
    public Person getPersonByCalendarId(long contactId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSONS, new String[] { KEY_PID,
                        KEY_CONTACT_ID, KEY_PERSON_NAME, KEY_PHONE_NUMBER, KEY_EMAIL_ADDRESS }, KEY_CONTACT_ID + "=?",
                new String[] { String.valueOf(contactId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Person person = new Person(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), cursor.getString(2),cursor.getString(3),
                cursor.getString(4));
        cursor.close();
        db.close();
        // return CalendarEvent
        return person;
    }
    // Getting All Persons
    public List<Person> getAllPersons() {
        List<Person> personList = new ArrayList<Person>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERSONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setPid(Long.parseLong(cursor.getString(0)));
                person.setContactId(Long.parseLong(cursor.getString(1)));
                person.setPersonName(cursor.getString(2));
                person.setPhoneNumber(cursor.getString(3));
                person.setEmailAddress(cursor.getString(4));
                // Adding person to list
                personList.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return Person list
        return personList;
    }

    // Updating single Person
    public int updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTACT_ID, person.getContactId());
        values.put(KEY_PERSON_NAME, person.getPersonName());
        values.put(KEY_PHONE_NUMBER, person.getPhoneNumber());
        values.put(KEY_EMAIL_ADDRESS, person.getEmailAddress());
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_PID + " = ?",
                new String[] { String.valueOf(person.getPid()) });
    }

    // Updating Person Name by Contact ID
    public int updatePersonNameByContactId(String personName, long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_NAME, personName);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_CONTACT_ID + " = ?",
                new String[] { String.valueOf(contactId) });
    }
    // Updating Person Phone Number by Contact ID
    public int updatePersonPhoneNumberByContactId(String phoneNumber, long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHONE_NUMBER, phoneNumber);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_CONTACT_ID + " = ?",
                new String[] { String.valueOf(contactId) });
    }
    // Updating Person Email Address by Contact ID
    public int updatePersonEmailAddressByContactId(String emailAddress, long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL_ADDRESS, emailAddress);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_CONTACT_ID + " = ?",
                new String[] { String.valueOf(contactId) });
    }

    // Updating Person Name by pid
    public int updatePersonNameByPId(String personName, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_NAME, personName);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    // Updating Person Phone Number by pid
    public int updatePersonPhoneNumberByPId(String phoneNumber, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHONE_NUMBER, phoneNumber);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }
    // Updating Person Email Address by pid
    public int updatePersonEmailAddressByPId(String emailAddress, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL_ADDRESS, emailAddress);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    //-*******************************Events*****************************************
    // Add Event
    public void addEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_EID,event.getEid());
        values.put(KEY_CALENDAR_ID,event.getCalendarId());
        values.put(KEY_EVENT_NAME,event.getEventName());
        values.put(KEY_EVENT_TYPE,event.getType());
        values.put(KEY_START_TIME,event.getStartTime());
        values.put(KEY_END_TIME,event.getEndTime());
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    public Event getEventByEId(long eid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_EID,
                        KEY_CALENDAR_ID, KEY_EVENT_NAME, KEY_EVENT_TYPE, KEY_START_TIME, KEY_END_TIME }, KEY_EID + "=?",
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
                        KEY_CALENDAR_ID, KEY_EVENT_NAME, KEY_EVENT_TYPE, KEY_START_TIME, KEY_END_TIME }, KEY_CALENDAR_ID + "=?",
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
        values.put(KEY_EVENT_TYPE, event.getType());
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
        values.put(KEY_EVENT_TYPE, type);
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

    //*********************************Places*************************************************

    public void addPlace(Place place){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_PLID,place.getPid());
        values.put(KEY_PLACE_NAME,place.getPlaceName());
        values.put(KEY_COORDINATE,place.getCoordinate());
        values.put(KEY_STREET_ADDRESS,place.getStreetAddress());
        values.put(KEY_PLACE_TYPE,place.getPlaceType());;
        db.insert(TABLE_PLACES, null, values);
        db.close();
    }

    public Place getPlaceByPId(long pid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[] {KEY_PLID,
                        KEY_PLACE_NAME, KEY_COORDINATE, KEY_STREET_ADDRESS, KEY_PLACE_TYPE },
                KEY_PLID + "=?",
                new String[] { String.valueOf(pid) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Place place = new Place(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),
                cursor.getString(4));
        cursor.close();
        db.close();
        // return Place
        return place;
    }

    // Getting All Places
    public List<Place> getAllPlaces() {
        List<Place> placeList = new ArrayList<Place>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLACES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setPid(Long.parseLong(cursor.getString(0)));
                place.setCoordinate(cursor.getString(1));
                place.setPlaceName(cursor.getString(2));
                place.setStreetAddress(cursor.getString(3));
                place.setPlaceType(cursor.getString(4));
                // Adding person to list
                placeList.add(place);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return Place list
        return placeList;
    }

    // Updating single Place
    public int updatePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, place.getPlaceName());
        values.put(KEY_COORDINATE, place.getCoordinate());
        values.put(KEY_STREET_ADDRESS, place.getStreetAddress());
        values.put(KEY_PLACE_TYPE, place.getPlaceType());
        // updating row
        return db.update(TABLE_PLACES, values, KEY_PLID + " = ?",
                new String[] { String.valueOf(place.getPid()) });
    }

    // Updating Place Name by pid
    public int updatePlaceNameByPId(String placeName, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, placeName);
        // updating row
        return db.update(TABLE_PLACES, values, KEY_PLID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    // Updating Coordinate by pid
    public int updateCoordinateByPId(String coordinate, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COORDINATE, coordinate);
        // updating row
        return db.update(TABLE_PLACES, values, KEY_PLID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    // Updating Street Address by pid
    public int updateStreetAddressByPId(String streetAddress, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STREET_ADDRESS, streetAddress);
        // updating row
        return db.update(TABLE_PLACES, values, KEY_PLID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    // Updating Place Type by pid
    public int updatePlaceTypeByPId(String placeType, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_TYPE, placeType);
        // updating row
        return db.update(TABLE_PLACES, values, KEY_PLID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    //*********************************EP Relationship*************************************************
    public void addEPRelationship(EventPersonRelationship relationship){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_EP_RID, relationship.getRid());
        values.put(KEY_EP_PERSON_ID, relationship.getPersonID());
        values.put(KEY_EP_EVENT_ID, relationship.getEventID());
        values.put(KEY_EVENT_PERSON_RELATIONSHIP_TYPE, relationship.getType());
        db.insert(TABLE_EVENT_PERSON, null, values);
        db.close();
    }

    public EventPersonRelationship getEPRByRId(long rid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_PERSON, new String[] {KEY_EP_RID,
                         KEY_EP_EVENT_ID,KEY_EP_PERSON_ID, KEY_EVENT_PERSON_RELATIONSHIP_TYPE},
                KEY_EP_RID + "=?",
                new String[] { String.valueOf(rid) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        EventPersonRelationship epr = new EventPersonRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return epr;
    }

    public EventPersonRelationship getEPRByEPId(long pid, long eid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_PERSON, new String[] {KEY_EP_RID,
                        KEY_EP_EVENT_ID,KEY_EP_PERSON_ID, KEY_EVENT_PERSON_RELATIONSHIP_TYPE},
                KEY_EP_PERSON_ID + "=? AND "+ KEY_EP_EVENT_ID +"=?",
                new String[] { String.valueOf(pid) }, String.valueOf(eid), null, null);
        if (cursor != null)
            cursor.moveToFirst();
        EventPersonRelationship epr = new EventPersonRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return epr;
    }


    public List<EventPersonRelationship> getAllEPR(){
        List<EventPersonRelationship> EPRList = new ArrayList<EventPersonRelationship>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENT_PERSON;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EventPersonRelationship epr = new EventPersonRelationship();
                epr.setRid(Long.parseLong(cursor.getString(0)));
                epr.setPersonID(Long.parseLong(cursor.getString(2)));
                epr.setEventID(Long.parseLong(cursor.getString(1)));
                epr.setType(cursor.getString(3));
                // Adding person to list
                EPRList.add(epr);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return EPR list
        return EPRList;
    }
    public List<EventPersonRelationship> getEPRByPid(long pid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_PERSON, new String[] {KEY_EP_RID,
                        KEY_EP_EVENT_ID,KEY_EP_PERSON_ID, KEY_EVENT_PERSON_RELATIONSHIP_TYPE},
                KEY_EP_PERSON_ID + "=?",
                new String[] { String.valueOf(pid) }, null, null, null);
        List<EventPersonRelationship> EPRList = new ArrayList<EventPersonRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    EventPersonRelationship epr = new EventPersonRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    EPRList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return EPRList
        return EPRList;
    }

    public List<EventPersonRelationship> getEPRByEid(long eid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_PERSON, new String[] {KEY_EP_RID,
                        KEY_EP_EVENT_ID,KEY_EP_PERSON_ID, KEY_EVENT_PERSON_RELATIONSHIP_TYPE},
                KEY_EP_EVENT_ID + "=?",
                new String[] { String.valueOf(eid) }, null, null, null);
        List<EventPersonRelationship> EPRList = new ArrayList<EventPersonRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    EventPersonRelationship epr = new EventPersonRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    EPRList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return EPRList
        return EPRList;
    }

    // Updating EPR type by rid
    public int updateEPRTypeByRid(String type, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_PERSON_RELATIONSHIP_TYPE, type);
        // updating row
        return db.update(TABLE_EVENT_PERSON, values, KEY_EP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating EPR PersonID by rid
    public int updateEPRPersonIDByRid(long pid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EP_PERSON_ID, pid);
        // updating row
        return db.update(TABLE_EVENT_PERSON, values, KEY_EP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating EPR EventID by rid
    public int updateEPREventIDByRid(long eid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EP_EVENT_ID, eid);
        // updating row
        return db.update(TABLE_EVENT_PERSON, values, KEY_EP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    //*********************************PP Relationship*************************************************

    public void addPPRelationship(PersonPlaceRelationship relationship){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_PP_RID, relationship.getRid());
        values.put(KEY_PP_PERSON_ID, relationship.getPersonID());
        values.put(KEY_PP_PLACE_ID, relationship.getPlaceID());
        values.put(KEY_PERSON_PLACE_RELATIONSHIP_TYPE, relationship.getType());
        db.insert(TABLE_PERSON_PLACE, null, values);
        db.close();
    }

    public PersonPlaceRelationship getPPRByRId(long rid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSON_PLACE, new String[] { KEY_PP_RID,
                        KEY_PP_PERSON_ID, KEY_PP_PLACE_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PP_RID + "=?",
                new String[] { String.valueOf(rid) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        PersonPlaceRelationship epr = new PersonPlaceRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return epr;
    }

    public PersonPlaceRelationship getPPRByEPId(long pid, long plid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSON_PLACE, new String[] { KEY_PP_RID,
                        KEY_PP_PERSON_ID, KEY_PP_PLACE_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PP_PERSON_ID + "=? AND "+ KEY_PP_PLACE_ID+"=?",
                new String[] { String.valueOf(pid) }, String.valueOf(plid), null, null);
        if (cursor != null)
            cursor.moveToFirst();
        PersonPlaceRelationship ppr = new PersonPlaceRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return ppr;
    }


    public List<PersonPlaceRelationship> getAllPPR(){
        List<PersonPlaceRelationship> PPRList = new ArrayList<PersonPlaceRelationship>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERSON_PLACE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PersonPlaceRelationship p = new PersonPlaceRelationship();
                p.setRid(Long.parseLong(cursor.getString(0)));
                p.setPersonID(Long.parseLong(cursor.getString(1)));
                p.setPlaceID(Long.parseLong(cursor.getString(2)));
                p.setType(cursor.getString(3));
                // Adding person to list
                PPRList.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return EPR list
        return PPRList;
    }
    public List<PersonPlaceRelationship> getPPRByPid(long pid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSON_PLACE, new String[] { KEY_PP_RID,
                        KEY_PP_PERSON_ID, KEY_PP_PLACE_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PP_PERSON_ID + "=?",
                new String[] { String.valueOf(pid) }, null, null, null);
        List<PersonPlaceRelationship> EPRList = new ArrayList<PersonPlaceRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    PersonPlaceRelationship epr = new PersonPlaceRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    EPRList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return EPRList
        return EPRList;
    }

    public List<PersonPlaceRelationship> getPPRByPLid(long plid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSON_PLACE, new String[] { KEY_PP_RID,
                        KEY_PP_PERSON_ID, KEY_PP_PLACE_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PP_PLACE_ID + "=?",
                new String[] { String.valueOf(plid) }, null, null, null);
        List<PersonPlaceRelationship> EPRList = new ArrayList<PersonPlaceRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    PersonPlaceRelationship epr = new PersonPlaceRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    EPRList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return EPRList
        return EPRList;
    }

    // Updating PPR type by rid
    public int updatePPRTypeByRid(String type, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_PLACE_RELATIONSHIP_TYPE, type);
        // updating row
        return db.update(TABLE_PERSON_PLACE, values, KEY_PP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating PPR PersonID by rid
    public int updatePPRPersonIDByRid(long pid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PP_PERSON_ID, pid);
        // updating row
        return db.update(TABLE_PERSON_PLACE, values, KEY_PP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating PPR EventID by rid
    public int updatePPRPlaceIDByRid(long plid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PP_PLACE_ID, plid);
        // updating row
        return db.update(TABLE_PERSON_PLACE, values, KEY_PP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    //*********************************PE Relationship*************************************************

    public void addPERelationship(PlaceEventRelationship relationship){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_PE_RID, relationship.getRid());
        values.put(KEY_PE_PLACE_ID, relationship.getPlaceID());
        values.put(KEY_PE_EVENT_ID, relationship.getEventID());
        values.put(KEY_PLACE_EVENT_RELATIONSHIP_TYPE, relationship.getType());
        db.insert(TABLE_PLACE_EVENT, null, values);
        db.close();
    }

    public PlaceEventRelationship getPERByRId(long rid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_EVENT, new String[] { KEY_PE_RID,
                         KEY_PE_PLACE_ID, KEY_PE_EVENT_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PE_RID + "=?",
                new String[] { String.valueOf(rid) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        PlaceEventRelationship epr = new PlaceEventRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return epr;
    }

    public PlaceEventRelationship getPERByPEId(long plid, long eid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_EVENT, new String[] { KEY_PE_RID,
                        KEY_PE_PLACE_ID,KEY_PE_EVENT_ID, KEY_PLACE_EVENT_RELATIONSHIP_TYPE},
                KEY_PE_EVENT_ID + "=? AND "+ KEY_PE_PLACE_ID+"=?",
                new String[] { String.valueOf(eid) }, String.valueOf(plid), null, null);
        if (cursor != null)
            cursor.moveToFirst();
        PlaceEventRelationship ppr = new PlaceEventRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return ppr;
    }


    public List<PlaceEventRelationship> getAllPER(){
        List<PlaceEventRelationship> PERList = new ArrayList<PlaceEventRelationship>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLACE_EVENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PlaceEventRelationship p = new PlaceEventRelationship();
                p.setRid(Long.parseLong(cursor.getString(0)));
                p.setPlaceID(Long.parseLong(cursor.getString(1)));
                p.setEventID(Long.parseLong(cursor.getString(2)));
                p.setType(cursor.getString(3));
                // Adding person to list
                PERList.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return PER list
        return PERList;
    }
    public List<PlaceEventRelationship> getPERByEid(long eid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_EVENT, new String[] { KEY_PE_RID,
                        KEY_PE_PLACE_ID,KEY_PE_EVENT_ID, KEY_PLACE_EVENT_RELATIONSHIP_TYPE},
                KEY_PE_EVENT_ID + "=?",
                new String[] { String.valueOf(eid) }, null, null, null);
        List<PlaceEventRelationship> PERList = new ArrayList<PlaceEventRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    PlaceEventRelationship epr = new PlaceEventRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    PERList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return PERList
        return PERList;
    }

    public List<PlaceEventRelationship> getPERByPLid(long plid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_EVENT, new String[] { KEY_PE_RID,
                         KEY_PE_PLACE_ID,KEY_PE_EVENT_ID, KEY_PLACE_EVENT_RELATIONSHIP_TYPE},
                KEY_PE_PLACE_ID + "=?",
                new String[] { String.valueOf(plid) }, null, null, null);
        List<PlaceEventRelationship> PERList = new ArrayList<PlaceEventRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    PlaceEventRelationship epr = new PlaceEventRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    PERList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return PERList
        return PERList;
    }

    // Updating PER type by rid
    public int updatePERTypeByRid(String type, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_EVENT_RELATIONSHIP_TYPE, type);
        // updating row
        return db.update(TABLE_PLACE_EVENT, values, KEY_PE_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating PER PlaceID by rid
    public int updatePERPlaceIDByRid(long plid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PE_PLACE_ID, plid);
        // updating row
        return db.update(TABLE_PLACE_EVENT, values, KEY_PE_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating PER EventID by rid
    public int updatePEREventIDByRid(long eid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PE_EVENT_ID, eid);
        // updating row
        return db.update(TABLE_PLACE_EVENT, values, KEY_PE_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

}
