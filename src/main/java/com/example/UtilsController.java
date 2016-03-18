package com.example;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UtilsController {

    @Autowired
    GeoLocationService geoLocationService;

    @RequestMapping(path="/ua")
    public UserAgent uas(@RequestParam(value="uas", defaultValue="") final String uas,
                         @RequestHeader(value="User-Agent", defaultValue="") final String headerUas) {
        String userAgentString = uas.isEmpty() ? headerUas : uas;
        UserAgent userAgent = new UserAgent(userAgentString);
        return userAgent;
    }

    @RequestMapping(path="/geolocation")
    public GeoLocationData geolocation(HttpServletRequest request,
                                    @RequestParam(value="ip", defaultValue="") final String ipParam) {
        String ipAddress = ipParam.isEmpty()? request.getRemoteAddr() : ipParam;
        System.out.println("IP " + ipAddress);
        GeoLocationData result = geoLocationService.getGeoLocation(ipAddress);
        return result;
    }
}
