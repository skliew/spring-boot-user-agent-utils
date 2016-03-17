package com.example;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class GeoLocationService {
    private DatabaseReader databaseReader;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct initialize databaseReader");
        Resource resource = resourceLoader.getResource("classpath:GeoLite2-City.mmdb");
        try {
            File database = resource.getFile();
            databaseReader = new DatabaseReader.Builder(database).build();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public GeoLocationData getGeoLocation(String ip) {
        CityResponse response = null;
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            response = databaseReader.city(inetAddress);
        }
        catch(GeoIp2Exception|IOException e) {
            System.err.println(e.getMessage());
        }
        if (response == null) {
            return new GeoLocationData(false, null);
        } else {
            return new GeoLocationData(true, response);
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying GeoLocationService");
        if (databaseReader != null) {
            try {
                databaseReader.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
