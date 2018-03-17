package com.example.mikel.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.mikel.dbtest.EntityTable.Event;
import com.example.mikel.dbtest.EntityTable.Person;
import com.example.mikel.dbtest.EntityTable.Place;
import com.example.mikel.dbtest.EntityTable.Relationship.EventPersonRelationship;
import com.example.mikel.dbtest.EntityTable.Relationship.PersonPlaceRelationship;
import com.example.mikel.dbtest.EntityTable.Relationship.PlaceEventRelationship;

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
        DBHandler dbHandler = new DBHandler(this);
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.e("Insert: ", "Inserting ..");
        Event event1 = new Event(1,77777771, "Fanglin's Birthday Party","A",1111111111,1111111117);
        Event event2 = new Event(2,77777772, "Adam's Moving Day","A",1112111111,1112111117);
        Event event3 = new Event(3,77777773, "Mars's Meeting","M",1112151111,1112151117);

        Person person1 = new Person(4,2222221,"Mingquan Liu","8572049278","mikelmq99@gmail.com");
        Person person2 = new Person(5,2222222,"Fanglin Chen","7777777777","chentc@gmail.com");

        Place place1 = new Place(6,"Logan Airport","355,111","Boston Airport","Transportation");
        Place place2 = new Place(7,"Gorden Library","177,62","100 Institute Rd","Study");



        dbHandler.addEvent(event1);
        dbHandler.addEvent(event2);
        dbHandler.addEvent(event3);

        dbHandler.addPerson(person1);
        dbHandler.addPerson(person2);

        dbHandler.addPlace(place1);
        dbHandler.addPlace(place2);

        EventPersonRelationship epr1 = new EventPersonRelationship(8,event1.getEid(),person1.getPid(),"Participant");
        EventPersonRelationship epr2 = new EventPersonRelationship(9,event3.getEid(),person2.getPid(),"Host");

        PersonPlaceRelationship ppr1 = new PersonPlaceRelationship(10,person1.getPid(),place1.getPid(),"Home");
        PersonPlaceRelationship ppr2 = new PersonPlaceRelationship(11,person2.getPid(),place2.getPid(),"Work");

        PlaceEventRelationship per1 = new PlaceEventRelationship(12,place1.getPid(),event1.getEid(),"Happens");
        PlaceEventRelationship per2 = new PlaceEventRelationship(13,place2.getPid(),event3.getEid(),"Happens");

        dbHandler.addEPRelationship(epr1);
        dbHandler.addEPRelationship(epr2);
        dbHandler.addPPRelationship(ppr1);
        dbHandler.addPPRelationship(ppr2);
        dbHandler.addPERelationship(per1);
        dbHandler.addPERelationship(per2);
        Log.e("Updating: ", "Updating  events 3");
        dbHandler.updateEventNameByEId("Mike's Holiday",event3.getEid());

        Log.e("Updating: ", "Updating  Fanglin's phone number");
        dbHandler.updatePersonPhoneNumberByPId("5555555555",person2.getPid());
        Log.e("Updating: ", "Updating  Mingquan's email address");
        dbHandler.updatePersonEmailAddressByContactId("mliu2@wpi.edu",person1.getContactId());

        Log.e("Updating: ", "Updating  Logan Airport's street address");
        dbHandler.updateStreetAddressByPId("148 Elm St",place1.getPid());
        // Reading all events
        Log.e("Reading: ", "Reading all events..");
        List<Event> events = dbHandler.getAllEvents();

        for (Event event : events) {
            String log = "Id: "+event.getEid() + " ,Calendar ID: " + event.getCalendarId() +
                    " ,Name: " + event.getEventName() + " ,Type: " + event.getType()
                    + " ,Start Time: " + event.getStartTime() + " ,End Time: " + event.getEndTime();
            // Writing Events to log
            Log.e("Event: ", log);
        }
        // Reading all Persons
        Log.e("Reading: ", "Reading all Persons..");
        List<Person> persons = dbHandler.getAllPersons();

        for (Person person : persons) {
            String log = "Id: "+person.getPid() + " ,Contact ID: " + person.getContactId() +
                    " ,Name: " + person.getPersonName() + " ,Phone Number: " + person.getPhoneNumber()
                    + " ,Email Address: " + person.getEmailAddress();
            // Writing Persons to log
            Log.e("Person: ", log);
        }

        // Reading all Places
        Log.e("Reading: ", "Reading all Places..");
        List<Place> places = dbHandler.getAllPlaces();

        for (Place place : places) {
            String log = "Id: "+place.getPid() + " ,Place Name: " + place.getPlaceName() +
                    " ,Coordinate: " + place.getCoordinate() + " ,Street Address: " +
                    place.getStreetAddress() + " ,Type: " + place.getPlaceType();
            // Writing Places to log
            Log.e("Places: ", log);
        }
        Log.e("Updates: ", "Update EPR2 event to event 2..");
        dbHandler.updateEPREventIDByRid(event2.getEid(), epr2.getRid());
        Log.e("Updates: ", "Update PPR2 person to person 1..");
        dbHandler.updatePPRPersonIDByRid(person1.getPid(), ppr2.getRid());

        Log.e("Updates: ", "Update PER2 event to event 2..");
        dbHandler.updatePEREventIDByRid(event2.getEid(), per2.getRid());

        Log.e("Reading: ", "Reading all Relationships..");
        List<EventPersonRelationship> eprList = dbHandler.getAllEPR();
        for (EventPersonRelationship epr : eprList) {
            String log = "Id: "+epr.getRid() + " ,Person ID: " + epr.getPersonID() +
                    " ,Event ID: " + epr.getEventID() + " ,Relationship Type: " +
                    epr.getType();
            // Writing Places to log
            Log.e("Relationships: ", log);
        }

        List<PersonPlaceRelationship> pprList = dbHandler.getAllPPR();
        for (PersonPlaceRelationship ppr : pprList) {
            String log = "Id: "+ppr.getRid() + " ,Person ID: " + ppr.getPersonID() +
                    " ,Place ID: " + ppr.getPlaceID() + " ,Relationship Type: " +
                    ppr.getType();
            // Writing Places to log
            Log.e("Relationships: ", log);
        }

        List<PlaceEventRelationship> perList = dbHandler.getAllPER();
        for (PlaceEventRelationship per : perList) {
            String log = "Id: "+per.getRid() + " ,Place ID: " + per.getPlaceID() +
                    " ,Event ID: " + per.getEventID() + " ,Relationship Type: " +
                    per.getType();
            // Writing Places to log
            Log.e("Relationships: ", log);
        }
    }
}
