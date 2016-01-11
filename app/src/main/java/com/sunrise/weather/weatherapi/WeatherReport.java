package com.sunrise.weather.weatherapi;


import java.util.UUID;

//class to hold weather reports returned from server
public class WeatherReport {

    private int WeatherCode;
    private int uv;
    private int windSpeed;
    private int rainChance;
    private String visiblity;
    private int temperature;
    private int humidity;
    private int windGust;
    private int relativeTemperature;
    private String location;
    private String time;
    private String WindDir;
    private UUID mWeatherId;

    public WeatherReport(){
        mWeatherId = UUID.randomUUID();
    }

    public UUID getWeatherId() {
        return mWeatherId;
    }

    public String getWindDir() {
        return WindDir;
    }

    public void setWindDir(String windDir) {
        WindDir = windDir;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getRainChance() {
        return rainChance;
    }

    public void setRainChance(int rainChance) {
        this.rainChance = rainChance;
    }

    public int getRelativeTemperature() {
        return relativeTemperature;
    }

    public void setRelativeTemperature(int relativeTemperature) {
        this.relativeTemperature = relativeTemperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public String getVisiblity() {
        return visiblity;
    }

    public void setVisiblity(String visiblity) {
        this.visiblity = visiblity;
    }

    public int getWeatherCode() {
        return WeatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        WeatherCode = weatherCode;
    }

    public int getWindGust() {
        return windGust;
    }

    public void setWindGust(int windGust) {
        this.windGust = windGust;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }
}
