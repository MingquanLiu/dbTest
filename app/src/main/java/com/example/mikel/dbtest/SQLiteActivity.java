package com.example.mikel.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.mikel.dbtest.messageontap.database.DBHandler;
import com.example.mikel.dbtest.messageontap.database.Entities.Event;
import com.example.mikel.dbtest.messageontap.database.Entities.GeocodeWrapper;
import com.example.mikel.dbtest.messageontap.database.Entities.Person;
import com.example.mikel.dbtest.messageontap.database.Entities.Place;
import com.example.mikel.dbtest.messageontap.database.Relationships.EventPersonRelationship;
import com.example.mikel.dbtest.messageontap.database.Relationships.PersonPlaceRelationship;
import com.example.mikel.dbtest.messageontap.database.Relationships.PlaceEventRelationship;

import java.util.List;

import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;

/**
 * Created by mikel on 2018/2/24.
 */

public class SQLiteActivity extends Activity
{

    public static class InsertionWrapper extends GeocodeWrapper
    {
        public InsertionWrapper(DBHandler dbh)
        {
            super(dbh);
        }

        public void wrapUp(Place loc)
        {
            db.addPlace(loc);
        }
    }
    @Override
    /**
     * A tester method that verifies the executions of the database.
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //ssetContentView(R.layout.activity_main);
        Log.e("In SQL Activity", "On create");
        DBHandler dbh = new DBHandler(this);
        InsertionWrapper iw = new InsertionWrapper(dbh);
        Place loc = new Place(40.4505480,-79.8998760,"Previous Abode");
        loc.wrap = iw;
        /**
         *Insertion into database.
         **/
        Log.e("Insert: ", "Inserting ..");
        Event event1 = new Event(1,77777771, "Fanglin's Birthday Party","A",1111111111,1111111117);
        Event event2 = new Event(2,77777772, "Adam's Moving Day","A",1112111111,1112111117);
        Event event3 = new Event(3,77777773, "Mars's Meeting","M",1112151111,1112151117);
        Person person1 = new Person(1,2222221,"Mingquan Liu",
                "8572049278","mikelmq99@gmail.com","mingquan@whatsapp","mingquan@facebook");
        Person person2 = new Person(2,2222222,"Fanglin Chen",
                "7777777777","chentc@gmail.com","fanglin@whatsapp","fanglin@facebook");
        EventPersonRelationship epr1 = new EventPersonRelationship(8,(long)event1.getValueByField(KEY_EID),
                (long)person1.getValueByField(KEY_PID),"Participant");
        EventPersonRelationship epr2 = new EventPersonRelationship(9,(long)event3.getValueByField(KEY_EID),
                (long)person2.getValueByField(KEY_PID),"Host");

        PersonPlaceRelationship ppr1 = new PersonPlaceRelationship(10,(long)person1.getValueByField(KEY_PID),
                1,"Home");
        PersonPlaceRelationship ppr2 = new PersonPlaceRelationship(11,(long)person2.getValueByField(KEY_PID),
                1,"Work");

        PlaceEventRelationship per1 = new PlaceEventRelationship(12,1,
                (long)event1.getValueByField(KEY_EID),"Happens");
        PlaceEventRelationship per2 = new PlaceEventRelationship(13,1,
                (long)event3.getValueByField(KEY_EID),"Happens");
        dbh.addEvent(event1);
        dbh.addEvent(event2);
        dbh.addEvent(event3);
//
        dbh.addPerson(person1);
        dbh.addPerson(person2);

        dbh.addPlace(loc);
        dbh.addEPRelationship(epr1);
        dbh.addEPRelationship(epr2);
        dbh.addPERelationship(per1);
        dbh.addPERelationship(per2);
        dbh.addPPRelationship(ppr1);
        dbh.addPPRelationship(ppr2);
        Log.e("Reading: ", "Reading events by time");
        List<Event> eventResult = dbh.getEventsByTime((long)event1.getValueByField(KEY_START_TIME)-1,
                (long)event1.getValueByField(KEY_END_TIME)+1);
        for (Event event : eventResult) {
            String log = "Id: "+event.getValueByField(KEY_EID) + " ,Calendar ID: " + event.getValueByField(KEY_CALENDAR_ID) +
                    " ,Name: " + event.getValueByField(KEY_EVENT_NAME) + " ,Type: " + event.getValueByField(KEY_EVENT_TYPE)
                    + " ,Start Time: " + event.getValueByField(KEY_START_TIME) + " ,End Time: " + event.getValueByField(KEY_END_TIME);
            // Writing Events to log
            Log.e("Event: ", log);
        }


