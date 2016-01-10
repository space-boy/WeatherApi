package com.sunrise.weather.weatherapi;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocSpecFragment extends Fragment{

    private static final String TAG = "LocSPecFragment";
    private double longitude;
    private double latitude;
    private RecyclerView mRecyclerView;
    private ArrayList<WeatherReport> mWeatherReportList = new ArrayList<>();
    private RecyclerView.Adapter mRepAdapter;

    public static LocSpecFragment newInstance(){
        return new LocSpecFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if ( ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location2 = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(location2 != null){
            longitude = location2.getLongitude();
            latitude = location2.getLatitude();
        }

        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        setRetainInstance(true);
        new GetMetData().execute(longitude,latitude);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.report_list_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.report_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupList();
        return v;
    }

    public void setupList(){

        if(isAdded()) {
            mRepAdapter = new RepAdapter(mWeatherReportList);
            mRecyclerView.setAdapter(mRepAdapter);
        }
    }

    public String decodeWeather(int code){

        switch(code){
            case 0:
                return "Clear Night";
            case 1:
                return "Sunny Day";
            case 2:
                return "Partly Cloudy (night)";
            case 3:
                return "Partly Cloudy (day)";
            case 4:
                return "N/A";
            case 5:
                return "Mist";
            case 6:
                return "Fog";
            case 7:
                return "Cloudy";
            case 8:
                return "Overcast";
            case 9:
                return "Light Rain Shower (night)";
            case 10:
                return "Light Rain Shower (day)";
            case 11:
                return "Drizzle";
            case 12:
                return "Light Rain";
            case 13:
                return "Heavy Rain Shower (night)";
            case 14:
                return "Heavy Rain Shower (day)";
            case 15:
                return "Heavy Rain";
            case 16:
                return "Sleet Shower (night)";
            case 17:
                return "Sleet Shower (day)";
            case 18:
                return "Sleet";
            case 19:
                return "Hail Shower (night)";
            case 20:
                return "Hail Shower (day)";
            case 21:
                return "Hail";
            case 22:
                return "Light Snow Shower (night)";
            case 23:
                return "Light Snow Shower (day)";
            case 24:
                return "Light Snow";
            case 25:
                return "Heavy Snow Shower (night)";
            case 26:
                return "Heavy Snow Shower (day)";
            case 27:
                return "Heavy Snow";
            case 28:
                return "Thunder Shower(night)";
            case 29:
                return "Thunder Shower(day)";
            case 30:
                return "Thunder";


        }
        return "N/A";
    }

    private class RepHolder extends RecyclerView.ViewHolder {

        private TextView mWeatherCode;
        private TextView mTemperature;

        public RepHolder(View itemView) {
            super(itemView);
            mWeatherCode = (TextView) itemView.findViewById(R.id.weather_textview);
            mTemperature = (TextView) itemView.findViewById(R.id.temperature_textview);
        }

        public void bindReport(WeatherReport report){
            mWeatherCode.setText(decodeWeather(report.getWeatherCode()));
            mTemperature.setText(String.valueOf(report.getTemperature()));
        }
    }

    private class RepAdapter extends RecyclerView.Adapter<RepHolder> implements View.OnClickListener{

        ArrayList<WeatherReport> mWeatherReports;

        public RepAdapter(ArrayList<WeatherReport> weatherReps){
            mWeatherReports = weatherReps;
        }

        @Override
        public RepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.rep_list_item,parent,false);
            return new RepHolder(view);
        }

        @Override
        public void onBindViewHolder(RepHolder holder, int position) {
            WeatherReport report = mWeatherReports.get(position);
            holder.bindReport(report);
        }

        @Override
        public int getItemCount() {
            return mWeatherReports.size();
        }

        @Override
        public void onClick(View v) {
            //create new fragment or new activity here
        }
    }


    private class GetMetData extends AsyncTask<Double, Void, ArrayList<WeatherReport>> {

        public GetMetData() {
            super();
        }

        @Override
        protected ArrayList<WeatherReport> doInBackground(Double... params) {
            String url = MetDataScraper.buildRequest("val", "wxfcs", "all", "json", "sitelist");
            try {
                try {
                    return new MetDataScraper().getLocalWeather3HourReports(url,params[0],params[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<WeatherReport> reportList) {
            super.onPostExecute(reportList);
            mWeatherReportList = reportList;

            if(mWeatherReportList != null) {
                mWeatherReportList.size();
                setupList();
            }

        }
    }
}
