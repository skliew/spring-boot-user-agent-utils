package com.example;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAgentStringController {

    @RequestMapping(path="/ua")
    public UserAgent uas(@RequestParam(value="uas", defaultValue="") final String uas, @RequestHeader("User-Agent") final String headerUas) {
        String userAgentString = uas.isEmpty() ? headerUas : uas;
        UserAgent userAgent = new UserAgent(userAgentString);
        return userAgent;
    }
}
