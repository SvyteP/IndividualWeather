package org.example.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_weather")
    private int id;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "weather_status")
    private String weatherStatus;
    @Column(name = "temperature")
    private int temprature;
    @Column(name = "wind_speed")
    private int windSpeed;
    @Column(name = "wind_direction")
    private String windDirection;
    @Column(name = "atmospheric")
    private int atmospheric;

    public Weather() {
    }

    public Weather(String cityName, String weatherStatus, int temprature, int windSpeed, String windDirection, int atmospheric) {
        this.cityName = cityName;
        this.weatherStatus = weatherStatus;
        this.temprature = temprature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.atmospheric = atmospheric;
    }

    @Override
    public String toString() {
        return
                "|id:" + id +"|\n"+
                "|Город:" + cityName + "|\n"+
                "|Погода: " + weatherStatus + "|\n"+
                "|Температура воздуха: " + temprature +" ℃|\n"+
                "|Скорость ветра: " + windSpeed +" м/c|\n"+
                "|Направление ветра: " + windDirection + "|\n" +
                "|Атмосферное давление: " + atmospheric +" мм рт. ст.|";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public int getTemprature() {
        return temprature;
    }

    public void setTemprature(int temprature) {
        this.temprature = temprature;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getAtmospheric() {
        return atmospheric;
    }

    public void setAtmospheric(int atmospheric) {
        this.atmospheric = atmospheric;
    }
}
