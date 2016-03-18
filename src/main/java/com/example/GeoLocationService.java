package com.example;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

@Service
public class GeoLocationService {
    private DatabaseReader databaseReader;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private Logger logger;

    @PostConstruct
    public void init() {
        logger.info("PostConstruct initialize databaseReader");
        Resource resource = resourceLoader.getResource("classpath:GeoLite2-City.mmdb");
        try {
            InputStream database = resource.getInputStream();
            databaseReader = new DatabaseReader.Builder(database).build();
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public GeoLocationData getGeoLocation(String ip) {
        CityResponse response = null;
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            response = databaseReader.city(inetAddress);
        }
        catch(GeoIp2Exception|IOException e) {
            logger.error(e.getMessage());
        }
        if (response == null) {
            return new GeoLocationData(false, null);
        } else {
            return new GeoLocationData(true, response);
        }
    }

    @PreDestroy
    public void destroy() {
        logger.info("Destroying GeoLocationService");
        if (databaseReader != null) {
            try {
                databaseReader.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
