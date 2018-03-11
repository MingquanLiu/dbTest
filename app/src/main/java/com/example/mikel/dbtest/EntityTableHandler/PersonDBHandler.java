package com.example.mikel.dbtest.EntityTableHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mikel.dbtest.EntityTable.Event;
import com.example.mikel.dbtest.EntityTable.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Persons table handler
 */

public class PersonDBHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "PersonDBHandler";
    // Database Name
    private static final String DATABASE_NAME = "PersonsManager";
    // Persons table name
    private static final String TABLE_PERSONS = "persons";

    // Persons Table Columns names
    private static final String KEY_PID = "pid";
    private static final String KEY_CONTACT_ID = "contact_id";
    private static final String KEY_PERSON_NAME = "person_name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_EMAIL_ADDRESS = "email_address";

    //Constructor
    public PersonDBHandler(Context context){
        super( context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_PERSONS + "("
                + KEY_PID + " INTEGER PRIMARY KEY," + KEY_CONTACT_ID + " INTEGER,"
                + KEY_PERSON_NAME + " TEXT,"+ KEY_PHONE_NUMBER + " TEXT,"
                + KEY_EMAIL_ADDRESS + " TEXT"
                + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV ) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
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
}
