package com.example;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UserAgentStringController {

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
        GeoLocationData result = geoLocationService.getGeoLocation(ipAddress);
        return result;
    }
}