        Log.e("Reading: ", "Reading persons by contactName");
        List<Person> personResult = dbh.getPersonByContactName(PACKAGE_NAME_WHATSAPP,"mingquan");
        for (Person person : personResult) {
            String log = "Id: "+person.getValueByField(KEY_PID) + " ,Contact ID: " + person.getValueByField(KEY_CONTACT_ID) +
                    " ,Name: " + person.getValueByField(KEY_PERSON_NAME) + " ,Phone Number: " + person.getValueByField(KEY_PHONE_NUMBER)
                    + " ,Email Address: " + person.getValueByField(KEY_EMAIL_ADDRESS);
            // Writing Persons to log
            Log.e("Person: ", log);
        }
        Log.e("Updating: ", "Updating  events 3");
        dbh.updateEventNameByEId("Mike's Holiday",3);

        Log.e("Updating: ", "Updating  Fanglin's phone number");
        dbh.updatePersonPhoneNumberByPId("5555555555",2);
        Log.e("Updating: ", "Updating  Mingquan's email address");
        dbh.updatePersonEmailAddressByContactId("mliu2@wpi.edu",2222221);

        Log.e("Updating: ", "Updating  Preferred name of Place");
        dbh.updatePlaceName("148 Elm St",0);

        // Reading all events
        Log.e("Reading: ", "Reading all events..");
        List<Event> events = dbh.getAllEvents();

        for (Event event : events) {
            String log = "Id: "+event.getValueByField(KEY_EID) + " ,Calendar ID: " + event.getValueByField(KEY_CALENDAR_ID) +
                    " ,Name: " + event.getValueByField(KEY_EVENT_NAME) + " ,Type: " + event.getValueByField(KEY_EVENT_TYPE)
                    + " ,Start Time: " + event.getValueByField(KEY_START_TIME) + " ,End Time: " + event.getValueByField(KEY_END_TIME);
            // Writing Events to log
            Log.e("Event: ", log);
        }
//         Reading all Persons
        Log.e("Reading: ", "Reading all Persons..");
        List<Person> persons = dbh.getAllPersons();

        for (Person person : persons) {
            String log = "Id: "+person.getValueByField(KEY_PID) + " ,Contact ID: " + person.getValueByField(KEY_CONTACT_ID) +
                    " ,Name: " + person.getValueByField(KEY_PERSON_NAME) + " ,Phone Number: " + person.getValueByField(KEY_PHONE_NUMBER)
                    + " ,Email Address: " + person.getValueByField(KEY_EMAIL_ADDRESS);
            // Writing Persons to log
            Log.e("Person: ", log);
        }

        Log.e("Reading: ", "Reading all Places..");
        List<Place> places = dbh.getAllPlaces();

        for (Place place : places)
        {
            String log = "Id: "+place.getValueByField(KEY_PLID) + " ,Place Name: " + place.getValueByField(KEY_PLACE_NAME) +
                    " ,Coordinate: " + place.getValueByField(KEY_LAT) + " ," + place.getValueByField(KEY_LNG) + " ,Street Address: " +
                    place.getValueByField(KEY_STREET_ADDRESS) + " ,Type: " + place.getValueByField(KEY_PLACE_TYPE);
            // Writing Places to log
            Log.e("Places: ", log);
        }

        Log.e("Reading: ", "Reading all Relationships..");
        List<EventPersonRelationship> eprList = dbh.getAllEPR();
        for (EventPersonRelationship epr : eprList) {
            String log = "Id: "+epr.getValueByField(KEY_EP_RID) + " ,Person ID: " + epr.getValueByField(KEY_EP_PERSON_ID) +
                    " ,Event ID: " + epr.getValueByField(KEY_EP_EVENT_ID) + " ,Relationship Type: " +
                    epr.getValueByField(KEY_EVENT_PERSON_RELATIONSHIP_TYPE);
            // Writing Places to log
            Log.e("Relationships: ", log);
        }

        List<PersonPlaceRelationship> pprList = dbh.getAllPPR();
        for (PersonPlaceRelationship ppr : pprList) {
            String log = "Id: "+ppr.getValueByField(KEY_PP_RID) + " ,Person ID: " + ppr.getValueByField(KEY_PP_PERSON_ID) +
                    " ,Place ID: " + ppr.getValueByField(KEY_PP_PLACE_ID) + " ,Relationship Type: " +
                    ppr.getValueByField(KEY_PERSON_PLACE_RELATIONSHIP_TYPE);
            // Writing Places to log
            Log.e("Relationships: ", log);
        }

        List<PlaceEventRelationship> perList = dbh.getAllPER();
        for (PlaceEventRelationship per : perList) {
            String log = "Id: "+per.getValueByField(KEY_PE_RID) + " ,Place ID: " + per.getValueByField(KEY_PE_PLACE_ID) +
                    " ,Event ID: " + per.getValueByField(KEY_PE_EVENT_ID) + " ,Relationship Type: " +
                    per.getValueByField(KEY_PLACE_EVENT_RELATIONSHIP_TYPE);
            // Writing Places to log
            Log.e("Relationships: ", log);
        }

    }
}
