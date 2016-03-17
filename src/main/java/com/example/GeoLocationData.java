package com.example;


import com.maxmind.geoip2.model.CityResponse;

public class GeoLocationData {
    public final CityResponse data;
    public final boolean result;

    GeoLocationData(boolean result, CityResponse cityResponse) {
        this.result = result;
        this.data = cityResponse;
    }
}
