package com.sunrise.weather.weatherapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.UUID;

public class ReportDetailFragment extends Fragment {

    private static final String TAG = "ReportDetailFragment";
    private static final String ARG_REPORT_ID = "report_id";
    private WeatherReport mWeatherReport;

    public static ReportDetailFragment newInstance(UUID weatherUuid) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_REPORT_ID,weatherUuid);
        ReportDetailFragment fragment = new ReportDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID reportid = (UUID) getArguments().getSerializable(ARG_REPORT_ID);
        mWeatherReport = WeatherRepSingleton.getWeatherRepSingleton(getActivity()).getWeatherReport(reportid);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_detail_fragment,container,false);

        TextView mLocationTextview = (TextView) v.findViewById(R.id.location_textview);
        TextView mTimeTextview = (TextView) v.findViewById(R.id.time_textview);
        TextView mHumidityTextview = (TextView) v.findViewById(R.id.humidity_textview);
        TextView mRainChanceTextview  = (TextView) v.findViewById(R.id.rain_chance_textview);
        TextView mFeelsLikeTempTextView = (TextView) v.findViewById(R.id.feels_like_temperature_textview);
        TextView mWindSpeedTextView = (TextView) v.findViewById(R.id.wind_speed);
        TextView mWindGustTextView = (TextView) v.findViewById(R.id.wind_gust_speed_textview);
        TextView mWindDirectionTextview = (TextView) v.findViewById(R.id.wind_direction_textview);
        TextView mTemperatureTextView = (TextView) v.findViewById(R.id.temperature_textview_detail);
        TextView mWeatherTypeTextView = (TextView) v.findViewById(R.id.weather_type_textview);

        mTimeTextview.setText(mWeatherReport.getTime());

        mLocationTextview.setText(getResources()
                .getString(R.string.location, String.valueOf(mWeatherReport.getLocation())));
        mHumidityTextview.setText(getResources()
                .getString(R.string.humidity, String.valueOf(mWeatherReport.getHumidity())));
        mRainChanceTextview.setText(getResources()
                .getString(R.string.rain_chance,String.valueOf(mWeatherReport.getRainChance())));
        mFeelsLikeTempTextView.setText(getResources()
                .getString(R.string.feels_temp,String.valueOf(mWeatherReport.getRelativeTemperature())));
        mWindSpeedTextView.setText(getResources()
                .getString(R.string.wind_speed,String.valueOf(mWeatherReport.getWindSpeed())));
        mWindDirectionTextview.setText(getResources()
                .getString(R.string.wind_dir,String.valueOf(mWeatherReport.getWindDir())));
        mWindGustTextView.setText(getResources()
                .getString(R.string.wind_dir,String.valueOf(mWeatherReport.getWindGust())));
        mTemperatureTextView.setText(getResources()
                .getString(R.string.temperature,String.valueOf(mWeatherReport.getTemperature())));
        mWeatherTypeTextView.setText(getResources().
                getString(R.string.weather_type,decodeWeather(mWeatherReport.getWeatherCode())));

        return v;

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
}
