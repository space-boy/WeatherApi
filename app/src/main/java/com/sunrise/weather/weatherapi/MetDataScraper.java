package com.sunrise.weather.weatherapi;


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//return data from API, also parse results into model objects for use in the app
public class MetDataScraper {

    private static final String TAG = "MetDataScraper";
    private static final String API_KEY = "YOUR-API-KEY-HERE";
    private static final Uri ENDPOINT  = Uri.parse("http://datapoint.metoffice.gov.uk/public/data/")
                                        .buildUpon().build();

    public byte[] getUrlBytes(String urlSpec) throws IOException {

        URL url = new URL(urlSpec);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + "with: " + urlSpec);
            }

            int bytesRead;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public static String buildRequest(String type, String obs, String what, String dataType, String resource){
        return ENDPOINT.buildUpon()
                .appendPath(type)
                .appendPath(obs)
                .appendPath(what)
                .appendPath(dataType)
                .appendPath(resource)
                .appendQueryParameter("key", API_KEY)
                .build().toString();
    }

    public ArrayList<WeatherReport> getLocalWeather3HourReports(String url, double longitude, double latitude)
            throws IOException, JSONException {

        ArrayList<WeatherReport> mReports = new ArrayList<>();
        //current service does not allow querying with long + lat, so need to get them all and filter...
        int location = obtainLocation(new JSONObject(new String(getUrlBytes(url))),longitude,latitude);

        String detailrequest = ENDPOINT.buildUpon()
                .appendPath("val")
                .appendPath("wxfcs")
                .appendPath("all")
                .appendPath("json")
                .appendPath(String.valueOf(location))
                .appendQueryParameter("res","3hourly")
                .appendQueryParameter("key", API_KEY)
                .build().toString();

        mReports = parseJson(mReports, new String(getUrlBytes(detailrequest)));
        return mReports;
    }

    public double distanceBetweenLocations(double longitude1, double latitude1, double longitude2, double latitude2){
        return (Math.abs(longitude1 - longitude2)+ Math.abs(latitude1 - latitude2));
    }

    private int obtainLocation(JSONObject jsonBody,double longitude, double latitude) throws JSONException {

        double newdistance;
        double distance = 0;
        JSONObject location;

        JSONArray locationArray = jsonBody.getJSONObject("Locations").getJSONArray("Location");
        location = locationArray.getJSONObject(0);

        for(int i = 0;i < locationArray.length();i++){

            JSONObject site = locationArray.getJSONObject(i);

           newdistance  = distanceBetweenLocations(site.getDouble("longitude"),site.getDouble("latitude"),
                                                    longitude,latitude);

            if(newdistance < distance || distance == 0){
                distance = newdistance;
                location = site;
            }
        }
        return location.getInt("id");
    }

    private ArrayList<WeatherReport> parseJson(ArrayList<WeatherReport> mReports, String jsonString) throws JSONException {

        JSONObject jsonBody = new JSONObject(jsonString);

        JSONArray jsonReportArray = jsonBody.getJSONObject("SiteRep").getJSONObject("DV")
                                    .getJSONObject("Location").getJSONArray("Period");
        JSONObject jsonLocation = jsonBody.getJSONObject("SiteRep")
                                          .getJSONObject("DV")
                                          .getJSONObject("Location");

        String locationName = jsonLocation.getString("name");

        for(int i = 0;i < jsonReportArray.length();i++){
            WeatherReport report = new WeatherReport();
            report.setLocation(locationName);
            JSONObject reportObject = jsonReportArray.getJSONObject(i).getJSONArray("Rep").getJSONObject(i);

            report.setWindDir(reportObject.get("D").toString());
            report.setWindGust(Integer.valueOf(reportObject.get("G").toString()));
            report.setHumidity(Integer.valueOf(reportObject.get("H").toString()));
            report.setRainChance(Integer.valueOf(reportObject.get("Pp").toString()));
            report.setRelativeTemperature(Integer.valueOf(reportObject.get("F").toString()));
            report.setTemperature(Integer.valueOf(reportObject.get("T").toString()));
            report.setUv(Integer.valueOf(reportObject.get("U").toString()));
            report.setVisiblity(reportObject.get("V").toString());
            report.setWindSpeed(Integer.valueOf(reportObject.get("S").toString()));
            report.setWeatherCode(Integer.valueOf(reportObject.get("W").toString()));

            mReports.add(report);
        }
        return mReports;
    }
}
