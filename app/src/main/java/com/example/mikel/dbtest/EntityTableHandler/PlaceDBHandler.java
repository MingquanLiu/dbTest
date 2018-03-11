package com.example.mikel.dbtest.EntityTableHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mikel.dbtest.EntityTable.Person;
import com.example.mikel.dbtest.EntityTable.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikel on 2018/3/7.
 */

public class PlaceDBHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "PlaceDBHandler";
    // Database Name
    private static final String DATABASE_NAME = "PlacesManager";
    // Persons table name
    private static final String TABLE_PLACES = "places";

    // Persons Table Columns names
    private static final String KEY_PID = "pid";
    private static final String KEY_PLACE_NAME = "place_name";
    private static final String KEY_COORDINATE = "coordinate";
    private static final String KEY_STREET_ADDRESS = "street_address";
    private static final String KEY_PLACE_TYPE = "place_type";

    //Constructor
    public PlaceDBHandler(Context context){
        super( context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
                + KEY_PID + " INTEGER PRIMARY KEY," + KEY_PLACE_NAME + " TEXT,"
                + KEY_COORDINATE + " TEXT,"+ KEY_STREET_ADDRESS + " TEXT,"
                + KEY_PLACE_TYPE + " TEXT"
                + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV ) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        // Create tables again
        onCreate(db);
    }

    // Add Place
    public void addPlace(Place place){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_PID,place.getPid());
        values.put(KEY_PLACE_NAME,place.getPlaceName());
        values.put(KEY_COORDINATE,place.getCoordinate());
        values.put(KEY_STREET_ADDRESS,place.getStreetAddress());
        values.put(KEY_PLACE_TYPE,place.getPlaceType());;
        db.insert(TABLE_PLACES, null, values);
        db.close();
    }

    public Place getPlaceByPId(long pid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[] { KEY_PID,
                        KEY_PLACE_NAME, KEY_COORDINATE, KEY_STREET_ADDRESS, KEY_PLACE_TYPE },
                KEY_PID + "=?",
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
        return db.update(TABLE_PLACES, values, KEY_PID + " = ?",
                new String[] { String.valueOf(place.getPid()) });
    }

    // Updating Place Name by pid
    public int updatePlaceNameByPId(String placeName, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, placeName);
        // updating row
        return db.update(TABLE_PLACES, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    // Updating Coordinate by pid
    public int updateCoordinateByPId(String coordinate, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COORDINATE, coordinate);
        // updating row
        return db.update(TABLE_PLACES, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    // Updating Street Address by pid
    public int updateStreetAddressByPId(String streetAddress, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STREET_ADDRESS, streetAddress);
        // updating row
        return db.update(TABLE_PLACES, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    // Updating Place Type by pid
    public int updatePlaceTypeByPId(String placeType, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_TYPE, placeType);
        // updating row
        return db.update(TABLE_PLACES, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }

}
