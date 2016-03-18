package com.example;

import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UtilsController {

    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private Logger logger;

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
        logger.info("IP Address: " + ipAddress);
        GeoLocationData result = geoLocationService.getGeoLocation(ipAddress);
        return result;
    }
}
