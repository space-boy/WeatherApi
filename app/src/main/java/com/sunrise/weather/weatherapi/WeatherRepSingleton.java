package com.sunrise.weather.weatherapi;


import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class WeatherRepSingleton {

    private static WeatherRepSingleton sWeatherRepSingleton;

    private ArrayList<WeatherReport> mWeatherReports;

    public static WeatherRepSingleton getWeatherRepSingleton(Context context){
        if(sWeatherRepSingleton == null){
            sWeatherRepSingleton = new WeatherRepSingleton(context);
        }
        return sWeatherRepSingleton;
    }

    private WeatherRepSingleton(Context context){
        mWeatherReports = new ArrayList<>();
    }

    public void AddWeatherReport(WeatherReport wr){
        mWeatherReports.add(wr);
    }

    public ArrayList<WeatherReport> getWeatherReports(){
        return mWeatherReports;
    }

    public WeatherReport getWeatherReport(UUID weatherReportId){
        for(WeatherReport wr : mWeatherReports){
            if(weatherReportId == wr.getWeatherId()){
                return wr;
            }
        }
        return null;
    }

}
