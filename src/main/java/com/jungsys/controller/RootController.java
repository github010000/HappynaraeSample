package com.jungsys.controller;

import com.jungsys.utils.Loggie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {
    @RequestMapping("/")
    public String root() {
        Loggie.info("##### Root path requested");

        return new String("You have visited Happynarae Sample.");
    }

    @RequestMapping("/sleep")
    public Map<String, Object> apps(@RequestParam Map<String, String> param) {
        String millis = param.get("millis");
        Loggie.info("##### sleep entered...millis=" + millis);

        Map<String, Object> myResult = new HashMap<String, Object>();
        myResult.put("svc_name", "SLEEP");
        myResult.put("result", "0000");
        myResult.put("millis", millis);

        try {
            Thread.sleep(Long.parseLong(millis));
        }
        catch(Exception e) {
            Loggie.error(e);
            myResult.put("result", "-1");
            myResult.put("reason", "Exception");
        }

        Loggie.info("##### the end..." + myResult.toString());
        return myResult;
    }
}
