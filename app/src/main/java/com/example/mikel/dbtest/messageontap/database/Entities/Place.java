package com.example.mikel.dbtest.messageontap.database.Entities;

import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;
import com.example.mikel.dbtest.messageontap.data.Entity;

import android.os.AsyncTask;
import android.util.Log;


import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import org.json.*;
/**
 *  A comprehensive representation of a place. Relevant information of this Place such as the zip code and the region are derived. The id given by a place is auto_generated by the database when stored, not given in the place.
 * @author Derek
 *
 */
public class Place extends Entity
{
    /*
        The portions of the url for the google api.
     */
    private static final String urlFirstPortion;
    private static final String urlSecondPortion;
    /**
     * The api keys for the google api.
     */
    private static String[] keys;
    /**
     * The counter that keeps track of the number of keys exhausted.
     */
    public static int keyCount;
    /**
     * A preferred means of finalizing the Place construction. If needed, this wrapper must be set immediately after the construction of the Place object.
     */
    public GeocodeWrapper wrap;
    /**
     * An indicator of whether the reverse geocode process is completed.
     */
    public boolean finished;

    static
    {
        keys = new String[]{"AIzaSyAe67qjsHOQomCNyIIyi_UKm5uqNvTGcvA", "AIzaSyDzqYwvSvnHhBAPf0ZisEPCuZWZv2ldry4", "AIzaSyDG4Gjp_mW0T25VA17Jmk5pYJRJUFvnbUA", "AIzaSyDnPcdlEZyqR6Cr2ite9aAvoTMDzDhty_E", "AIzaSyDnfk0csfpKC1G12EUh4BzyuXOoa_B0fKE", "AIzaSyCZ-htBGcdRL3edgKX3XIHbvcH52Z-ITIk", "AIzaSyCmhv5_zalf68jlqsdeZAbwJxzsbCy7U4k", "AIzaSyC7_s8YWKxnaKqswFBp7riTccHxNI3EBoA", "AIzaSyC-a4AUdyCipXHFev5hopOUz_Mo0QVg2Tk"};
        keyCount = 0;
        urlFirstPortion = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
        urlSecondPortion = "&key=";
    }

    /**
     * Create a Place of the given latitude and longitude along with an optional name.
     * @param lat  The latitude.
     * @param lon  The longitude.
     * @param name An optional preferred name of this Place.
     */
    public Place(double lat, double lon, String... name)
    {
        super();
        setFieldValue(KEY_LAT,lat);
        setFieldValue(KEY_LNG,lon);
        setFieldValue(KEY_PLACE_NAME,name[0]);
        ReverseGeocoder rg = new ReverseGeocoder();
        rg.execute(lat,lon);
    }

    /**
     * A preferred way of reconstruction of a Place object retrieved from the database. The inherited map of fields and values would be cloned from this given map.
     * @param fvals The keys as the fields of this Place, the values as the values.
     */
    public Place(Map<String,Object> fvals)
    {
        super();
        for(String holder: fvals.keySet())
            if(!PLACE_KEYS.contains(holder))
                throw new RuntimeException("Invalid Fields for a Place Object");
        getItemMap().putAll(fvals);
    }

    /**
     * A reserved constructor for reconstruction of objects retrieved from the database to avoid duplicate reverse geo-coding process.
     */
    public Place()
    {

    }

    private class ReverseGeocoder extends AsyncTask<Double,Void,Map<String,Object>>
    {
        /**
         * The actions to be executed succeeding the process of reverse geocoding.
         * @param rec All the information obtained from reverse geocoding in its standard format of the constants in the DBConstants.
         */
        protected void onPostExecute(Map<String,Object> rec)
        {
            getItemMap().putAll(rec);
            if(wrap!=null)
                wrap.wrapUp(Place.this);
            finished = true;
        }

        protected Map<String,Object> doInBackground(Double... coor)
        {
            double lat = coor[0];
            double lon = coor[1];
            Map<String,Object> rec;
            try
            {
                rec = interpretLoc(lat,lon);
            }catch(Exception ex)
            {
                if(keyCount<keys.length)
                    keyCount++;
                try
                {
                    rec = interpretLoc(lat,lon);
                } catch(Exception ex2)
                {
                    throw new RuntimeException(ex2);
                }
            }
            return rec;
        }


        /**
         * A helper method that computes relevant geographic information based on the latitude and longitude given through the google map geocoding api. The results are recorded in the given map whose keys, to be filled by this method along with their proper values, are the enumerated types declared in PhyLocation.
         * @param lati The latitude of the location.
         * @param lon The longitude of the location.
         * @return A map containing all the fields and their corresponding values of this Place Object.
         * @throws IOException
         */
        private Map<String,Object> interpretLoc(Double lati, Double lon) throws IOException, JSONException
        {
            Map<String,Object> record = new HashMap<>();
            URL url = new URL(String.format("%s%s,%s%s%s",urlFirstPortion,lati,lon,urlSecondPortion,keys[keyCount]));
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while((line=reader.readLine())!=null)
            {
                json.append(line);
                json.append("\n");
            }
            String resp = json.substring(0,json.length()-1);
            JSONObject loc = new JSONObject(resp).getJSONArray("results").getJSONObject(0);
            JSONArray add = loc.getJSONArray("address_components");
            for(int i=0; i<add.length(); i++)
            {
                JSONObject obj = (JSONObject)add.get(i);
                //The identifier of the piece of information.
                String val = obj.getJSONArray("types").getString(0);
                if(val.equals("street_number"))
                    record.put(KEY_STREET_NUM,obj.getString("long_name"));
                else if(val.equals("route"))
                    record.put(KEY_ROUTE,obj.getString("short_name"));
                else if(val.equals("neighborhood"))
                    record.put(KEY_NEIGHBORHOOD,obj.getString("short_name"));
                else if(val.equals("locality"))
                    record.put(KEY_LOCALITY,obj.getString("short_name"));
                else if(val.equals("administrative_area_level_2"))
                    record.put(KEY_ADMINISTRATIVE2,obj.getString("short_name"));
                else if(val.equals("administrative_area_level_1"))
                    record.put(KEY_ADMINISTRATIVE1,obj.getString("short_name"));
                else if(val.equals("country"))
                    record.put(KEY_COUNTRY,obj.getString("short_name"));
                else if(val.equals("postal_code"))
                    record.put(KEY_ZIP,obj.getString("short_name"));
            }
            record.put(KEY_STREET_ADDRESS,loc.getString("formatted_address"));
            JSONObject geo = loc.getJSONObject("geometry");
            record.put(KEY_PLACE_TYPE,loc.getJSONArray("types").getString(0));
            JSONObject lal = geo.getJSONObject("location");
            record.put(KEY_LAT,lal.getDouble("lat"));
            record.put(KEY_LNG,lal.getDouble("lng"));
            return record;
        }
    }

    public String toString()
    {
        if(containsField(KEY_STREET_ADDRESS))
            Log.v("GeocodingProcess","Process Not Complete");
        return String.format("%s",getValueByField(KEY_STREET_ADDRESS));
    }
}

