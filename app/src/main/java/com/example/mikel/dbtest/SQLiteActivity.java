package com.example.mikel.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.mikel.dbtest.EntityTable.Event;
import com.example.mikel.dbtest.EntityTable.Person;
import com.example.mikel.dbtest.EntityTable.Place;
import com.example.mikel.dbtest.EntityTableHandler.EventDBHandler;
import com.example.mikel.dbtest.EntityTableHandler.PersonDBHandler;
import com.example.mikel.dbtest.EntityTableHandler.PlaceDBHandler;

import java.util.List;

/**
 * Created by mikel on 2018/2/24.
 */

public class SQLiteActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ssetContentView(R.layout.activity_main);
        Log.e("In SQL Activity", "On create");
        EventDBHandler eventDB = new EventDBHandler(this);
        PersonDBHandler personDBHandler = new PersonDBHandler(this);
        PlaceDBHandler placeDBHandler = new PlaceDBHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.e("Insert: ", "Inserting ..");
        eventDB.addEvent(new Event(1,77777771, "Fanglin's Birthday Party","A",1111111111,1111111117));
        eventDB.addEvent(new Event(2,77777772, "Adam's Moving Day","A",1112111111,1112111117));
        eventDB.addEvent(new Event(3,77777773, "Mars's Meeting","M",1112151111,1112151117));

        personDBHandler.addPerson(new Person(1,2222221,"Mingquan Liu","8572049278","mikelmq99@gmail.com"));
        personDBHandler.addPerson(new Person(2,2222222,"Fanglin Chen","7777777777","chentc@gmail.com"));

        placeDBHandler.addPlace(new Place(1,"Logan Airport","355,111","Boston Airport","Transportation"));
        placeDBHandler.addPlace(new Place(2,"Gorden Library","177,62","100 Institute Rd","Study"));

        Log.e("Updating: ", "Updating  events 3");
        eventDB.updateEventNameByEId("Mike's Holiday",3);

        Log.e("Updating: ", "Updating  Fanglin's phone number");
        personDBHandler.updatePersonPhoneNumberByPId("5555555555",2);
        Log.e("Updating: ", "Updating  Mingquan's email address");
        personDBHandler.updatePersonEmailAddressByContactId("mliu2@wpi.edu",2222221);

        Log.e("Updating: ", "Updating  Logan Airport's street address");
        placeDBHandler.updateStreetAddressByPId("148 Elm St",1);
        // Reading all events
        Log.e("Reading: ", "Reading all events..");
        List<Event> events = eventDB.getAllEvents();

        for (Event event : events) {
            String log = "Id: "+event.getEid() + " ,Calendar ID: " + event.getCalendarId() +
                    " ,Name: " + event.getEventName() + " ,Type: " + event.getType()
                    + " ,Start Time: " + event.getStartTime() + " ,End Time: " + event.getEndTime();
            // Writing Events to log
            Log.e("Event: ", log);
        }
        // Reading all Persons
        Log.e("Reading: ", "Reading all Persons..");
        List<Person> persons = personDBHandler.getAllPersons();

        for (Person person : persons) {
            String log = "Id: "+person.getPid() + " ,Contact ID: " + person.getContactId() +
                    " ,Name: " + person.getPersonName() + " ,Phone Number: " + person.getPhoneNumber()
                    + " ,Email Address: " + person.getEmailAddress();
            // Writing Persons to log
            Log.e("Person: ", log);
        }

        // Reading all Places
        Log.e("Reading: ", "Reading all Places..");
        List<Place> places = placeDBHandler.getAllPlaces();

        for (Place place : places) {
            String log = "Id: "+place.getPid() + " ,Place Name: " + place.getPlaceName() +
                    " ,Coordinate: " + place.getCoordinate() + " ,Street Address: " +
                    place.getStreetAddress() + " ,Type: " + place.getPlaceType();
            // Writing Places to log
            Log.e("Places: ", log);
        }
    }
}
